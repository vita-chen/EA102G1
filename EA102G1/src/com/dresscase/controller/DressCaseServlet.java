package com.dresscase.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import com.dresscase.model.*;
import com.dresscasepic.model.*;
import com.vender.model.VenderService;
import com.vender.model.VenderVO;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class DressCaseServlet extends HttpServlet {

//	doGet抓圖片，doPost做雜事
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("image/jpg");

		ServletOutputStream out = res.getOutputStream();

		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		try {
			if(req.getParameter("drpic_id")== null){
//			1. 若drpic_id無值，表示為來自listAllCase的請求，抓drcase_id
			String drcase_id = req.getParameter("drcase_id").trim();
//			讓dressPicService去尋找具有drcase_id的VO，呼叫其getDrpic方法，再用byteArayInputStream轉換
			DressPicService dpSvc= new DressPicService();
			List<DressPicVO> list= dpSvc.findPics(drcase_id);
			
//			情況1-1: drpic無值，drcase_id有值，有抓到圖片
			if(!list.isEmpty()) {
			for(DressPicVO dpVO:list) {
				ByteArrayInputStream bais = new ByteArrayInputStream(dpVO.getDrpic());
				BufferedInputStream bis = new BufferedInputStream(bais);
				byte[] buf = new byte[4*1024];
				int len;
				while((len = bis.read(buf))!= -1) {
					out.write(buf,0,len);
				}
				bis.close();
				bais.close();
			}
			}
//			情況1-2:drpic_id無值，drcase_id有值，沒抓到圖片，表示廠商尚未上傳圖片
			else {
				InputStream in = getServletContext().getResourceAsStream("/front_end/dresscase/images/暫無圖片2.png");
				byte[] b= new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
				}
			}
			
			else {
//			2. 若drpic_id有值，表示為來自addPic的請求，抓drpic_id
				String drpic_id = req.getParameter("drpic_id").trim();
//				讓dressPicService去尋找具有drpic的VO，呼叫其getDrpic方法，再用byteArayInputStream轉換
				DressPicService dpSvc= new DressPicService();
				try {
					
				DressPicVO dpVO= dpSvc.findPic(drpic_id);
				ByteArrayInputStream bais = new ByteArrayInputStream(dpVO.getDrpic());
				BufferedInputStream bis = new BufferedInputStream(bais);
				byte[] buf = new byte[4*1024];
				int len;
					while((len = bis.read(buf))!= -1) {
						out.write(buf,0,len);
					}
				bis.close();
				bais.close();
				
				}
				catch(Exception e) {
					errorMsgs.add("無法取得圖片"+e.getMessage());
					req.getRequestDispatcher("/front_end/dresscase/addDressPic_Vender.jsp").forward(req, res);
				}}}
			catch (Exception e) {
				errorMsgs.add("無法取得圖片"+e.getMessage());
				InputStream in = getServletContext().getResourceAsStream("/front_end/dresscase/images/暫無圖片2.png");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();			
			}
			doPost(req,res);
			}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//1. 廠商 總首頁:From Dress_page.jsp
		if ("getOne_For_Display".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.抓Dress_page頁面上的請求參數，並做格式的錯誤處理**********************/
				String str = req.getParameter("drcase_id");
				if (str == null || (str.trim()).length() != 6) {
					errorMsgs.add("婚紗方案編號格式錯誤:請輸入WDC+三位數字");
				}
				System.out.println(str);
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/Dress_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始查詢資料*****************************************/
				DressCaseService drSvc = new DressCaseService();
				DressCaseVO dcVO = drSvc.findByPrimaryKey(str);
				if (dcVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/Dress_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************3.查詢完成，準備轉交(Send the Success view)*************/
				req.setAttribute("dcVO", dcVO); // 從DB中取出dcVO物件，存入req
				String url = "/front_end/dresscase/ListOneDC_Vender.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneDressCase.jsp
				successView.forward(req, res);
				/***************************其他錯誤處理:導到DressCase HomePage*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/Dress_page.jsp");
				failureView.forward(req, res);
			}
		}
		//2. 廠商查看與更新個別的DressCase:From listAllDC.jsp
		if ("getOne_For_Update".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String drcase_id = req.getParameter("drcase_id");
				System.out.println(drcase_id);
				DressCaseService drSvc = new DressCaseService();
				DressCaseVO dcVO = drSvc.findByPrimaryKey(drcase_id);
				req.setAttribute("dcVO", dcVO);
				String url = "/front_end/dresscase/updateDC_Vender.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/dress/vender_ListAllDC.jsp");
				failureView.forward(req, res);
			}
		}
		
		//3. 廠商更新方案資料:From updateDC_Vender.jsp
		if ("update".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				//drcase_id, vender_id兩者為不可修改，故不做判斷
				String drcase_id = req.getParameter("drcase_id");
				String vender_id = req.getParameter("vender_id");
				String drcase_na = req.getParameter("drcase_na").trim();
				
				if (drcase_na == null || drcase_na.trim().length() == 0) {
					errorMsgs.add("方案名稱，請勿空白");
				} 
				String drcase_br = req.getParameter("drcase_br").trim();
				if (drcase_br == null || drcase_br.trim().length() == 0) {
					errorMsgs.add("方案簡介，請勿空白");
				}	
				Integer drcase_pr = null;
					drcase_pr = new Integer(req.getParameter("drcase_pr").trim());
				Integer drcase_st = null;
					drcase_st = new Integer(req.getParameter("drcase_st").trim());
				
				DressCaseVO dcVO = new DressCaseVO();
				dcVO.setDrcase_id(drcase_id);
				dcVO.setVender_id(vender_id);
				dcVO.setDrcase_na(drcase_na);
				dcVO.setDrcase_br(drcase_br);
				dcVO.setDrcase_pr(drcase_pr);
				dcVO.setDrcase_st(drcase_st);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dcVO", dcVO); 
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/updateDC_Vender.jsp");
					failureView.forward(req, res);
					return; 
				}
				DressCaseService dcSvc = new DressCaseService();
				dcVO = dcSvc.updateDress(drcase_id,vender_id,drcase_na,drcase_br,drcase_pr,drcase_st);
				req.setAttribute("dcVO", dcVO); 
				String url = "/front_end/vender/dress/vender_ListAllDC.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗"+e.getMessage());
				
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/updateDC_Vender.jsp");
				failureView.forward(req, res);
			}
		}
//		4.廠商修改圖片(從updateDressCaseInput.jsp中抓drcase_id,存在VO中，再將VO存入req，經requestDepatcher轉交給addDressPic.jsp)
//		  並列出該drcase_id的圖片:
		if("modifyPic".equals(action)){
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try { 
//			1.抓參數:drcase_id不會在updateDressCaseInput中被更動，故不用trim
			String drcase_id = req.getParameter("drcase_id");
			if(drcase_id == null|| drcase_id.trim().length() == 0) {
				errorMsgs.add("drcase_id不可為空值");
			}
//			2.開始查詢資料
			DressCaseService dcSvc = new DressCaseService();
			DressCaseVO dcVO = dcSvc.findByPrimaryKey(drcase_id);
			
//			3.存入屬性並轉交
			req.setAttribute("dcVO",dcVO);
			String url = "/front_end/dresscase/addDressPic_Vender.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);
			}	/***************************其他可能的錯誤處理*************************************/
			catch (Exception e) {
			System.out.println("修改資料失敗"+e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/addDressPic_Vender.jsp");
			failureView.forward(req, res);
			}
		}
		// 5. 廠商新增方案: From ListAllDC_Vender.jsp 
        if ("insert".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			VenderVO vVO = (VenderVO)req.getAttribute("venderVO");
			
			try {
				String vender_id = req.getParameter("vender_id").trim();
				if (vender_id == null || vender_id.trim().length() == 0) {
					errorMsgs.add("廠商名稱，請勿空白");
				} 
				String drcase_na = req.getParameter("drcase_na").trim();
				if (drcase_na == null || drcase_na.trim().length() == 0) {
					errorMsgs.add("方案名稱，請勿空白");
				} 
				String drcase_br = req.getParameter("drcase_br").trim();
				if (drcase_br == null || drcase_br.trim().length() == 0) {
					errorMsgs.add("方案簡介，請勿空白");
				}	
				Integer drcase_pr = null;
				try {
					drcase_pr = new Integer(req.getParameter("drcase_pr").trim());
				} catch (NumberFormatException e) {
					drcase_pr = 0;
					errorMsgs.add("方案價格，請填入正整數");
				}
				Integer drcase_st = null;
				try {
					drcase_st = new Integer(req.getParameter("drcase_st").trim());
				} catch (NumberFormatException e) {
					drcase_st = 0;
					errorMsgs.add("方案狀態，請填入正整數");
				}

				DressCaseVO dcVO = new DressCaseVO();
				dcVO.setVender_id(vender_id);
				dcVO.setDrcase_na(drcase_na);
				dcVO.setDrcase_br(drcase_br);
				dcVO.setDrcase_pr(drcase_pr);
				dcVO.setDrcase_st(drcase_st);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dcVO", dcVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/addDress_Vender.jsp");
					failureView.forward(req, res);
					return;
				}
				DressCaseService dcSvc = new DressCaseService();
				dcVO = dcSvc.addDress(vender_id, drcase_na, drcase_br, drcase_pr, drcase_st);
				/***************************3.新增完成，準備轉交(Send the Success view)***********/
				String url = "/front_end/vender/dress/vender_ListAllDC.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 轉交給ListAllDC_Vender.jsp
				req.setAttribute("venderVO", vVO);
				successView.forward(req, res);		
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/addDress_Vender.jsp");
				failureView.forward(req, res);
			}
		}
//        6.廠商新增圖片:from addDressPic.jsp的請求
        	if("insertPic".equals(action)) {
        		List<String> errorMsgs = new LinkedList<String>();
    			req.setAttribute("errorMsgs", errorMsgs);
    			try {
    				String drcase_id = req.getParameter("drcase_id");
    				
    				ByteArrayOutputStream bos = new ByteArrayOutputStream();
    				Collection<Part> parts = req.getParts();
    				for(Part part:parts) {
    					System.out.println(part.getName());
    					if(part.getName().equals("drpic")) {
    						InputStream is = part.getInputStream();
    						int i;
    						byte[] buffer = new byte[8*1024];
    						while ((i = is.read(buffer)) != -1) {
    							bos.write(buffer, 0, i);;
    						}
    					}
    				}
    				byte[] drpic = bos.toByteArray();
    				
    				if(drpic == null|| drpic.length == 0) {
    					errorMsgs.add("請上傳圖片");
    				}
    				
//    				用Service新增一個dpVO,設定其drcase_id和圖片內容
    				DressPicVO dpVO = new DressPicVO();
    				dpVO.setDrcase_id(drcase_id);
    				dpVO.setDrpic(drpic);
    				
    				DressCaseService dcSvc = new DressCaseService();
    				DressCaseVO dcVO = dcSvc.findByPrimaryKey(drcase_id);
    				
//    				因addDressPic需有dcVO，故需要帶著dcVO
    				if (!errorMsgs.isEmpty()) { 
    					req.setAttribute("dcVO", dcVO);
    					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/seeDressPic_Vender.jsp");
    					failureView.forward(req, res);
    					return;
    				}
    				DressPicService dpSvc = new DressPicService();
    				dpVO= dpSvc.insertdrPic(drcase_id, drpic);
					req.setAttribute("dcVO", dcVO);
    				String url = "/front_end/vender/dress/vender_ListAllDC.jsp";
    				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllDC.jsp
    				successView.forward(req, res);				
    				} catch (Exception e) {
        			errorMsgs.add("請上傳圖片!");
    				
    				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/addDressPic_Vender.jsp");
    				failureView.forward(req, res);
    				} 
        }
//        7. 廠商刪除圖片(seeDressPic_Vender.jsp)的請求
        	if("deletePic".equals(action)) {
        	List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String drpic_id = req.getParameter("drpic_id");
				String drcase_id = req.getParameter("drcase_id");
				DressPicService drSvc = new DressPicService();
				drSvc.deletedrPic(drpic_id);				
				DressCaseService dcSvc = new DressCaseService();
				DressCaseVO dcVO = dcSvc.findByPrimaryKey(drcase_id);
				
				req.setAttribute("dcVO", dcVO);
				String url = "/front_end/dresscase/seeDressPic_Vender.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);//成功轉交 addDressPic.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("錯誤發生QQ" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/seeDressPic_Vender.jsp");
				failureView.forward(req, res);
			}
		}	
//        	8.會員瀏覽單一方案
        	if ("getOne".equals(action)) { 
    			try {
    				String drcase_id = req.getParameter("drcase_id");
    				DressCaseService drSvc = new DressCaseService();
    				DressCaseVO dcVO = drSvc.findByPrimaryKey(drcase_id);
    				req.setAttribute("dcVO", dcVO);    
    				String url = "/front_end/dresscase/ListOneDC_guest.jsp";
    				RequestDispatcher successView = req.getRequestDispatcher(url);
    				successView.forward(req, res);
    			} catch (Exception e) {
    				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/DressHome.jsp");
    				failureView.forward(req, res);
    			}
    		}//end of 8.
//        	9. 廠商查看或刪除圖片
        	if("seePic".equals(action)) {
        		try {
        		String drcase_id = req.getParameter("drcase_id");
        		DressCaseService drSvc = new DressCaseService();
				DressCaseVO dcVO = drSvc.findByPrimaryKey(drcase_id);
				req.setAttribute("dcVO", dcVO);
				String url = "/front_end/dresscase/seeDressPic_Vender.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
        		} catch(Exception e) {
    				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/DressHome.jsp");
    				failureView.forward(req, res);
    			}
        		
        	}
        	
//        	10.從廠商婚紗訂單管理首頁，連到新增方案
        	if("seeInsert".equals(action)) {
        		try {
        			String vender_id = req.getParameter("vender_id");
        			VenderService vSvc = new VenderService();
        			VenderVO vVO = vSvc.findByPrimaryKey(vender_id);
        			req.setAttribute("venderVO", vVO);
        			RequestDispatcher successView = req.getRequestDispatcher("/front_end/dresscase/addDress_Vender.jsp");
        			successView.forward(req, res);
        		}catch (Exception e) {
        			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/dress/vender_ListAllDC.jsp");
    				failureView.forward(req, res);
        		}
        		
        	}
	}
}