package com.carCollect.model;

public class CarCollectService {
	
	private CarCollectDAO_interface dao;
	
	public CarCollectService() {
		dao = new CarCollectDAO();
	}
	
	public void insert(CarCollectVO carCollectVO) {
		dao.insert(carCollectVO);
	}

}
