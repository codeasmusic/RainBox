package manager.service;


public interface UserService {
	  public boolean setUserLevel(String userEmail, String level);
	  public boolean setLevelToCapacity(String capacity);
	  public String searchUser(String userEmail);
	  public String getUserInfo(String userEmail);
	  public boolean deleteUsers(String userEmail);
	  public boolean disableUsers(String userEmail);
	  public boolean activeUsers(String userEmail);
}