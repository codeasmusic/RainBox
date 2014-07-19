package user.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.struts2.ServletActionContext;

import com.hbase.HBaseOperation;

import user.service.ModifyPersonInformationService;

public class ModifyPersonInformationServiceImpl implements ModifyPersonInformationService{
	String tableName = "user";
	public void save(String email,String sex,String nickName,
			String birthday,String introduction,String headName){
		HBaseOperation hbase = new HBaseOperation();
		hbase.addRecord(tableName, email, "info","sex", sex);
		hbase.addRecord(tableName, email, "info","nickName", nickName);
		hbase.addRecord(tableName, email, "info","birthday", birthday);
		hbase.addRecord(tableName, email, "info","introduction", introduction);
		hbase.addRecord(tableName, email, "info", "headName", headName);
	}
	
	public void saveHead (File source,String headName)throws Exception{
		FileOutputStream out = new FileOutputStream(
				new File(ServletActionContext.getServletContext().
						getRealPath("/style/uploadHead/" + headName)));

		FileInputStream input = new FileInputStream(source);
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = input.read(buffer)) > 0){
			out.write(buffer, 0, len);
		}
	}
}
