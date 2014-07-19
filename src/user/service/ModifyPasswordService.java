package user.service;

public interface ModifyPasswordService {
	public boolean match(String userEmail,String password);
	public void modify(String userEmail,String password);
}
