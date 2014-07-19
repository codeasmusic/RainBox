package user.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import user.service.FolderService;
import user.service.UserFilesService;

public class FoldersAction implements Action{
	private FolderService folder;
	private String folderId;
	private String upperFolderId;
	private String newFolderName;
	private String setTime;
	
	private UserFilesService ufs;
	private String result;
	
	public FolderService getFolder() {
		return folder;
	}

	public void setFolder(FolderService folder) {
		this.folder = folder;
	}

	public String getNewFolderName() {
		return newFolderName;
	}

	public void setNewFolderName(String newFolderName) {
		this.newFolderName = newFolderName;
	}
	
	public String getUpperFolderId() {
		return upperFolderId;
	}

	public void setUpperFolderId(String upperFolderId) {
		this.upperFolderId = upperFolderId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getSetTime() {
		return setTime;
	}

	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}

	public UserFilesService getUfs() {
		return ufs;
	}

	public void setUfs(UserFilesService ufs) {
		this.ufs = ufs;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String execute() throws Exception {
		String email = (String) ActionContext.getContext().getSession().get("userEmail");
		System.out.println(upperFolderId);
		result = folder.createNewFolder(newFolderName, email, upperFolderId, setTime);
		return SUCCESS;
	}
	
	public String deleteFolder(){
		String email = (String) ActionContext.getContext().getSession().get("userEmail");
		if(folder.deleteFolder(email, folderId, upperFolderId)){
			result = "文件夹删除成功！";
		}else{
			result = "文件夹删除失败，请重试。";
		}
		return SUCCESS;
	}
	
	public String enterFolder(){
		String userEmail = (String) ActionContext.getContext().getSession().get("userEmail");
		System.out.println(userEmail+ "\t"+ folderId);
		result = ufs.getFilesAndFolders(userEmail, folderId);
		System.out.println(result);
		return SUCCESS;
	}
}
