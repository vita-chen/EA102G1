package com.membre.controller;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailService {
	
	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	public static void sendMail(String to, String mem_name, String url ) {
			
	   try {
		   // 設定使用SSL連線至 Gmail smtp Server
		   Properties props = new Properties();
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.socketFactory.port", "465");
		   props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.port", "465");

       // ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
       // ●須將myGmail的【安全性較低的應用程式存取權】打開
	     final String myGmail = "ixlogic.wu@gmail.com";
	     final String myGmail_password = "BBB45678BBB";
		   Session session = Session.getInstance(props, new Authenticator() {
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(myGmail, myGmail_password);
			   }
		   });

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myGmail));
			message.setContent(
					"<div style='background-color: #edf0f3;'><div style='margin: 0 auto; background-image:url(https://lh3.googleusercontent.com/proxy/3SH5GWT2TtpnEOtLg3TKGcj_64cv2xvmg6oNx2LcMIRWZCc_oi7U3LIskY4xxrdpJqliq-Zdx3CxQ2ffIrpdvv7Xxin8j_v0M6F16Q8R5m6gWv6GbJs8vhIz_Rxe1t8wj-fRbaOLFQ);"
							+ "background-repeat:no-repeat;" 
							+ "background-size:cover; width:500px; height:200px'>"
							+ "<div style='backdrop-filter: blur(2px); padding: 20px;'><H1 style='text-align:center; color: white;'>Hello!" + mem_name + "</H1>"
							+ "<p style='color: white; text-align: center;'>感謝您加入婚禮導航，請點選按鈕進行驗證</p>"
							+ "<div style='margin-top:10px;'><a href=" + url + " style='text-decoration:none;'><div style='border: 1px solid white; width:fit-content; margin:0 auto; background-color:white; border-radius:2px; line-height:40px; font-size:15px; color:#202124;'><div style='margin: 0 12px;'>驗證去</div></div></a></div></div></div>",
							"text/html; charset=UTF-8");

			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			// 設定信中的主旨
			message.setSubject("婚禮導航 帳號驗證");

		   Transport.send(message);
		   System.out.println("傳送成功!");
     }catch (MessagingException e){
	     System.out.println("傳送失敗!");
	     e.printStackTrace();
     }
   }
}