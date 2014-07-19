package user.action;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import user.service.EmailService;

import com.opensymphony.xwork2.Action;

public class SendEmailAction implements Action{
	private String userEmail;
	private String newPassword;
	private String id;
	private EmailService email;
	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EmailService getEmail() {
		return email;
	}

	public void setEmail(EmailService email) {
		this.email = email;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		boolean success = email.sendEmail("smtp.163.com", "hi_for_alice@163.com", userEmail, "czh621734",
					"亲，点击链接帮你找回RainBox网盘密码", "http://czh_pc:8080/RainBox/resetPage?userEmail="+userEmail);
		request.setAttribute("userEmail", userEmail);
		
		request.setAttribute("error", "账号不存在，请重新输入"); 
		if(!success)
			return ERROR;
		
		return SUCCESS;
	}

	public String enterResetPage(){
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("userEmail", (String) request.getAttribute("userEmail"));
		request.setAttribute("id", (String) request.getAttribute("id"));
		return SUCCESS;
	}
	
	public String resetPassword(){
		if(email.resetPWD(userEmail, newPassword, id))
			return SUCCESS;
		return ERROR;
	}
	
}
