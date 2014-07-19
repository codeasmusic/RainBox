package user.action;

import java.util.Map;

import user.service.LoginService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction implements Action{
	private String email;
	private String password;
	private LoginService ls;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLs(LoginService ls) {
		this.ls = ls;
	}

	public LoginService getLs() {
		return ls;
	}

	public String execute() throws Exception {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put("userEmail", email);
		if(ls.exist(email))
			if(ls.match(email,password))
				if(ls.isDisable(email))
					return SUCCESS;
				else actionContext.put("error", "亲爱的用户您已被放逐");
			else actionContext.put("error", "密码输入错误");
		else actionContext.put("error", "邮箱尚未注册，请注册。");
		return ERROR;
	}
}
