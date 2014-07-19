package user.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hbase.HBaseOperation;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class SaveDownloadLog implements Action{
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String execute() throws Exception {
		String email = (String) ActionContext.getContext().getSession().get("userEmail");
		// TODO Auto-generated method stub
		HBaseOperation hbase = new HBaseOperation();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String opTime = df.format(new Date());
		String rowkey = email + "_" + opTime;
		String opType = "下载";
		String opObject = getFileName();
		hbase.addRecord("log", rowkey, "content", "opType", opType);
		hbase.addRecord("log", rowkey, "content", "opObject", opObject);
		return "success";
	}

}
