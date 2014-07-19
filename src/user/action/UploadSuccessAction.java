package user.action;

import user.service.UploadSuccessService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class UploadSuccessAction implements Action{
	private String MD5;
	private String result;
	private  UploadSuccessService uploadSuccessService;
	
	public UploadSuccessService getUploadSuccessService() {
		return uploadSuccessService;
	}
	public void setUploadSuccessService(UploadSuccessService uploadSuccessService) {
		this.uploadSuccessService = uploadSuccessService;
	}
	public String getMD5(){
		return MD5;
	}
	public void setMD5(String MD5){
		this.MD5 = MD5;
	}
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String execute() throws Exception {
		String useremail = (String)ActionContext.getContext().getSession().get("userEmail");
		try{
			uploadSuccessService.StartTranscodeing(MD5,useremail);
			result = "文件转码成功，尽情播放。";
		}catch(Exception e){
			result = "文件转码失败，请重试。";
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
