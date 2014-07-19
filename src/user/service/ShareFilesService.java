package user.service;

public interface ShareFilesService {
	public String shareFile(String sharePath, String md5);
	public boolean cancelShare(String email, String md5, String uploadTime);
	public String getSharedFiles();
	public String getMyShare(String email);
	public void saveShareLog(String email,String fileName);
	public void saveCancelShareLog(String email,String fileName);
}
