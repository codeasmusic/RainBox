package user.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import user.service.PlayAVIService;

import com.opensymphony.xwork2.Action;

public class PlayAVIAction implements Action{

	private String MD5;
	private String fileName;
	private PlayAVIService playaviservice;
	
	public PlayAVIService getPlayaviservice() {
		return playaviservice;
	}
	public void setPlayaviservice(PlayAVIService playaviservice) {
		this.playaviservice = playaviservice;
	}
	public String getMD5() {
		return MD5;
	}
	public void setMD5(String MD5) {
		this.MD5 = MD5;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String execute() throws Exception {
		String path = "http://192.168.1.250:50075/webhdfs/v1/upload/";
		String postfix = ".mp4?op=OPEN&namenoderpcaddress=czh:9000&offset=0";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String playLink = playaviservice.getAviPath(path, MD5, postfix);
		
		request.setAttribute("playLink", playLink);
		request.setAttribute("fileName", fileName);
		return SUCCESS;
	}
	
}
