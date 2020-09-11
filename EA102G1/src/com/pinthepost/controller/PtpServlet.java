package com.pinthepost.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;

import com.ad.model.AdService;
import com.ad.model.AdVO;
import com.pinthepost.model.*;

public class PtpServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//insert
		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				// String ptp_subject, +ptp_subject
				String ptp_subject = req.getParameter("ptp_subject").trim();
				if (ptp_subject == null || ptp_subject.trim().length() == 0) {
					errorMsgs.add("公告主題請勿空白");
				}

				String ptp_detail = req.getParameter("ptp_detail").trim();
				if (ptp_detail == null || ptp_detail.trim().length() == 0) {
					errorMsgs.add("公告內容請勿空白");
				}

				PtpVO ptpVO = new PtpVO();
				ptpVO.setPtp_subject(ptp_subject);
				ptpVO.setPtp_detail(ptp_detail);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ptpVO", ptpVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ptp/addPtp.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				PtpService ptpSvc = new PtpService();
				ptpVO = ptpSvc.addPtp(ptp_detail, ptp_subject);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/ptp/listAllPtp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ptp/addPtp.jsp");
				failureView.forward(req, res);
			}
		}

//update
		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String ptp_id = req.getParameter("ptp_id");
				
				String ptp_subject = req.getParameter("ptp_subject");
				if (ptp_subject == null || ptp_subject.trim().length() == 0) {
					errorMsgs.add("標題: 請勿空白");
				}

				String ptp_detail = req.getParameter("ptp_detail");
				if (ptp_detail == null || ptp_detail.trim().length() == 0) {
					errorMsgs.add("內容: 請勿空白");
				}

				PtpVO ptpVO = new PtpVO();
				ptpVO.setPtp_id(ptp_id);
				ptpVO.setPtp_subject(ptp_subject);
				ptpVO.setPtp_detail(ptp_detail);

				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ptpVO", ptpVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ptp/update_ptp_input.jsp");
					failureView.forward(req, res);
					return;
				}

				/********************* 2.開始新增 ******************************/
				PtpService ptpSvc= new PtpService();
				ptpVO = ptpSvc.updatePtp(ptp_detail, ptp_subject, ptp_id);

				/******************** 3.新增完成，準備轉交 *******************/
				req.setAttribute("ptpVO", ptpVO);
				String url = "/back_end/ptp/listAllPtp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ptp/update_ptp_input.jsp");
				failureView.forward(req, res);
			}
		}

//查詢修改	 "getOne_For_Update"
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String ptp_id = new String(req.getParameter("ptp_id"));

				/*************************** 2.開始查詢資料 *****************************************/
				PtpService PtpSvc = new PtpService();
				PtpVO ptpVO = PtpSvc.getOnePtp(ptp_id);
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("ptpVO", ptpVO); // 資料庫取出的admVO物件,存入req
				String url = "/back_end/ptp/update_ptp_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneADM.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ptp/listAllPtp.jsp");
				failureView.forward(req, res);
			}
		}

	
		
//findByPrimaryKey
		
	 
	}
}
