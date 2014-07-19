package user.action;

import user.service.FolderService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class ModifyFolderNameAction implements Action{
	private String folderid;
	private String newfoldername;
	private FolderService modifyFolderName;

	public FolderService getModifyFolderName() {
		return modifyFolderName;
	}

	public void setModifyFolderName(FolderService modifyFolderName) {
		this.modifyFolderName = modifyFolderName;
	}

	public String getNewfoldername() {
		return newfoldername;
	}

	public void setNewfoldername(String newfoldername) {
		this.newfoldername = newfoldername;
	}

	public String getFolderid() {
		return folderid;
	}

	public void setFolderid(String folderid) {
		this.folderid = folderid;
	}

	public String execute() throws Exception {
		String userEmail = (String) ActionContext.getContext().getSession().get("userEmail");
		modifyFolderName.ModifyFolderName(userEmail, folderid, newfoldername);
		return SUCCESS;
	}

}
