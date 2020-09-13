package com.dressorder.controller;

//Vender_id待改:260行
//membre_id待改:262行
import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.dressaddon.model.*;
import com.dressaddorder.model.*;
import com.dresscase.model.*;
import com.dressorder.model.*;
import com.dressorderdetail.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONObject;

public class DressOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//1. DressOrder 總首頁
		if ("getOne_For_Display".equals(action)) { // 來自DressOrde_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this list in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.抓Dress_page頁面上的請求參數，並做格式的錯誤處理**********************/
				String str = req.getParameter("drord_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入您想尋找的婚紗訂單編號~");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dressorder/DressOrder_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				// 如果字串長度不等於0，且也無錯誤訊息，則檢查其方案編號是否為六位
				if (str.length() != 6) {
					errorMsgs.add("婚紗訂單編號格式錯誤");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dressorder/DressOrder_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				DressOrderService doSvc = new DressOrderService();
				DressOrderVO doVO = doSvc.findByPrimaryKey(str);
				if (doVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dressorder/DressOrder_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************3.查詢完成，準備轉交(Send the Success view)*************/
				req.setAttribute("doVO", doVO); // 從DB中取出doVO物件，存入req
				String url = "/front_end/dressorder/ListOneOrder_Vender.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneOrder_Vender.jsp
				successView.forward(req, res);

				/***************************其他錯誤處理:導到DressCase HomePage*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dressorder/DressOrder_page.jsp");
				failureView.forward(req, res);
			}
		}
		//2. 更新
		if ("getOne_For_Update".equals(action)) { // 來自listAllDressCase.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String drord_id = (req.getParameter("drord_id"));
				DressOrderService doSvc = new DressOrderService();
				DressOrderVO doVO = doSvc.findByPrimaryKey(drord_id);
				req.setAttribute("doVO", doVO);      // 從DB取出dcVO物件，存入req
				String url = "/front_end/dressorder/updateDressOrderInput.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);//成功轉交 updateDressCaseInput.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dressorder/ListAllOrder_Vender.jsp");
				failureView.forward(req, res);
			}
		}
		
		//3. 更新  
		if ("update".equals(action)) { // from updateDressCaseInput.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				String drord_id = req.getParameter("drord_id");
				String membre_id = req.getParameter("membre_id");
				String vender_id = req.getParameter("vender_id");
				String drord_time = req.getParameter("drord_time");
				Integer drord_pr = new Integer(req.getParameter("drord_pr").trim());
				Integer drord_depo = new Integer(req.getParameter("drord_depo").trim());
				Integer drord_ini = new Integer(req.getParameter("drord_ini").trim());
				
				Timestamp order_time = new Timestamp(System.currentTimeMillis());  
				try {  
					order_time= Timestamp.valueOf(drord_time);  
				} catch (Exception e) {  
				e.printStackTrace();  
				} 
				
				Integer drord_pay_st = null;
				try {
					drord_pay_st = new Integer(req.getParameter("drord_pay_st").trim());
				} catch (NumberFormatException e) {
					drord_pay_st = 0;
					errorMsgs.add("付款狀態，請填入正整數");
				}

				Integer drord_fin_st = null;
				try {
					drord_fin_st = new Integer(req.getParameter("drord_fin_st").trim());
				} catch (NumberFormatException e) {
					drord_pay_st = 0;
					errorMsgs.add("訂單完成狀態，請填入正整數");
				}
				Integer dr_mrep_st = null;
				try {
					dr_mrep_st = new Integer(req.getParameter("dr_mrep_st").trim());
				} catch (NumberFormatException e) {
					dr_mrep_st = 0;
					errorMsgs.add("會員檢舉狀態，請填入正整數");
				}
				Integer dr_vrep_st = null;
				try {
					dr_vrep_st = new Integer(req.getParameter("dr_vrep_st").trim());
				} catch (NumberFormatException e) {
					dr_vrep_st = 0;
					errorMsgs.add("廠商檢舉狀態，請填入正整數");
				}
				
				String dr_mrep_de = req.getParameter("dr_mrep_de").trim();
				if (dr_mrep_de == null || dr_mrep_de.trim().length() == 0) {
						errorMsgs.add("會員檢舉細節，請勿空白");
				}	
				String dr_vrep_de = req.getParameter("dr_vrep_de").trim();
				if (dr_vrep_de == null || dr_vrep_de.trim().length() == 0) {
						errorMsgs.add("廠商檢舉細節，請勿空白");
				}
				String dr_mrep_res = req.getParameter("dr_mrep_res").trim();
				if (dr_mrep_res == null || dr_mrep_res.trim().length() == 0) {
						errorMsgs.add("會員檢舉結果，請勿空白");
				}	
				String dr_vrep_res = req.getParameter("dr_vrep_res").trim();
				if (dr_vrep_res == null || dr_vrep_res.trim().length() == 0) {
						errorMsgs.add("廠商檢舉結果，請勿空白");
				}
				Integer dr_rev_star = null;
				try {
					dr_rev_star = new Integer(req.getParameter("dr_rev_star").trim());
				} catch (NumberFormatException e) {
					dr_rev_star = 0;
					errorMsgs.add("訂單評價星數，請填入正整數");
				}
				String dr_rev_con = req.getParameter("dr_rev_con").trim();
				if (dr_rev_con == null || dr_rev_con.trim().length() == 0) {
						errorMsgs.add("訂單評價內容，請勿空白");
				}
				
				DressOrderVO doVO = new DressOrderVO();
				doVO.setDrord_id(drord_id);
				doVO.setMembre_id(membre_id);
				doVO.setVender_id(vender_id);
				doVO.setDrord_pr(drord_pr);
				doVO.setDrord_depo(drord_depo);
				doVO.setDrord_ini(drord_ini);
				
				doVO.setDrord_pay_st(drord_pay_st);
				doVO.setDrord_fin_st(drord_fin_st);
				doVO.setDr_mrep_st(dr_mrep_st);
				doVO.setDr_vrep_st(dr_vrep_st);
				doVO.setDr_mrep_de(dr_mrep_de);
				doVO.setDr_vrep_de(dr_vrep_de);
				
				doVO.setDr_mrep_res(dr_mrep_res);
				doVO.setDr_vrep_res(dr_vrep_res);
				doVO.setDr_rev_star(dr_rev_star);
				doVO.setDr_rev_con(dr_rev_con);
				doVO.setDrord_time(order_time);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dcVO", doVO); // 將輸入格式錯誤的dcVO物件，存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dressorder/updateDressOrderInput.jsp");
					failureView.forward(req, res);
					return; 
				}
				
				DressOrderService doSvc = new DressOrderService();
				doVO = doSvc.updateDrOrder(drord_id,membre_id,vender_id,order_time,drord_pr,drord_depo,drord_ini,
						drord_pay_st,drord_fin_st,dr_mrep_st, dr_vrep_st, dr_mrep_de, dr_vrep_de,
						 dr_mrep_res,  dr_vrep_res, dr_rev_star, dr_rev_con);
				
				/***************************3.修改完成，準備轉交(Send the Success view)*************/
				req.setAttribute("doVO", doVO); // 將正確的dcVO存入req
				String url = "/front_end/dressorder/ListOneOrder_Vender.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); //修改成功後，轉交給listOneOrder.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dressorder/updateDressOrderInput.jsp");
//				若錯誤則回到原網頁
				failureView.forward(req, res);
			}
		}
		// 4. 結帳
        if ("CHECKOUT".equals(action)) { // from Cart.jsp 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
//				取出amount,vender_id
				String amount =  req.getParameter("amount");
				System.out.println(amount);
				String vender_id = req.getParameter("vender_id");
				
				//4-1:addOrder
//				membreID先hardcore為M004,待改成從session中getMembreVO.getMembre_id()
				String membre_id = req.getParameter("membre_id");
				
				DressOrderService doSvc = new DressOrderService();
				Integer drord_pr =  Integer.parseInt(amount);
				Integer drord_depo = (Integer)drord_pr *3 /10;
				Integer drord_ini = (Integer) drord_pr *3/10;
//				預設的各種訂單狀態順序為(0,1,0,0)
				doSvc.addDrOrder(membre_id, vender_id, drord_pr, drord_depo, drord_ini, 0, 1, 0, 0);
//				取出OrderID以給OrderDetail用
				String ord_id = doSvc.findLatestOrder(membre_id).getDrord_id();
				
				//4-2:addOrderDetail:抓drde_id
//				抓購物車內容(map)
				HttpSession session = req.getSession();
				@SuppressWarnings("unchecked")
				LinkedHashMap<DressCaseVO,List<DressAddOnVO>> map = (LinkedHashMap<DressCaseVO,List<DressAddOnVO>>) session.getAttribute("dresscart");
				@SuppressWarnings("unchecked")
				List<DressCaseVO> dressList = (List<DressCaseVO>) session.getAttribute("dressList");
				
				System.out.println(dressList.size());
				//第一層迭代
				for(int i=0;i<dressList.size();i++) {
					DressCaseVO dcVO= dressList.get(i);
					String drcase_id = dcVO.getDrcase_id();
					Integer drcase_totpr = dcVO.getDrcase_pr();
					
//					計算一個方案加上其(1-多個)加購項目的價錢
					if(map.get(dcVO).size()>0) {
						List<DressAddOnVO> addList = map.get(dcVO);
//						第二層迭代
						for(int j=0;j<addList.size();j++) {
							DressAddOnVO daoVO = addList.get(j);
							drcase_totpr += daoVO.getDradd_pr();
					}
					}
//					插入orderDetail
					DressOrderDetailService dodSvc = new DressOrderDetailService();
					dodSvc.addDressOrder(ord_id, drcase_id, 0, drcase_totpr);
//					
//					//4-3:插入DRESS_ADD_ORDER(關聯dradd_id與drde_id的表格)	
					String drde_id = dodSvc.findByDrcaseAndOrder(drcase_id,ord_id).getDrde_id();
					if(map.get(dcVO).size()>0) {
						
						List<DressAddOnVO> addList = map.get(dcVO);
						for(int k=0;k<addList.size();k++) {
							String dradd_id= addList.get(k).getDradd_id();
//							插入資料!!!
							DressAddOrderService daoSvc = new DressAddOrderService();
							daoSvc.add(dradd_id, drde_id);
						}
//					若map.size()為0，表該order_detail沒有add-on，故不需插入Dradd_order表格
					}
				}
//				完成下訂後，session中的dresscart將以order和orderDetail的形式存在req中:
				session.removeAttribute("dresscart");
				session.removeAttribute("dressList");
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/Cart.jsp");
					failureView.forward(req, res);
					return;
				}
				String url = "/front_end/dresscase/OrderSuccess_membre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/Cart.jsp");
				failureView.forward(req, res);
			}
		}// end of CheckOut
//       5. membre在訂單頁面看訂單內容
        if("SeeDetail".equals(action)){
        	String drord_id = req.getParameter("order_id");
        	System.out.println(drord_id);
        	DressOrderDetailService  dSvc = new DressOrderDetailService();
        	List<DressOrderDetailVO> deVOList = dSvc.findByOrder(drord_id);
        	
//        	由order_id，找尋相對應的訂單明細，再由訂單明細去找尋婚紗方案與加購項目
        	if(deVOList != null && deVOList.size()>0) {
//        		母層
        		Integer detail_pr = 0;
        		List<String> caseNaList = new ArrayList<String>();
    			List<Integer> casePrList = new ArrayList<Integer>();
    			List<List<String>> addNaList = new ArrayList<List<String>>();
    			List<Integer> dePrList = new ArrayList<Integer>();
    			Integer row= deVOList.size();
    			
//    			遍歷每個order_detail：子層
        		for(int i=0;i<row;i++) {
        			
        			detail_pr = 0;
        			String drde_id = deVOList.get(i).getDrde_id();
        			String drcase_id = deVOList.get(i).getDrcase_id();
//        			找方案名字
        			DressCaseService dcSvc = new DressCaseService();
        			DressCaseVO dcVO = dcSvc.findByPrimaryKey(drcase_id);
        			caseNaList.add(dcVO.getDrcase_na());
        			detail_pr += dcVO.getDrcase_pr();
        			casePrList.add(dcVO.getDrcase_pr());
//        			找加購
        			DressAddOrderService daSvc = new DressAddOrderService();
        			List<DressAddOrderVO> addOrderVOList = daSvc.findByDrde_id(drde_id);
        			
        			List<String> naArr = new ArrayList<String>();
//        			加購項目可能為0-多個，故要再多包一層arrayList：孫層：遍歷addVO
        			if(addOrderVOList!=null & addOrderVOList.size()>0) {
        				for(int j=0;j<addOrderVOList.size();j++) {
        					String add_id = addOrderVOList.get(j).getDradd_id();
//        					找加購項目名稱
        					DressAddOnService daoSvc = new DressAddOnService();
        					DressAddOnVO addVO = daoSvc.findByPrimaryKey(add_id);
        					naArr.add(addVO.getDradd_na());
        					detail_pr += addVO.getDradd_pr();
        				}
        			}
        			addNaList.add(naArr);
        			dePrList.add(detail_pr);
        			
        		} //end of for
        		Gson gson = new GsonBuilder().create();
    			JsonArray caseNaArr = gson.toJsonTree(caseNaList).getAsJsonArray();
    			JsonArray casePrArr = gson.toJsonTree(casePrList).getAsJsonArray();
    			JsonArray addNaArr = gson.toJsonTree(addNaList).getAsJsonArray();
    			JsonArray dePrArr = gson.toJsonTree(dePrList).getAsJsonArray();
    			
    			JSONObject mainObj = new JSONObject();
    			mainObj.put("caseNa",caseNaArr);
    			mainObj.put("casePr",casePrArr);
    			mainObj.put("addNa",addNaArr);
    			mainObj.put("dePr", dePrArr);
    			mainObj.put("row", row);
        		res.setContentType("application/json");
    			res.setCharacterEncoding("UTF-8");
    			res.getWriter().print(mainObj);
    			System.out.println(mainObj);
        	}
        }
//      6.  membre/vender在訂單頁面看檢舉內容
        if("SeeRep".equals(action)){
        	String drord_id = req.getParameter("order_id");
        	DressOrderService  dSvc = new DressOrderService();
        	
        	DressOrderVO doVO = dSvc.findByPrimaryKey(drord_id);
        	Integer dr_mrep_st = doVO.getDr_mrep_st();
        	Integer dr_vrep_st = doVO.getDr_vrep_st();
        	String mrep_de = doVO.getDr_mrep_de();
        	String vrep_de = doVO.getDr_vrep_de();
        	String mrep_res = doVO.getDr_mrep_res();
        	String vrep_res = doVO.getDr_vrep_res();
        	
        	JSONObject rep = new JSONObject();
        	rep.put("mSt", dr_mrep_st);
        	rep.put("vSt", dr_vrep_st);
        	rep.put("mDe", mrep_de);
        	rep.put("vDe", vrep_de);
        	rep.put("mRes", mrep_res);
        	rep.put("vRes", vrep_res);
//        	偷渡drord_id回前端
        	rep.put("drord_id", drord_id);
        	
    		res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(rep);
			System.out.println(rep);
        	}
        //7.會員送出檢舉
        if("confirmRep".equals(action)){
        	String drord_id = req.getParameter("order_id");
        	String mrep_de = req.getParameter("mrep_de");
        	System.out.println(drord_id);
        	System.out.println(mrep_de);
        	DressOrderService dSvc = new DressOrderService();
        	DressOrderVO doVO = dSvc.findByPrimaryKey(drord_id);
        	String membre_id = doVO.getMembre_id();
    		String vender_id = doVO.getVender_id();
    		Timestamp drord_time = doVO.getDrord_time();
    		Integer drord_pr = doVO.getDrord_pr();
    		Integer drord_depo = doVO.getDrord_depo();
    		Integer drord_ini = doVO.getDrord_ini();
    		Integer drord_pay_st = doVO.getDrord_pay_st();
    		Integer drord_fin_st = doVO.getDrord_fin_st();
    		
    		Integer dr_vrep_st = doVO.getDr_vrep_st();
    		String vrep_de = doVO.getDr_vrep_de();
    		String dr_mrep_res = doVO.getDr_mrep_res();
    		String dr_vrep_res = doVO.getDr_vrep_res();
    		
    		String review = doVO.getDr_rev_con();
        	
    		dSvc.updateDrOrder(drord_id, membre_id, vender_id, drord_time, drord_pr, drord_depo, drord_ini, drord_pay_st, drord_fin_st,
    				1, dr_vrep_st, mrep_de,vrep_de, dr_mrep_res, dr_vrep_res, 0, review);
    		
    		RequestDispatcher view = req.getRequestDispatcher("/front_end/membre_order/membre_order_dress.jsp");
    		view.forward(req, res);	
        }
        
//      8. 廠商送出檢舉
        if("venRep".equals(action)){
        	String drord_id = req.getParameter("order_id");
        	String vrep_de = req.getParameter("vrep_de");
        	
        	DressOrderService dSvc = new DressOrderService();
        	DressOrderVO doVO = dSvc.findByPrimaryKey(drord_id);
        	String membre_id = doVO.getMembre_id();
    		String vender_id = doVO.getVender_id();
    		Timestamp drord_time = doVO.getDrord_time();
    		Integer drord_pr = doVO.getDrord_pr();
    		Integer drord_depo = doVO.getDrord_depo();
    		Integer drord_ini = doVO.getDrord_ini();
    		Integer drord_pay_st = doVO.getDrord_pay_st();
    		Integer drord_fin_st = doVO.getDrord_fin_st();
    		
    		Integer dr_mrep_st = doVO.getDr_mrep_st();
    		String mrep_de = doVO.getDr_mrep_de();
    		String dr_mrep_res = doVO.getDr_mrep_res();
    		String dr_vrep_res = doVO.getDr_vrep_res();
    		
    		String review = doVO.getDr_rev_con();
        	
    		dSvc.updateDrOrder(drord_id, membre_id, vender_id, drord_time, drord_pr, drord_depo, drord_ini, drord_pay_st, drord_fin_st,
    				dr_mrep_st, 1, mrep_de,vrep_de, dr_mrep_res, dr_vrep_res, 0, review);
    		
    		RequestDispatcher view = req.getRequestDispatcher("/front_end/vender/dress/vender_listDressOrder.jsp");
    		view.forward(req, res);	
        }
//      
//        9.  會員完成訂單:from listOrder_Membre.jsp
        if("memComp".equals(action)){
        	String drord_id = req.getParameter("order_id");
        	System.out.println("Hi there");
        	DressOrderService dSvc = new DressOrderService();
        	DressOrderVO doVO = dSvc.findByPrimaryKey(drord_id);
        	Integer drord_fin_st = doVO.getDrord_fin_st();
        	
//        	如果訂單狀態為1，會員才能完成(訂單狀態改為3)，才有必要更新此訂單;其他情形無效。
        	if(drord_fin_st == 1) {
        		String membre_id = doVO.getMembre_id();
        		String vender_id = doVO.getVender_id();
        		Timestamp drord_time = doVO.getDrord_time();
        		Integer drord_pr = doVO.getDrord_pr();
        		Integer drord_depo = doVO.getDrord_depo();
        		Integer drord_ini = doVO.getDrord_ini();
        		Integer drord_pay_st = doVO.getDrord_pay_st();
        		Integer dr_mrep_st = doVO.getDr_mrep_st();
        		Integer dr_vrep_st = doVO.getDr_vrep_st();
        		String mrep_de = doVO.getDr_mrep_de();
        		String vrep_de = doVO.getDr_vrep_de();
        		String dr_mrep_res = doVO.getDr_mrep_res();
        		String dr_vrep_res = doVO.getDr_vrep_res();
        		String review = doVO.getDr_rev_con();

        		dSvc.updateDrOrder(drord_id, membre_id, vender_id, drord_time, drord_pr, drord_depo, drord_ini, drord_pay_st, 3,
        				dr_mrep_st,dr_vrep_st, mrep_de,vrep_de, dr_mrep_res, dr_vrep_res, 0, review);
        		
        		RequestDispatcher view = req.getRequestDispatcher("/front_end/membre_order/membre_order_dress.jsp");
        		view.forward(req, res);	

        	}
        }
//        10.廠商確認完成訂單
        if("vfinOrder".equals(action)){
        	String drord_id = req.getParameter("order_id");
        	
        	DressOrderService dSvc = new DressOrderService();
        	DressOrderVO doVO = dSvc.findByPrimaryKey(drord_id);
        	Integer drord_fin_st = doVO.getDrord_fin_st();
        	
//        	如果訂單狀態為3，廠商才能完成(訂單狀態改為4)，才有必要更新此訂單;其他情形無效。
        	if(drord_fin_st == 3) {
        		String membre_id = doVO.getMembre_id();
        		String vender_id = doVO.getVender_id();
        		Timestamp drord_time = doVO.getDrord_time();
        		Integer drord_pr = doVO.getDrord_pr();
        		Integer drord_depo = doVO.getDrord_depo();
        		Integer drord_ini = doVO.getDrord_ini();
        		Integer drord_pay_st = doVO.getDrord_pay_st();
        		Integer dr_mrep_st = doVO.getDr_mrep_st();
        		Integer dr_vrep_st = doVO.getDr_vrep_st();
        		String mrep_de = doVO.getDr_mrep_de();
        		String vrep_de = doVO.getDr_vrep_de();
        		String dr_mrep_res = doVO.getDr_mrep_res();
        		String dr_vrep_res = doVO.getDr_vrep_res();
        		String review = doVO.getDr_rev_con();

        		dSvc.updateDrOrder(drord_id, membre_id, vender_id, drord_time, drord_pr, drord_depo, drord_ini, drord_pay_st, 4,
        				dr_mrep_st,dr_vrep_st, mrep_de,vrep_de, dr_mrep_res, dr_vrep_res, 0, review);
        		
        	}
        	RequestDispatcher view = req.getRequestDispatcher("/front_end/vender/dress/vender_listDressOrder.jsp");
    		view.forward(req, res);	
        }
//        11.會員評價訂單
        if("review".equals(action)){
        	String drord_id = req.getParameter("order_id");
        	String review_con = req.getParameter("review_con");
        	String stars = req.getParameter("numOfStar");
        	
        	DressOrderService dSvc = new DressOrderService();
        	DressOrderVO doVO = dSvc.findByPrimaryKey(drord_id);
        	Integer drord_fin_st = doVO.getDrord_fin_st();
        	
//        	如果訂單狀態為4，會員才能評價(訂單狀態改為5)，才有必要更新此訂單;其他情形無效。
        	if(drord_fin_st == 4) {
        		String membre_id = doVO.getMembre_id();
        		String vender_id = doVO.getVender_id();
        		Timestamp drord_time = doVO.getDrord_time();
        		Integer drord_pr = doVO.getDrord_pr();
        		Integer drord_depo = doVO.getDrord_depo();
        		Integer drord_ini = doVO.getDrord_ini();
        		Integer drord_pay_st = doVO.getDrord_pay_st();
        		Integer dr_mrep_st = doVO.getDr_mrep_st();
        		Integer dr_vrep_st = doVO.getDr_vrep_st();
        		String mrep_de = doVO.getDr_mrep_de();
        		String vrep_de = doVO.getDr_vrep_de();
        		String dr_mrep_res = doVO.getDr_mrep_res();
        		String dr_vrep_res = doVO.getDr_vrep_res();

        		dSvc.updateDrOrder(drord_id, membre_id, vender_id, drord_time, drord_pr, drord_depo, drord_ini, drord_pay_st, 5,
        				dr_mrep_st,dr_vrep_st, mrep_de,vrep_de, dr_mrep_res, dr_vrep_res, 0, review_con);
        		} // end of inner-if
        		RequestDispatcher view = req.getRequestDispatcher("/front_end/membre_order/membre_order_dress.jsp");
        		view.forward(req, res);
        } //end of 11.
        
//        12.後台審核檢舉成果
        if("backRep".equals(action)){
        	
        	String drord_id = req.getParameter("order_id");
        	DressOrderService dSvc = new DressOrderService();
        	DressOrderVO doVO = dSvc.findByPrimaryKey(drord_id);
 
        	Integer vrep_st = null;
        	if(req.getParameter("vrep_st")== null) {
        		vrep_st = doVO.getDr_vrep_st();
        	}else {
        		vrep_st = new Integer(req.getParameter("vrep_st"));
        	}
			
			Integer mrep_st = null;
			if(req.getParameter("mrep_st")== null) {
        		mrep_st = doVO.getDr_mrep_st();
        	} else {
        		mrep_st = new Integer(req.getParameter("mrep_st"));
        	}
			
        	System.out.println(drord_id);
        	System.out.println(vrep_st);
        	System.out.println(mrep_st);
        	
//        	更改檢舉審核結果(vrep_st)與(mrep_st)
    		String membre_id = doVO.getMembre_id();
    		String vender_id = doVO.getVender_id();
    		Timestamp drord_time = doVO.getDrord_time();
    		Integer drord_pr = doVO.getDrord_pr();
    		Integer drord_depo = doVO.getDrord_depo();
    		Integer drord_ini = doVO.getDrord_ini();
    		Integer drord_pay_st = doVO.getDrord_pay_st();
    		Integer drord_fin_st = doVO.getDrord_fin_st();
    		
    		String mrep_de = doVO.getDr_mrep_de();
    		String vrep_de = doVO.getDr_vrep_de();
    		String dr_mrep_res = doVO.getDr_mrep_res();
    		String dr_vrep_res = doVO.getDr_vrep_res();
    		String review = doVO.getDr_rev_con();

    		dSvc.updateDrOrder(drord_id, membre_id, vender_id, drord_time, drord_pr, drord_depo, drord_ini, drord_pay_st, drord_fin_st,
    				mrep_st,vrep_st, mrep_de,vrep_de, dr_mrep_res, dr_vrep_res, 0,review );
    		RequestDispatcher view = req.getRequestDispatcher("/back_end/dressorder/listOrder.jsp");
    		view.forward(req, res);
        } 
        
        
	}
}
