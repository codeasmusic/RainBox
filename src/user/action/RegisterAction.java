package user.action;

import java.util.Map;

import user.service.RegisterService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class RegisterAction implements Action{
	private String email;
	private String password;
	private RegisterService rs;
	
	
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

	public RegisterService getRs() {
		return rs;
	}



	public void setRs(RegisterService rs) {
		this.rs = rs;
	}

	public String execute() throws Exception {
		//创建session保存userEmail,方便其他
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put("userEmail", email);
		
		if(rs.exist(email))
		{
			actionContext.put("error", "该邮箱已被注册");
			return "error";
		}
		actionContext.put("savePath", "/style/pic/user.png");
		rs.insert(email, password);
	    return "success";
	}

}
