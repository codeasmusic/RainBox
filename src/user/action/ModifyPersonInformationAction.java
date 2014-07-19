package user.action;

import java.io.File;
import java.util.Map;

import user.service.ModifyPersonInformationService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class ModifyPersonInformationAction implements Action {
	private String sex;
	private String nickName;
	private String birthday;
	private String introduction;
	private File image;
	private String imageFileName;
	private String imageContentType;
	private ModifyPersonInformationService mpis;
	
	
	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getIntroduction() {
		return introduction;
	}


	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}


	public File getImage() {
		return image;
	}


	public void setImage(File image) {
		this.image = image;
	}


	public String getImageFileName() {
		return imageFileName;
	}


	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}


	public String getImageContentType() {
		return imageContentType;
	}


	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}


	public ModifyPersonInformationService getMpis() {
		return mpis;
	}


	public void setMpis(ModifyPersonInformationService mpis) {
		this.mpis = mpis;
	}


	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		String email = (String)session.get("userEmail");
		
		String imgName = "";
		String savePath = "/style/";
		if(getImage()!=null){
			System.out.println(getImage().getName());
			
			String postfix = imageFileName.substring(imageFileName.lastIndexOf(".")
					, imageFileName.length());
			imgName = email + postfix;
			savePath += "uploadHead/" + imgName;
			mpis.saveHead(getImage(),imgName);
		}else{
			savePath += "pic/user.png";
		}
		
		session.put("nickName",nickName);
		session.put("savePath",savePath);
		session.put("sex", sex);
		session.put("birthday", birthday);
		session.put("introduction", introduction);
		
		mpis.save(email,sex,nickName,birthday,introduction,imgName);
		return "success";
	}

}
