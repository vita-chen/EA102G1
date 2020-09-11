package com.carExt.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;


import com.carExt.model.*;
import com.vender.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024) // 沒加這行的話，insert的參數會拿不到(getParameter)
public class CarExtServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		/*******************拿圖片********************/
		if ("getCarExtPic".equals(action)) {
			res.setContentType("image/jpeg"); // 拿圖片的ContentType要先宣告「image/jpeg"」，這樣Browser看到Response Headers有此tag後，就會知道URL帶的參數是圖片不是文字
			
			try {
				String cext_id = req.getParameter("cext_id"); // 用PK取圖片
				CarExtVO carExtVO = new CarExtVO();
				carExtVO.setCext_id(cext_id);
				CarExtService carExtSvc = new CarExtService();
				carExtSvc.getOneCExtPic(carExtVO); // 打包成carExtVO後丟給CarExtService，svc會再透過getOneCarPic()方法丟到carPicDAO去進行insert圖片的操作
											   // 這樣下面的轉換圖片輸出的carPicVO.getCpic()才能拿到值(不然最一剛開始創建carPicVO時，carPicVO是沒有值的)
				// 轉換圖片輸出
				if(carExtVO.getCext_pic()!=null) {
				ByteArrayInputStream bis = new ByteArrayInputStream(carExtVO.getCext_pic());
				ServletOutputStream sos = res.getOutputStream();
				byte[] buffer = new byte[8*1024];
				int i = 0;
				while((i = bis.read(buffer)) != -1) {
					sos.write(buffer, 0, i);
				}
				bis.close();
				sos.close();
				}else {
					InputStream bis = getServletContext().getResourceAsStream("/img/null_img.jpg");
					ServletOutputStream sos = res.getOutputStream();
					byte[] buffer = new byte[8*1024];
					int i = 0;
					while((i = bis.read(buffer)) != -1) {
						sos.write(buffer, 0, i);
					}
					bis.close();
					sos.close();	
				}
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}// end of doGet



	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		/***********************
		 * 模擬對接vender資料開始(因為所有STMT都需要vendorID)
		 *************************/
		HttpSession se = req.getSession();  // 先取得目前這個用戶(廠商)的資料空間出來(new一個HttpSession，並且也拿到目前這個用戶的Session)
											// 尚未登入，登入行為是下面三行在做的事情。此行只是一個開口，讓se可以指向到Session暫存的資料區域。
		/**** 日後對接完之後可以拿掉(因為這三行是為了塞vendervo所做的操作，setAttribute) ********/
//		VenderVO venTemp = new VenderVO(); 	// 第一步：先建一個Temp的VO						
//		venTemp.setVender_id("V001"); 		// 第二步：建完之後assign廠商ID給這個Temp的VO(先hardcode)
//		se.setAttribute("vendervo", (Object) venTemp); // 第三步：
													   // 在session上去設定venVO。也就是第二步assign完之後把內有廠商ID的Temp的這個VO塞到session上去
													   // 把「venTemp」這個複雜的class先轉成Object，塞到Attribute裡
		/************/
		VenderVO vendervo = (VenderVO) se.getAttribute("vendervo"); // 第四步：從session拿venVO出來
														// 再把目前這個用戶(廠商)的資料拿出來(注意(Object)要轉回成原本的資料型態(VenVO)，才有辦法getAttribute

		/*********************** 模擬對接vender資料結束 *************************/
		
		

		/*********************** insert *************************/
		if ("insert".equals(action)) { // 來自addcar.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			


			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				// 加購品類別
				Integer cext_cat_id = Integer.parseInt(req.getParameter("cext_cat_id"));

				String cext_name = req.getParameter("cext_name").trim();
				if (cext_name == null || cext_name.trim().length() == 0) {
					errorMsgs.add("加購品名稱：請勿空白");
				}

				Integer cext_price = null;
				try {
					cext_price = new Integer(req.getParameter("cext_price").trim());
				} catch (NumberFormatException e) {
					cext_price = 0;
					errorMsgs.add("加購品價格：請填數字。");
				}

				// 加購品照片
				Collection<Part> parts = req.getParts(); // 因為input的type是file，只要是file就要用getParts()而不是getParameter()。
				byte[] cext_pic = null; // 初始化時先給一個空值
				try {
					for (Part part : parts) {
						if (part.getName().equals("cext_pic")) { 
																	
							// 將上傳圖片轉成 ByteArray
							InputStream is = part.getInputStream();
							ByteArrayOutputStream bos = new ByteArrayOutputStream(); // 取到上傳進來的圖片
							byte[] buffer = new byte[8192];
							int i;
							while ((i = is.read(buffer)) != -1) {
								bos.write(buffer, 0, i);
							}
							cext_pic = bos.toByteArray();
	System.out.println("f1");
	System.out.println(cext_pic.length);
							break;// 取到一張照片就可以break(但理論上也只會收到一張照片)
						
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (cext_pic == null || cext_pic.length == 0) {
					errorMsgs.add("加購品圖片：請上傳一張圖片。");
				}

				CarExtVO carExtVO = new CarExtVO();
				carExtVO.setVender_id(vendervo.getVender_id());
				carExtVO.setCext_cat_id(cext_cat_id);
				carExtVO.setCext_name(cext_name);
				carExtVO.setCext_price(cext_price);
				carExtVO.setCext_pic(cext_pic); // 轉成ByteArray
			

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("carExtVO", carExtVO); // 含有輸入格式錯誤的carExtVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/car/addCarExt.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				CarExtService carExtSvc = new CarExtService();
				carExtSvc.insert(carExtVO);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/vender/car/vender_listAllCarExt.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllcar.jsp
				successView.forward(req, res);				

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				PrintWriter out = res.getWriter();
				out.println(e.toString()); // 把SQL指令的錯誤訊息印出來(記得要先把轉交(跳轉OR調度)的程式碼註解掉)
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/car/addCar.jsp");
//				failureView.forward(req, res);
			}
		}// end of insert
		
		
		/*********************** Update *************************/
		if ("update".equals(action)) {
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/			
				String cext_name = req.getParameter("cext_name");
				Integer cext_price = Integer.parseInt(req.getParameter("cext_price"));
				Integer cext_status = Integer.parseInt(req.getParameter("cext_status"));
				String cext_id = req.getParameter("cext_id");

				CarExtVO carExtVO = new CarExtVO();
				carExtVO.setCext_name(cext_name);
				carExtVO.setCext_price(cext_price);
				carExtVO.setCext_status(cext_status);
				carExtVO.setCext_id(cext_id);

				// 加購品照片
//				Collection<Part> parts = req.getParts(); // 因為input的type是file，只要是file就要用getParts()而不是getParameter()。
//				byte[] cext_pic = null; // 初始化時先給一個空值
//				try {
//					for (Part part : parts) {
//						if (part.getName().equals("cext_pic")) { 
//																	
//							// 將上傳圖片轉成 ByteArray
//							InputStream is = part.getInputStream();
//							ByteArrayOutputStream bos = new ByteArrayOutputStream(); // 取到上傳進來的圖片
//							byte[] buffer = new byte[8192];
//							int i;
//							while ((i = is.read(buffer)) != -1) {
//								bos.write(buffer, 0, i);
//							}
//							cext_pic = bos.toByteArray();
//							break;// 取到一張照片就可以break(但理論上也只會收到一張照片)
//						}
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}

				/*************************** 2.開始更新資料 ***************************************/
				CarExtService carExtSvc = new CarExtService();
				carExtSvc.update(carExtVO);
				
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				// 302 轉址
//				res.sendRedirect("car_rental_list.jsp");
				
				// 調度
				RequestDispatcher successView = req.getRequestDispatcher("/front_end/vender/car/vender_listAllCarExt.jsp");
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				PrintWriter out = res.getWriter();
				out.println(e.toString()); // 把SQL指令的錯誤訊息印出來(記得要先把轉交(跳轉OR調度)的程式碼註解掉)
				e.printStackTrace();
			}

		} // end of update
		
		
		/***********************Delete*************************/
		if ("delete".equals(action)) {
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String cext_id = req.getParameter("cext_id");
				
				CarExtVO carExtVO = new CarExtVO();
				carExtVO.setCext_id(cext_id);
				
				/***************************2.開始新增資料***************************************/			
				CarExtService carExtSvc = new CarExtService();
				carExtSvc.delete(carExtVO);
				
				
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				// 302 轉址
//				res.sendRedirect(req.getContextPath() + "/front-end/car/listAllCar.jsp");
				
				// 調度
				RequestDispatcher successView = req.getRequestDispatcher("/front_end/vender/car/vender_listAllCarExt.jsp");
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				PrintWriter out = res.getWriter();
				out.println(e.toString());
				e.printStackTrace();
			}
		}		
		
		

	} // end of doPost
} // end of class
