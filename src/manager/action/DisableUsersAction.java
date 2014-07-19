package manager.action;

import manager.service.UserService;

import com.opensymphony.xwork2.Action;

public class DisableUsersAction implements Action{
	private String userEmail;
	private String result;
	private UserService user;
	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public UserService getUser() {
		return user;
	}
	
	public void setUser(UserService user) {
		this.user = user;
	}

	public String execute() throws Exception {
		if(user.disableUsers(userEmail)){
			result = "用户禁用成功！";
		}else{
			result = "用户禁用失败，请重试。";
		}
		return SUCCESS;
	}
}
