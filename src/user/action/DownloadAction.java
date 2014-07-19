package user.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import user.service.DownloadService;

import com.bean.File;
import com.bean.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class DownloadAction implements Action{
	private DownloadService ds;

	public DownloadService getDs() {
		return ds;
	}

	public void setDs(DownloadService ds) {
		this.ds = ds;
	}

	public String execute() throws Exception {
		String userEmail = (String) ActionContext.getContext().getSession().get("userEmail");
		if(userEmail == null){
			userEmail = "";
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		String sharedEmail = (String) request.getParameter("sharer");
		String MD5 = (String) request.getParameter("MD5");
		
		File fileInfo = ds.getFileInfo(sharedEmail, MD5);
		String longUploadTime = fileInfo.getUploadTime();
		Date uploadTime = new Date();
		uploadTime.setTime(Long.valueOf(longUploadTime));
		fileInfo.setUploadTime(uploadTime.toString());
		request.setAttribute("fileInfo", fileInfo);
		request.setAttribute("longUploadTime", longUploadTime);
		
		String downloadLink = "http://192.168.1.250:50075/webhdfs/v1/upload/" 
				+ MD5 + ".avi?op=OPEN&namenoderpcaddress=czh:9000&offset=0";
		request.setAttribute("downloadLink", downloadLink);
		boolean isSharer = userEmail.equals(sharedEmail);
		request.setAttribute("isSharer", isSharer);
		
		User sharer = ds.getUserInfo(sharedEmail); 
		request.setAttribute("sharer", sharer);
		return SUCCESS;
	}
	
}
