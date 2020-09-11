package com.carExt.model;

import java.util.List;

public class CarExtService {
	
	private CarExtDAO_interface dao;
	
	
	public CarExtService() {
		dao = new CarExtDAO();
	}

	public void insert(CarExtVO carExtVO) {
		dao.insert(carExtVO);
	}

	public void update(CarExtVO carExtVO) {
		dao.update(carExtVO);
	}

	public void delete(CarExtVO carExtVO) {
		dao.delete(carExtVO);
	}
	
	// 取加購品照片(透過加購品編號取得該加購品照片)
	public void getOneCExtPic(CarExtVO carExtVO) {
		dao.getOneCExtPic(carExtVO);
	}
	
	// 取加購品資料(透過加購品編號取得該加購品資料)
	public void getOneCExt(CarExtVO carExtVO) {
		dao.getOneCExt(carExtVO);
	}

	public List<CarExtVO> getAllByVenderId(CarExtVO carExtVO) {
		return dao.getAllByVenderId(carExtVO);
	}
	
	
	
	

} // end of CarExtService
