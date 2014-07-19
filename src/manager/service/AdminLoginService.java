package manager.service;

public interface AdminLoginService {
	public boolean exist(String adminName);
	public boolean match(String adminName,String password);
}
