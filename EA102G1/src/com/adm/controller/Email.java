package com.adm.controller;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email{
	
//    public static void main(String[] args) {
//    
//    	Email h1 = new Email();
//    	
//    	Email aaa = new Email();
//    	
//    	String pwd = aaa.genAuthCode();
//    	
//    	System.out.println(pwd);
//
//    }
	
//	public void run() {
//		try {
//	        Email aa = new Email();
//	        
//	    	Email aaa = new Email();
//	    	
//	    	String pwd = aaa.genAuthCode();
//	        
//	        aa.email(getName(),pwd);
//			
//			
//		} catch (Exception e) {
//			System.out.println("被中斷了");
//		}
//	}
	
    /*
     * 需要
     * javamail.jar
     * session.jar
     * 如果import是使用Ctrl shift+O，↓這兩個import要修改成這樣，不然它會import到別的
     * import javax.mail.Authenticator;
     * import javax.mail.PasswordAuthentication;
     */

	public void email(String email,String pwdd) {
		
			String user = "a0908280731@gmail.com";//user
	        String pwd = "zz199227m1224389";//password
	        String to= email;
	        String from = "a0908280731@gmail.com";//寄件人的email
	        /*
	         * host
	         * yahoo:"smtp.mail.yahoo.com"
	         * outlook:"smtp-mail.outlook.com"
	         * 
	         * 1.GMAIL需檢查 IMAP 存取是否已啟用
	         * https://support.google.com/mail/answer/7126229?hl=zh-Hant
	         * 2.GOOGLE開啟低安全性應用程式
	         * https://support.google.com/accounts/answer/6010255?hl=zh-Hant
	         */
	        String host ="smtp.gmail.com" ;

	        String subject = "管理員密碼"; //主旨
	        String body = "請妥善保管密碼:"+pwdd; //內文

	        // 建立一個Properties來設定Properties
	        Properties properties = System.getProperties();
	        //設定傳輸協定為smtp
	        properties.setProperty("mail.transport.protocol", "smtp");
	        //設定mail Server
	        properties.setProperty("mail.smtp.host", host);
	        properties.setProperty("mail.smtp.port", "465");//安全資料傳輸層 (SSL) 通訊埠：465
	        
	        properties.put("mail.smtp.auth", "true");//需要驗證帳號密碼
	        //SSL authentication
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.starttls.enable", "true");

	       // 建立一個Session物件，並把properties傳進去
	        Session mailSession = Session.getInstance(properties, new Authenticator(){
	            protected PasswordAuthentication getPasswordAuthentication(){
	                 return new PasswordAuthentication(user,pwd);
	            }
	        });  
	        
	        try {
	            //建立一個 MimeMessage object.
	            MimeMessage message = new MimeMessage(mailSession);
	            //設定郵件
	            message.setFrom(new InternetAddress(from)); // 設定寄件人
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 設定收件人
	            message.setSubject(subject); // 設定主旨

	            // 宣告一個multipart , 它可以使內文有不同的段落，
	            //使其可以用用來夾帶內文及檔案
	            Multipart multipart = new MimeMultipart();
	            //第一個段落
	            BodyPart messageBodyPart = new MimeBodyPart(); //宣告一個BodyPart用以夾帶內文     
	            messageBodyPart.setText(body);//設定內文
	            multipart.addBodyPart(messageBodyPart); //把BodyPart加入Multipart  
	            //第二個段落
//	            BodyPart fileBodyPart = new MimeBodyPart(); //宣告一個BodyPart用以夾帶附加檔案
//	            String filename = "要送的檔案路徑";  //要夾帶的檔案名稱  
//	            DataSource source = new FileDataSource(filename);//讀取檔案
//	            fileBodyPart.setDataHandler(new DataHandler(source));
//	            fileBodyPart.setFileName("要顯示的檔案名稱"); //設定附加檔案顯示的名稱
//	            multipart.addBodyPart(fileBodyPart);//把BodyPart加入Multipart（這個part夾帶檔案）
	            
	            message.setContent(multipart); //設定eMultipart為messag的Content           
	            Transport transport = mailSession.getTransport("smtp");
	            transport.connect(host ,user, pwd);
	            //傳送信件         
	            transport.sendMessage(message,message.getAllRecipients());
	            
	            //關閉連線
	            transport.close();
	            
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        }
	                

	}
	
	public String genAuthCode() {
		String str = "0123456789abcdefghijkmnopqrstuvwxyz";
		StringBuffer sb = new StringBuffer();
		
		for(int x=0;x<=3;x++) {
			int y =(int)(Math.random()*str.length());

			sb.append(str.charAt(y));
		}		
		return sb.toString();
	}
	
}