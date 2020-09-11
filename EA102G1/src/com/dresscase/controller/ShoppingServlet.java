package com.dresscase.controller;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.dresscase.model.*;
import com.dressaddon.model.*;

import org.json.JSONObject;
import com.google.gson.*;

public class ShoppingServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		List<String> errors = new ArrayList<String>();
		String action = req.getParameter("action");
		
//		1.客人看加購項目
		if(action.equals("SeeAddOn")) {
			//ajax
			String vender_id = req.getParameter("vender_id");
			DressAddOnService daSvc = new DressAddOnService();
			List<DressAddOnVO> list= daSvc.findByVender(vender_id);
			
			List<String> idList = new ArrayList<String>();
			List<String> naList = new ArrayList<String>();
			List<Integer> prList = new ArrayList<Integer>();
			
			int row = 0;
			for(DressAddOnVO daVO:list) {
//				只會撈出有上架的加購項目，送給前台
				if (daVO.getDradd_st() == 1) {
					String dradd_id= daVO.getDradd_id();
					idList.add(dradd_id);
					String dradd_na = daVO.getDradd_na();
					naList.add(dradd_na);
					Integer dradd_pr = daVO.getDradd_pr();
					prList.add(dradd_pr);
//				資料筆數，給前台用
					row++;
				}     
			}
			
			Gson gson = new GsonBuilder().create();
			JsonArray idArr = gson.toJsonTree(idList).getAsJsonArray();
			JsonArray naArr = gson.toJsonTree(naList).getAsJsonArray();
			JsonArray prArr = gson.toJsonTree(prList).getAsJsonArray();
			JSONObject mainObj = new JSONObject();
			mainObj.put("addid",idArr);
			mainObj.put("addna",naArr);
			mainObj.put("addpr",prArr);
			mainObj.put("row", row);
			
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(mainObj);
			} //end of seeAddOn
		// 2.加入購物車
		else if (action.equals("ADD")) {
			HttpSession session = req.getSession();
//			因輸出的方案順序需等於放入的方案順序，故選用LinkedHashMap，而不用treeMap或hashMap
			@SuppressWarnings("unchecked")
			LinkedHashMap<DressCaseVO,List<DressAddOnVO>> map = (LinkedHashMap<DressCaseVO,List<DressAddOnVO>>) session.getAttribute("dresscart");
			
//				設置flag，預防user重複購買同一方案
				boolean match = false;
				DressCaseVO dcVO2 = getDr(req);
				List<DressAddOnVO> addList2 = getAddOn(req);
				
//				2-1:若map為空值，則須new出一個map
//				(1)在map內以key的形式放入dresscaseVO (2)在map內以value的形式放入list<DressAddOnVO>
			    if(map==null) {
			    	map = new LinkedHashMap<DressCaseVO,List<DressAddOnVO>>();
			    	map.put(dcVO2, addList2);
			    	System.out.println(addList2.size()+"Hey");
			    }
			    else {
//			    	若確定不是同一件，則放入購物車:
			    	if(!match)
			    		map.put(dcVO2, addList2);
			    } 
//			    在session中放入map
				session.setAttribute("dresscart", map);
				String url = "/front_end/dresscase/Cart.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
			} //end of add 
		
//		3.從購物車內刪除{DressCaseVO,DressAddOnList}
		else if (action.equals("DELETE")) {
			String drcase_id = req.getParameter("del");
			HttpSession session = req.getSession();
			
//			因輸出的方案順序需等於放入的方案順序，故選用LinkedHashMap，而不用treeMap或hashMap
			@SuppressWarnings("unchecked")
			LinkedHashMap<DressCaseVO,List<DressAddOnVO>> map = (LinkedHashMap<DressCaseVO,List<DressAddOnVO>>) session.getAttribute("dresscart");
			
			Iterator<DressCaseVO> it2 = map.keySet().iterator();
			//用drcase_id去尋找dresscaseVO:
			DressCaseService dcSvc = new DressCaseService();
			DressCaseVO dcVOD =dcSvc.findByPrimaryKey(drcase_id);
			
			while(it2.hasNext()) {
				DressCaseVO dcVON= it2.next();
//				用iterator，從map中移除所選的方案與其附加的加購項目
				if(dcVON.getDrcase_id().contentEquals(dcVOD.getDrcase_id())) {
				System.out.println(dcVON.getDrcase_id());
					it2.remove();
				}
			}
			session.setAttribute("dresscart", map);
			String url = "/front_end/dresscase/Cart.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}
		
		}//end of doPost
	private DressCaseVO getDr(HttpServletRequest req) {

		String drcase_id = req.getParameter("drcase_id");
		DressCaseService dcSvc = new DressCaseService();
		DressCaseVO dcVO = dcSvc.findByPrimaryKey(drcase_id);
		return dcVO;
	}
	
	private List<DressAddOnVO> getAddOn(HttpServletRequest req) {
		
		List<DressAddOnVO> addList = new ArrayList<DressAddOnVO>();
//		抓被選擇的AddOn們
		String addids= req.getParameter("selected");
		String[] addArr = addids.split(",");
		
		for(int k=0;k<addArr.length;k++) {
			String add_id = addArr[k];
			DressAddOnService daSvc = new DressAddOnService();
			DressAddOnVO daVO= daSvc.findByPrimaryKey(add_id);
			
			if(daVO!=null) {
				addList.add(daVO);
			}
			}
		return addList;
	}
}
