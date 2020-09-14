package com.membre.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.buy_list.model.ListService;
import com.buy_list.model.ListVO;
import com.membre.model.MembreService;
import com.membre.model.MembreVO;
import com.orderWebSocket.controller.OrderNoticeSender;


@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class MembreServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		MembreService memSvc = new MembreService();
		
		if ("verifyAccount".equals(action)) {
			String key = request.getParameter("key");
			ServletContext context = getServletConfig().getServletContext();
			@SuppressWarnings("unchecked")
			HashMap<String, String> keyMap = (HashMap<String, String>)  context.getAttribute("keyMap");
			if (keyMap != null) {
				String membre_id = keyMap.get(key);
				if (membre_id == null) {
					System.out.println("null");
					List<String> errors = new ArrayList<String>();
					errors.add("此驗證無效，請重新驗證");
					request.setAttribute("errors", errors);
					RequestDispatcher view = request.getRequestDispatcher("/front_end/home/home.jsp");
					view.forward(request, response);
					return;
				}
				MembreVO membrevo = memSvc.getOneById(membre_id);
				membrevo.setStatus("0");
				memSvc.update(membrevo);
				session.setAttribute("membrevo", membrevo);
				
			    List<String> buyList = (ArrayList<String>) session.getAttribute("buyList");
			    if (buyList != null) {
				    ListService listService = new ListService();
				    for (String prod_no : buyList) {
				    	ListVO listvo = listService.getOne(prod_no, membre_id);
				    	if (listvo.getProd_no() != null)
							continue;
				    		listService.add(prod_no, membre_id);
				    }
			    }
				
				request.setAttribute("verifySuccess", "驗證成功");
				RequestDispatcher view = request.getRequestDispatcher("/front_end/home/home.jsp");
				view.forward(request, response);
			}
		}
		
		
		if ("openShop".equals(action)) {
			MembreVO membrevo = (MembreVO) session.getAttribute("membrevo");
			if (membrevo == null) {
				response.sendRedirect(request.getContextPath() + "/front_end/membre/login.jsp");
				return;
			}
			membrevo.setStatus("1");
			memSvc.update(membrevo);
			RequestDispatcher view = request.getRequestDispatcher("/front_end/prod/myShop.jsp");
			view.forward(request, response);
		}
		
		if ("logOut".equals(action)) {
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/front_end/home/home.jsp");
		}
		

	 

		
		//註冊
		if("regis".equals(action)) {

			String mem_name = request.getParameter("mem_name");
			String sex = request.getParameter("sex");
			String address = "我家";
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String status = "2";
			String compte = email;
			String passe = request.getParameter("passe");
			MembreVO membrevo = new MembreVO();
			membrevo.setMem_name(mem_name);
			membrevo.setSex(sex);
			membrevo.setAddress(address);
			membrevo.setPhone(phone);
			membrevo.setEmail(email);
			membrevo.setCompte(compte);
			membrevo.setPasse(passe);
			List<String> errors = new ArrayList<String>();
			if (compte.trim().length() == 0) {
				errors.add("帳號不可為空");
			}
			if (passe.trim().length() == 0) {
				errors.add("密碼不可為空");
			}
			if (! errors.isEmpty()) {
				RequestDispatcher view = request.getRequestDispatcher("/front_end/membre/regis.jsp");
				request.setAttribute("errors", errors);
				request.setAttribute("errorMembrevo", membrevo);
				view.forward(request, response);
				return;
			}
			List<String> accountList = memSvc.getAllAccount();
			if (accountList.contains(compte)) {
				errors.add("此信箱已被註冊");
				RequestDispatcher view = request.getRequestDispatcher("/front_end/membre/regis.jsp");
				request.setAttribute("errors", errors);
				request.setAttribute("errorMembrevo", membrevo);
				view.forward(request, response);
				return;
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			Collection<Part> parts = request.getParts();
			for (Part part : parts) {
				if(part.getName().equals("photo")) {
					InputStream is = part.getInputStream();
					int i;
					byte[] buffer = new byte[8*1024];
					while ((i = is.read(buffer)) != -1) {
						bos.write(buffer, 0, i);;
					}
					break;
				}
			}
			byte[] photo = bos.toByteArray();
			long time = System.currentTimeMillis();
			java.sql.Timestamp regis_time = new java.sql.Timestamp(time);
			
			MembreVO newMembrevo = memSvc.regis(mem_name, sex, address, phone, email, status, compte, passe, photo, regis_time);
			String key = KeyGenerator.genAuthCode();
			ServletContext context = getServletConfig().getServletContext();
			HashMap<String, String> keyMap = (HashMap<String, String>) context.getAttribute("keyMap");
			if (keyMap == null) {
				keyMap = new HashMap<String, String>();
			}
			context.setAttribute("keyMap", keyMap);
			keyMap.put(key, newMembrevo.getMembre_id());
			String url = "http://weddingnavi.tk/EA102G1/membre/membre.do?action=verifyAccount&key="+key;
			MailService.sendMail(email, mem_name, url);
			request.setAttribute("success", "success");
//			http://weddingnavi.tk/EA102G1/front_end/home/home.jsp
			RequestDispatcher view = request.getRequestDispatcher("/front_end/home/home.jsp");
			view.forward(request, response);
		} // end of 註冊
		
		//登入
		if("login".equals(action)) {
			List<String> errors = new ArrayList<String>();
			String compte = request.getParameter("compte");
			if (compte.trim().length() == 0) {
				errors.add("帳號不可為空");
			}
			String passe = request.getParameter("passe");
			if (passe.trim().length() == 0) {
				errors.add("密碼不可為空");
			}
			if (! errors.isEmpty()) {
				RequestDispatcher view = request.getRequestDispatcher("/front_end/membre/login.jsp");
				request.setAttribute("errors", errors);
				view.forward(request, response);
				return;
			}
			
		 
			List<String> accountList = memSvc.getAllAccount();
			if (!accountList.contains(compte)) {
				errors.add("帳號不正確");
				RequestDispatcher view = request.getRequestDispatcher("/front_end/membre/login.jsp");
				request.setAttribute("errors", errors);
				view.forward(request, response);
				return;
			}
			MembreVO membrevo = memSvc.login(compte);
		    if (! passe.equals(membrevo.getPasse())) {
		    	errors.add("密碼不正確");
				RequestDispatcher view = request.getRequestDispatcher("/front_end/membre/login.jsp");
				request.setAttribute("errors", errors);
				view.forward(request, response);
				return;
		    }
		    String status  = membrevo.getStatus();
		    if (status.equals("2")) {
		    	errors.add("此帳號尚未驗證");
				RequestDispatcher view = request.getRequestDispatcher("/front_end/membre/login.jsp");
				request.setAttribute("errors", errors);
				view.forward(request, response);
				return;
		    }
		    //成功登入
		    
		    session.setAttribute("membrevo", membrevo);
		    String membre_id = membrevo.getMembre_id();
		    
		    @SuppressWarnings("unchecked")
		    List<String> buyList = (ArrayList<String>) session.getAttribute("buyList");
		    if (buyList != null) {
			    ListService listService = new ListService();
			    for (String prod_no : buyList) {
			    	ListVO listvo = listService.getOne(prod_no, membre_id);
			    	if (listvo.getProd_no() != null)
						continue;
			    		listService.add(prod_no, membre_id);
			    }
		    }
		    ServletContext servletContext = getServletConfig().getServletContext();
		    @SuppressWarnings("unchecked")
		    Map<String, List<String>> noticeMap = (HashMap<String, List<String>>)servletContext.getAttribute("noticeMap");
		    Thread myThread = null;
		    OrderNoticeSender sender = new OrderNoticeSender();
		    if (noticeMap != null) {
		    	List<String> noticeList = noticeMap.get(membre_id);
		    	System.out.println(noticeList==null);
		    	if (noticeList != null) {
				   myThread = new Thread() {
					   @Override
				    	public void run() {
				    		try {
								sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				    		for (int i = noticeList.size() -1; i >= 0; i --) {
				    			System.out.println(i);
				    			sender.sendOffLineMessage(membre_id, noticeList.get(i));
				    			noticeList.remove(i);
				    		}
				    		if (noticeList.size() == 0) {
				    			noticeMap.remove(membre_id);
				    			System.out.println("remove");
				    		}
				    	}
				    }; 
				    myThread.start();
		    	}
		    }
		    
		    String locationFromRequest = request.getParameter("location");
		    if  (! locationFromRequest.isEmpty()) {
		    	response.sendRedirect(locationFromRequest);
		    	return;
		    }
		  
//		    String location = (String) session.getAttribute("location");
//		    if (location != null) {
//		    	response.sendRedirect(location);
//		    	return;
//		    }
		    
		    response.sendRedirect(request.getContextPath() + "/front_end/home/home.jsp");
		} // end of login
		

		
		// 大頭貼
		if ("getphoto".equals(action)) {
			ServletOutputStream sos = response.getOutputStream();
			MembreVO membrevo = memSvc.getOneById(request.getParameter("membre_id"));
			if (membrevo.getPhoto()!=null) {
				ByteArrayInputStream bis = new ByteArrayInputStream(membrevo.getPhoto());
				byte[] buffer = new byte[8192];
				int i = 0;
				while ((i=bis.read(buffer)) != -1) {
					sos.write(buffer, 0 ,i);
				}
				bis.close();
				sos.close();
			} else {
				InputStream is = getServletContext().getResourceAsStream("/img/membre_img/default_avatar.jpg");
				byte[] buffer = new byte[8192];
				int i = 0;
				while ((i=is.read(buffer)) != -1) {
					sos.write(buffer, 0 ,i);
				}
				is.close();
				sos.close();
			}
	
		} // end of 大頭貼
		

		

	} // end of doPost
	
} // end of class
