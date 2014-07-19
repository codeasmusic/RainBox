package user.implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import com.hbase.HBaseOperation;
import user.service.UserFilesService;

public class UserFilesServiceImpl implements UserFilesService {
	private HBaseOperation hbaseOp = new HBaseOperation();
	private Configuration configuration;
	private FileSystem hdfs;
	private String useremail;
	
	UserFilesServiceImpl(){
		configuration = new Configuration();
		configuration.set("fs.default.name", "hdfs://192.168.1.250:9000");
		
		try {
			hdfs = FileSystem.get(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean DeleteFile(String MD5, String fileName, String userEmail, String folderId) {
		/*取出数据：MD5,useremail*/
		this.useremail = userEmail;
		try {
			DeleteFileRecord(MD5, fileName, userEmail, folderId);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	public boolean ModifyFileName(String useremail, String MD5, String NewName) {
		String localRowKey = null;
		ResultScanner rs;
		rs = hbaseOp.getRecordBySubRowkey("file", useremail + "_" + MD5);
		for(Result r : rs){
			for(Cell c : r.rawCells()){
				localRowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
			}
		}
		boolean success = hbaseOp.addRecord("file", localRowKey, "info", "fileName", NewName);
		return success;
	}

	/******************数据库操作******************************/
	public void DeleteFileRecord(String MD5, String fileName, 
			String userEmail, String folderId) throws IOException{
		
		String[] qualifiers = {"fileName", "folderId"};
		String[] values = {fileName, folderId};
		System.out.println(MD5+"-"+fileName+"-"+userEmail+"-"+folderId);
		/*取出包含"useremail_MD5"的记录*/
		ResultScanner rs = hbaseOp.getRecordByRowkeyAndColumn("file", "info", 
				useremail+"_"+MD5, qualifiers, values, true);
		
		/*从记录中取出rowKey*/
		List<String> rowKey = new ArrayList<String>();
		String key = null;
		for(Result r : rs){
			for(Cell c : r.rawCells()){
				key = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
			}
			rowKey.add(key);
		}
		System.out.println("key------------------------------------" + key);
		setUsedCapacity(rowKey, userEmail);
		/*根据rowkey删除文件记录*/
		hbaseOp.delRecord("file", rowKey);
		/* 检查数据库中是否还有该文件，通过MD5
		 * 如果记录为空，则证明该文件已被所有用户删除，从物理磁盘上删去*/
		ResultScanner rss = hbaseOp.getRecordBySubRowkey("file", MD5);
		/*i为0时，证明没有这条记录*/
		int i = 0;
		for(Result r : rss){
			if(!r.isEmpty()){
				i++;
			}
		}
		if( i == 0 ){
			Path delFile = new Path("/upload/" + MD5 );
			hdfs.delete(delFile,false);
		}
	}
	
	public void setUsedCapacity(List<String> rowKey, String userEmail){
		Result getFileSize, getUsedSpace;
		System.out.println("--------------------------------------------"+rowKey.get(0));
		getFileSize = hbaseOp.getRecord("file", rowKey.get(0));
		String size, usedCapacity;
		
		size = Bytes.toString(getFileSize.getValue(Bytes.toBytes("info"), Bytes.toBytes("size")));
		
		double number = Double.valueOf(size.substring(0, size.length()-1));
		String unit = size.substring(size.length()-1, size.length());
		
		if(unit.equals("K"))
			number = number / (1024*1024);
		else if(unit.equals("M"))
			number = number / 1024;
		
		getUsedSpace = hbaseOp.getRecord("user", userEmail);
		usedCapacity = Bytes.toString(getUsedSpace.getValue(Bytes.toBytes("pan"), Bytes.toBytes("usedCapacity")));
		double left = Double.valueOf(usedCapacity) - number;
		
		String used = String.format("%.3f", left);
		hbaseOp.addRecord("user", userEmail, "pan", "usedCapacity", used);
	}

	//czh
	public String getFilesAndFolders(String email, String folderId) {
		JSONObject jsonObj;
		JSONArray fileArray = new JSONArray();
		JSONArray folderArray = new JSONArray();
		String rowKey;
		String arr[];
		String[] qualifiers = {"folderId"};
		String[] values = {folderId};
		
		ResultScanner fileRS = hbaseOp.getRecordByRowkeyAndColumn("file", "info", 
				email, qualifiers, values, false);
		for(Result r : fileRS){
				jsonObj = new JSONObject();
				jsonObj.element("type", "file");
				jsonObj.element("fileName", Bytes.toString(
						r.getValue(Bytes.toBytes("info"), Bytes.toBytes("fileName"))));
				jsonObj.element("size", Bytes.toString(
						r.getValue(Bytes.toBytes("info"), Bytes.toBytes("size"))));
				jsonObj.element("share", Bytes.toString(
						r.getValue(Bytes.toBytes("info"), Bytes.toBytes("share"))));
				
				for(Cell c : r.rawCells()){
					rowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
					arr = rowKey.split("_");
					jsonObj.element("md5", arr[1]);
					jsonObj.element("uploadTime", arr[2]);
				}
				fileArray.add(jsonObj);
		}
		
		ResultScanner folderRS = hbaseOp.getRecordBySubRowkey("folder", folderId);
		boolean flag = false;
		for(Result r : folderRS){
				jsonObj = new JSONObject();
				for(Cell c : r.rawCells()){
					rowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
					arr = rowKey.split("_");
					if(arr[1].contains(folderId)){
						flag = true;
						break;
					}
					jsonObj.element("folderId", arr[1]);
				}
				if(flag){
					flag = false;
					continue;
				}
				
				jsonObj.element("type", "folder");
				jsonObj.element("folderName", Bytes.toString(
						r.getValue(Bytes.toBytes("info"), Bytes.toBytes("folderName"))));
				jsonObj.element("setTime", Bytes.toString(
						r.getValue(Bytes.toBytes("info"), Bytes.toBytes("setTime"))));
				
				folderArray.add(jsonObj);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{all:");
		
		if(!fileArray.isEmpty())
			sb.append(fileArray);
		
		if(!folderArray.isEmpty())
			sb.append(folderArray);
		
		if( fileArray.isEmpty() && folderArray.isEmpty()){
			sb.append("[]");
		}
		sb.append("}");
		if(sb.toString().contains("][")){
			return sb.toString().replace("][", ",");
		}
		return sb.toString();
	}

	public void transferToMe(String email, String md5, String fileName,
			String size, String transferTime) {
		String rowKey = email + "_" + md5 + "_" + transferTime;
		hbaseOp.addRecord("file", rowKey, "info", "fileName", fileName);
		hbaseOp.addRecord("file", rowKey, "info", "size", size);
		hbaseOp.addRecord("file", rowKey, "info", "folderId", email+"root");
		hbaseOp.addRecord("file", rowKey, "info", "share", "0");
	}

}
