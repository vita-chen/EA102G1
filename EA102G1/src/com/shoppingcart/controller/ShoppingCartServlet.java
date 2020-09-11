package com.shoppingcart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.membre.model.MembreVO;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;
public class ShoppingCartServlet extends HttpServlet{


		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doPost(request, response);
		}

		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			MembreVO membrevo = (MembreVO) session.getAttribute("membrevo");
			String action = request.getParameter("action")	;
			@SuppressWarnings("unchecked")
			List<ProdVO> shoppingCart = (LinkedList<ProdVO>) session.getAttribute("shoppingCart");
			
			if ("buyAgain".equals(action)) {
				String order_no = request.getParameter("order_no");
				ProdService prodSvc = new ProdService();
				List<ProdVO> prodList = prodSvc.getAllByOrder_no(order_no);
				prodList = prodList.stream()
											 .filter(vo -> (vo.getProd_qty() > 0))
											 .collect(Collectors.toList());
				if (shoppingCart ==null) {
					shoppingCart = new LinkedList<ProdVO>();
					for (ProdVO vo : prodList) {
						shoppingCart.add(vo);
					}
				} else {
					for (ProdVO vo : prodList) {
						if (!shoppingCart.contains(vo)) {
							shoppingCart.add(vo);
						}
					}
				}
				session.setAttribute("shoppingCart", shoppingCart);
				RequestDispatcher view = request.getRequestDispatcher("/front_end/order/shoppingCart.jsp");
				view.forward(request, response);
			}
			
			
			if ("addToCart".equals(action)) {
				String prod_no = request.getParameter("prod_no");
				ProdService prodSvc = new ProdService();
				ProdVO prodvo = prodSvc.findByKey(prod_no);
				if (shoppingCart == null) {
					shoppingCart = new LinkedList<ProdVO>();
					shoppingCart.add(prodvo);
				} else if(!shoppingCart.contains(prodvo)) {
					shoppingCart.add(prodvo);
				}
				session.setAttribute("shoppingCart", shoppingCart);
				PrintWriter writer= response.getWriter();
				writer.print(shoppingCart.size());
			} // end of if
		  if ("getCartNum".equals(action)) {
			  PrintWriter writer= response.getWriter();
			  response.setContentType("text/plain");
			  if(shoppingCart ==null) {
				  writer.print("0");
			  } else {
				  writer.print(shoppingCart.size());
			  }
		  }
		  if ("delete".equals(action)) {
			 String prod_no = request.getParameter("prod_no");
			 for (int i = 0; i < shoppingCart.size(); i++) {
				 ProdVO prodvo = shoppingCart.get(i);
				 if(prodvo.getProd_no().equals(prod_no)) {
					 shoppingCart.remove(prodvo);
				 }
			 }
		  }
		  if ("goToCart".equals(action)) {
			  if (membrevo == null) {
				  String location = request.getParameter("location");
				  System.out.println(location);
				  request.setAttribute("location", location);
				  RequestDispatcher view =request.getRequestDispatcher("/front_end/membre/login.jsp");
				  view.forward(request, response);
				  return;
			  }
			  response.sendRedirect(request.getContextPath() + "/front_end/order/shoppingCart.jsp");
		  }
		} // end of doPost
		
//		private ProdVO getProdVO(HttpServletRequest request) {
//			String jsonStr = request.getParameter("jsonStr");
//			
//			JSONObject jsonObj = new JSONObject(jsonStr);
//			
//			String prod_no = jsonObj.getString("prod_no");
//			String prod_name = jsonObj.getString("prod_name");
//			String price = jsonObj.getString("price");
//			String membre_id = jsonObj.getString("membre_id");
//			Integer prod_qty = Integer.parseInt(jsonObj.getString("prod_qty"));
//
//			ProdVO prodvo = new ProdVO();
//			prodvo.setProd_no(prod_no);
//			prodvo.setMembre_id(membre_id);
//			prodvo.setPrice(Integer.parseInt(price));
//			prodvo.setProd_name(prod_name);
//			prodvo.setProd_qty(prod_qty);
//			
//			return prodvo;
//		}
	} // end of class

