package user.action;

import user.service.UploadFileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class UploadFileAction implements Action{

	/*上传文件的各项属性*/
	private File uploadfile;
	private String uploadfileFileName;
	private String uploadfileContentType;
	/*文件MD5*/
	private String MD5;
	/*文件在服务器的路径*/
	/*private String path;*/
	/*文件所处当前路径（在数据库内）,其实是文件夹id*/
	private String upperFolderId;
	/*存放信息*/
	private Map<String, String> fileInfo;
	/*private List<Map<String, String>> list;*/
	private String uploadtime;
	/*一个服务对象*/
	private UploadFileService uploadFileService;
	/*5个tag*/
	private String t1;
	private String t2;
	private String t3;
	private String t4;
	private String t5;
	/*账号名*/
	private String useremail;
	private String result;
	private String size;

	/*******************getter setter方法******************************/
	public String getSize(){
		return size;
	}
	public void setSize(String size){
		this.size = size;
	}
	public String getT1() {
		return t1;
	}
	public void setT1(String t1) {
		this.t1 = t1;
	}
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		this.t2 = t2;
	}
	public String getT3() {
		return t3;
	}
	public void setT3(String t3) {
		this.t3 = t3;
	}
	public String getT4() {
		return t4;
	}
	public void setT4(String t4) {
		this.t4 = t4;
	}
	public String getT5() {
		return t5;
	}
	public void setT5(String t5) {
		this.t5 = t5;
	}
	public File getUploadfile() {
		return this.uploadfile;
	}

	public void setUploadfile(File uploadfile) {
		this.uploadfile = uploadfile;
	}

	public String getUploadfileFileName() {
		return uploadfileFileName;
	}

	public void setUploadfileFileName(String uploadfileFileName) {
		this.uploadfileFileName = uploadfileFileName;
	}

	public String getUploadfileContentType() {
		return uploadfileContentType;
	}

	public void setUploadfileContentType(String uploadfileContentType) {
		this.uploadfileContentType = uploadfileContentType;
	}

	public UploadFileService getUploadFileService(){
		return uploadFileService;
	}
	public void setUploadFileService(UploadFileService uploadFileService){
		this.uploadFileService = uploadFileService;
	}
	
	public String getMD5() {
		return MD5;
	}

	public void setMD5(String MD5) {
		this.MD5 = MD5;
	}

	public String getUpperFolderId() {
		return upperFolderId;
	}

	public void setUpperFolderId(String upperFolderId) {
		this.upperFolderId = upperFolderId;
	}
	public String getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(String uploadtime) {
		this.uploadtime = uploadtime;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getUseremail() {
		return useremail;
	}
	
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
/************************以上是get set操作***********************************/
    
	/* 参数:null
	 * 设置要插入数据库的文件信息*/
	public void setUploadInfo(){
		
		fileInfo = new HashMap<String,String>();
		System.out.println("setUploadInfoing...\nMD5:"+MD5);
		System.out.println("uploadtime:"+uploadtime);
		this.useremail = (String) ActionContext.getContext().getSession().get("userEmail");
		fileInfo.put("useremail", useremail);
		String path = "/upload/";
		fileInfo.put("path",path);
		fileInfo.put("MD5", MD5);
		fileInfo.put("upperFolderId",upperFolderId);
		fileInfo.put("uploadfileFileName", uploadfileFileName);
		fileInfo.put("uploadtime",uploadtime);
		fileInfo.put("size",size);
		fileInfo.put("tag1",t1);
		fileInfo.put("tag2",t2);
		fileInfo.put("tag3",t3);
		fileInfo.put("tag4",t4);
		fileInfo.put("tag5",t5);
		fileInfo.put("share", "0");
	}
	
	public String execute(){
		System.out.println("进入execute方法...");
		try {
			System.out.println("获取文件名：" + getUploadfileFileName());
			/*基于Uploadfile创建一个文件输入流*/  
	        InputStream in = new FileInputStream(getUploadfile());
	        /*设置文件信息*/
	        setUploadInfo();
	        uploadFileService.uploadFileByPath(fileInfo, in);
	        uploadFileService.saveUploadLog(getUseremail(),getUploadfileFileName());
	        System.out.println("=====================上传结束===========================");
	        
	        result = "1";
		} catch (FileNotFoundException e) {
			result = "0";
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
		
	}
}
