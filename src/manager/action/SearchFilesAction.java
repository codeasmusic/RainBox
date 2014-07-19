package manager.action;

import manager.service.FilesService;
import com.opensymphony.xwork2.Action;

public class SearchFilesAction implements Action{
	private String content;
	private String MD5;
	private String userEmail;
	private String uploadTime;
	private FilesService search;
	private String result;
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
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

	public FilesService getSearch() {
		return search;
	}
	
	public void setSearch(FilesService search) {
		this.search = search;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String execute() throws Exception {
		result = search.searchByFileName(content);
		return SUCCESS;
	}
	
	public String searchByTag(){
		result = search.searchByTag(content);
		return SUCCESS;
	}
	
	public String getFileInfo(){
		String rowKey = userEmail + "_" + MD5 + "_" + uploadTime;
		result = search.getFileInfo(rowKey);
		return SUCCESS;
	}
}
