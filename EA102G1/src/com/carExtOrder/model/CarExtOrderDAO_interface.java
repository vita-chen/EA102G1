package com.carExtOrder.model;

import java.util.List;

public interface CarExtOrderDAO_interface {
	
	public void insertCarExtOrder(CarExtOrderVO carExtOrderVO); // for客戶選加購品

	public List<CarExtOrderVO> getAllCarExt();


	

}
