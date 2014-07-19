package user.service;

public interface PersonInformationService {
	public String getNickName(String userEmail);
	public String getHeadName(String userEmail);
	public String getSex(String userEmail);
	public String getBirthday(String userEmail);
	public String getIntroduction(String userEmail);
	public String getLevel(String userEmail);
	public String getShareNum(String userEmail);
	public String getFileNum(String userEmail);
	public String getUsedCapacity(String userEmail);
	public String getCapacity(String userEmail);
}
