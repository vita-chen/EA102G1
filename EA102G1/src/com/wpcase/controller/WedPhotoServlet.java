package com.wpcase.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONObject;

import com.membre.model.MembreDAO;
import com.membre.model.MembreVO;
import com.vender.model.VenderJDBCDAO;
import com.vender.model.VenderVO;
import com.wpcase.model.WPCaseService;
import com.wpcase.model.WPCaseVO;
import com.wpcollect.model.WPCollectVO;
import com.wpdetail.model.WPDetailVO;
import com.wpimg.model.WPImgVO;
import com.wporder.model.WPOrderService;
import com.wporder.model.WPOrderVO;


@MultipartConfig
public class WedPhotoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String vender_id = req.getParameter("VENDER_ID");
				String vender_idReg = "^[(A-Z)]{1}[(0-9)]{3}$";
				if (vender_id == null || vender_id.trim().length() == 0) {
					errorMsgs.add("廠商編號: 請勿空白");
				} else if (!vender_id.trim().matches(vender_idReg)) {
					errorMsgs.add("廠商編號: 格式錯誤");
				}

				String wed_photo_name = req.getParameter("WED_PHOTO_NAME").trim();
				if (wed_photo_name == null || wed_photo_name.trim().length() == 0) {
					errorMsgs.add("方案名稱: 請勿空白");
				}

				String wed_photo_intro = req.getParameter("WED_PHOTO_INTRO").trim();
				if (wed_photo_intro == null || wed_photo_intro.trim().length() == 0) {
					errorMsgs.add("方案簡介: 請勿空白");
				}

				String wed_photo_prices = req.getParameter("WED_PHOTO_PRICE");
				String wed_photo_priceReg = "^[(0-9)]{3,6}$";
				if (wed_photo_prices == null || wed_photo_prices.trim().length() == 0) {
					errorMsgs.add("方案價格: 請勿空白");
				} else if (!wed_photo_prices.trim().matches(wed_photo_priceReg)) {
					errorMsgs.add("方案價格: 請再確認金額");
				}
				Integer wed_photo_price = new Integer(wed_photo_prices);

				String wed_photo_statuss = req.getParameter("WED_PHOTO_STATUS");
				Integer wed_photo_status = new Integer(wed_photo_statuss);

				WPCaseVO WPCaseVO = new WPCaseVO();
				WPCaseVO.setVender_id(vender_id);
				WPCaseVO.setWed_photo_name(wed_photo_name);
				WPCaseVO.setWed_photo_intro(wed_photo_intro);
				WPCaseVO.setWed_photo_price(wed_photo_price);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("WPCaseVO", WPCaseVO);// 含有輸入格式錯誤的WPCaseVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/wp/vender_addWPcase.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				WPCaseService WPSvc = new WPCaseService();
				WPCaseVO = WPSvc.addWPCase(vender_id, wed_photo_name, wed_photo_intro, wed_photo_price,
						wed_photo_status);
				String[] img_num = req.getParameterValues("WP_IMG_NO");
//				System.out.println("servlet return: " + WPCaseVO.getWed_photo_case_no());
				
				Collection<Part> parts = req.getParts();
				for (Part part : parts) {
					if (part.getName().equals("upfile1")) {
						if(img_num == null) {							
							WPSvc.addCaseImg(WPCaseVO, part);														
						}else {							
							String filename = part.getSubmittedFileName();							
							for(int i =0;i<img_num.length;i++) {
								if(filename.equals(img_num[i])) {
//									System.out.println(filename+" 此圖保留");
									WPSvc.addCaseImg(WPCaseVO, part);
								}
							}
						}
					}
				}
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/				
				String url = "/front_end/vender/wp/vender_listAllWPCase.jsp";
				res.sendRedirect(req.getContextPath() + url);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/wp/vender_addWPcase.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) { //ajax
			try {
				String wed_photo_case_no = new String(req.getParameter("wed_photo_case_no"));			
				
				WPCaseVO wpcasevo = new WPCaseVO();
				wpcasevo.setWed_photo_case_no(wed_photo_case_no);;
				
				WPCaseService wpcasesvc = new WPCaseService();
				wpcasesvc.deleteWPCaseNo(wed_photo_case_no);
				
				System.out.println("刪除方案 成功- "+wed_photo_case_no);

				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");			
				PrintWriter out = res.getWriter();
				out.write("成功");
				out.flush();
				out.close();				
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");			
				PrintWriter out = res.getWriter();
				out.write("失敗");
				out.flush();
				out.close();
			}
		}
		if ("delete_img".equals(action)) { 
			try {
				String[] WP_IMG_NO = req.getParameterValues("WP_IMG_NO");
				
				WPCaseService wpcasesvc = new WPCaseService();
				wpcasesvc.deleteWPImg(WP_IMG_NO);
				String url = "/front_end/vender/wp/vender_listAllWPCase.jsp";
				res.sendRedirect(req.getContextPath() + url);
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/wp/vender_listAllWPCase.jsp");
				failureView.forward(req, res);
				
			}
		}
		if ("getone_for_update".equals(action)) {
			try {
				String wed_photo_case_no = new String(req.getParameter("wed_photo_case_no"));

				WPCaseService wpSvc = new WPCaseService();
				WPCaseVO WPCaseVO = wpSvc.getOneWPno(wed_photo_case_no);
				List<WPImgVO> imgList = wpSvc.getAllImg(wed_photo_case_no);

				req.setAttribute("WPCaseVO", WPCaseVO);
				req.setAttribute("imgList", imgList);
				String url = "/front_end/vender/wp/vender_updateWPCase.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/wp/updateWPCase.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_CasePage".equals(action)) { //抓取方案編號 導至詳細方案頁面
			try {
				String wed_photo_case_no = new String(req.getParameter("wed_photo_case_no"));

				WPCaseService wpSvc = new WPCaseService();
				WPCaseVO WPCaseVO = wpSvc.getOneWPno(wed_photo_case_no);
				List<WPImgVO> imgList = wpSvc.getAllImg(wed_photo_case_no);

				req.setAttribute("WPCaseVO", WPCaseVO);
				req.setAttribute("imgList", imgList);
				String url = "/front_end/wed_photo/casePage_WP.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/wed_photo/listAllWPCase.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("goVenderPage".equals(action)) { //抓取廠商編號 導至廠商頁面
			try {
				String vender_id = new String(req.getParameter("vender_id"));

				WPCaseService wpSvc = new WPCaseService();
				List<WPCaseVO> list_case = wpSvc.getAll();
				list_case = list_case.stream()
						.filter(vo -> vo.getVender_id().equals(vender_id))
						.collect(Collectors.toList());
				
				List<VenderVO> list_vender = wpSvc.list_vender().stream()
						.filter(vo -> vo.getVender_id().equals(vender_id))
						.collect(Collectors.toList());
				
				req.setAttribute("list_case", list_case);
				req.setAttribute("VenderVO", list_vender);
				String url = "/front_end/wed_photo/venderPage_WP.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/wed_photo/.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String wed_photo_case_no = req.getParameter("WED_PHOTO_CASE_NO").trim();
				String vender_id = req.getParameter("VENDER_ID").trim();

				String wed_photo_name = req.getParameter("WED_PHOTO_NAME").trim();
				if (wed_photo_name == null || wed_photo_name.trim().length() == 0) {
					errorMsgs.add("方案名稱: 請勿空白");
				}

				String wed_photo_intro = req.getParameter("WED_PHOTO_INTRO").trim();
				if (wed_photo_intro == null || wed_photo_intro.trim().length() == 0) {
					errorMsgs.add("方案簡介: 請勿空白");
				}

				String wed_photo_statuss = req.getParameter("WED_PHOTO_STATUS");
				Integer wed_photo_status = new Integer(wed_photo_statuss);
				String wed_photo_prices = req.getParameter("WED_PHOTO_PRICE");
				String wed_photo_priceReg = "^[(0-9)]{3,6}$";
				if (wed_photo_prices == null || wed_photo_prices.trim().length() == 0) {
					errorMsgs.add("方案價格: 請勿空白");
				} else if (!wed_photo_prices.trim().matches(wed_photo_priceReg)) {
					errorMsgs.add("方案價格: 請再確認金額");
				}
				Integer wed_photo_price = new Integer(wed_photo_prices);

				WPCaseVO WPCaseVO = new WPCaseVO();
				WPCaseVO.setWed_photo_case_no(wed_photo_case_no);
				WPCaseVO.setVender_id(vender_id);
				WPCaseVO.setWed_photo_name(wed_photo_name);
				WPCaseVO.setWed_photo_intro(wed_photo_intro);
				WPCaseVO.setWed_photo_price(wed_photo_price);
				
				WPCaseService wpSvc = new WPCaseService();
				List<WPImgVO> WPImgVO = wpSvc.getAllImg(wed_photo_case_no);
				req.setAttribute("WPImgVO", WPImgVO);
				req.setAttribute("WPCaseVO", WPCaseVO); // 含有輸入格式錯誤的WPCaseVO物件,也存入req
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/wp/vender_updateWPCase.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 ***************************************/
				WPCaseService WPSvc = new WPCaseService();
				WPCaseVO = WPSvc.updateWPCase(wed_photo_name, wed_photo_intro, wed_photo_status, wed_photo_price,
						wed_photo_case_no);
				System.out.println("servlet " + WPCaseVO.getWed_photo_case_no());

				String[] img_num = req.getParameterValues("WP_IMG_NO");
//				System.out.println("servlet return: " + WPCaseVO.getWed_photo_case_no());
				
				Collection<Part> parts = req.getParts();
				for (Part part : parts) {
					if (part.getName().equals("upfile1")) {
						if(img_num == null) {							
							WPSvc.addCaseImg(WPCaseVO, part);														
						}else {							
							String filename = part.getSubmittedFileName();							
							for(int i =0;i<img_num.length;i++) {
								if(filename.equals(img_num[i])) {
//									System.out.println(filename+" 此圖保留");
									WPSvc.addCaseImg(WPCaseVO, part);
								}
							}
						}
					}
				}
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/vender/wp/vender_listAllWPCase.jsp";
				res.sendRedirect(req.getContextPath() + url);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/wp/vender_updateWPCase.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("Get_WPImg".equals(action)) { //單一方案用 查全部圖片
			
			WPCaseService wpcsv = new WPCaseService();
			List<WPImgVO> imglist = wpcsv.getAllImg(req.getParameter("wed_photo_case_no"));
			String imgno = req.getParameter("wed_photo_imgno");
			
			for (WPImgVO img : imglist) {
				if (img.getWed_photo_imgno().equals(imgno)) {
					ByteArrayInputStream bis = new ByteArrayInputStream(img.getWed_photo_img());
					ServletOutputStream sos = res.getOutputStream();
					byte[] buffer = new byte[8*1024];
					int length = 0;
					while ((length = bis.read(buffer)) != -1) {
						sos.write(buffer, 0, length);
					}					
					sos.close();
					bis.close();				
					return;
				}
			}
		}
		if ("getOne_WPImg".equals(action)) { //最新方案&其他方案 使用
			
			WPCaseService wpcsv = new WPCaseService();
			List<WPImgVO> imglist = wpcsv.getAllImg(req.getParameter("wed_photo_case_no"));
			try {
				if(imglist.size()==0)throw new Exception(); //沒圖片就到catch區取預設圖片
				
				ByteArrayInputStream bis = new ByteArrayInputStream(imglist.get(0).getWed_photo_img());
				ServletOutputStream sos = res.getOutputStream();
				byte[] buffer = new byte[8*1024];
				int length = 0;
				while ((length = bis.read(buffer)) != -1) {
					sos.write(buffer, 0, length);
				}					
				sos.close();
				bis.close();				
				return;
			}catch(Exception e) {
				
//				e.printStackTrace();
				InputStream is = getServletContext().getResourceAsStream("/img/wp_img/v0204.jpg");
				ServletOutputStream sos = res.getOutputStream();
				byte[] buffer = new byte[is.available()];
				is.read(buffer);
				sos.write(buffer);
				sos.close();
				is.close();				
				return;
			}
			
		}
		if ("GetImgForVender".equals(action)) {
			
			WPCaseService wpcsv = new WPCaseService();
			WPImgVO imgvo = wpcsv.imgForVender(req.getParameter("vender_id"));
			try {
				
				ByteArrayInputStream bis = new ByteArrayInputStream(imgvo.getWed_photo_img());
				ServletOutputStream sos = res.getOutputStream();
				byte[] buffer = new byte[8*1024];			
				int length = 0;
				
				while ((length = bis.read(buffer)) != -1) {
					sos.write(buffer, 0, length);
				}
				sos.close();
				bis.close();				
				return;
				
			}catch(NullPointerException n) {
				
				InputStream is = getServletContext().getResourceAsStream("/img/wp_img/v0204.jpg");
				ServletOutputStream sos = res.getOutputStream();
				byte[] buffer = new byte[is.available()];
				is.read(buffer);
				sos.write(buffer);
				sos.close();
				is.close();				
				return;
				
			}
			
		}
		
		if ("place_an_order".equals(action)) { //抓取方案編號 導至填寫訂單頁面
			try {
				String wed_photo_case_no = new String(req.getParameter("wed_photo_case_no"));

				WPCaseService wpSvc = new WPCaseService();
				WPCaseVO WPCaseVO = wpSvc.getOneWPno(wed_photo_case_no);

				req.setAttribute("WPCaseVO", WPCaseVO);
				String url = "/front_end/wed_photo/placeWPOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/wed_photo/wp_home.jsp");
				failureView.forward(req, res);
			}
		}

		if ("Generate_order".equals(action)) { //填寫訂單頁面 >> 產生訂單 + ajax
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			JSONObject obj = new JSONObject();
			System.out.println(req.getParameter("TOOL"));
			try {
				String wed_photo_case_no = req.getParameter("WED_PHOTO_CASE_NO");				
				String vender_id = req.getParameter("VENDER_ID");			
//				String wed_photo_name = req.getParameter("WED_PHOTO_NAME");
//				String wed_photo_prices = req.getParameter("WED_PHOTO_PRICE");
//				Integer wed_photo_price = new Integer(wed_photo_prices);			
				
				WPCaseVO wpcasevo = new WPCaseVO();
				wpcasevo.setWed_photo_case_no(wed_photo_case_no);
				wpcasevo.setVender_id(vender_id);
//				wpcasevo.setWed_photo_name(wed_photo_name);
//				wpcasevo.setWed_photo_price(wed_photo_price);				
				req.setAttribute("WPCaseVO", wpcasevo);
				
				WPOrderVO wpordervo = new WPOrderVO();
				req.setAttribute("WPOrderVO", wpordervo);
				
				String membre_id = req.getParameter("MEMBRE_ID").trim();
				wpordervo.setMembre_id(membre_id);				
				if (membre_id.trim().length() == 0)	errorMsgs.add("會員編號: 請勿空白");				
				String order_explain = req.getParameter("ORDER_EXPLAIN").trim();
				wpordervo.setOrder_explain(order_explain);
				
				java.sql.Timestamp current_time = new java.sql.Timestamp(System.currentTimeMillis());
				java.sql.Timestamp filming_time = new java.sql.Timestamp(System.currentTimeMillis());
				try {
					filming_time = java.sql.Timestamp.valueOf(req.getParameter("FILMING_TIME") + ":00");
					System.out.println(filming_time + "這邊是時間正常區 進行第二次判斷");					
					
					if((current_time.getTime()+(14*86400000))-(filming_time.getTime()) > 0) {
						errorMsgs.add("不得輸入過往日期 且請選擇大於14天之後!!");//-值才是正確的 且大於14天
						obj.put("time","不得輸入過往日期 且請選擇大於14天之後!!");
						throw new Exception();
					};
				} catch (IllegalArgumentException ie) { //時間錯誤不准進資料庫
					errorMsgs.add("時間錯誤!");
					obj.put("time","不得輸入過往日期 且請選擇大於14天之後!!");
					throw ie; 
				}
								
				wpordervo.setFilming_time(filming_time);
				WPOrderService wpodsvc = new WPOrderService();//返回訂單編號
				wpordervo = wpodsvc.addWPOrder(membre_id, vender_id, filming_time, order_explain);
				WPDetailVO wpdetailvo = new WPDetailVO();//訂單編號與方案關聯
				wpdetailvo = wpodsvc.addWPDetail(wpordervo.getWed_photo_order_no(), wed_photo_case_no);
				
				//訂單生成成功依工具返回
				if(req.getParameter("TOOL").equals("ajax")) {					
					res.setContentType("text/plain");
					res.setCharacterEncoding("UTF-8");			
					PrintWriter out = res.getWriter();
					obj.put("note", "成功");
					obj.put("wed_photo_order_no", wpordervo.getWed_photo_order_no());
					out.print(obj);
					out.flush();
					out.close();					
					
				}else {					
					String url = "/front_end/membre/wp/listAllWPOrder.jsp";
					res.sendRedirect(req.getContextPath() + url);
				}
				//訂單生成失敗依工具返回
			} catch (Exception e) {				
				if(req.getParameter("TOOL").equals("ajax")) {
					obj.put("note","失敗");
					res.setContentType("text/plain");
					res.setCharacterEncoding("UTF-8");			
					PrintWriter out = res.getWriter();
					out.print(obj);
					out.flush();
					out.close();
					return;
				}else {
					errorMsgs.add("生成訂單: 失敗");				
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/wed_photo/placeWPOrder.jsp");
					failureView.forward(req, res);
				}
				
			}
		}

		if ("getone_for_report".equals(action)) { //抓取訂單資訊 導至檢舉頁面 X
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				WPOrderVO wpordervo = new WPOrderVO();
				
				/****************************************************/
				wpordervo.setWed_photo_order_no(req.getParameter("wed_photo_order_no"));
				wpordervo.setMembre_id(req.getParameter("membre_id"));
				wpordervo.setVender_id(req.getParameter("vender_id"));
								
				java.sql.Timestamp filming_time = new java.sql.Timestamp(System.currentTimeMillis());
				java.sql.Timestamp wed_photo_odtime = new java.sql.Timestamp(System.currentTimeMillis());				
				try {
					filming_time = java.sql.Timestamp.valueOf(req.getParameter("filming_time"));
					wed_photo_odtime = java.sql.Timestamp.valueOf(req.getParameter("wed_photo_odtime"));
					System.out.println(filming_time + "這邊是時間正常區");
				} catch (IllegalArgumentException ie) {
					System.out.println(filming_time + "這邊是時間錯誤區");
					errorMsgs.add("時間錯誤!");
					throw ie;
				}				
				wpordervo.setFilming_time(filming_time);
				wpordervo.setWed_photo_odtime(wed_photo_odtime);
				
				/****************************************************/
				req.setAttribute("WPOrderVO", wpordervo);
				String url = "/front_end/membre/memRepWP.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/listOneWPOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("mem_report".equals(action)) { // 會員檢舉廠商 ajax
			try {
				String wp_mrep_d = req.getParameter("wp_mrep_d");
				String wed_photo_order_no = req.getParameter("wed_photo_order_no");
				System.out.println(wed_photo_order_no +" <-訂單編號 ; 檢舉內容-> " +wp_mrep_d);
				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");			
				PrintWriter out = res.getWriter();
				
				if (wp_mrep_d.trim().length() == 0) {
					String error = "檢舉內容請勿空白";//沒填不准進資料庫
					out.write(error);
					out.flush();
					out.close();
					return;
				}
				WPOrderVO wpordervo = new WPOrderVO();
				wpordervo.setWed_photo_order_no(wed_photo_order_no);
				wpordervo.setWp_mrep_d(wp_mrep_d);				
				WPOrderService wpodsvc = new WPOrderService();
				wpodsvc.Mem_Report(wpordervo);				
				
				out.write(wed_photo_order_no);
				out.flush();
				out.close();				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if ("ven_report".equals(action)) { // 廠商檢舉會員 ajax
			try {				
				String wed_photo_order_no = req.getParameter("wed_photo_order_no");
				String wp_vrep_d = req.getParameter("wp_vrep_d");
				System.out.println(wed_photo_order_no +" <-訂單編號 ; 檢舉內容-> " +wp_vrep_d);
				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");			
				PrintWriter out = res.getWriter();
				
				if (wp_vrep_d.trim().length() == 0) {
					String error = "檢舉資訊請勿空白!";//沒填不准進資料庫
					out.write(error);
					out.flush();
					out.close();
					return;
				}
				WPOrderVO wpordervo = new WPOrderVO();
				wpordervo.setWed_photo_order_no(wed_photo_order_no);
				wpordervo.setWp_vrep_d(wp_vrep_d);				
				WPOrderService wpodsvc = new WPOrderService();
				wpodsvc.Ven_Report(wpordervo);				
				
				out.write(wed_photo_order_no);
				out.flush();
				out.close();				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if ("add_Collect".equals(action)) { //加入收藏 ajax
			try {
				WPCollectVO wpcollectvo = new WPCollectVO();
				String wed_photo_case_no = new String(req.getParameter("wed_photo_case_no"));
				String membre_id = new String(req.getParameter("membre_id"));
				
				wpcollectvo.setWed_photo_case_no(wed_photo_case_no);
				wpcollectvo.setMembre_id(membre_id);
				
				WPCaseService wpSvc = new WPCaseService();
				wpSvc.addCollect(wpcollectvo);
				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");			
				PrintWriter out = res.getWriter();
				out.write("加入收藏成功!");
				out.flush();
				out.close();
				
//				String url = "/front_end/membre/casePage_WP.jsp";
//				res.sendRedirect(req.getContextPath() + url);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");			
				PrintWriter out = res.getWriter();
				out.write("已經加入收藏囉!");
				out.flush();
				out.close();
//				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/membre/listAllWPCase.jsp");
//				failureView.forward(req, res);
			}
		}
		if ("dis_Collect".equals(action)) { //取消收藏 ajax
			try {
				WPCollectVO wpcollectvo = new WPCollectVO();
				String wed_photo_case_no = new String(req.getParameter("wed_photo_case_no"));
				String membre_id = new String(req.getParameter("membre_id"));
				
				System.out.println(wed_photo_case_no+" / "+membre_id);
				
				wpcollectvo.setWed_photo_case_no(wed_photo_case_no);
				wpcollectvo.setMembre_id(membre_id);
				
				WPCaseService wpSvc = new WPCaseService();
				wpSvc.disCollect(wpcollectvo);				
				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");			
				PrintWriter out = res.getWriter();
				out.write("已取消收藏!");
				out.flush();
				out.close();
				
//				String url = "/front_end/membre/casePage_WP.jsp";
//				res.sendRedirect(req.getContextPath() + url);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/membre/listAllWPCase.jsp");
				failureView.forward(req, res);
			}
		}
		if ("sel_Collect".equals(action)) { //查詢收藏
			try {
				String membre_id = new String(req.getParameter("membre_id"));
				
				WPCaseService wpSvc = new WPCaseService();
				List<WPCollectVO> list = wpSvc.selCollect(membre_id);
				//list 轉送到查詢收藏頁面
				String url = "/front_end/membre/casePage_WP.jsp";
				res.sendRedirect(req.getContextPath() + url);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/membre/listAllWPCase.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("cancel_order".equals(action)) { //Ajax
			try {
				String wed_photo_order_no = new String(req.getParameter("wed_photo_order_no"));				
				System.out.println("取消此筆訂單 - "+wed_photo_order_no);
				
				WPOrderVO wpordervo = new WPOrderVO();
				wpordervo.setWed_photo_order_no(wed_photo_order_no);
				
				WPOrderService wpordersvc = new WPOrderService();
				wpordersvc.cancel_order(wed_photo_order_no);
				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");			
				PrintWriter out = res.getWriter();
				out.write(wed_photo_order_no);
				out.flush();
				out.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/membre/listAllWPCase.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("complete_order".equals(action)) { //Ajax
			try {
				String identity = new String(req.getParameter("identity"));
				String wed_photo_order_no = new String(req.getParameter("wed_photo_order_no"));
				String vender_id = new String(req.getParameter("vender_id"));

				if(identity.equals("vender")) {
					WPOrderVO wpordervo = new WPOrderVO();
					wpordervo.setWed_photo_order_no(wed_photo_order_no);
					wpordervo.setVender_id(vender_id);
					WPOrderService wpordersvc = new WPOrderService();
					wpordersvc.complete_order(wpordervo);
					
					res.setContentType("text/plain");
					res.setCharacterEncoding("UTF-8");
					PrintWriter out = res.getWriter();
					out.write(wed_photo_order_no);
					out.flush();
					out.close();
					
					return;
				}else {
					String review_stars = new String(req.getParameter("review_star"));
					String review_content = new String(req.getParameter("review_content").trim());				
					Integer review_star = new Integer(review_stars);
					
					WPOrderVO wpordervo = new WPOrderVO();
					wpordervo.setVender_id(vender_id);
					wpordervo.setWed_photo_order_no(wed_photo_order_no);				
					wpordervo.setReview_star(review_star);
					wpordervo.setReview_content(review_content);
					
					WPOrderService wpordersvc = new WPOrderService();
					wpordersvc.complete_order(wpordervo);
					
					res.setContentType("text/plain");
					res.setCharacterEncoding("UTF-8");			
					PrintWriter out = res.getWriter();
					out.write(wed_photo_order_no);
					out.flush();
					out.close();
				}
				

			} catch (Exception e) {
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/membre/listAllWPCase.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_Order_Ajax".equals(action)) {
			
			String wed_photo_order_no = req.getParameter("wed_photo_order_no");
			String identity = req.getParameter("identity");
			WPOrderService wpodsvc = new WPOrderService();
			WPOrderVO ordervo = wpodsvc.getOne(wed_photo_order_no);
			MembreDAO membredao = new MembreDAO();
			MembreVO memvo = membredao.getOneById(ordervo.getMembre_id());
			VenderJDBCDAO venderdao = new VenderJDBCDAO();
			VenderVO vendervo = venderdao.findByPrimaryKey(ordervo.getVender_id());
			WPCaseService wpcasvc = new WPCaseService();
			WPCaseVO casevo = wpcasvc.getOneWPno(wpodsvc.orderChange(wed_photo_order_no));
			
			//宣告json物件 put list值
			JSONObject obj = new JSONObject();
				if(ordervo.getOrder_status() == 2) {
					obj.put("order_status", "訂單取消");
				}else if(ordervo.getOrder_status() == 3){
					obj.put("order_status", "訂單完成");
				}else{
					obj.put("order_status", "訂單成立");
				}				
				obj.put("order_explain", ordervo.getOrder_explain());
				obj.put("review_star", ordervo.getReview_star());
				obj.put("review_content", ordervo.getReview_content());
				obj.put("wed_photo_case_no", casevo.getWed_photo_case_no());
				obj.put("wed_photo_name", casevo.getWed_photo_name());
				obj.put("mem_id", memvo.getMembre_id());
				obj.put("mem_name", memvo.getMem_name());
				obj.put("m_mail", memvo.getEmail());
				obj.put("m_phone", memvo.getPhone());
				obj.put("contact", vendervo.getVen_contact());
				obj.put("v_mail", vendervo.getVen_mail());
				obj.put("v_phone", vendervo.getVen_phone());
				if(identity.equals("member")) { // 身分判別 廠商給廠商檢舉資訊 會員給會員檢舉資訊
					if(ordervo.getWp_mrep_s() == 1) {
						obj.put("wp_mrep_s", "已提出檢舉 待管理員審核");
					}else if(ordervo.getWp_mrep_s() == 2) {
						obj.put("wp_mrep_s", "檢舉成立");
					}else if(ordervo.getWp_mrep_s() == 3) {
						obj.put("wp_mrep_s", "檢舉無效");
					}else {
						obj.put("wp_mrep_s", "無檢舉資訊");
					}				
					obj.put("wp_mrep_d", ordervo.getWp_mrep_d());
				}else {
					if(ordervo.getWp_vrep_s() == 1) {
						obj.put("wp_vrep_s", "已提出檢舉 待管理員審核");
					}else if(ordervo.getWp_vrep_s() == 2) {
						obj.put("wp_vrep_s", "檢舉成立");
					}else if(ordervo.getWp_vrep_s() == 3) {
						obj.put("wp_vrep_s", "檢舉無效");
					}else {
						obj.put("wp_vrep_s", "無檢舉資訊");
					}				
					obj.put("wp_vrep_d", ordervo.getWp_vrep_d());
				}
			
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.print(obj);
			out.flush();
			out.close();
		}
		
		if ("getOne_Order_Ajax_2".equals(action)) { //Ajax 狀態數字版
			
			System.out.println(req.getParameter("action"));
			System.out.println(req.getParameter("wed_photo_order_no"));
			String wed_photo_order_no = req.getParameter("wed_photo_order_no");
			String identity = req.getParameter("identity");
			WPOrderService wpodsvc = new WPOrderService();
			WPOrderVO ordervo = wpodsvc.getOne(wed_photo_order_no);			
			
			//宣告json物件 put list值
			JSONObject obj = new JSONObject();
				obj.put("order_status", ordervo.getOrder_status());
				obj.put("order_explain", ordervo.getOrder_explain());
				obj.put("review_star", ordervo.getReview_star());
				obj.put("review_content", ordervo.getReview_content());
				obj.put("wp_mrep_s", ordervo.getWp_mrep_s());
				obj.put("wp_mrep_d", ordervo.getWp_mrep_d());
				obj.put("wp_vrep_s", ordervo.getWp_vrep_s());
				obj.put("wp_vrep_d", ordervo.getWp_vrep_d());
							
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.print(obj);
			out.flush();
			out.close();
		}
		
		if ("update_order_Ajax".equals(action)) {
			
			String wed_photo_order_no = req.getParameter("wed_photo_order_no");
			Integer review_star = new Integer(req.getParameter("review_star"));
			String review_content = req.getParameter("review_content");
			String order_explain = req.getParameter("order_explain");
			String vender_id = req.getParameter("vender_id");
			if(req.getParameter("order_status").equals("訂單取消")) {
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write("訂單取消無法做任何動作哦!");
				out.flush();
				out.close();
				return;
				
			}
			if(req.getParameter("order_status").equals("訂單成立")) {
				
				WPOrderService wpordersvc = new WPOrderService();
				WPOrderVO ordervo = new WPOrderVO();
				ordervo.setOrder_explain(order_explain);
				ordervo.setWed_photo_order_no(wed_photo_order_no);
				wpordersvc.Mem_Explain(ordervo);
				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write("馬上為您更新訂單備註 !");
				out.flush();
				out.close();
				return;
			}			
			if(req.getParameter("order_status").equals("訂單完成")) {
				
				WPOrderService wpordersvc = new WPOrderService();
				WPOrderVO ordervo = new WPOrderVO();
				ordervo.setReview_star(review_star);
				ordervo.setReview_content(review_content);
				ordervo.setWed_photo_order_no(wed_photo_order_no);
				ordervo.setVender_id(vender_id);
				wpordersvc.complete_order(ordervo);
				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write("評價更新成功囉!");
				out.flush();
				out.close();
				return;
			}

		}
		
		if ("select_order_no_Ajax".equals(action)) {
			WPOrderVO wpordervo = new WPOrderVO();
			WPOrderService wpordersvc = new WPOrderService();			
			List<WPOrderVO> list = wpordersvc.getAll();
			
			JSONArray array = new JSONArray();
			for(WPOrderVO vo : list) {
				JSONObject obj = new JSONObject();
				obj.put("wed_photo_order_no", vo.getWed_photo_order_no());
				obj.put("vender_id", vo.getVender_id());
				obj.put("membre_id", vo.getMembre_id());
				array.put(obj);
			}		
			
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.print(array.toString());
			out.flush();
			out.close();
		}
		

		if("inquireCase".equals(action)){
			
			String search_case_name = req.getParameter("search_case");
			
			WPCaseService wpcsv = new WPCaseService();
			List<WPCaseVO> casename_list = wpcsv.search_case(search_case_name);
			
			req.setAttribute("location", "search");//前版本跳區塊用
			req.setAttribute("casename_list", casename_list);
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/wed_photo/find_WPCase.jsp");
			failureView.forward(req, res);
		}
		if("inquireCasebyPrice".equals(action)){
			
			Integer min = new Integer(req.getParameter("min"));
			Integer max = new Integer(req.getParameter("max"));
			
			WPCaseService wpcsv = new WPCaseService();
			List<WPCaseVO> casename_list = wpcsv.search_case(min,max);
			
			req.setAttribute("location", "search");
			req.setAttribute("casename_list", casename_list);
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/wed_photo/find_WPCase.jsp");
			failureView.forward(req, res);
		}
		if("inquireCasebyAddr".equals(action)){
			
			String addr = req.getParameter("search_case_addr");
			
			WPCaseService wpcsv = new WPCaseService();
			List<WPCaseVO> casename_list = wpcsv.search_case_addr(addr);
			
			req.setAttribute("location", "search");
			req.setAttribute("casename_list", casename_list);
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/wed_photo/find_WPCase.jsp");
			failureView.forward(req, res);
		}
		if("inquireVender".equals(action)){//關鍵字查廠商
			
			String search_vender = req.getParameter("search_vender");
			
			WPCaseService wpcsv = new WPCaseService();
			List<VenderVO> list_vender = wpcsv.search_vender(search_vender);

			req.setAttribute("list_vender", list_vender);
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/wed_photo/find_WPVender.jsp");
			failureView.forward(req, res);
		}
		if("inquireVenderbyAddr".equals(action)){ //地址查廠商
			
			String addr = req.getParameter("search_vender_addr");
			
			WPCaseService wpcsv = new WPCaseService();
			List<VenderVO> list_vender = wpcsv.search_vender_addr(addr);
			
			req.setAttribute("list_vender", list_vender);
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/wed_photo/find_WPVender.jsp");
			failureView.forward(req, res);
		}
		
		if("inquireVenderbyStar".equals(action)){//評價查廠商
			
			String select_star = req.getParameter("select_star");
			System.out.println(select_star);
			Double star = new Double(select_star);
			
			WPCaseService wpcsv = new WPCaseService();
			List<VenderVO> list_vender = wpcsv.search_vender_star(star);
			
			req.setAttribute("list_vender", list_vender);
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/wed_photo/find_WPVender.jsp");
			failureView.forward(req, res);
		}
		
	}	
}
