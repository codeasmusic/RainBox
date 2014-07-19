package user.action;

import java.util.Map;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import user.service.ModifyPasswordService;

public class ModifyPasswordAction implements Action{
	private String oldPassword;
	private String newPassword;
	private String result;
	private ModifyPasswordService mps;
	
	
	public String getOldPassword() {
		return oldPassword;
	}


	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public ModifyPasswordService getMps() {
		return mps;
	}


	public void setMps(ModifyPasswordService mps) {
		this.mps = mps;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		String email = (String)session.get("userEmail");
		
		System.out.println(oldPassword + "\t" + newPassword + "\t" + email);
		
		JSONObject jsonObj = new JSONObject();
		if(mps.match(email,oldPassword))
		{
			mps.modify(email,newPassword);
			jsonObj.element("result", "success");
		}else{
			jsonObj.element("result", "fail");
		}
		result = jsonObj.toString();
		return SUCCESS;
	}
	
}
