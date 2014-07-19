package user.implementation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.hbase.HBaseOperation;

import user.service.EmailService;

public class EmailServiceImpl implements EmailService{
	private String host = "";
	private String email = "";
	private String password = "";
	private HBaseOperation hbaseOp = new HBaseOperation();
	
	public void setHost(String host) {
        this.host = host;
    }
	
    public void setAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public void send(String from, String to, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host); // ָ��SMTP������
        props.put("mail.smtp.auth", "true"); // ָ���Ƿ���ҪSMTP��֤
        try {
            Session mailSession = Session.getDefaultInstance(props);
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from)); // ������
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // �ռ���
            message.setSubject(subject); // �ʼ�����
            //ָ���������ݼ�ContentType�ͱ��뷽ʽ
            message.setContent(content, "text/html;charset=utf-8");
            //ָ���ʼ���������
            message.setSentDate(new Date());
            message.saveChanges();
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, email, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

	public boolean sendEmail(String host, String from, String to, String password, 
			String title, String link) {
		setHost(host);
		setAccount(from, password);
		
		Result r = hbaseOp.getRecord("user", to);
		if(r.isEmpty())
			return false;
			
		String forgotPWD = Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("password")));
		send(from, to, title, link+"&id="+getMD5(forgotPWD));
		return true;
	}
	
	public boolean resetPWD(String userEmail, String password, String id) {
		String tableName = "user";
		String rowKey = userEmail;
		String family = "info";
		String qualifier = "password";
		try{
			Result r = hbaseOp.getRecord("user", userEmail);
			String forgotPWD = Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("password")));

			if(id.equals(getMD5(forgotPWD))){
				hbaseOp.addRecord(tableName, rowKey, family, qualifier, password);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String getMD5(String pwd){
		StringBuffer sBuffer = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5Byte = md.digest(pwd.getBytes());
			
			sBuffer = new StringBuffer();
	        for (int i = 0; i < md5Byte.length; i++) {
	            sBuffer.append(md5Byte[i]);
	        }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return sBuffer.toString().replace("-", "");
	}
	
}
