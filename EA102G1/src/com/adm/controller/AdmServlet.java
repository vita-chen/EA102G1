package com.adm.controller;


import java.io.IOException;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.adm.model.AdmService;
import com.adm.model.AdmVO;


@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

@WebServlet("/AdmServlet")
public class AdmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdmServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//後臺管理員註冊
		if ("add_adm".equals(action)) { // 來自addVender.jsp的請求  
			//錯誤顯示紅字
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			//錯誤顯示紅字
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/			
				String adm_account = req.getParameter("adm_account").trim();
				if (adm_account == null || adm_account.trim().length() == 0) {
					errorMsgs.add("Email請勿空白");
				}				
				
				String adm_name = req.getParameter("adm_name").trim();
				if (adm_name == null || adm_name.trim().length() == 0) {
					errorMsgs.add("管理員姓名請勿空白");
				}	
				
				//email套件
				Email aa = new Email();
				//genAuthCode是email.Calss套件下的亂數密碼方法
				String adm_pwd = aa.genAuthCode();
				
				AdmVO admVO = new AdmVO();
				admVO.setAdm_account(adm_account);
				admVO.setAdm_pwd(adm_pwd);
				admVO.setAdm_name(adm_name);		

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("admVO", admVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/adm/adm_regis.jsp");
					failureView.forward(req, res);
					return;
				}			
				/***************************2.開始新增資料***************************************/
				AdmService admSvc = new AdmService();
				admVO = admSvc.add_adm(adm_account,adm_pwd,adm_name);
				//寄送eamil
				aa.email(adm_account,adm_pwd);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front_end/home/session_off_adm_regis.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("Email重複囉,請使用另外一個");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/adm/adm_regis.jsp");
				failureView.forward(req, res);
			} 
		}
		
		
		// 後臺管理員登入
		if ("adm_login".equals(action)) {
			List<String> errorMsgs = new ArrayList<String>();
			String adm_account = req.getParameter("adm_account");
			if (adm_account.trim().length() == 0) {
				errorMsgs.add("帳號不能空白");
			}
			String adm_pwd = req.getParameter("adm_pwd");
			if (adm_pwd.trim().length() == 0) {
				errorMsgs.add("密碼不能空白");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher view = req.getRequestDispatcher("/back_end/adm/adm_login.jsp");
				req.setAttribute("errorMsgs", errorMsgs);
				view.forward(req, res);
				return;
			}

			AdmService service = new AdmService();
			List<String> accountList = service.getAlladm_account();
			if (!accountList.contains(adm_account)) {
				errorMsgs.add("帳號錯誤");
				RequestDispatcher view = req.getRequestDispatcher("/back_end/adm/adm_login.jsp");
				req.setAttribute("errorMsgs", errorMsgs);
				view.forward(req, res);
				return;
			}
			AdmVO admvo = service.adm_login(adm_account);
			if (!adm_pwd.equals(admvo.getAdm_pwd())) {
				errorMsgs.add("密碼錯誤");
				RequestDispatcher view = req.getRequestDispatcher("/back_end/adm/adm_login.jsp");
				req.setAttribute("errorMsgs", errorMsgs);
				view.forward(req, res);
				return;
			}

			// 成功登入
			HttpSession session = req.getSession();
			session.setAttribute("admvo", admvo);
			RequestDispatcher view = req.getRequestDispatcher("/back_end/back_end_home.jsp");
			view.forward(req, res);
		}
		
		// 管理員權限修改
		if ("update_adm".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
//String adm_account,String adm_pwd,String adm_name,Integer adm_1,Integer adm_2,Integer adm_3,adm_id
				String adm_id = req.getParameter("adm_id");
				String adm_account = req.getParameter("adm_account");
				String adm_pwd = req.getParameter("adm_pwd");
				String adm_name = req.getParameter("adm_name");
				if (adm_id == null || adm_id.trim().length() == 0) {
					errorMsgs.add("ID請勿空白");
				}
				if (adm_account == null || adm_account.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				}
				if (adm_pwd == null || adm_pwd.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				}
				if (adm_name == null || adm_name.trim().length() == 0) {
					errorMsgs.add("姓名請勿空白");
				}
				
				String adm1 = req.getParameter("adm_1");
				String adm2 = req.getParameter("adm_2");
				String adm3 = req.getParameter("adm_3");
				int adm_1 = Integer.parseInt(adm1);
				int adm_2 = Integer.parseInt(adm2);
				int adm_3 = Integer.parseInt(adm3);

				if ( adm_1 != 0 && adm_1 != 1) {
					errorMsgs.add("權限錯誤");
				}
				if ( adm_2 != 0 && adm_2 != 1) {
					errorMsgs.add("權限錯誤");
				}
				if ( adm_3 != 0 && adm_3 != 1) {
					errorMsgs.add("權限錯誤");
				}
				AdmVO admVO = new AdmVO();
				admVO.setAdm_account(adm_account);
				admVO.setAdm_pwd(adm_pwd);
				admVO.setAdm_name(adm_name);
				admVO.setAdm_1(adm_1);
				admVO.setAdm_2(adm_2);
				admVO.setAdm_3(adm_3);	
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("admVO", admVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/adm/list_all_adm.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始新增資料 ***************************************/
				AdmService admSvc = new AdmService();
				admVO = admSvc.update_adm(adm_account,adm_pwd,adm_name,adm_1,adm_2,adm_3,adm_id);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/adm/list_all_adm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				System.out.println(e);

			}

		}		
	}

}
