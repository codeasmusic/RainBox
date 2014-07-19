package manager.implementation;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.hbase.HBaseOperation;

import manager.service.AdminLoginService;

public class AdminLoginServiceImpl implements AdminLoginService{
	String tableName = "admin";
	public boolean exist(String adminName) {
		HBaseOperation hbase = new HBaseOperation();
		Result rs = hbase.getRecord(tableName, adminName);
		if(rs.isEmpty())
			return false;
		return true;
	}
	public boolean match(String adminName,String password){
		HBaseOperation hbase = new HBaseOperation();
		Result rs = hbase.getRecord(tableName, adminName);
		if(Bytes.toString(rs.getValue(Bytes.toBytes("info"), 
				Bytes.toBytes("password"))).compareTo(password)==0)
			return true;
		return false;
	}
}
