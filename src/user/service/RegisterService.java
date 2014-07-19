package user.service;

public interface RegisterService {
	public boolean exist(String email);
	public void insert(String email,String password);
}
