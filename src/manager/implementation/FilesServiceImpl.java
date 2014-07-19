package manager.implementation;


import java.util.ArrayList;
import java.util.List;

import manager.service.FilesService;
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


public class FilesServiceImpl implements FilesService{
	private static HBaseOperation hbaseOp = new HBaseOperation();
	private String tableName = "file";
	private String family;
	
	public String searchByFileName(String content) {
		family = "info";
		String[] qualifier = {"fileName"};
		String[] value = {content};
		ResultScanner rs = hbaseOp.getRecordByColumn(tableName, family, qualifier, value);
		return getFileArray(rs);
	}

	public String searchByTag(String content) {
		family = "tag";
		String[] qualifiers = {"tag1", "tag2","tag3" ,"tag4", "tag5"};
		String[] value = {content, content, content, content, content};
		ResultScanner rs = hbaseOp.getRecordByColumn(tableName, family, qualifiers, value);
		return getFileArray(rs);
	}
	
	public static String getFileArray (ResultScanner rs){
		JSONObject jsonObj;
		JSONArray fileArray = new JSONArray();
		String rowKey;
		String arr[];
		
		for(Result r : rs){
				jsonObj = new JSONObject();
				jsonObj.element("fileName", Bytes.toString(
						r.getValue(Bytes.toBytes("info"), Bytes.toBytes("fileName"))));
				jsonObj.element("share", Bytes.toString(
						r.getValue(Bytes.toBytes("info"), Bytes.toBytes("share"))));
				
				for(Cell c : r.rawCells()){
					rowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
					arr = rowKey.split("_");
					jsonObj.element("userEmail", arr[0]);
					jsonObj.element("md5", arr[1]);
					jsonObj.element("uploadTime", arr[2]);
				}
				fileArray.add(jsonObj);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{search:");
		sb.append(fileArray);
		sb.append("}");
		
		return sb.toString();
	}

	public String getFileInfo(String rowKey) {
		Result rs = hbaseOp.getRecord(tableName, rowKey);
		String size = Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("size")));
		String tag1 = Bytes.toString(rs.getValue(Bytes.toBytes("tag"), Bytes.toBytes("tag1")));
		String tag2 = Bytes.toString(rs.getValue(Bytes.toBytes("tag"), Bytes.toBytes("tag2")));
		String tag3 = Bytes.toString(rs.getValue(Bytes.toBytes("tag"), Bytes.toBytes("tag3")));
		String tag4 = Bytes.toString(rs.getValue(Bytes.toBytes("tag"), Bytes.toBytes("tag4")));
		String tag5 = Bytes.toString(rs.getValue(Bytes.toBytes("tag"), Bytes.toBytes("tag5")));
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("size", size);
		jsonObj.element("tag1", tag1);
		jsonObj.element("tag2", tag2);
		jsonObj.element("tag3", tag3);
		jsonObj.element("tag4", tag4);
		jsonObj.element("tag5", tag5);
		
		return jsonObj.toString();
	}
	
	public boolean deleteFile(String MD5, String rowKey) {
		List<String> key = new ArrayList<String>();
		key.add(rowKey);
		
		try{
			hbaseOp.delRecord(tableName, key);

			Configuration conf = new Configuration();
			conf.set("fs.default.name", "hdfs://192.168.1.250:9000");
			FileSystem hdfs = FileSystem.get(conf);
			Path aviPath = new Path("/upload/" + MD5 + ".avi");
			Path mp4Path = new Path("/upload/" + MD5 + ".mp4");

			hdfs.delete(aviPath, false);
			hdfs.delete(mp4Path, false);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
