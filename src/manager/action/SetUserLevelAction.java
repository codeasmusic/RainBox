package manager.action;

import manager.service.UserService;

import com.opensymphony.xwork2.Action;

public class SetUserLevelAction implements Action{
	private String userEmail;
	private String level;
	private String result;
	private UserService user;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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
		String numLevel = level.substring((level.indexOf("P")+1), level.length());
		if(user.setUserLevel(userEmail, numLevel)){
			result = "级别设置成功！";
		}else{
			result = "级别设置失败，请重试。";
		}
		return SUCCESS;
	}

	
	
}
