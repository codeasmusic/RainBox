package user.service;

public interface LoginService {
	public boolean exist(String email);
	public boolean match(String email,String password);
	public boolean isDisable(String email);
}
