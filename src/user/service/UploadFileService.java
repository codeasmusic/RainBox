package user.service;

import java.io.InputStream;
import java.util.Map;

public interface UploadFileService {
	public String uploadFileByPath(Map<String, String> fileInfo,InputStream in);
	public boolean uploadFileCheck(Map<String, String> fileInfo);
	public void saveUploadLog(String email,String fileName);
}
