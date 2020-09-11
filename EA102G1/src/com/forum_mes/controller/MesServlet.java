package com.forum_mes.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.forum.model.ForumService;
import com.forum.model.ForumVO;
import com.forum_mes.model.*;

public class MesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOne_For_Display".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("forum_mes_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入留言編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/forum/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String forum_mes_id = null;
				try {
					forum_mes_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("檢舉編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/forum/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************2.開始查詢資料**********************************/
				MesService mesSvc = new MesService();
				MesVO mesVO = mesSvc.getOneMes(forum_mes_id);
				if (mesVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/forum/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("mesVO", mesVO);
				String url = "/front_end/forum/listOneForum.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneArtResponse.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************/
			} catch  (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/forum/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllArtResponse.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String forum_mes_id = new String(req.getParameter("forum_mes_id"));
				
				/***************************2.開始查詢資料****************************************/
				MesService mesSvc = new MesService();
				MesVO mesVO = mesSvc.getOneMes(forum_mes_id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("mesVO", mesVO);        // 資料庫取出的mesVO物件,存入req
				
				String url = "/front_end/forum/update_emp_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/forum/listAllMes.jsp");
				failureView.forward(req, res);
			}
		}
		
	if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			
				/*String forum_mes_id,String membre_id, String forum_id, 
			String mes_text, Timestamp mes_addtime*/
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String forum_mes_id = new String(req.getParameter("forum_mes_id").trim());
				
				String membre_id = new String(req.getParameter("membre_id").trim());
				
				String forum_id = new String(req.getParameter("forum_id").trim());
				
				String mes_text = req.getParameter("mes_text");
				
				if (mes_text == null || mes_text.trim().length() == 0) {
					errorMsgs.add("文章內容: 請勿空白");
				}
				
				java.sql.Timestamp mes_addtime = null;
				
				MesVO mesVO = new MesVO();
				mesVO.setForum_mes_id(forum_mes_id);
				mesVO.setMembre_id(membre_id);
				mesVO.setForum_id(forum_id);
				mesVO.setMes_text(mes_text);
				mesVO.setMes_addtime(mes_addtime);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mesVO", mesVO); // 含有輸入格式錯誤的mesVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/forum/update_emp_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MesService mesSvc = new MesService();
				mesVO = mesSvc.updateMes(forum_mes_id, membre_id, forum_id, mes_text, mes_addtime);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("mesVO", mesVO); // 資料庫update成功後,正確的的mesVO物件,存入req
				String url = "/front_end/forum/listOneForum.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneArtResponse.jsp
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/forum/update_emp_input.jsp");
				failureView.forward(req, res);
			}
		}
	
	if ("insert".equals(action)) { // 來自addMes.jsp的請求  
		
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);

			/*String membre_id, String forum_id, 
			String mes_text, Timestamp mes_addtime*/
		try {
			/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			String membre_id = new String(req.getParameter("membre_id").trim());
			
			String forum_id = new String(req.getParameter("forum_id").trim());
			
			String mes_text = req.getParameter("mes_text");
			
			if (mes_text == null || mes_text.trim().length() == 0) {
				errorMsgs.add("文章內容: 請勿空白");
			}
			
			java.sql.Timestamp mes_addtime = null;
			
			MesVO mesVO = new MesVO();
			mesVO.setMembre_id(membre_id);
			mesVO.setForum_id(forum_id);
			mesVO.setMes_text(mes_text);
			mesVO.setMes_addtime(mes_addtime);
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("mesVO", mesVO); // 含有輸入格式錯誤的mesVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/forum/addMes.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/***************************2.開始新增資料***************************************/
			//新增的部分
			MesService mesSvc = new MesService();
			mesVO = mesSvc.addMes(membre_id, forum_id, mes_text, mes_addtime);
			req.setAttribute("mesVO", mesVO);
			
			//get forumVO的部分
			ForumService frSvc = new ForumService();
			ForumVO forumVO = frSvc.getOneForum(forum_id);
			req.setAttribute("forumVO", forumVO);
			
			//get meslist的部分
			List<MesVO> meslist = mesSvc.getOneByForum_id(forum_id);
			req.setAttribute("meslist", meslist);
			
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			String url = "/front_end/forum/listOneForum.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllArtResponse.jsp
			successView.forward(req, res);		
			
			/***************************其他可能的錯誤處理**********************************/
		} catch  (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front_end/forum/listOneForum.jsp");
			failureView.forward(req, res);
		}
	}
	
	if ("delete".equals(action)) { // 來自listAllArtResponse.jsp

		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/***************************1.接收請求參數***************************************/
			String forum_mes_id = new String(req.getParameter("forum_mes_id"));
			
			/***************************2.開始刪除資料***************************************/
			MesService mesSvc = new MesService();
			mesSvc.deleteMes(forum_mes_id);
			
			/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
			String url = "/front_end/forum/listAllMes.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
			
			/***************************其他可能的錯誤處理**********************************/
		} catch (Exception e) {
			errorMsgs.add("刪除資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front_end/forum/listAllMes.jsp");
			failureView.forward(req, res);
		}
	}
		
  }
}
