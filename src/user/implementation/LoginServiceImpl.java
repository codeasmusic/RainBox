package user.implementation;

import java.util.Map;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.hbase.HBaseOperation;
import com.opensymphony.xwork2.ActionContext;

import user.service.LoginService;

public class LoginServiceImpl implements LoginService{
	String tableName = "user";
	public boolean exist(String email) {
		HBaseOperation hbase = new HBaseOperation();
		Result rs = hbase.getRecord(tableName, email);
		if(rs.isEmpty())
			return false;
		return true;
	}
	public boolean match(String email,String password){
		HBaseOperation hbase = new HBaseOperation();
		Result rs = hbase.getRecord(tableName, email);
		
		if(Bytes.toString(rs.getValue(Bytes.toBytes("info"), 
				Bytes.toBytes("password"))).compareTo(password)==0){
			String nickName = Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("nickName")));
			String headName = Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("headName")));
			String sex = Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("sex")));
			String birthday = Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("birthday")));
			String introduction = Bytes.toString(rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("introduction")));
			
			String level = Bytes.toString(rs.getValue(Bytes.toBytes("pan"), Bytes.toBytes("level")));
			String shareNum = Bytes.toString(rs.getValue(Bytes.toBytes("pan"), Bytes.toBytes("shareNum")));
			String fileNum = Bytes.toString(rs.getValue(Bytes.toBytes("pan"), Bytes.toBytes("fileNum")));
			String usedCapacity = Bytes.toString(rs.getValue(Bytes.toBytes("pan"), Bytes.toBytes("usedCapacity")));
			
			
			int l = Integer.valueOf(level);		
			Result capacityRs = hbase.getRecord("authority", "1");
			String capacity = Bytes.toString(capacityRs.getValue(Bytes.toBytes("info"), Bytes.toBytes("capacity")));
			double totalCapacity = l * Double.valueOf(capacity);
			String total = String.format("%.2f", totalCapacity);
			
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("nickName", nickName);
			
			if(headName==null || headName.equals("")){
				headName = "user.png";
			}
			session.put("savePath", "/style/uploadHead/" + headName);
			session.put("sex", sex);
			session.put("birthday", birthday);
			session.put("introduction", introduction);
			
			session.put("level", level);
			session.put("shareNum", shareNum);
			session.put("fileNum", fileNum);
			session.put("usedCapacity", usedCapacity);
			session.put("capacity", total+"G");
			return true;
		}
		return false;
	}
	public boolean isDisable(String email){
		HBaseOperation hbase = new HBaseOperation();
		Result rs = hbase.getRecord(tableName, email);
		String isDisable = Bytes.toString(rs.getValue(Bytes.toBytes("info"), 
				Bytes.toBytes("isDisable")));
		if(isDisable.contains("1"))
			return true;
		return false;
	}
}
