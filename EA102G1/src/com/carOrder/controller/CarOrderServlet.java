package com.carOrder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.model.CarService;
import com.car.model.CarVO;
import com.carExt.model.CarExtService;
import com.carExt.model.CarExtVO;
import com.carExtOrder.model.*;
import com.carOrder.model.*;

public class CarOrderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		/*********************** checkout *************************/

		if ("checkout".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String membre_id = req.getParameter("membre_id");
				String vender_id = req.getParameter("vender_id");
				String cid = req.getParameter("cid");

				java.sql.Date cneed_date = null;
				try {
					cneed_date = java.sql.Date.valueOf(req.getParameter("cneed_date").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入租車日期");
				}

				java.sql.Date creturn_date = null;
				try {
					creturn_date = java.sql.Date.valueOf(req.getParameter("creturn_date").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入還車日期");
				}

				String cstart = req.getParameter("cstart").trim();
				if (cstart.length() == 0) {
					errorMsgs.add("起點請勿空白");
				}
				String cdest = req.getParameter("cdest").trim();
				if (cdest.length() == 0) {
					errorMsgs.add("目的地請勿空白");
				}

				// 若有客戶點選加購品，會在此接收點選的參數。
				String[] cext_id_list = req.getParameterValues("cext_id");

				if (errorMsgs.size() != 0) {
					System.out.println("e");
					RequestDispatcher error = req.getRequestDispatcher("/front_end/carOrder/carReservation.jsp");
					error.forward(req, res);
					return;
				}
				/***************************
				 * 2-1.開始收集訂單資料以顯示在結帳頁
				 ***************************************/

				CarOrderVO carOrderVO = new CarOrderVO();
				carOrderVO.setMembre_id(membre_id);
				carOrderVO.setVender_id(vender_id);
				carOrderVO.setCid(cid);
				carOrderVO.setCneed_date(cneed_date);
				carOrderVO.setCreturn_date(creturn_date);
				carOrderVO.setCstart(cstart);
				carOrderVO.setCdest(cdest);

				req.setAttribute("carOrderVO", carOrderVO);

				/***************************
				 * 2-2.開始收集訂單加購品資料以顯示在結帳頁
				 ***************************************/
				List<CarExtVO> carExtVOList = new ArrayList<CarExtVO>();
				CarExtService carExtSvc = new CarExtService();
				for (String cext_id : cext_id_list) {
					CarExtVO carExtVO = new CarExtVO();
					carExtVO.setCext_id(cext_id);
					carExtSvc.getOneCExt(carExtVO);
					carExtVOList.add(carExtVO);
				}

				req.setAttribute("carExtVOList", carExtVOList);

				/***************************
				 * 算錢
				 ***************************************/

				Integer cfinal_price = 0;
				Integer cdeposit = 0;
				Integer creturn_pay = 0;
				CarVO carVO = new CarVO();
				carVO.setCid(cid);
				CarService carSvc = new CarService();
				carSvc.getOneCar(carVO);
				
				java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");    
				java.util.Date beginDate= format.parse(req.getParameter("cneed_date").trim());    
				java.util.Date endDate= format.parse(req.getParameter("creturn_date").trim());    
				int day=(int)((endDate.getTime()-beginDate.getTime())/(24*60*60*1000))+1;   //相隔的天數 +1

				
				cfinal_price += carVO.getCprice()*day; // 等同於cfinal_price = cfinal_price + carVO.getCprice();

				for (CarExtVO carExtVO : carExtVOList) {
					cfinal_price += carExtVO.getCext_price();
				}

				cdeposit = (int) (cfinal_price * 0.3);
				creturn_pay = cdeposit;

				carOrderVO.setCfinal_price(cfinal_price);
				carOrderVO.setCdeposit(cdeposit);
				carOrderVO.setCreturn_pay(creturn_pay);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 調度
				String page = "/front_end/carOrder/carCheckout.jsp";
				if (errorMsgs.size() > 0)
					page = "/front_end/carOrder/carReservation.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(page);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				PrintWriter out = res.getWriter();
				out.println(e.toString()); // 把SQL指令的錯誤訊息印出來(記得要先把轉交(跳轉OR調度)的程式碼註解掉)
				e.printStackTrace();
			}

		} // end of checkout

		/*********************** insert *************************/
		if ("insert".equals(action))

		{ // 來自carReservation.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String membre_id = req.getParameter("membre_id");
				String vender_id = req.getParameter("vender_id");
				String cid = req.getParameter("cid");

				java.sql.Date cneed_date = null;
				try {
					cneed_date = java.sql.Date.valueOf(req.getParameter("cneed_date").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入租車日期");
				}

				java.sql.Date creturn_date = null;
				try {
					creturn_date = java.sql.Date.valueOf(req.getParameter("creturn_date").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入還車日期");
				}

				String cstart = req.getParameter("cstart").trim();
				if (cstart.length() == 0) {
					errorMsgs.add("起點請勿空白");
				}
				String cdest = req.getParameter("cdest").trim();
				if (cdest.length() == 0) {
					errorMsgs.add("目的地請勿空白");
				}

				// 若有客戶點選加購品，會在此接收點選的參數。
				String[] cext_id_list = req.getParameterValues("cext_id");

				if (errorMsgs.size() != 0) {
					System.out.println("e");
					RequestDispatcher error = req.getRequestDispatcher("/front_end/carOrder/carReservation.jsp");
					error.forward(req, res);
					return;
				}
				/***************************
				 * 2-1.開始insert訂單資料
				 ***************************************/

				CarOrderVO carOrderVO = new CarOrderVO();
				carOrderVO.setMembre_id(membre_id);
				carOrderVO.setVender_id(vender_id);
				carOrderVO.setCid(cid);
				carOrderVO.setCneed_date(cneed_date);
				carOrderVO.setCreturn_date(creturn_date);
				carOrderVO.setCstart(cstart);
				carOrderVO.setCdest(cdest);

				/***************************
				 * 算錢
				 ***************************************/

				Integer cfinal_price = 0;
				Integer cdeposit = 0;
				Integer creturn_pay = 0;
				CarVO carVO = new CarVO();
				carVO.setCid(cid);
				CarService carSvc = new CarService();
				carSvc.getOneCar(carVO);
				
				
				
				java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");    
				java.util.Date beginDate= format.parse(req.getParameter("cneed_date").trim());    
				java.util.Date endDate= format.parse(req.getParameter("creturn_date").trim());    
				int day=(int)((endDate.getTime()-beginDate.getTime())/(24*60*60*1000))+1;   //相隔的天數 +1

				
				cfinal_price += carVO.getCprice()*day; // 等同於cfinal_price = cfinal_price + carVO.getCprice();
				

				List<CarExtVO> carExtVOList = new ArrayList<CarExtVO>();
				CarExtService carExtSvc = new CarExtService();
				for (String cext_id : cext_id_list) {
					CarExtVO carExtVO = new CarExtVO();
					carExtVO.setCext_id(cext_id);
					carExtSvc.getOneCExt(carExtVO);
					carExtVOList.add(carExtVO);
				}

				for (CarExtVO carExtVO : carExtVOList) {
					cfinal_price += carExtVO.getCext_price();
				}

				cdeposit = (int) (cfinal_price * 0.3);
				creturn_pay = cdeposit;

				carOrderVO.setCfinal_price(cfinal_price);
				carOrderVO.setCdeposit(cdeposit);
				carOrderVO.setCreturn_pay(creturn_pay);

				/***************************
				 * 寫入資料庫
				 ***************************************/
				CarOrderService carOrderSvc = new CarOrderService();
				carOrderSvc.insertCarOrder(carOrderVO);

				// 開始insert訂單加購品資料並寫入資料庫
				CarExtOrderVO carExtOrderVO = new CarExtOrderVO();
				for (String cext_id : cext_id_list) {
					carExtOrderVO.setCext_id(cext_id);
					carExtOrderVO.setCod_id(carOrderVO.getCod_id());
					carOrderSvc.insertCarExtOrder(carExtOrderVO);
				}

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 302 轉址
				res.sendRedirect(req.getContextPath() + "/front_end/membre_order/membre_order_car.jsp");


				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				PrintWriter out = res.getWriter();
				out.println(e.toString()); // 把SQL指令的錯誤訊息印出來(記得要先把轉交(跳轉OR調度)的程式碼註解掉)
				e.printStackTrace();
			}

		} // end of insert

		/*********************** 更新訂單狀態 *************************/
		if ("updateCarOrderStatus".equals(action)) { // 來自carReservation.jsp的請求
			try {

				/*********************** 1.接收請求參數 *************************/
				String cod_id = req.getParameter("cod_id");
				Integer cod_status = Integer.parseInt(req.getParameter("cod_status"));

				CarOrderVO carOrderVO = new CarOrderVO();
				carOrderVO.setCod_id(cod_id);
				carOrderVO.setCod_status(cod_status);

				/*************************** 2.開始更新訂單狀態 ***************************************/
				CarOrderService carOrderSvc = new CarOrderService();
				carOrderSvc.updateCarOrderStatus(carOrderVO);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/

				// 調度
				String referrer = req.getHeader("referer");
				if (referrer.indexOf("listAllCarOrderMem") != -1) {

					res.sendRedirect(req.getContextPath() + "/front_end/membre_order/membre_order_car.jsp");

				} else if (referrer.indexOf("listAllCarOrderVen") != -1) {
					res.sendRedirect(req.getContextPath() + "/front_end/vender/car/vender_listAllCarOrderVen.jsp");

				}

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				PrintWriter out = res.getWriter();
				out.println(e.toString()); // 把SQL指令的錯誤訊息印出來(記得要先把轉交(跳轉OR調度)的程式碼註解掉)
				e.printStackTrace();
			}

		} // end of updateCarOrderStatus

		/*********************** 會員提交評價星數&心得 *************************/

		if ("submitReview".equals(action)) // 來自listAllCarOrderMem.jsp的請求
		{

			try {

				/*********************** 1.接收請求參數 *************************/
				String cod_id = req.getParameter("cod_id");
				String crev_msgs = req.getParameter("crev_msgs");
				Integer crev_star = Integer.parseInt(req.getParameter("crev_star"));

				System.out.println(cod_id);
				System.out.println(crev_msgs);
				System.out.println(crev_star);

				CarOrderVO carOrderVO = new CarOrderVO();
				carOrderVO.setCod_id(cod_id);
				carOrderVO.setCrev_star(crev_star);
				carOrderVO.setCrev_msgs(crev_msgs);

				/***************************
				 * 2.開始更新訂單評價&星數
				 ***************************************/
				CarOrderService carOrderSvc = new CarOrderService();
				carOrderSvc.submitReview(carOrderVO);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 302 轉址
				res.sendRedirect(req.getContextPath() + "/front_end/membre_order/membre_order_car.jsp");

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				PrintWriter out = res.getWriter();
				out.println(e.toString()); // 把SQL指令的錯誤訊息印出來(記得要先把轉交(跳轉OR調度)的程式碼註解掉)
				e.printStackTrace();
			}

		} // end of submitReview
	}
}// end of class

//System.out.println(membre_id);
//System.out.println(vender_id);
//System.out.println(cid);
//System.out.println(cfinal_price);
//System.out.println(cdeposit);
//System.out.println(creturn_pay);
//System.out.println(cneed_date);
//System.out.println(creturn_date);
//System.out.println(cstart);
//System.out.println(cdest);

// System.out.println(cext_id);
