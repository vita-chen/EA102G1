package com.dressaddorder.model;

import java.util.List;

public class DressAddOrderService {

	private DressAddOrderDAO_interface dctDAO;
	
	public DressAddOrderService() {
		dctDAO = new DressAddOrderDAO();
	}
	
	public DressAddOrderVO add(String dradd_id,String drde_id) {

		DressAddOrderVO daoVO  = new DressAddOrderVO();

		daoVO.setDradd_id(dradd_id);
		daoVO.setDrde_id(drde_id);
		dctDAO.insert(daoVO);
		return daoVO;
	}
	
	public List<DressAddOrderVO> findByDrde_id(String drde_id) {
		return dctDAO.findByDrde_id(drde_id);
	}
	
	public List<DressAddOrderVO> getAll(){
		return dctDAO.getAll();
	}
	
	
}
