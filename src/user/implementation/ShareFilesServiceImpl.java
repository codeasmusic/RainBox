package user.implementation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import com.hbase.HBaseOperation;

import user.service.ShareFilesService;

public class ShareFilesServiceImpl implements ShareFilesService{
	private static HBaseOperation hbaseOp= new HBaseOperation();
	private static String tableName = "file";
	private static String family = "info";
	private static String qualifier = "share";
	
	public String shareFile(String sharePath, String md5) {
		ResultScanner rs = hbaseOp.getRecordBySubRowkey(tableName, md5);
		String rowKey = null;
		for(Result r : rs){
			for(Cell c : r.rawCells()){
				rowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
			}
		}
		if(rowKey != null){
			hbaseOp.addRecord(tableName, rowKey, family, qualifier, "1");
		}
		return (sharePath + md5);
	}

	public boolean cancelShare(String email, String md5, String uploadTime) {
		ResultScanner rs = hbaseOp.getRecordBySubRowkey(tableName, email + "_" + md5 + "_" + uploadTime);
		String rowKey = null;
		for(Result r : rs){
			for(Cell c : r.rawCells()){
				rowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
			}
		}
		if(rowKey != null){
			hbaseOp.addRecord(tableName, rowKey, family, qualifier, "0");
			return true;
		}
		return false;
	}

	public String getSharedFiles() {
		ResultScanner rs = hbaseOp.getByExactValue("file", "info", "share", "1");
		Set<String> md5Set = new HashSet<String>();
		JSONObject jsonObj;
		String rowKey;
		String md5 = null;
		String[] arr;
		JSONArray fileArray = new JSONArray();

		for(Result r : rs){
			jsonObj = new JSONObject();
			
			for(Cell c : r.rawCells()){
				rowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
				arr = rowKey.split("_");
				md5 = arr[1];
				
				jsonObj.element("userEmail", arr[0]);
				jsonObj.element("md5", md5);
				jsonObj.element("uploadTime", arr[2]);
			}
			
			if(md5Set.contains(md5)){
				continue;
			}
			md5Set.add(md5);
			
			jsonObj.element("fileName", Bytes.toString(
					r.getValue(Bytes.toBytes("info"), Bytes.toBytes("fileName"))));
			jsonObj.element("size", Bytes.toString(
					r.getValue(Bytes.toBytes("info"), Bytes.toBytes("size"))));
			jsonObj.element("tag1", Bytes.toString(
					r.getValue(Bytes.toBytes("tag"), Bytes.toBytes("tag1"))));
			jsonObj.element("tag2", Bytes.toString(
					r.getValue(Bytes.toBytes("tag"), Bytes.toBytes("tag2"))));
			jsonObj.element("tag3", Bytes.toString(
					r.getValue(Bytes.toBytes("tag"), Bytes.toBytes("tag3"))));
			jsonObj.element("tag4", Bytes.toString(
					r.getValue(Bytes.toBytes("tag"), Bytes.toBytes("tag4"))));
			jsonObj.element("tag5", Bytes.toString(
					r.getValue(Bytes.toBytes("tag"), Bytes.toBytes("tag5"))));
			
			fileArray.add(jsonObj);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{share:");
		
		if(!fileArray.isEmpty())
			sb.append(fileArray);
		
		sb.append("}");
		return sb.toString();
	}

	public String getMyShare(String email) {
		JSONObject jsonObj;
		JSONArray fileArray = new JSONArray();
		String rowKey;
		String arr[];
		
		String[] qualifiers = {"share"};
		String[] values = {"1"};
		
		ResultScanner fileRS = hbaseOp.getRecordByRowkeyAndColumn("file", "info",
				email, qualifiers, values, false);
		for(Result r : fileRS){
				jsonObj = new JSONObject();
				jsonObj.element("fileName", Bytes.toString(
						r.getValue(Bytes.toBytes("info"), Bytes.toBytes("fileName"))));
				jsonObj.element("size", Bytes.toString(
						r.getValue(Bytes.toBytes("info"), Bytes.toBytes("size"))));
				
				for(Cell c : r.rawCells()){
					rowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
					arr = rowKey.split("_");
					jsonObj.element("md5", arr[1]);
					jsonObj.element("shareTime", arr[2]);
				}
				fileArray.add(jsonObj);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{myShare:");
		sb.append(fileArray);
		sb.append("}");
		return sb.toString();
	}
	public void saveShareLog(String email,String fileName){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String opTime = df.format(new Date());
		String rowkey = email + "_" + opTime;
		String opType = "分享";
		String opObject = fileName;
		hbaseOp.addRecord("log", rowkey, "content", "opType", opType);
		hbaseOp.addRecord("log", rowkey, "content", "opObject", opObject);
	}
	
	public void saveCancelShareLog(String email,String fileName){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String opTime = df.format(new Date());
		String rowkey = email + "_" + opTime;
		String opType = "取消分享";
		String opObject = fileName;
		hbaseOp.addRecord("log", rowkey, "content", "opType", opType);
		hbaseOp.addRecord("log", rowkey, "content", "opObject", opObject);
	}
}
