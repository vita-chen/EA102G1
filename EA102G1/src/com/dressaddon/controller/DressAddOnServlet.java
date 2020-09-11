package com.dressaddon.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.dressaddon.model.*;
import com.dresscase.model.DressCaseService;
import com.dresscase.model.DressCaseVO;
import com.vender.model.VenderService;
import com.vender.model.VenderVO;

public class DressAddOnServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 1. 廠商新增加購項目
        if ("insert".equals(action)) { // from addDress.jsp 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				//drcase_id為自增主鍵，故不做判斷
				String vender_id = req.getParameter("vender_id").trim();
				if (vender_id == null || vender_id.trim().length() == 0) {
					errorMsgs.add("廠商名稱，請勿空白");
				} 
				
				String dradd_type = req.getParameter("dradd_type").trim();
				if (dradd_type == null || dradd_type.trim().length() == 0) {
					errorMsgs.add("加購項目種類，請勿空白");
				} 
				
				String dradd_na = req.getParameter("dradd_na").trim();
				if (dradd_na == null || dradd_na.trim().length() == 0) {
					errorMsgs.add("加購項目名稱，請勿空白");
				}	

				Integer dradd_pr = null;
				try {
					dradd_pr = new Integer(req.getParameter("dradd_pr").trim());
				} catch (NumberFormatException e) {
					dradd_pr = 0;
					errorMsgs.add("加購項目價格，請填入正整數");
				}

				Integer dradd_st = null;
				try {
					dradd_st = new Integer(req.getParameter("dradd_st").trim());
				} catch (NumberFormatException e) {
					dradd_st = 0;
					errorMsgs.add("方案狀態，請填入正整數");
				}
				
				DressAddOnVO daVO = new DressAddOnVO();
				daVO.setVender_id(vender_id);
				daVO.setDradd_type(dradd_type);
				daVO.setDradd_na(dradd_na);
				daVO.setDradd_pr(dradd_pr);
				daVO.setDradd_st(dradd_st);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("daVO", daVO); // 含有輸入格式錯誤的dcVO，存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dressaddon/addAddon.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				DressAddOnService daoSvc = new DressAddOnService();
				daVO= daoSvc.addDraddOn(vender_id, dradd_type, dradd_na, dradd_pr, dradd_st);
				
				/***************************3.新增完成，準備轉交(Send the Success view)***********/
				String url = "/front_end/vender/dress/vender_ListAddDressOn.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 轉交給AddOn.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("格式錯拉");
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dressaddon/addAddOn.jsp");
				failureView.forward(req, res);
			}
		}
        
//        2.UpdateAddOn_Vender.jsp：更新功能
        	if ("update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.抓參數+輸入格式錯誤處理**********************/
				String dradd_id = req.getParameter("dradd_id");
				System.out.println(dradd_id);
				String vender_id = req.getParameter("vender_id");
				System.out.println(vender_id);
				String dradd_na = req.getParameter("dradd_na");
				System.out.println(dradd_na);
				String dradd_type = req.getParameter("dradd_type");
				System.out.println(dradd_type);
				
				Integer	dradd_pr = new Integer(req.getParameter("dradd_pr").trim());
				System.out.println(dradd_pr);
				Integer dradd_st =  new Integer(req.getParameter("dradd_st").trim());
				System.out.println(dradd_st);
				
				DressAddOnVO daoVO = new DressAddOnVO();
				daoVO.setDradd_id(dradd_id);
				daoVO.setVender_id(vender_id);
				daoVO.setDradd_na(dradd_na);
				daoVO.setDradd_type(dradd_type);
				daoVO.setDradd_pr(dradd_pr);
				daoVO.setDradd_st(dradd_st);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("daoVO", daoVO); // 將輸入格式錯誤的dcVO物件，存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dressaddon/updateAddOn.jsp");
					failureView.forward(req, res);
					return; 
				}
				DressAddOnService dcSvc = new DressAddOnService();
				daoVO = dcSvc.updateDraddOn(dradd_id,vender_id,dradd_na,dradd_type,dradd_pr,dradd_st);
				req.setAttribute("daoVO", daoVO); // 將正確的dcVO存入req
				String url = "/front_end/vender/dress/vender_ListAddDressOn.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); //修改成功後，轉交給listOneEmp.jsp
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dressaddon/updateAddOn.jsp");
				failureView.forward(req, res);
			}
        }
//        	3.ListAddOn_Vender.jsp:選取要更新的addOn
        	if ("getOne_For_Update".equals(action)) {
    			String dradd_id = req.getParameter("dradd_id");
    			DressAddOnService daoSvc = new DressAddOnService();
    			DressAddOnVO daVO = daoSvc.findByPrimaryKey(dradd_id);
    			
    			req.setAttribute("addonVO", daVO);
    			String url = "/front_end/dressaddon/updateAddOn.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); //修改成功後，轉交給listOneEmp.jsp
				successView.forward(req, res);
        	}
//        	4.ListAddOn_Vender.jsp:刊登新方案
        	if("seeInsert".equals(action)) {
        		try {
        			String vender_id = req.getParameter("vender_id");
        			System.out.println(vender_id);
        			VenderService vSvc = new VenderService();
        			VenderVO vVO = vSvc.findByPrimaryKey(vender_id);
        			req.setAttribute("venderVO", vVO);
        			RequestDispatcher successView = req.getRequestDispatcher("/front_end/dressaddon/addAddon.jsp");
        			successView.forward(req, res);
        		}catch (Exception e) {
        			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/vender/dress/vender_ListAddDressOn.jsp");
    				failureView.forward(req, res);
        		}
        		
        	}
	}
}
