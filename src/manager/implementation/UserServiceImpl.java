package manager.implementation;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import com.hbase.HBaseOperation;

import manager.service.UserService;

public class UserServiceImpl implements UserService{
	private static HBaseOperation hbaseOp= new HBaseOperation();

	public boolean setUserLevel(String userEmail, String level) {
		String tableName = "user";
		String family = "pan";
		String qualifier = "level";
		
		return hbaseOp.addRecord(tableName, userEmail, family, qualifier, level);
	}

	public boolean setLevelToCapacity(String capacity) {
		String tableName = "authority";
		String family = "info";
		String qualifier = "capacity";

		return hbaseOp.addRecord(tableName, "1", family, qualifier, capacity);
	}

	public String searchUser(String userEmail) {
		JSONObject jsonObj = new JSONObject();
		JSONArray userArray = new JSONArray();
		ResultScanner rs = hbaseOp.getRecordBySubRowkey("user", userEmail);
		String rowKey = null;
		
		for(Result r : rs){
			for(Cell c : r.rawCells()){
				rowKey = Bytes.toString(c.getRowArray(), c.getRowOffset(), c.getRowLength());
			}
			
			jsonObj.element("userEmail", rowKey);
			jsonObj.element("nickName", Bytes.toString(
					r.getValue(Bytes.toBytes("info"), Bytes.toBytes("nickName"))));
			jsonObj.element("isDisable", Bytes.toString(
					r.getValue(Bytes.toBytes("info"), Bytes.toBytes("isDisable"))));
			jsonObj.element("level", Bytes.toString(
					r.getValue(Bytes.toBytes("pan"), Bytes.toBytes("level"))));
			
			userArray.add(jsonObj);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{user:");
		sb.append(userArray);
		sb.append("}");
		
		return sb.toString();
	}

	public String getUserInfo(String userEmail) {
		JSONObject jsonObj = new JSONObject();
		Result r = hbaseOp.getRecord("user", userEmail);
		
		jsonObj.element("userEmail", userEmail);
		jsonObj.element("headName", Bytes.toString(
				r.getValue(Bytes.toBytes("info"), Bytes.toBytes("headName"))));
		jsonObj.element("nickName", Bytes.toString(
				r.getValue(Bytes.toBytes("info"), Bytes.toBytes("nickName"))));
		jsonObj.element("sex", Bytes.toString(
				r.getValue(Bytes.toBytes("info"), Bytes.toBytes("sex"))));
		jsonObj.element("birthday", Bytes.toString(
				r.getValue(Bytes.toBytes("info"), Bytes.toBytes("birthday"))));
		jsonObj.element("level", Bytes.toString(
				r.getValue(Bytes.toBytes("pan"), Bytes.toBytes("level"))));
		jsonObj.element("introduction", Bytes.toString(
				r.getValue(Bytes.toBytes("info"), Bytes.toBytes("introduction"))));
		
		return jsonObj.toString();
	}

	public boolean deleteUsers(String userEmail) {
		List<String> rowKey = new ArrayList<String>();
		rowKey.add(userEmail);
		return hbaseOp.delRecord("user", rowKey);
	}

	public boolean disableUsers(String userEmail) {
		return hbaseOp.addRecord("user", userEmail, "info", "isDisable", "0");
	}

	public boolean activeUsers(String userEmail) {
		return hbaseOp.addRecord("user", userEmail, "info", "isDisable", "1");
	}
}
