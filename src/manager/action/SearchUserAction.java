package manager.action;


import manager.service.UserService;

import com.opensymphony.xwork2.Action;

public class SearchUserAction implements Action{
	private String userEmail;
	private String result;
	private UserService searchUser;
	
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

	public UserService getSearchUser() {
		return searchUser;
	}

	public void setSearchUser(UserService searchUser) {
		this.searchUser = searchUser;
	}

	public String execute() throws Exception {
		try{
			result = searchUser.searchUser(userEmail);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String getUserInfo(){
		result = searchUser.getUserInfo(userEmail);
		return SUCCESS;
	}
}
