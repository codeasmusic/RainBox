package user.action;

import user.service.ShareFilesService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class MyShareAction implements Action{
	private String result;
	private ShareFilesService share;
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ShareFilesService getShare() {
		return share;
	}

	public void setShare(ShareFilesService share) {
		this.share = share;
	}

	public String execute() throws Exception {
		String email = (String) ActionContext.getContext().getSession().get("userEmail");
		result = share.getMyShare(email);
		return SUCCESS;
	}

}
