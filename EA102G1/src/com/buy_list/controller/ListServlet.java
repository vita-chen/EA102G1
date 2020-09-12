package com.buy_list.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.buy_list.model.ListService;
import com.buy_list.model.ListVO;
import com.membre.model.MembreVO;
import com.prod.model.ProdVO;

public class ListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String action  = request.getParameter("action");
		MembreVO membrevo = (MembreVO) session.getAttribute("membrevo");
		ListService listSvc = new ListService();
		
		if ("getFollowList".equals(action)) {
			if (membrevo ==null)
				return;
			PrintWriter writer = response.getWriter();
			String membre_id = membrevo.getMembre_id();
			List<ProdVO> prodList = listSvc.getAll(membre_id);
			List<String> prod_nos = new ArrayList<String>();
			for (ProdVO prodvo : prodList) {
				prod_nos.add(prodvo.getProd_no());
			}
			writer.print(prod_nos);
		}
		
		if ("add".equals(action)) {
			String prod_no = request.getParameter("prod_no");
			
			if (membrevo ==null) {
				@SuppressWarnings("unchecked")
				List<String> buyList = (ArrayList<String>) session.getAttribute("buyList");
				if (buyList ==null) 
					buyList = new ArrayList<String>();
				
				buyList.add(prod_no);
				session.setAttribute("buyList", buyList);
				return;
			}
			
			String membre_id = membrevo.getMembre_id();
			ListVO listvo = listSvc.getOne(prod_no, membre_id);
			//避免重複新增
			if (listvo.getProd_no() != null)
				return;
			listSvc.add(prod_no, membre_id);
		}
		if ("delete".equals(action)) {
			String membre_id = membrevo.getMembre_id();
			String prod_no = request.getParameter("prod_no");
			listSvc.delete(prod_no, membre_id);
		}
		
		if ("checkBuyList".equals(action)) {
			if (membrevo == null) {
				String location = request.getParameter("location");
				session.setAttribute("location", location);
				response.sendRedirect(request.getContextPath() + "/front_end/membre/login.jsp");
				return;
			}
			RequestDispatcher view = request.getRequestDispatcher("/front_end/buy_list/myWishList.jsp");
			view.forward(request, response);
		}
		
	} // end of doPost
} // end of class
