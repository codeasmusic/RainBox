package manager.service;

public interface FilesService {
	public String searchByFileName(String content);
	public String searchByTag(String content);
	public String getFileInfo(String rowKey);
	public boolean deleteFile(String MD5, String rowKey);
}
