package user.action;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import user.service.ShareFilesService;

public class ShareFilesAction implements Action{
	private ShareFilesService share;
	private String MD5;
	private String uploadTime;
	private String result;
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ShareFilesService getShare() {
		return share;
	}

	public void setShare(ShareFilesService share) {
		this.share = share;
	}

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String mD5) {
		MD5 = mD5;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String execute() throws Exception {
		String email = (String) ActionContext.getContext().getSession().get("userEmail");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String sharePath = request.getScheme() + "://"+request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath() + "/"
				+ "download.action?sharer=" + email + "&MD5=";
		String shareLink = share.shareFile(sharePath, MD5);
		share.saveShareLog(email,fileName);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("shareLink", shareLink);
		result = jsonObj.toString();
		return SUCCESS;
	}
	
	public String cancelShare(){
		String email = (String) ActionContext.getContext().getSession().get("userEmail");
		String status = "文件取消共享失败，请重试。";
		if(share.cancelShare(email, MD5, uploadTime)){
			status = "文件取消共享成功！";
		}
		share.saveCancelShareLog(email,fileName);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("status", status);
		result = jsonObj.toString();
		return SUCCESS;
	}
	
	public String getSharedFiles(){
		result = share.getSharedFiles();
		return SUCCESS;
	}
}
