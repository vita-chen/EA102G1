package com.prod.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.googleCloudVisionAPI.PicDetector;
import com.membre.model.MembreVO;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;
import com.prod_pic.model.ImageUtil;
import com.prod_pic.model.PicDAO;
import com.prod_pic.model.PicVO;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class ProdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		

	
		
		if ("sorting".equals(action)) {
			String type = request.getParameter("type");
			String order = request.getParameter("order");
			String shopper = request.getParameter("shopper");
			ProdService prodSvc = new ProdService();
			List<ProdVO> prodList = prodSvc.sorting(shopper, type, order);
			request.setAttribute("prodList", prodList);
			RequestDispatcher view =request.getRequestDispatcher("/front_end/prod/index.jsp?shopper="+shopper);
			view.forward(request, response);
		}
		
		
		if ("query".equals(action)) {
			String query = request.getParameter("query");
			String shopper = request.getParameter("shopper");
			ProdService prodSvc = new ProdService();
			List<ProdVO> prodList = prodSvc.query(shopper, query);
			request.setAttribute("prodList", prodList);
			RequestDispatcher view =request.getRequestDispatcher("/front_end/prod/index.jsp?shopper="+shopper);
			view.forward(request, response);
		}
		
		
		if ("filter".equals(action)	) {
			String shopper = request.getParameter("shopper");
			String type = request.getParameter("type");
			ProdService prodSvc = new ProdService();
			List<ProdVO> prodList = null;
			if (type == null || type.trim().length() == 0) {
				prodList = prodSvc.getAllByMembre_id(shopper);
			} else {
			    prodList = prodSvc.filterProduct(shopper, type);
			}
			request.setAttribute("prodList", prodList);
			RequestDispatcher view =request.getRequestDispatcher("/front_end/prod/index.jsp?shopper="+shopper);
			view.forward(request, response);
		}
		
		
		if ("toggleStatus".equals(action)) {
			String prod_no = request.getParameter("prod_no");
			ProdService prodSvc = new ProdService();
			ProdVO prodvo = prodSvc.findByKey(prod_no);
			if (prodvo.getProd_status().equals("0")) {
				prodvo.setProd_status("1");
			} else {
				prodvo.setProd_status("0");
			}
			prodSvc.updateProd(prodvo);
			RequestDispatcher view = request.getRequestDispatcher("/front_end/prod/myProds.jsp");
			view.forward(request, response);
		}
		
		if ("getOneForUpdate".equals(action)) {
			String prod_no = request.getParameter("prod_no");
			ProdService prodSvc = new ProdService();
			List<PicVO> piclist = prodSvc.getPics(prod_no);
			session.setAttribute("piclist", piclist);
			ProdVO prodvo = prodSvc.findByKey(prod_no);
			request.setAttribute("prodvo", prodvo);
			RequestDispatcher view = request.getRequestDispatcher("/front_end/prod/updating.jsp");
			view.forward(request, response);
		}
		
		if ("update".equals(action)) {
			List<String> errors = new ArrayList<String>();
			request.setAttribute("errors", errors);
			String prod_name = request.getParameter("prod_name").trim();
			if (prod_name.length() == 0) {
				errors.add ("商品名稱請勿空白");
			}
			if (prod_name.length() > 25) {
				errors.add("商品名稱請勿超過25個字");
			}
			String priceStr = request.getParameter("price");
			Integer price = null;
			if (priceStr.trim().length() ==0) {
				errors.add("價格請勿空白");
			} else {
				try {
					price = Integer.parseInt(priceStr);
				} catch (NumberFormatException e) {
					price = 0;
					errors.add("價格請輸入數字");
				}
				if (price<= 0) {
					errors.add("價格須大於0");
				}
			}
			String qtyStr = request.getParameter("prod_qty");
			Integer prod_qty = null;
			if (qtyStr.trim().length() ==0) {
				errors.add("數量請勿空白");
			} else {
				try {
					prod_qty = Integer.parseInt(qtyStr);
				} catch (NumberFormatException e) {
					prod_qty = 0;
					errors.add("數量請輸入數字");
				}
				if (prod_qty <=0) {
					errors.add("數量須大於0");
				}
			}
			String type_no = request.getParameter("type_no");
			String prod_no = request.getParameter("prod_no");
			ProdService prodSvc = new ProdService();
			ProdVO prodvo = prodSvc.findByKey(prod_no);
			prodvo.setProd_name(prod_name);
			prodvo.setProd_qty(prod_qty);
			prodvo.setPrice(price);
			prodvo.setType_no(type_no);
			prodvo.setProd_no(prod_no);
			
			if (!errors.isEmpty()) {
				request.setAttribute("prodvo", prodvo);
				RequestDispatcher failView = request.getRequestDispatcher("/front_end/prod/updating.jsp");
				failView.forward(request, response);
				return;
			}
			prodSvc.updateProd(prodvo);
			RequestDispatcher view = request.getRequestDispatcher("/front_end/prod/myProds.jsp");
			view.forward(request, response);
		} // end of if
		
		
		if ("add".equals(action)) {
			List<String> errors = new ArrayList<String>();
			request.setAttribute("errors", errors);
			
			MembreVO membrevo = (MembreVO)session.getAttribute("membrevo");
			if (membrevo == null) {
				response.sendRedirect(request.getContextPath() + "/front_end/membre/login.jsp");
				return;
			}
			String membre_id  = membrevo.getMembre_id();
			String type_no =request.getParameter("type_no");
			String prod_name = request.getParameter("prod_name").trim();
			if (prod_name.length() == 0) {
				errors.add ("商品名稱請勿空白");
			} 
			if (prod_name.length() > 25) {
				errors.add ("商品名稱請勿超過25個字");
			}
			String priceStr = request.getParameter("price");
			Integer price = null;
			if (priceStr.trim().length() ==0) {
				errors.add("價格請勿空白");
			} else {
				try {
					price = Integer.parseInt(priceStr);
				} catch (NumberFormatException e) {
					price = 0;
					errors.add("價格請輸入數字");
				}
				if (price<= 0) {
					errors.add("價格須大於0");
				}
			}
			String qtyStr = request.getParameter("prod_qty");
			Integer prod_qty = null;
			if (qtyStr.trim().length() ==0) {
				errors.add("數量請勿空白");
			} else {
				try {
					prod_qty = Integer.parseInt(qtyStr);
				} catch (NumberFormatException e) {
					prod_qty = 0;
					errors.add("數量請輸入數字");
				}
				if (prod_qty <=0) {
					errors.add("數量須大於0");
				}
			} 
			String prod_status = "1";
			long time = System.currentTimeMillis();
			java.sql.Timestamp sale_time = new java.sql.Timestamp(time);
			ProdVO prodvo = new ProdVO();
			prodvo.setMembre_id(membre_id);
			prodvo.setPrice(price);
			prodvo.setProd_qty(prod_qty);
			prodvo.setProd_name(prod_name);
			prodvo.setType_no(type_no);
			prodvo.setProd_status(prod_status);
			prodvo.setSale_time(sale_time);
			if (!errors.isEmpty()) {
				request.setAttribute("prodvo", prodvo);
				RequestDispatcher failView = request.getRequestDispatcher("/front_end/prod/addProd.jsp");
				failView.forward(request, response);
				return;
			}
			//檢查商品圖片
			Collection<Part> parts = request.getParts();
			 String picToPass = request.getParameter("pass");
			 int index= 0;
			PicDetector detector = new PicDetector();
			if (picToPass.trim().length() > 0) {
				for (Part part : parts) {	
					String toPass = String.valueOf(index);
					if (part.getName().equals("pic") && picToPass.indexOf(toPass) == -1) {
						index++;
						if (!detector.detectPic(part)) {
							errors.add("請勿上傳含有人物、動物的圖片");
							break;
						}
					}
					if (part.getName().equals("pic") && picToPass.indexOf(toPass) != -1) {
						index++;
						continue;
					}
				}
			} else {
				for (Part part : parts) {
					if (part.getName().equals("pic") && !detector.detectPic(part)) {
						errors.add("請勿上傳含有人物、動物的圖片");
						break;
					}
				}
			}

			if (!errors.isEmpty()) {
				request.setAttribute("prodvo", prodvo);
				RequestDispatcher failView = request.getRequestDispatcher("/front_end/prod/addProd.jsp");
				failView.forward(request, response);
				return;
			}
			ProdService prodSvc = new ProdService();
			prodSvc.addProd(prodvo);
					//開始新增商品
		    //新增商品圖片
			 String prod_no = prodvo.getProd_no();
			
			index= 0;
			for (Part part : parts) {
				if (part.getName().equals("pic")) {
					String toPass = String.valueOf(index);
					if(picToPass.trim().length() != 0 && picToPass.indexOf(toPass)!=-1) {
						index ++;
						continue;
					}
					prodSvc.insertPic(prod_no, part);
					index++;
				}
			}
			response.sendRedirect(request.getContextPath() + "/front_end/prod/myShop.jsp");
		} // end of if

		if ("deletePic".equals(action)) {
			int pic_no = Integer.parseInt(request.getParameter("pic_no"));
			PicDAO picdao = new PicDAO();
			picdao.delete(pic_no);
		}
		if ("addPic".equals(action)) {
			String prod_no = request.getParameter("prod_no");
			ProdService prodSvc = new ProdService();
			Collection<Part> parts = request.getParts();
			
			for(Part part : parts) {
				if (part.getName().equals("pic")) {
					prodSvc.insertPic(prod_no, part);
				}
			}
			List<PicVO> piclist = prodSvc.getPics(prod_no);
			session.setAttribute("piclist", piclist);
			ProdVO prodvo = prodSvc.findByKey(prod_no);
			request.setAttribute("prodvo", prodvo);
			RequestDispatcher view = request.getRequestDispatcher("/front_end/prod/updating.jsp");
			view.forward(request, response);
		}
		
		if("getOne".equals(action)) {
		 List<String> errors = new ArrayList<String>();
		 request.setAttribute("errors", errors);
		 try {
			 	@SuppressWarnings("unchecked")
			 	ArrayList<PicVO> oldPiclist = (ArrayList<PicVO>)session.getAttribute("piclist");
			   	String string= request.getParameter("pic_no"); 
			   	String number = request.getParameter("number");
			    // 已經進來一次拿到參數一個商品取多張
			   	if(oldPiclist  != null && "many".equals(number)) {
			   		int picno = Integer.parseInt(string);
			   		for (PicVO pic : oldPiclist) {
			   			if(pic.getPic_no().equals(picno)) {
			   				byte[] picBytes = ImageUtil.shrink(pic.getPic(), 800);
							ByteArrayInputStream bis = new ByteArrayInputStream(picBytes);
							ServletOutputStream sos = response.getOutputStream();
							byte[] buffer = new byte[8*1024];
							int i = 0;
							while((i = bis.read(buffer)) != -1) {
								sos.write(buffer, 0, i);
						}
							bis.close();
							sos.close();
							return;
			   			}
			   		}
			 } 
			 	String prod_no= request.getParameter("prod_no");
			 	//開始查詢
				ProdService service  = new ProdService();
				List<PicVO> 	piclist = service.getPics(prod_no);
			   	//每個商品取一張
			   	if (string == null && "one".equals(number)) {
			   		ServletOutputStream sos = response.getOutputStream();
			   		    if (piclist.size() !=0) {
			   		    	PicVO pic = piclist.get(0);
			   		    	byte[] picBytes = ImageUtil.shrink(pic.getPic(), 400);
							ByteArrayInputStream bis = new ByteArrayInputStream(picBytes);
							byte[] buffer = new byte[8*1024];
							int i = 0;
							while((i = bis.read(buffer)) != -1) {
								sos.write(buffer, 0, i);
						}
							bis.close();
							sos.close();
							return;
			   		    }
			   		    InputStream in  = getServletContext().getResourceAsStream("/img/prod_img/defaultPic.png");
						byte[] b = new byte[in.available()];
						in.read(b);
						sos.write(b);
						in.close();	
			   			sos.close();
			   			return;
			   	}
			   //第一次查詢

				session.setAttribute("piclist", piclist);
			   ProdVO prodvo = service.findByKey(prod_no);
			  //送出參數
				request.setAttribute("piclist", piclist);
				request.setAttribute("prodvo", prodvo);
				RequestDispatcher view = request.getRequestDispatcher("/front_end/prod/prod_detail.jsp");
				view.forward(request, response);
				
		 } catch (Exception e) {
			 errors.add("找不到產品資料" + e.getMessage());
			 e.printStackTrace();
			 RequestDispatcher errorview = request.getRequestDispatcher("/front_end/prod/select_page.jsp");
			 errorview.forward(request, response);
		 }
	  }  
		
	} // end of doPost
} // end of class
