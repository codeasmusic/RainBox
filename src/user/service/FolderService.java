package user.service;

public interface FolderService {
	public String createNewFolder(String folderName, String userEmail, String upperFolderId, String setTime);
	public boolean deleteFolder(String userEmail, String folderId, String upperFolderId);
	public boolean ModifyFolderName(String useremail, String folderID, String NewFolderName);
}
