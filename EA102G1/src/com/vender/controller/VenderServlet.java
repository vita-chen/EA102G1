package com.vender.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.adm.controller.Email;
import com.vender.model.VenderService;
import com.vender.model.VenderVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)


@WebServlet("/VenderServlet")
public class VenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public VenderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//查詢廠商
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
			//錯誤顯示紅字
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			//錯誤顯示紅字
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("vender_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入廠商編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/back_end_home.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String vender_id = null;
				try {
					vender_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("廠商編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/back_end_home.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				VenderService venderSvc = new VenderService();
				VenderVO venderVO = venderSvc.findByPrimaryKey(vender_id);
				if (venderVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/back_end_home.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("venderVO", venderVO); // 資料庫取出的empVO物件,存入req
				String url = "/back_end/vender/list_one_vender.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/back_end_home.jsp");
				failureView.forward(req, res);
			}		
		}
		
		//手機驗證
		if ("insert_phone".equals(action)) {
			//錯誤顯示紅字
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			//錯誤顯示紅字
			try {
			String ven_phone = req.getParameter("ven_phone").trim();
			String ven_phoneReg = "^[(0-9)]{10}$";
			if (ven_phone == null || ven_phone.trim().length() == 0) {
				errorMsgs.add("手機號碼請勿空白");
			} else if(!ven_phone.trim().matches(ven_phoneReg)) { //以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("請輸入正確的手機號碼");
            }
			
			VenderService venderSvc = new VenderService();
			List<String> phoneList = venderSvc.getAllVen_phone();
			
			if (phoneList.contains(ven_phone)) {
				errorMsgs.add("此手機已被註冊");
			}
			
			
			if (!errorMsgs.isEmpty()) {
				
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/vender/vender_regis_phone.jsp");
				failureView.forward(req, res);
				return;
			}
			
			String phone_verification = genAuthCode();
			System.out.println("手機驗證碼:"+phone_verification);
			
			//串接手機驗證API
			SMSHttpService sms=new SMSHttpService();
			String userID="0908280731";	//帳號
			String password="tvev";	//密碼
			String subject="手機驗證";	//簡訊主旨，主旨不會隨著簡訊內容發送出去。用以註記本次發送之用途。可傳入空字串。
			String content="驗證碼為:"+phone_verification;	//簡訊發送內容
			String mobile=ven_phone;	//接收人之手機號碼。格式為: +886912345678或09123456789。多筆接收人時，請以半形逗點隔開( , )，如0912345678,0922333444。
			String sendTime="";	//簡訊預定發送時間。-立即發送：請傳入空字串。-預約發送：請傳入預計發送時間，若傳送時間小於系統接單時間，將不予傳送。格式為YYYYMMDDhhmnss；例如:預約2009/01/31 15:30:00發送，則傳入20090131153000。若傳遞時間已逾現在之時間，將立即發送。
			
			if(sms.getCredit(userID, password)){
				System.out.println(new StringBuffer("取得餘額成功，餘額：").append(String.valueOf(sms.getCreditValue())).toString());
			}else{
				System.out.println(new StringBuffer("取得餘額失敗，失敗原因：").append(sms.getProcessMsg()).toString());
			}
			if(sms.sendSMS(userID, password, subject, content, mobile, sendTime)){
				System.out.println(new StringBuffer("發送簡訊成功，餘額：").append(String.valueOf(sms.getCreditValue())).append("，簡訊批號：").append(sms.getBatchID()).toString());
			}else{
				System.out.println(new StringBuffer("發送簡訊失敗，失敗原因：").append(sms.getProcessMsg()).toString());
			}
			
			//手機session傳值
		    HttpSession session = req.getSession();
		    session.setAttribute("phonesession", phone_verification);
		    session.setAttribute("phone", ven_phone);
		    RequestDispatcher view = req.getRequestDispatcher("/front_end/vender/vender_regis_phone_1.jsp");
		    view.forward(req, res);
			
			}catch (Exception e) {
				errorMsgs.add("手機重複囉");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/vender/vender_regis_phone.jsp");
				failureView.forward(req, res);
			} 
			
		}
		
		//廠商手機session
		if ("insert_phone_session".equals(action)) {
			
			//錯誤顯示紅字
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			//錯誤顯示紅字
			
			String phone_session1 = req.getParameter("phone_session1");
			String phone_session = req.getParameter("phone_session");
			
			if (phone_session == null || phone_session.trim().length() == 0) {
				errorMsgs.add("請輸入驗證碼");
			}
			

			if (!phone_session.equals(phone_session1)) {
				errorMsgs.add("驗證碼錯誤");
			}
			
			
			if (!errorMsgs.isEmpty()) {
				
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/vender/vender_regis_phone_1.jsp");
				failureView.forward(req, res);
				return;
			}
			
			String url = "/front_end/vender/vender_regis.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);

			
		}
		
		
		//廠商註冊
        if ("insert".equals(action)) { // 來自addVender.jsp的請求  
			//錯誤顯示紅字
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			//錯誤顯示紅字
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String ven_account = req.getParameter("ven_account");
				String ven_accountReg = "^[(a-zA-Z0-9)]{6,12}$";
				if (ven_account == null || ven_account.trim().length() == 0) {
					errorMsgs.add("帳號: 請勿空白");
				} else if(!ven_account.trim().matches(ven_accountReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("帳號: 只能是英文字母、數字 , 且長度必需在6到12之間");
	            }
				
				String ven_pwd = req.getParameter("ven_pwd");
				String ven_pwdReg = "^[(a-zA-Z0-9)]{4,12}$";
				if (ven_pwd == null || ven_pwd.trim().length() == 0) {
					errorMsgs.add("密碼: 請勿空白");
				} else if(!ven_pwd.trim().matches(ven_pwdReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("密碼: 只能是英文字母、數字 , 且長度必需在4到12之間");
	            }
				
				String ven_name = req.getParameter("ven_name").trim();
				if (ven_name == null || ven_name.trim().length() == 0) {
					errorMsgs.add("廠商名稱請勿空白");
				}
				String addr1 = req.getParameter("addr1").trim();
				String addr2 = req.getParameter("addr2").trim();
				String addr3 = req.getParameter("addr3").trim();
			    StringBuffer sBuffer = new StringBuffer(addr1+addr2+addr3);
			    String ven_addr = sBuffer.toString();
//				String ven_addr = req.getParameter("ven_addr").trim();
				if (ven_addr == null || ven_addr.trim().length() == 0) {
					errorMsgs.add("廠商地址請勿空白");
				}	
				
				String ven_phone = req.getParameter("ven_phone").trim();
				String ven_phoneReg = "^[(0-9)]{10}$";
				if (ven_phone == null || ven_phone.trim().length() == 0) {
					errorMsgs.add("手機號碼請勿空白");
				} else if(!ven_phone.trim().matches(ven_phoneReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("請輸入正確的手機號碼 09開頭");
	            }	
				
				String ven_contact = req.getParameter("ven_contact").trim();
				if (ven_contact == null || ven_contact.trim().length() == 0) {
					errorMsgs.add("聯絡人請勿空白");
				}	
				
				String ven_mail = req.getParameter("ven_mail").trim();
				if (ven_mail == null || ven_mail.trim().length() == 0) {
					errorMsgs.add("Email請勿空白");
				}	
				
				VenderVO venderVO = new VenderVO();
				venderVO.setVen_name(ven_name);
				venderVO.setVen_addr(ven_addr);
				venderVO.setVen_phone(ven_phone);
				venderVO.setVen_contact(ven_contact);
				venderVO.setVen_mail(ven_mail);
				venderVO.setVen_account(ven_account);
				venderVO.setVen_pwd(ven_pwd);
				
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				Collection<Part> parts = req.getParts();
				for (Part part : parts) {
					if(part.getName().equals("ven_evidence_pic")) {
						InputStream is = part.getInputStream();
						int i;
						byte[] buffer = new byte[8*1024];
						while ((i = is.read(buffer)) != -1) {
							bos.write(buffer, 0, i);;
						}
					}
				}
				byte[] ven_evidence_pic = bos.toByteArray();
				if (ven_evidence_pic == null || ven_evidence_pic.length == 0) {
					errorMsgs.add("請上傳圖片");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("venderVO", venderVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/vender/vender_regis.jsp");
					failureView.forward(req, res);
					return;
				}
				
				
				/***************************2.開始新增資料***************************************/
				VenderService venderSvc = new VenderService();
				venderVO = venderSvc.add_vender(ven_name, ven_addr, ven_phone,ven_contact,ven_mail,
						 ven_evidence_pic,ven_account,ven_pwd);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front_end/home/session_off_regis.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("帳號重複囉");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/vender/vender_regis.jsp");
				failureView.forward(req, res);
			} 
		}
        
		//廠商登入
		if("vender_login".equals(action)) {
			List<String> errorMsgs = new ArrayList<String>();
			String ven_account = req.getParameter("ven_account");
			if (ven_account.trim().length() == 0) {
				errorMsgs.add("帳號不能空白");
			}
			String ven_pwd = req.getParameter("ven_pwd");
			if (ven_pwd.trim().length() == 0) {
				errorMsgs.add("密碼不能空白");
			}
			
			if (! errorMsgs.isEmpty()) {
				RequestDispatcher view = req.getRequestDispatcher("/front_end/vender/vender_login.jsp");
				req.setAttribute("errorMsgs", errorMsgs);
				view.forward(req, res);
				return;
			}
			
			VenderService service = new VenderService();
			List<String> accountList = service.getAllVender();
			if (!accountList.contains(ven_account)) {
				errorMsgs.add("帳號錯誤");
				RequestDispatcher view = req.getRequestDispatcher("/front_end/vender/vender_login.jsp");
				req.setAttribute("errorMsgs", errorMsgs);
				view.forward(req, res);
				return;
			}
			VenderVO vendervo = service.vender_login(ven_account);
		    if (!ven_pwd.equals(vendervo.getVen_pwd())) {
		    	errorMsgs.add("密碼錯誤");
				RequestDispatcher view = req.getRequestDispatcher("/front_end/vender/vender_login.jsp");
				req.setAttribute("errorMsgs", errorMsgs);
				view.forward(req, res);
				return;
		    }
		    
		    //成功登入
		    HttpSession session = req.getSession();
		    session.setAttribute("vendervo", vendervo);
		    RequestDispatcher view = req.getRequestDispatcher("/front_end/vender/vender_home.jsp");
		    view.forward(req, res);
		}
		
		//登出
		if("session_off".equals(action)) {
			
			RequestDispatcher view = req.getRequestDispatcher("/front_end/home/session_off.jsp");
		    view.forward(req, res);
		
		}
		
		
		// 廠商圖片
		if ("getphot".equals(action)) {
			VenderService service = new VenderService();
			VenderVO vendervo = service.findByPrimaryKey(req.getParameter("vender_id"));
			//service.getOne_For_Display(req.getParameter("vonder_id"));
			ServletOutputStream sos = res.getOutputStream();
			
			if (vendervo.getVen_evidence_pic()!=null) {
				ByteArrayInputStream bis = new ByteArrayInputStream(vendervo.getVen_evidence_pic());
				byte[] buffer = new byte[8192];
				int i = 0;
				while ((i=bis.read(buffer)) != -1) {
					sos.write(buffer, 0 ,i);
				}
				bis.close();
				sos.close();
			} else {
				InputStream is = getServletContext().getResourceAsStream("/img/null_img.jpg");
				byte[] buffer = new byte[8192];
				int i = 0;
				while ((i=is.read(buffer)) != -1) {
					sos.write(buffer, 0 ,i);
				}
				is.close();
				sos.close();
			}
		} 
		
		//廠商修改
		if ("update".equals(action)) {
			
			//錯誤顯示紅字
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			//錯誤顯示紅字
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				String vender_id = req.getParameter("vender_id");
							
				String ven_pwd = req.getParameter("ven_pwd");
				String ven_pwdReg = "^[(a-zA-Z0-9)]{4,12}$";
				if (ven_pwd == null || ven_pwd.trim().length() == 0) {
					errorMsgs.add("密碼: 請勿空白");
				} else if(!ven_pwd.trim().matches(ven_pwdReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("密碼: 只能是英文字母、數字 , 且長度必需在4到12之間");
	            }
				
				String ven_name = req.getParameter("ven_name").trim();
				if (ven_name == null || ven_name.trim().length() == 0) {
					errorMsgs.add("廠商名稱請勿空白");
				}		
				
				String addr1 = req.getParameter("addr1").trim();
				String addr2 = req.getParameter("addr2").trim();
				String addr3 = req.getParameter("addr3").trim();
			    StringBuffer sBuffer = new StringBuffer(addr1+addr2+addr3);
			    String ven_addr = sBuffer.toString();
//				String ven_addr = req.getParameter("ven_addr").trim();
				if (ven_addr == null || ven_addr.trim().length() == 0) {
					errorMsgs.add("廠商地址請勿空白");
				}	
				
				String ven_phone = req.getParameter("ven_phone").trim();
				String ven_phoneReg = "^[(0-9)]{10}$";
				if (ven_phone == null || ven_phone.trim().length() == 0) {
					errorMsgs.add("手機號碼請勿空白");
				} else if(!ven_phone.trim().matches(ven_phoneReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("請輸入正確的手機號碼");
	            }	
				
				String ven_contact = req.getParameter("ven_contact").trim();
				if (ven_contact == null || ven_contact.trim().length() == 0) {
					errorMsgs.add("聯絡人請勿空白");
				}	
				
				String ven_mail = req.getParameter("ven_mail").trim();
				if (ven_mail == null || ven_mail.trim().length() == 0) {
					errorMsgs.add("Email請勿空白");
				}	
				
				VenderVO venderVO = new VenderVO();
				venderVO.setVender_id(vender_id);
				venderVO.setVen_name(ven_name);
				venderVO.setVen_addr(ven_addr);
				venderVO.setVen_phone(ven_phone);
				venderVO.setVen_contact(ven_contact);
				venderVO.setVen_mail(ven_mail);
				venderVO.setVen_pwd(ven_pwd);
				
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				Collection<Part> parts = req.getParts();
				for (Part part : parts) {
					if(part.getName().equals("ven_evidence_pic")) {
						InputStream is = part.getInputStream();
						int i;
						byte[] buffer = new byte[8*1024];
						while ((i = is.read(buffer)) != -1) {
							bos.write(buffer, 0, i);;
						}
					}
				}
				byte[] ven_evidence_pic = bos.toByteArray();
				if (ven_evidence_pic == null || ven_evidence_pic.length == 0) {
					errorMsgs.add("請上傳圖片");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("venderVO", venderVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/vender/vender_management/vender_modify.jsp");
					failureView.forward(req, res);
					return;
				}
				
				
				/***************************2.開始新增資料***************************************/
				VenderService venderSvc = new VenderService();
				venderVO = venderSvc.update_vender(ven_name,ven_addr,ven_phone,ven_contact,ven_mail,
						ven_evidence_pic,ven_pwd,vender_id);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front_end/home/session_off_modify.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				System.out.println(e);
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/vender/vender_management/vender_modify.jsp");
				failureView.forward(req, res);
			} 
		
					
		}
		
		// 廠商驗證
		if ("update_vender_enable".equals(action)) {

			try {

				String vender_id = req.getParameter("vender_id");

				VenderVO venderVO = new VenderVO();
				/*************************** 2.開始修改資料 ***************************************/
				VenderService venderSvc = new VenderService();
				venderVO = venderSvc.update_vender_enable(vender_id);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/vender/list_all_verification_vender.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/vender/list_all_verification_vender.jsp");
				failureView.forward(req, res);
			}

		}
		
		// 廠商封鎖
		if ("delete_vender".equals(action)) {

			try {

				String vender_id = req.getParameter("vender_id");

				VenderVO venderVO = new VenderVO();
				/*************************** 2.開始修改資料 ***************************************/
				VenderService venderSvc = new VenderService();
				venderVO = venderSvc.delete_vender(vender_id);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/vender/list_all_blockade_vender.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/vender/list_all_blockade_vender.jsp");
				failureView.forward(req, res);
			}

		}
		
		//寄送廠商未驗證修改資料連結
		if ("vender_modifyemail".equals(action)) {
			String ven_mail = req.getParameter("ven_mail");
			String ven_account = req.getParameter("ven_account");
			
			//email套件
			VenderEmail aa = new VenderEmail();
			//自訂方法
			aa.vender_email(ven_mail,ven_account);
			
			String url = "/back_end/vender/list_all_verification_vender.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
		}
		
		//廠商未驗證修改資料 連結
		if ("vender_test".equals(action)) {
			
			String ven_account = req.getParameter("ven_account");
			
			VenderService service = new VenderService();
			VenderVO vendervo = service.vender_login(ven_account);
			HttpSession session = req.getSession();
			session.setAttribute("vendervo", vendervo);
			
			String url = "/back_end/vender/modify_vender.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
			
		}
		
		//廠商未驗證修改資料
		if ("update_modify".equals(action)) {

			// 錯誤顯示紅字
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			// 錯誤顯示紅字
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String vender_id = req.getParameter("vender_id");

				String ven_pwd = req.getParameter("ven_pwd");
				String ven_pwdReg = "^[(a-zA-Z0-9)]{4,12}$";
				if (ven_pwd == null || ven_pwd.trim().length() == 0) {
					errorMsgs.add("密碼: 請勿空白");
				} else if (!ven_pwd.trim().matches(ven_pwdReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("密碼: 只能是英文字母、數字 , 且長度必需在4到12之間");
				}

				String ven_name = req.getParameter("ven_name").trim();
				if (ven_name == null || ven_name.trim().length() == 0) {
					errorMsgs.add("廠商名稱請勿空白");
				}

				String addr1 = req.getParameter("addr1").trim();
				String addr2 = req.getParameter("addr2").trim();
				String addr3 = req.getParameter("addr3").trim();
				StringBuffer sBuffer = new StringBuffer(addr1 + addr2 + addr3);
				String ven_addr = sBuffer.toString();
//						String ven_addr = req.getParameter("ven_addr").trim();
				if (ven_addr == null || ven_addr.trim().length() == 0) {
					errorMsgs.add("廠商地址請勿空白");
				}

				String ven_phone = req.getParameter("ven_phone").trim();
				String ven_phoneReg = "^[(0-9)]{10}$";
				if (ven_phone == null || ven_phone.trim().length() == 0) {
					errorMsgs.add("手機號碼請勿空白");
				} else if (!ven_phone.trim().matches(ven_phoneReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("請輸入正確的手機號碼");
				}

				String ven_contact = req.getParameter("ven_contact").trim();
				if (ven_contact == null || ven_contact.trim().length() == 0) {
					errorMsgs.add("聯絡人請勿空白");
				}

				String ven_mail = req.getParameter("ven_mail").trim();
				if (ven_mail == null || ven_mail.trim().length() == 0) {
					errorMsgs.add("Email請勿空白");
				}

				VenderVO venderVO = new VenderVO();
				venderVO.setVender_id(vender_id);
				venderVO.setVen_name(ven_name);
				venderVO.setVen_addr(ven_addr);
				venderVO.setVen_phone(ven_phone);
				venderVO.setVen_contact(ven_contact);
				venderVO.setVen_mail(ven_mail);
				venderVO.setVen_pwd(ven_pwd);

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				Collection<Part> parts = req.getParts();
				for (Part part : parts) {
					if (part.getName().equals("ven_evidence_pic")) {
						InputStream is = part.getInputStream();
						int i;
						byte[] buffer = new byte[8 * 1024];
						while ((i = is.read(buffer)) != -1) {
							bos.write(buffer, 0, i);
							;
						}
					}
				}
				byte[] ven_evidence_pic = bos.toByteArray();
				if (ven_evidence_pic == null || ven_evidence_pic.length == 0) {
					errorMsgs.add("請上傳圖片");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("venderVO", venderVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/vender/modify_vender.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				VenderService venderSvc = new VenderService();
				venderVO = venderSvc.update_vender(ven_name, ven_addr, ven_phone, ven_contact, ven_mail,
						ven_evidence_pic, ven_pwd, vender_id);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/home/session_off_modify_vender.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				System.out.println(e);
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/vender/modify_vender.jsp");
				failureView.forward(req, res);
			}

		}
		
	}
	
	public String genAuthCode() {
		String str = "0123456789";
		StringBuffer sb = new StringBuffer();
		
		for(int x=0;x<=3;x++) {
			int y =(int)(Math.random()*str.length());

			sb.append(str.charAt(y));
		}		
		return sb.toString();
	}
}
