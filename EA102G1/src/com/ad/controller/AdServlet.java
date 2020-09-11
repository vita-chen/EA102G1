package com.ad.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;

import com.ad.model.*;

import java.io.*;
import java.util.*;

@MultipartConfig
public class AdServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

// 新增
		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				// String ad_detail, byte[] ad_pic
				String ad_detail = req.getParameter("ad_detail").trim();
				if (ad_detail == null || ad_detail.trim().length() == 0) {
					errorMsgs.add("廣告內容請勿空白");
				}


				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				Collection<Part> parts = req.getParts();
				for (Part part : parts) {
					if (part.getName().equals("ad_pic")) {
						InputStream is = part.getInputStream();
						int i;
						byte[] buffer = new byte[8 * 1024];
						while ((i = is.read(buffer)) != -1) {
							bos.write(buffer, 0, i);
						}
					}
				}
				byte[] ad_pic = bos.toByteArray();
				if (ad_pic == null || ad_pic.length == 0) {
					errorMsgs.add("請上傳圖片");
				}
				
				AdVO adVO = new AdVO();
				adVO.setAd_detail(ad_detail);
				adVO.setAd_pic(ad_pic);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adVO", adVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ad/addAd.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				AdService adSvc = new AdService();
				adVO = adSvc.addAd(ad_detail, ad_pic);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/ad/listAllAd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ad/addAd.jsp");
				failureView.forward(req, res);
			}
		}

// 圖片
		if ("getphot".equals(action)) {
			AdService adSvc = new AdService();
			AdVO adVO = adSvc.getOneAd(req.getParameter("ad_id"));
			
			ByteArrayInputStream bis = new ByteArrayInputStream(adVO.getAd_pic());
			ServletOutputStream sos = res.getOutputStream();
			byte[] buffer = new byte[8192];
			int i = 0;
			while ((i=bis.read(buffer)) != -1) {
				sos.write(buffer, 0 ,i);
			}
			bis.close();
			sos.close();
		} 

// 修改
		if("update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String ad_id = req.getParameter("ad_id");
				
				String ad_detail = req.getParameter("ad_detail");
				if (ad_detail == null || ad_detail.trim().length() == 0) {
					errorMsgs.add("內容: 請勿空白");
				}
				
				AdVO adVO = new AdVO();
				adVO.setAd_id(ad_id);
				adVO.setAd_detail(ad_detail);
				
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				Collection<Part> parts = req.getParts();
				for (Part part : parts) {
					if (part.getName().equals("ad_pic")) {
						InputStream is = part.getInputStream();
						int i;
						byte[] buffer = new byte[8 * 1024];
						while ((i = is.read(buffer)) != -1) {
							bos.write(buffer, 0, i);
						}
					}
				}
				byte[] ad_pic = bos.toByteArray();
				if (ad_pic == null || ad_pic.length == 0) {
					errorMsgs.add("請上傳圖片");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adVO", adVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/ad/update_ad_input.jsp");
				failureView.forward(req, res);
				return;
			}
				
				/*********************2.開始新增******************************/
				AdService adSvc = new AdService();
				adVO = adSvc.updateAd(ad_pic, ad_detail, ad_id);
				
				/********************3.新增完成，準備轉交*******************/
				String url = "/back_end/ad/listAllAd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ad/update_ad_input.jsp");
				failureView.forward(req, res);
			}
		}

// 查詢
		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("ad_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ad/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String ad_id = null;
				try {
					ad_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ad/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				AdService AdSvc = new AdService();
				AdVO adVO = AdSvc.getOneAd(ad_id);
				if (adVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/adm/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("adVO", adVO); // 資料庫取出的admVO物件,存入req
				String url = "/back_end/ad/listOneAdm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneADM.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ad/select_page.jsp");
				failureView.forward(req, res);
			}
		}

//查詢修改
		/*************************************************************************/
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String ad_id = new String(req.getParameter("ad_id"));

				/*************************** 2.開始查詢資料 *****************************************/
				AdService AdSvc = new AdService();
				AdVO adVO = AdSvc.getOneAd(ad_id);
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("adVO", adVO); // 資料庫取出的admVO物件,存入req
				String url = "/back_end/ad/update_ad_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneADM.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/ad/listAllAd.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
