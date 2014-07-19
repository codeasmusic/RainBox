package user.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hbase.HBaseOperation;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import user.service.UserFilesService;

public class DeleteFileAction implements Action {
	private UserFilesService deleteFileService;
	private String MD5;
	private String fileName;
	private String folderId;
	private String result;

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String MD5) {
		this.MD5 = MD5;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public UserFilesService getDeleteFileService() {
		return deleteFileService;
	}

	public void setDeleteFileService(UserFilesService deleteFileService) {
		this.deleteFileService = deleteFileService;
	}

	public String execute() throws Exception {
		String userEmail = (String) ActionContext.getContext().getSession().get("userEmail");
		if(deleteFileService.DeleteFile(MD5, fileName, userEmail, folderId)){
			result = "文件删除成功！";
		}else{
			result = "文件删除失败，请重试。";
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String opTime = df.format(new Date());
		String rowkey = userEmail + "_" + opTime;
		String opType = "删除";
		String opObject = fileName;
		HBaseOperation hbase = new HBaseOperation();
		hbase.addRecord("log", rowkey, "content", "opType", opType);
		hbase.addRecord("log", rowkey, "content", "opObject", opObject);
		return SUCCESS;
	}

}