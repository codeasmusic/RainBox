package user.service;

public interface EmailService {
	public boolean sendEmail(String host, String from, String to, String password, String title, String link);
	public boolean resetPWD(String userEmail, String password, String id);
}
