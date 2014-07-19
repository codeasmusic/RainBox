package user.action;

import java.util.Map;

import user.service.PersonInformationService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class PersonInformationAction implements Action{
	private PersonInformationService pis;
	
	public PersonInformationService getPis() {
		return pis;
	}

	public void setPis(PersonInformationService pis) {
		this.pis = pis;
	}

	public String execute() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		String userEmail= (String)session.get("userEmail");
		
		try{
			ctx.put("nickName", pis.getNickName(userEmail));
			ctx.put("headName", pis.getHeadName(userEmail));
			ctx.put("sex", pis.getSex(userEmail));
			ctx.put("birthday", pis.getBirthday(userEmail));
			ctx.put("introduction", pis.getIntroduction(userEmail));
			ctx.put("level", pis.getLevel(userEmail));
			ctx.put("shareNum", pis.getShareNum(userEmail));
			ctx.put("fileNum", pis.getFileNum(userEmail));
			ctx.put("usedCapacity", pis.getUsedCapacity(userEmail));
			ctx.put("capacity", pis.getCapacity(userEmail));
			ctx.put("savePath", "/style/uploadHead/" + pis.getHeadName(userEmail));
			
			System.out.println(pis.getHeadName(userEmail));
			System.out.println(pis.getIntroduction(userEmail));
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return "success";
	}
}
