package manager.action;

import manager.service.UserService;

import com.opensymphony.xwork2.Action;

public class SetStorageCapacityAction implements Action{
	private String capacity;
	private UserService user;
	private String result;

	public String getCapacity() {
		return capacity;
	}
	
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
	public UserService getUser() {
		return user;
	}
	
	public void setUser(UserService user) {
		this.user = user;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String execute() throws Exception {
		if(user.setLevelToCapacity(capacity)){
			result = "容量大小设置成功！";
		}else{
			result = "容量大小设置失败，请重试。";
		}
		return SUCCESS;
	}
}
