package com.forum.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.forum.model.*;
import com.forum_mes.model.*;


public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//查單個
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("forum_id");
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入文章編號");
				}
				
				
				String forum_id = null;
				try {
					forum_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("文章編號格式不正確");
				}
				
				//mes
//				String mes = req.getParameter("mes_text");
//				if (mes == null || (mes.trim()).length() == 0) {
//					errorMsgs.add("請輸入留言編號");
//				}

				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/forum/listAllForum.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ForumService frSvc = new ForumService();
				ForumVO forumVO = frSvc.getOneForum(forum_id);
				if (forumVO == null) {
					errorMsgs.add("查無資料");
				}
				
				//getmes
				
				MesService mesSvc = new MesService();
				List<MesVO> meslist = mesSvc.getOneByForum_id(forum_id);
				if (meslist == null) {
					errorMsgs.add("查無資料");
				}
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/forum/listAllForum.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				System.out.println(forumVO.getForum_id());
				System.out.println(forumVO.getForum_title());
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("forumVO", forumVO); // 資料庫取出的forumVO物件,存入req
				
				req.setAttribute("meslist", meslist);	  // 資料庫取出的mesVO物件,存入req
				
				String url = "/front_end/forum/listOneForum.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneForum.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/forum/listAllForum.jsp");
				failureView.forward(req, res);
			}
		}

//		修改單筆資料
		if ("getOne_For_Update".equals(action)) { // 來自listAllForum.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String forum_id = new String(req.getParameter("forum_id"));

				/*************************** 2.開始查詢資料 ****************************************/
				ForumService frSvc = new ForumService();
				ForumVO forumVO = frSvc.getOneForum(forum_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("forumVO", forumVO); // 資料庫取出的forumVO物件,存入req
				String url = "/front_end/forum/update_forum_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_forum_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/forum/listAllForum.jsp");
				failureView.forward(req, res);
			}
		}

//修改
		if ("update".contentEquals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String forum_id = new String(req.getParameter("forum_id").trim());

				String membre_id = new String(req.getParameter("membre_id").trim());

				String forum_class = req.getParameter("forum_class");

				String forum_title = req.getParameter("forum_title");

//				String forumReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,60}$";
				if (forum_title == null || forum_title.trim().length() == 0) {
					errorMsgs.add("文章標題: 請勿空白");
				} 
//					else if (!forum_title.trim().matches(forumReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("文章標題: 只能是中、英文字母、數字和_, 且長度必需在2到60之間");
//				}

				String forum_content = req.getParameter("forum_content").trim();

				if (forum_content == null || forum_content.trim().length() == 0) {
					errorMsgs.add("文章內容: 請勿空白");
				}

				java.sql.Timestamp forum_addtime = null;

				ForumVO forumVO = new ForumVO();
				forumVO.setForum_id(forum_id);
				forumVO.setMembre_id(membre_id);
				forumVO.setForum_class(forum_class);
				forumVO.setForum_title(forum_title);
				forumVO.setForum_content(forum_content);
				forumVO.setForum_addtime(forum_addtime);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("forumVO", forumVO); // 含有輸入格式錯誤的articleVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/forum/update_forum_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				ForumService frSvc = new ForumService();
				forumVO = frSvc.updateForum(forum_id, membre_id, forum_class, forum_title, forum_content,
						forum_addtime);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("forumVO", forumVO); // 資料庫update成功後,正確的的articleVO物件,存入req
				String url = "/front_end/forum/listOneForum.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneArticle.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/forum/update_forum_input.jsp");
				failureView.forward(req, res);
			}
		}

//新增
		if ("insert".equals(action)) { // 來自addForum.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/**************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
				String membre_id = new String(req.getParameter("membre_id").trim());

				String forum_class = req.getParameter("forum_class");

				String forum_title = req.getParameter("forum_title");
				String forumReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,50}$";
				if (forum_title == null || forum_title.trim().length() == 0) {
					errorMsgs.add("文章標題: 請勿空白");
				} else if (!forum_title.trim().matches(forumReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("文章標題: 只能是中、英文字母、數字和_, 且長度必需在2到50之間");
				}

				String forum_content = req.getParameter("forum_content").trim();
				if (forum_content == null || forum_content.trim().length() == 0) {
					errorMsgs.add("文章內容: 請勿空白");
				}

				java.sql.Timestamp forum_addtime = null;

				ForumVO forumVO = new ForumVO();
				forumVO.setMembre_id(membre_id);
				forumVO.setForum_class(forum_class);
				forumVO.setForum_title(forum_title);
				forumVO.setForum_content(forum_content);
				forumVO.setForum_addtime(forum_addtime);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("forumVO", forumVO); // 含有輸入格式錯誤的articleVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/forum/addForum.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 **********************************/
				ForumService frSvc = new ForumService();
				forumVO = frSvc.addForum(membre_id, forum_class, forum_title, forum_content, forum_addtime);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/forum/listAllForum.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllArticle.jsp
				successView.forward(req, res);

				/***************************
				 * 其他可能的錯誤處理
				 **********************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/forum/addForum.jsp");
				failureView.forward(req, res);
			}
		}
//刪除
		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/******************** 1.接收請求參數 *******************************/
				String forum_id = new String(req.getParameter("forum_id"));

				/******************* 2.開始刪除資料 **********************************/
				ForumService frSvc = new ForumService();
				frSvc.deleteForum(forum_id);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/forum/listAllForum.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/forum/listAllForum.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
