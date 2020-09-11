package com.prod_order.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.membre.model.MembreVO;
import com.prod_order.model.OrderService;
import com.prod_order.model.OrderVO;

public class OrderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String action = request.getParameter("action")	;
		OrderService orderSvc = new OrderService();
		
		
		if ("getInnerStatus".equals(action)) {
			String order_no = request.getParameter("order_no");
			OrderVO ordervo = orderSvc.getOneOrder(order_no);
			 PrintWriter writer= response.getWriter();
			 writer.print(ordervo.getOrder_status());
		}
		
		
		if ("getDifferentStatusOrders_buyer".equals(action)) {
			String order_status = request.getParameter("order_status");
			System.out.println(order_status);
			MembreVO membrevo = (MembreVO) session.getAttribute("membrevo");
			if (membrevo ==null) {
				response.sendRedirect(request.getContextPath() + "/front_end/membre/login.jsp");
				return;
			}
			String membre_id = membrevo.getMembre_id();
			List<OrderVO> orderList = null;
			if (order_status.equals("All")) {
				 orderList = orderSvc.getAllOrder(membre_id);
			} else {
				 orderList = orderSvc.getAllBuyerOrderByStatus(membre_id, order_status);
			}
			request.setAttribute("orderList", orderList);
			request.setAttribute("order_status", order_status);
			RequestDispatcher view = request.getRequestDispatcher("/front_end/order/myOrders.jsp");
			view.forward(request, response);
		}
		
		if ("getDifferentStatusOrders_seller".equals(action)) {
			String order_status = request.getParameter("order_status");
			MembreVO membrevo = (MembreVO) session.getAttribute("membrevo");
			if (membrevo ==null) {
				response.sendRedirect(request.getContextPath() + "/front_end/membre/login.jsp");
				return;
			}
			String membre_id = membrevo.getMembre_id();
			List<OrderVO> orderList = null;
			if (order_status.equals("All")) {
				 orderList = orderSvc.getAllSellerOrder(membre_id);
			} else {
				 orderList = orderSvc.getAllSellerOrderByStatus(membre_id, order_status);
			}
			request.setAttribute("orderList", orderList);
			request.setAttribute("order_status", order_status);
			RequestDispatcher view = request.getRequestDispatcher("/front_end/order/myOrders_seller.jsp");
			view.forward(request, response);
		}
		
		if ("changeStatus".equals(action)) {
			List<String> errors = new ArrayList<String>();
			String order_no = request.getParameter("order_no");
			String order_status = request.getParameter("order_status");
			OrderVO ordervo = orderSvc.getOneOrder(order_no);
			String innerStatus = ordervo.getOrder_status();
			if (order_status.equals("0") && innerStatus.equals("2")) {
				errors.add("此訂單已經出貨，無法取消");
				session.setAttribute("errors", errors);
				return;
			}
			if (order_status.equals("2") && innerStatus.equals("0")) {
				errors.add("此訂單已經取消，無法出貨");
				session.setAttribute("errors", errors);
				return;
			}
			ordervo.setOrder_status(order_status);
			orderSvc.updateOrder(ordervo);
		}
		
		if ("order".equals(action)) {
			//移除購物車
			session.removeAttribute("shoppingCart");
			
			MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");
			String membre_id = membrevo.getMembre_id();
			String jsonStr = request.getParameter("json");
			JSONObject jsonObj = new JSONObject(jsonStr);
			Integer total = Integer.parseInt(jsonObj.getString("total"));
			
			
			
			long time = System.currentTimeMillis();
			java.sql.Timestamp order_date = new java.sql.Timestamp(time);
			// 建立order主檔
			OrderVO ordervo = orderSvc.addOrder(membre_id, total, order_date, null, "1");
			//取出order_no以建立order_detail
			String order_no = ordervo.getOrder_no();
//			//準備修改商品剩餘數量
//			ProdService prodSvc = new ProdService();
			
			JSONArray jsonArr = jsonObj.getJSONArray("prodvos");
			for (int i = 0; i < jsonArr.length(); i++) {
				
				JSONObject obj = new JSONObject(jsonArr.getString(i));
				String prod_no = obj.getString("prod_no");
				String qty = obj.getString("quantity");
				Integer order_qty = Integer.parseInt(qty);
				orderSvc.addOrderDetail(order_no, prod_no, order_qty);
				//取出商品更新數量
//				ProdVO prodvo = prodSvc.findByKey(prod_no);
//				prodvo.setProd_qty(prodvo.getProd_qty() - order_qty);
//				prodSvc.updateProd(prodvo);
			}
		}
	} // end of doPost
} // end of class