package user.implementation;

import java.util.Map;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.hbase.HBaseOperation;
import com.opensymphony.xwork2.ActionContext;

import user.service.RegisterService;


public class RegisterServiceImpl implements RegisterService{
	String tableName = "user";
	public boolean exist(String email) {
		HBaseOperation hbase = new HBaseOperation();
		Result rs = hbase.getRecord(tableName, email);
		if(rs.isEmpty()){
			hbase.addRecord(tableName, email, "pan", "level", "1");
			hbase.addRecord(tableName, email, "pan", "shareNum", "0");
			hbase.addRecord(tableName, email, "pan", "fileNum", "0");
			hbase.addRecord(tableName, email, "pan", "usedCapacity", "0.00");
			
			Result capacityRs = hbase.getRecord("authority", "1");
			String capacity = Bytes.toString(capacityRs.getValue(Bytes.toBytes("info"), Bytes.toBytes("capacity")));
			
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("level", "1");
			session.put("shareNum", "0");
			session.put("fileNum", "0");
			session.put("usedCapacity", "0.00");
			session.put("capacity", capacity+"G");
			return false;
		}
		return true;
	}
	
	public void insert(String email,String password){
		HBaseOperation hbase = new HBaseOperation();
		hbase.addRecord(tableName, email,"info","password", password);
		hbase.addRecord(tableName, email, "pan","shareNum", "0");
		hbase.addRecord(tableName, email, "pan","fileNum", "0");
		hbase.addRecord(tableName, email, "pan","usedCapacity", "0");
		hbase.addRecord(tableName, email, "pan","level", "1");
		hbase.addRecord(tableName, email, "info", "isDisable", "1");
	}
	
}
