package com.carOrder.model;

import java.util.List;

public interface CarOrderDAO_interface {
	
	public void insertCarOrder(CarOrderVO carOrderVO);
	public List<CarOrderVO> getAllByVenderId(CarOrderVO carOrderVO);
	public void updateCarOrderStatus(CarOrderVO carOrderVO);
	public void submitReview(CarOrderVO carOrderVO); // 會員提交評價星數&心得
	public List<CarOrderVO> getAllByMemberId(CarOrderVO carOrderVO);
	public List<CarOrderVO> getCarAvgStar(); // 透過存在每筆訂單中的星數，來算出每台車的平均星數

}
