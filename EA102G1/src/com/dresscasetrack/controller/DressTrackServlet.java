package com.dresscasetrack.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONObject;

import com.dresscase.model.DressCaseService;
import com.dresscase.model.DressCaseVO;
import com.dresscasetrack.model.*;

//34與72行的membre_id：待改
public class DressTrackServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		List<String> errors = new ArrayList<String>();
		
//		會員將方案加入追蹤清單
		if("addTrack".equals(action)) {
			
		try {
			String drcase_id = req.getParameter("drcase_id");
			String membre_id = req.getParameter("membre_id");
			DressCaseService dcSvc = new DressCaseService();
			DressCaseVO dcVO = dcSvc.findByPrimaryKey(drcase_id);
			DressCaseTrackService dctSvc = new DressCaseTrackService();
		
//			檢查該方案是否已存在該會員的追蹤清單
		List<DressCaseTrackVO> trackList = dctSvc.findByMembre(membre_id);
		if(trackList.size()>0 && trackList!= null) {
			for(int i=0;i<trackList.size();i++) {
				if(trackList.get(i).getDrcase_id().contentEquals(drcase_id)) {
					errors.add("已追蹤此方案!");
					req.setAttribute("dcVO", dcVO);
					return;
					}
				}//end of for迴圈：表示該會員追蹤清單沒有該方案
			} // end of if:表示該會員追蹤清單為空值
			//上述兩種情形，都要addTrack
			System.out.println(drcase_id+" "+membre_id);
			dctSvc.addTrack(drcase_id, membre_id);	
			
			JSONObject mainObj = new JSONObject();
			mainObj.put("success",1);
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(mainObj);
			
		}	catch (Exception e) {
			errors.add("無法取得要修改的資料" + e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/ListOneDC_guest.jsp");
			failureView.forward(req, res);
		}
		}
		
//		2.會員將方案從追蹤清單中移除
		if("delTrack".equals(action)) {
		try {
			String drcase_id = req.getParameter("drcase_id");
//			membre_id待改
			String membre_id = req.getParameter("membre_id");
			
			DressCaseTrackService dctSvc = new DressCaseTrackService();
			dctSvc.deleteTrack(drcase_id,membre_id);
			
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/ListAllTrack_membre.jsp");
			failureView.forward(req, res);
		}	catch (Exception e) {
			errors.add("無法取得要修改的資料" + e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dresscase/ListAllTrack_membre.jsp");
			failureView.forward(req, res);
		}
		}
	}
}
