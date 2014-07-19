package user.service;

import com.bean.File;
import com.bean.User;

public interface DownloadService {
	public File getFileInfo(String userEmail, String md5);
	public User getUserInfo(String userEmail);
}
