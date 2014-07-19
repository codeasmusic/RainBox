package manager.action;

import manager.service.FilesService;

import com.opensymphony.xwork2.Action;

public class DeleteFileAction implements Action{
	private String MD5;
	private String userEmail;
	private String uploadTime;
	private String result;
	private FilesService delete;
	
	public String getMD5() {
		return MD5;
	}

	public void setMD5(String mD5) {
		MD5 = mD5;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public FilesService getDelete() {
		return delete;
	}
	
	public void setDelete(FilesService delete) {
		this.delete = delete;
	}

	public String execute() throws Exception {
		String rowKey = userEmail + "_" + MD5 + "_" + uploadTime;
		if(delete.deleteFile(MD5, rowKey)){
			result = "文件删除成功！";
		}else{
			result = "文件删除失败，请重试。";
		}
		return SUCCESS;
	}
}
