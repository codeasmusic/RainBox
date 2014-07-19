package user.implementation;

import com.hbase.HBaseOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import red5.video.filepath.ClassLoaderUtil;

import user.service.UploadFileService;

public class UploadFileServiceImpl implements UploadFileService {
	Configuration configuration;
	FileSystem hdfs;
	/* 数据库初始化 */
	HBaseOperation hbaseoperation;
	private Map<String, String> localmap;
	private String rowkey;
	/* 文件上传状态，0为未成功上传，1为已经成功上传 */
	private String status = "0";

	public UploadFileServiceImpl() {
		System.out.println("初始化实现类参数ing...");
		configuration = new Configuration();
		/*设置服务器信息*/
		configuration.set("fs.default.name", "hdfs://192.168.1.250:9000");
		try {
			hdfs = FileSystem.get(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 文件上传实现函数，文件信息通过第一个参数传递， 上传动作的完成需要取得的是文件流，文件存储路径(即path)
	 */
	public String uploadFileByPath(Map<String, String> fileInfo, InputStream in) {
		this.localmap = fileInfo;
		/* 
		 * 检查文件是否存在 存在返回Exist 不存在返回notExist
		 */
		if (uploadFileCheck(localmap)) {
			/* 文件存在，插入数据 */
			this.status = "1";
			InsertRecord();
			return "1";
		}
		/*储存others文件夹的路径*/
		String othersfolder = null;
		/*传到服务器的avi文件名，已经使用MD5命名了*/
		String name = localmap.get("MD5") + ".avi";
		try {
			othersfolder = ClassLoaderUtil.getExtendResource("../others/")
					.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		othersfolder = othersfolder.substring(6, othersfolder.lastIndexOf("/"))
				+ "/" + name;
		othersfolder = othersfolder.replace("%20", " ");
		othersfolder = othersfolder.replace("/", "\\\\");
		/*创建avi文件*/
		File cachfile = new File(othersfolder);
		try {
			FileOutputStream fos = new FileOutputStream(cachfile);
			byte[] buffer = new byte[4096];
			int read = 0;
			while((read = in.read(buffer)) != -1){
				fos.write(buffer, 0, read);
			}
			in.close();
			fos.close();
		} catch (FileNotFoundException e) {
			System.out.println("===========找不到该文件================");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("===========IO流读写操作出错================");
			e.printStackTrace();
		}

	
	    /*文件成功上传到服务器之后，插入记录*/
		this.status = "0";
		InsertRecord();
		return "0";
	}

	/* 先检索数据库查看有没有相同的文件，存在则返回Exist，不存在返回notExist */
	public boolean uploadFileCheck(Map<String, String> fileInfo) {
		System.out.println("正在初始化数据库链接...");
		hbaseoperation = new HBaseOperation();
		System.out.println("正在扫描数据库...");
		System.out.println("要扫描的MD5:" + localmap.get("MD5"));
		ResultScanner rs = hbaseoperation.getRecordBySubRowkey("file",
				localmap.get("MD5"));
		boolean isExist = checkStatus(rs);

		return isExist;
	}

	/**************** 数据库操作(在文件上传之前进行插入操作，但是status置为0) **************************/
	public void InsertRecord() {
		System.out.println("-------------------------------------------------------------");
		System.out.println("插入数据库ing...");
		hbaseoperation = new HBaseOperation();
		/* 获取数据 */
		rowkey = localmap.get("useremail") + "_" + localmap.get("MD5") + "_"
				+ localmap.get("uploadtime");
		hbaseoperation.addRecord("file", rowkey, "info", "fileName",
				localmap.get("uploadfileFileName"));
		hbaseoperation.addRecord("file", rowkey, "info", "folderId",
				localmap.get("upperFolderId"));
		hbaseoperation.addRecord("file", rowkey, "info", "share",
				localmap.get("share"));
		hbaseoperation.addRecord("file", rowkey, "info", "size",
				localmap.get("size"));
		
		hbaseoperation.addRecord("file", rowkey, "tag", "tag1",
				localmap.get("tag1"));
		hbaseoperation.addRecord("file", rowkey, "tag", "tag2",
				localmap.get("tag2"));
		hbaseoperation.addRecord("file", rowkey, "tag", "tag3",
				localmap.get("tag3"));
		hbaseoperation.addRecord("file", rowkey, "tag", "tag4",
				localmap.get("tag4"));
		hbaseoperation.addRecord("file", rowkey, "tag", "tag5",
				localmap.get("tag5"));
		System.out.println("插入数据库的statuts:" + status);
		hbaseoperation.addRecord("file", rowkey, "info", "status", status);
	}

	/************* 设置status ********************/
	/* 
	 * */
	public void setStatus() {
		hbaseoperation = new HBaseOperation();
		hbaseoperation.addRecord("file", rowkey, "info", "status", status);
	}

	/************* 查询status ********************/
	/*
	 * 参数：ResultScanner 返回值:rs内，status全部为0 返回notExist rs内，status存在1，返回值为Exist
	 */
	public boolean checkStatus(ResultScanner rs) {
		byte[] f = Bytes.toBytes("info");
		byte[] q = Bytes.toBytes("status");
		int r_time = 0;
		for (Result r : rs) {
			if (Bytes.toString(r.getValue(f, q)).contains("1")) {
				r_time++;
			}
		}
		if (r_time > 0) {
			return true;
		}
		return false;
	}
	/*************保存上传操作日志********************/
	public void saveUploadLog(String email,String fileName){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String opTime = df.format(new Date());
		String rowkey = email + "_" + opTime;
		String opType = "上传";
		String opObject = fileName;
		hbaseoperation = new HBaseOperation();
		hbaseoperation.addRecord("log", rowkey, "content", "opType", opType);
		hbaseoperation.addRecord("log", rowkey, "content", "opObject", opObject);
	}
}
