package manager.action;

import com.opensymphony.xwork2.Action;

import manager.service.UserService;

public class ActiveUsersAction implements Action{
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
		if(user.activeUsers(userEmail)){
			result = "用户激活成功！";
		}else{
			result = "用户激活失败，请重试。";
		}
		return SUCCESS;
	}
}
