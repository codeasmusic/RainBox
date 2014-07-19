package user.action;

import net.sf.json.JSONObject;
import user.service.UserFilesService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class TransferToMeAction implements Action{
	private String MD5;
	private String transferTime;
	private String fileName;
	private String size;
	private String result;
	private UserFilesService ufs;

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String mD5) {
		MD5 = mD5;
	}

	public String getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public UserFilesService getUfs() {
		return ufs;
	}

	public void setUfs(UserFilesService ufs) {
		this.ufs = ufs;
	}

	public String execute() throws Exception {
		String userEmail = (String) ActionContext.getContext().getSession().get("userEmail");
		
		JSONObject jsonObj = new JSONObject();
		try{
			ufs.transferToMe(userEmail, MD5, fileName, size, transferTime);
			jsonObj.element("status", "成功转存至你的网盘！");
		}catch(Exception e){
			jsonObj.element("status", "转存失败，请重试。");
			e.printStackTrace();
		}
		result = jsonObj.toString();
		return SUCCESS;
	}
}
