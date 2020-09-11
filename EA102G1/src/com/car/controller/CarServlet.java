package com.car.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.car.model.*;
import com.carPic.model.*;
import com.vender.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024) // 沒加這行的話，insert的參數會拿不到(getParameter)
public class CarServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		/*******************拿圖片********************/
		if ("getCarPic".equals(action)) {
			res.setContentType("image/jpeg"); // 拿圖片的ContentType要先宣告「image/jpeg"」，這樣Browser看到Response Headers有此tag後，就會知道URL帶的參數是圖片不是文字
			
			try {
				String cpic_id = req.getParameter("cpic_id"); // 用PK取圖片
				CarPicVO carPicVO = new CarPicVO();
				carPicVO.setCpic_id(cpic_id);
				CarService carSvc = new CarService();
				carSvc.getOneCarPic(carPicVO); // 打包成carPicVO後丟給Carservice，svc會再透過getOneCarPic()方法丟到carPicDAO去進行insert圖片的操作
											   // 這樣下面的轉換圖片輸出的carPicVO.getCpic()才能拿到值(不然最一剛開始創建carPicVO時，carPicVO是沒有值的)
				// 轉換圖片輸出
				if(carPicVO.getCpic()!=null) {
				ByteArrayInputStream bis = new ByteArrayInputStream(carPicVO.getCpic());
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
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		/*********************** 模擬對接vender資料開始(因為所有STMT都需要vendorID) *************************/
		
		HttpSession se = req.getSession();  // 先取得目前這個用戶(廠商)的資料空間出來(new一個HttpSession，並且也拿到目前這個用戶的Session)
											// 尚未登入，登入行為是下面三行在做的事情。此行只是一個開口，讓se可以指向到Session暫存的資料區域。
 		/**** 日後對接完之後可以拿掉(因為這三行是為了塞vendervo所做的操作，setAttribute) ********/
//		VenderVO venTemp = new VenderVO(); // 為了在session塞資料出來，所以要創建一個實體(為了在「VenderVO venVo = (VenderVO) se.getAttribute("venVo");」從session讀進來)  創建完實體後要將實體塞到session內，「VenderVO venVo = (VenderVO) se.getAttribute("VenderVO");」是要將之從session拿出來
//									 // 第一步：先建一個Temp的VO
//	
//		venTemp.setVender_id("V001"); // 第二步：建完之後assign廠商ID給這個Temp的VO(先hardcode)
//		se.setAttribute("vendervo", (Object) venTemp); // 第三步： 在session上去設定venVO。也就是第二步assign完之後把內有廠商ID的Temp的這個VO塞到session上去
													// 把「venTemp」這個複雜的class先轉成Object，塞到Attribute裡
		/************/
		VenderVO vendervo = (VenderVO) se.getAttribute("vendervo"); 	// 第四步：從session拿venVO出來
															// 再把目前這個用戶(廠商)的資料拿出來(注意(Object)要轉回成原本的資料型態(VenVO)，才有辦法getAttribute )

		
		/*********************** 模擬對接vender資料結束 *************************/
		
		
		
		/*********************** insert *************************/
		if ("insert".equals(action)) { // 來自addcar.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String cbrand = req.getParameter("cbrand");
			System.out.println(cbrand);
				if (cbrand == null || cbrand.trim().length() == 0) {
					errorMsgs.add("車子品牌請勿空白");
				}

				String cmodel = req.getParameter("cmodel").trim();
			System.out.println(cmodel);
				if (cmodel == null || cmodel.trim().length() == 0) {
					errorMsgs.add("車子型號請勿空白");
				}

				String cintro = req.getParameter("cintro").trim();
			System.out.println(cintro);
				if (cintro == null || cintro.trim().length() == 0) {
					errorMsgs.add("車子介紹請勿空白");
				}				
				
				Integer cprice = null;
				try {
					cprice = new Integer(req.getParameter("cprice").trim());
			System.out.println(cprice);
				} catch (NumberFormatException e) {
					cprice = 0;
					errorMsgs.add("請填上一天的租金(數字)。");
				}
				
				/*一.取上半部的parameter(把carVO的所有參數先組好)*/
				CarVO carVO = new CarVO();
				carVO.setVender_id(vendervo.getVender_id()); // 拿到vendervo(整合對接後拿到的)
				carVO.setCbrand(cbrand);
				carVO.setCmodel(cmodel);
				carVO.setCintro(cintro);
				carVO.setCprice(cprice);


				// 二.檢查圖片(取下半部的parameter，組出carPicVO)
				// 先處理照片(try-catch裡面利用增強型的for-loop，將3張照片依序讀出再塞到carPicVO，再塞到array(即carPicVoList)裡面)
				Collection<Part> parts = req.getParts(); // 因為input的type是file，只要是file就要用getParts()而不是getParameter()。
				List<CarPicVO> carPicVoList = new ArrayList<CarPicVO>(); // 為了要call service，因為要把拿到的圖片塞到資料庫裡
																		 // 此carPicVoList裡現在只有照片，會依序把照片1、2、3塞到carPicVO後再塞到carPicVoList這個array，
				                                                         // 要注意的是同一張照片的其他值都還沒塞(其他值的意思是表格的其他欄位CID跟CPIC_ID還沒塞)
				try {  
					for (Part part: parts) {
															  // Part這個陣列裡有可能會有不同的變數，有能因為有可能會有其他按鈕是設計來傳不是照片的資料
						if (part.getName().equals("cpic")) {  // 所以要像action一樣，要去判斷現在是抓到哪個button，如果等於cpic(在listAllCar.jsp那邊設定input name是cpic)，就是傳圖片
							// 將上傳圖片轉成 ByteArray
							InputStream is = part.getInputStream();
							ByteArrayOutputStream bos = new ByteArrayOutputStream(); // 取到上傳進來的圖片
							byte[] buffer = new byte[8192];
							int i;
							while((i = is.read(buffer)) != -1) {
								bos.write(buffer, 0, i);
							}
							if(bos.toByteArray().length>0){
									// 新增 carPicVO，並放入 List 中
								CarPicVO carPicVO = new CarPicVO();
								carPicVO.setCpic(bos.toByteArray()); //轉成ByteArray
								carPicVoList.add(carPicVO);
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (carPicVoList.size()==0) {
					errorMsgs.add("加購品圖片：請至少上傳一張圖片。");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("carVO", carVO); // 含有輸入格式錯誤的carVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/car/addCar.jsp");
					failureView.forward(req, res);
					return;
				}
				
				
				/*三.上下半部都組好後，開始insert，先新增主表再新增圖片*/
				/*************************** 2.開始新增資料(同一個請求要做兩件事情(對carVO跟carPicVo都要做事情)。先塞carVO，之後再從carPicVoList逐一地塞3張照片的carPicVo，有幾張這片就是塞幾次資料表) ***************************************/
				CarService carSvc = new CarService(); // 要callservice所以要先把service宣告出來
				carSvc.insertCar(carVO);
				System.out.println(carVO.getCid());
				/*
				for (int i = 0; i < carPicVoList.size(); i++)
				{
					carPicVoList.get(i).setCid(carVo.getCid());
					carSvc.insertCarPic(carPicVoList.get(i));
				}
				*/
				for (CarPicVO carPicVo: carPicVoList) {
					carPicVo.setCid(carVO.getCid()); // Cid是car的pk，又是carPic的fk，所以若要拿到禮車編號就要透過carVO才能拿到。
					carSvc.insertCarPic(carPicVo);   // 既然圖片跟cid都拿到了，carPicVo就完成了，可以塞到carSvc，讓carSvc去丟到DAO。
				}
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/vender/car/vender_listAllCar.jsp";
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
		}

		/*********************** Update *************************/
		if ("update".equals(action)) {

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/			
				String cbrand = req.getParameter("cbrand");
				String cmodel = req.getParameter("cmodel");
				String cintro = req.getParameter("cintro");
				Integer cprice = Integer.parseInt(req.getParameter("cprice"));
				Integer cstatus = Integer.parseInt(req.getParameter("cstatus"));
				String cid = req.getParameter("cid");

				CarVO carVO = new CarVO();
				carVO.setCbrand(cbrand);
				carVO.setCmodel(cmodel);
				carVO.setCintro(cintro);
				carVO.setCprice(cprice);
				carVO.setCstatus(cstatus);
				carVO.setCid(cid);
				
				/*************************** 2.開始新增資料 ***************************************/
				CarService carSvc = new CarService();
				carSvc.updateCar(carVO);
				
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				// 302 轉址
//				res.sendRedirect("car_rental_list.jsp");
				
				// 調度
				RequestDispatcher successView = req.getRequestDispatcher("/front_end/vender/car/vender_listAllCar.jsp");
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				PrintWriter out = res.getWriter();
				out.println(e.toString()); // 把SQL指令的錯誤訊息印出來(記得要先把轉交(跳轉OR調度)的程式碼註解掉)
				e.printStackTrace();
			}

		}
		
		/***********************Delete*************************/
		if ("delete".equals(action)) {
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String cid = req.getParameter("cid");
				
				CarVO carVO = new CarVO();
				carVO.setCid(cid); 
				CarPicVO carPicVO = new CarPicVO();
				carPicVO.setCid(cid);
				
				/***************************2.開始新增資料***************************************/			
				CarService carSvc = new CarService();
				carSvc.deleteCarPic(carPicVO);
				carSvc.deleteCar(carVO);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				// 302 轉址
//				res.sendRedirect(req.getContextPath() + "/front-end/car/listAllCar.jsp");
				
				// 調度
				RequestDispatcher successView = req.getRequestDispatcher("/front_end/vender/car/vender_listAllCar.jsp");
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
