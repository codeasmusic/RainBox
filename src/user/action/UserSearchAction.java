package user.action;

import user.service.UserSearchService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class UserSearchAction implements Action{
	private String content;
	private UserSearchService search;
	private String result;
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public UserSearchService getSearch() {
		return search;
	}

	public void setSearch(UserSearchService search) {
		this.search = search;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String execute() throws Exception {
		result = search.searchSharedFiles(content);
		return SUCCESS;
	}
	
	public String searchMyFiles(){
		String userEmail = (String) ActionContext.getContext().getSession().get("userEmail");
		result = search.searchMyFiles(userEmail, content);
		return SUCCESS;
	}
}
