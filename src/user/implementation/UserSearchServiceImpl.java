package user.implementation;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import com.hbase.HBaseOperation;

import user.service.UserSearchService;

public class UserSearchServiceImpl implements UserSearchService{
	private static HBaseOperation hbaseOp = new HBaseOperation(); 
	
	public String searchSharedFiles(String content) {
		String tableName = "file";
		String family = "info";
		String[] qualifier = {"share", "fileName"};
		String[] value = {"1", content};
		ResultScanner rs = hbaseOp.getRecordByColumn(tableName, family, qualifier, value);
		return getFilesJson(rs, "shareCenter");
	}
	
	public String searchMyFiles(String userEmail, String fileName) {
		String tableName = "file";
		String family = "info";
		String[] qualifiers = {"fileName"};
		String[] values = {fileName};
		
		ResultScanner rs = hbaseOp.getRecordByRowkeyAndColumn(tableName, 
				family, userEmail, qualifiers, values, true);
		return getFilesJson(rs, "myFiles");
	}

	public String getFilesJson(ResultScanner rs, String type){
		JSONArray fileArray = new JSONArray();
		JSONObject jsonObj;		
		String rowKey;
		String arr[];
		
		for(Result r : rs){
			jsonObj = new JSONObject();
			
			for(Cell c : r.rawCells()){
				rowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
				arr = rowKey.split("_");
				jsonObj.element("userEmail", arr[0]);
				jsonObj.element("md5", arr[1]);
				jsonObj.element("uploadTime", arr[2]);
			}
			
			jsonObj.element("fileName", Bytes.toString(
					r.getValue(Bytes.toBytes("info"), Bytes.toBytes("fileName"))));
			jsonObj.element("size", Bytes.toString(
					r.getValue(Bytes.toBytes("info"), Bytes.toBytes("size"))));
			
			if(type.equals("shareCenter")){
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
			}
			else{
				jsonObj.element("share", Bytes.toString(
						r.getValue(Bytes.toBytes("info"), Bytes.toBytes("share"))));
			}
			fileArray.add(jsonObj);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{all:");
		sb.append(fileArray);
		sb.append("}");
		
		return sb.toString();
	}

}
