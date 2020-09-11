package com.carOrder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.carExt.model.*;
import com.carExtOrder.model.*;

public class CarOrderService {

	private CarOrderDAO_interface carOrderDAO;
	private CarExtOrderDAO_interface carExtOrderDAO;
	private CarExtDAO_interface carExtDAO;

	public CarOrderService() {
		carOrderDAO = new CarOrderDAO();
		carExtOrderDAO = new CarExtOrderDAO();
		carExtDAO = new CarExtDAO();
	}

	
	/********************* function for CarOrder *********************/
	// 新增禮車訂單
	public void insertCarOrder(CarOrderVO carOrderVO) {
		
		carOrderDAO.insertCarOrder(carOrderVO);

	}
	
	// for listAllCarOrderVen.jsp使用(以VenderId來filter出該廠商的禮車訂單一覽)
	public List<CarOrderVO> getAllByVenderId(CarOrderVO carOrderVO) {
		return carOrderDAO.getAllByVenderId(carOrderVO); 
	}
	
	// for listAllCarOrderMen.jsp使用(以MemberId來filter出該會員的禮車訂單一覽)
	public List<CarOrderVO> getAllByMemberId(CarOrderVO carOrderVO) {
		return carOrderDAO.getAllByMemberId(carOrderVO); 
	}
	
	
	// 更新訂單狀態
	public void updateCarOrderStatus(CarOrderVO carOrderVO){
		carOrderDAO.updateCarOrderStatus(carOrderVO);
	}
	
	// 會員提交評價星數&心得
	public void submitReview(CarOrderVO carOrderVO){
		carOrderDAO.submitReview(carOrderVO);
	}
	
	// 透過存在每筆訂單中的星數，來算出每台車的平均星數
	public Map<String, CarOrderVO> getCarAvgStarMap() {
		Map<String, CarOrderVO> carAvgStarMap = new HashMap<String, CarOrderVO>();
		for (CarOrderVO carOrderVO : carOrderDAO.getCarAvgStar()) {
			carAvgStarMap.put(carOrderVO.getCid(), carOrderVO);
		}
		return carAvgStarMap;
	}

	
	/********************* function for CarExtOrder *********************/
	// 新增禮車訂單明細(即加購品)
	public void insertCarExtOrder(CarExtOrderVO carExtOrderVO) {
		carExtOrderDAO.insertCarExtOrder(carExtOrderVO);

	}
	
	public List<CarExtOrderVO> getAllCarExt() {
		return carExtOrderDAO.getAllCarExt(); 
	}
	
	public Map<String, List<CarExtVO>> getAllCarExtMap() {
		Map<String, List<CarExtVO>> allCarExtMap = new HashMap<String, List<CarExtVO>>();
		for(CarExtOrderVO carExtOrderVO: carExtOrderDAO.getAllCarExt())
		{
			String cext_id = carExtOrderVO.getCext_id();
			CarExtVO carExtVO = new CarExtVO();
			carExtVO.setCext_id(cext_id);
			carExtDAO.getOneCExt(carExtVO);
			
			String key = carExtOrderVO.getCod_id();
			List<CarExtVO> carExtVOList = allCarExtMap.get(key);
			if (carExtVOList == null)
			{
				allCarExtMap.put(key, new ArrayList<>());
			}
			allCarExtMap.get(key).add(carExtVO);
		}
		return allCarExtMap; 
	}
	
	

} // end of CarOrderService
