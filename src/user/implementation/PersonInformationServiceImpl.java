package user.implementation;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.hbase.HBaseOperation;

import user.service.PersonInformationService;

public class PersonInformationServiceImpl implements PersonInformationService{
	String tableName = "user";
	HBaseOperation hbase = new HBaseOperation();
	public String getNickName(String userEmail){
		Result rs = hbase.getRecord(tableName, userEmail);
		return Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("nickName")));
	}
	public String getHeadName(String userEmail){
		Result rs = hbase.getRecord(tableName, userEmail);
		return Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("headName")));
	}
	public String getSex(String userEmail){
		Result rs = hbase.getRecord(tableName, userEmail);
		return Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("sex")));
	}
	public String getBirthday(String userEmail){
		Result rs = hbase.getRecord(tableName, userEmail);
		return Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("birthday")));
	}
	public String getIntroduction(String userEmail){
		Result rs = hbase.getRecord(tableName, userEmail);
		return Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("introduction")));
	}
	public String getLevel(String userEmail){
		Result rs = hbase.getRecord(tableName, userEmail);
		return Bytes.toString(rs.getValue(Bytes.toBytes("pan"), Bytes.toBytes("level")));
	}
	public String getShareNum(String userEmail){
		Result rs = hbase.getRecord(tableName, userEmail);
		return Bytes.toString(rs.getValue(Bytes.toBytes("pan"), Bytes.toBytes("shareNum")));
	}
	public String getFileNum(String userEmail){
		Result rs = hbase.getRecord(tableName, userEmail);
		return Bytes.toString(rs.getValue(Bytes.toBytes("pan"), Bytes.toBytes("fileNum")));
	}
	public String getUsedCapacity(String userEmail){
		Result rs = hbase.getRecord(tableName, userEmail);
		return Bytes.toString(rs.getValue(Bytes.toBytes("pan"), Bytes.toBytes("usedCapacity")));
	}
	public String getCapacity(String userEmail){
		Result rs = hbase.getRecord(tableName, userEmail);
		String level = Bytes.toString(rs.getValue(Bytes.toBytes("pan"), Bytes.toBytes("level")));
		Result rs1 = hbase.getRecord("authority", level);
		return Bytes.toString(rs1.getValue(Bytes.toBytes("info"), Bytes.toBytes("capacity")));
	}
}
