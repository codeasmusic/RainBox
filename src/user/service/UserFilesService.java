package user.service;

public interface UserFilesService {
	public boolean DeleteFile(String MD5, String fileName, String userEmail, String folderId);
	
	public boolean ModifyFileName(String useremail, String MD5,String NewName);
	
	public String getFilesAndFolders(String email, String folderId );
	
	public void transferToMe(String email, String md5, String fileName,
			String size, String transferTime);
}
