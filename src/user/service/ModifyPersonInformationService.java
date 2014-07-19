package user.service;

import java.io.File;

public interface ModifyPersonInformationService {
	public void save(String email,String sex,String nickName,
			String birthday,String introduction,String headName);
	public void saveHead (File source,String headName) throws Exception;
}
