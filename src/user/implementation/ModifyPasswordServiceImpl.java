package user.implementation;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.hbase.HBaseOperation;

import user.service.ModifyPasswordService;

public class ModifyPasswordServiceImpl implements ModifyPasswordService{
	String tableName = "user";
	public boolean match(String userEmail,String password){
		HBaseOperation hbase = new HBaseOperation();
		Result rs = hbase.getRecord(tableName, userEmail);
		if(Bytes.toString(rs.getValue(Bytes.toBytes("info"), 
				Bytes.toBytes("password"))).compareTo(password)==0)
			return true;
		return false;
	}
	public void modify(String userEmail,String password){
		HBaseOperation hbase = new HBaseOperation();
		hbase.addRecord(tableName, userEmail, "info", "password", password);
	}
}
