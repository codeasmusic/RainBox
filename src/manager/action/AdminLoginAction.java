package manager.action;

import manager.service.AdminLoginService;

public class AdminLoginAction {
	private String adminName;
	private String password;
	private AdminLoginService als;
	
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AdminLoginService getAls() {
		return als;
	}

	public void setAls(AdminLoginService als) {
		this.als = als;
	}

	public String execute() throws Exception {
		if(als.exist(getAdminName()))
			if(als.match(adminName, password))
				return "success";
		return "error";
	}
}
