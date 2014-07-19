package user.action;

import user.service.UserFilesService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class ModifyFileNameAction implements Action {
	private String filename;
	private String filemd5;
	private String newfilename;
	private UserFilesService userFilesService;

	public UserFilesService getUserFilesService() {
		return userFilesService;
	}

	public void setUserFilesService(UserFilesService userFilesService) {
		this.userFilesService = userFilesService;
	}

	public String getFilemd5() {
		return filemd5;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}

	public String getNewfilename() {
		return newfilename;
	}

	public void setNewfilename(String newfilename) {
		this.newfilename = newfilename;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String execute() throws Exception {
		String userEmail = (String) ActionContext.getContext().getSession().get("userEmail");
		userFilesService.ModifyFileName(userEmail, filemd5, newfilename);
		return SUCCESS;
	}

}
