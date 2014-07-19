package user.service;

public interface UserSearchService {
	public String searchSharedFiles(String content);
	public String searchMyFiles(String userEmail, String fileName);
}
