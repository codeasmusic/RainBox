package user.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IOUtils;

import com.hbase.HBaseOperation;

import red5.video.RainBox.ConverBegin;
import red5.video.filepath.ClassLoaderUtil;
import user.service.UploadSuccessService;

public class StartTranscodeingServiceImpl implements UploadSuccessService {
	private Configuration configuration;
	private FileSystem hdfs;
	private String local_MD5;
	private String local_useremail;
	private HBaseOperation hbaseoperation;
	
	StartTranscodeingServiceImpl(){
		configuration = new Configuration();
		configuration.set("fs.default.name", "hdfs://192.168.1.250:9000");
		hbaseoperation = new HBaseOperation();
		try {
			hdfs = FileSystem.get(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void StartTranscodeing(String MD5,String useremail) {
		this.local_MD5 = MD5;
		this.local_useremail = useremail;
		String othersfolder = null;
		String aviname = local_MD5 + ".avi";
		String mp4name = local_MD5 + ".mp4";
		
		List<String> rowKey = new ArrayList<String>();
		ResultScanner rs = hbaseoperation.getRecordBySubRowkey("file", local_useremail + "_" + local_MD5);
		for(Result r : rs){
			for(Cell c : r.rawCells()){
				rowKey.add(Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength()));
			}
		}
		
		if(checkStatus()){
			setUsedCapacity(rowKey);
			System.out.println("------------秒传--------------");
			return;
		}
		try {
			othersfolder = ClassLoaderUtil.getExtendResource("../others/")
					.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		othersfolder = othersfolder.substring(6, othersfolder.lastIndexOf("/"))
				+ "/";
		othersfolder = othersfolder.replace("%20", " ");
		othersfolder = othersfolder.replace("/", "\\\\");
		othersfolder = othersfolder + aviname;
		System.out.println("othersfolder:"+othersfolder);
		/*开始转码*/
		ConverBegin cb = new ConverBegin(aviname);
		if(cb.transCode()){
			System.out.println("转码成功！");
			cb = null;
		}
		
		System.out.println("aviname:" + aviname);
		/*建立avi文件对象，开始往hadoop传输avi文件*/
		File avifile = new File(othersfolder);
		try {
			FSDataOutputStream out = hdfs.create(new Path("/upload/" + local_MD5 + ".avi"));
			InputStream fis = new FileInputStream(avifile);
			IOUtils.copyBytes(fis, out, 4096, true);
			fis.close();
			out.close();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* 开始传MP4文件 */
		String mp4folder = null;
		try {
			mp4folder = ClassLoaderUtil.getExtendResource("../flv/")
					.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} // flv视频的目录
		String mp4path = mp4folder.substring(6, mp4folder.lastIndexOf("/"))
				+ "/";
		mp4path = mp4path.replace("%20", " ");
		mp4path = mp4path.replace("/", "\\\\");
		String mp4file = mp4path + mp4name;
		File mp4 = new File(mp4file);
		try {
			FSDataOutputStream out2 = hdfs.create(new Path("/upload/" + local_MD5 + ".mp4"));
			InputStream fis2 = new FileInputStream(mp4);
			IOUtils.copyBytes(fis2, out2, 4096, true);
			fis2.close();
			out2.close();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*为每条该文件的status设定为1,表明文件已经写入hadoop*/
		for(String rk : rowKey){
			hbaseoperation.addRecord("file", rk, "info", "status", "1");
		}
		
		setUsedCapacity(rowKey);
		
		/*删除本地缓存*/
		if(mp4.delete()){
			System.out.println("文件" + mp4name + "已删除");
		}
		
		if(avifile.delete()){
			System.out.println("文件" + aviname + "已删除");
		}
		System.out.println("-------------删除本地缓存成功----------------");
	}
	/*检查有没有传进hadoop*/
	public boolean checkStatus(){
		/*注意转码是需要时间的，如果此时需要用户在此传输，可能会得到两个status都为1的结果*/
		ResultScanner rs = hbaseoperation.getRecordBySubRowkey("file", local_useremail + "_" + local_MD5);
		
		String status;
		for(Result r : rs){
			status = Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("status")));
			if(status.contains("1")){
				return true;
			}
		}
		
		return false;
	}
	
	public void setUsedCapacity(List<String> rowKey){
		Result getFileSize, getUsedSpace;
		getFileSize = hbaseoperation.getRecord("file", rowKey.get(0));
		String size, usedCapacity;
		
		size = Bytes.toString(getFileSize.getValue(Bytes.toBytes("info"), Bytes.toBytes("size")));
		
		double number = Double.valueOf(size.substring(0, size.length()-1));
		String unit = size.substring(size.length()-1, size.length());
		
		if(unit.equals("K"))
			number = number / (1024*1024);
		else if(unit.equals("M"))
			number = number / 1024;
		
		getUsedSpace = hbaseoperation.getRecord("user", local_useremail);
		usedCapacity = Bytes.toString(getUsedSpace.getValue(Bytes.toBytes("pan"), Bytes.toBytes("usedCapacity")));
		double sum = number + Double.valueOf(usedCapacity);
		String used = String.format("%.2f", sum);
		hbaseoperation.addRecord("user", local_useremail, "pan", "usedCapacity", used);
	}
}







