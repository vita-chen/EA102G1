package com.dressaddtype.model;

import java.util.List;

public class DressAddTypeService {

	private DressAddTypeDAO_interface datDAO;
	
	public DressAddTypeService() {
		datDAO = new DressAddTypeDAO();
	}

	public DressAddTypeVO addType(String dradd_type) {

		DressAddTypeVO datVO = new DressAddTypeVO();

		datVO.setDradd_type(dradd_type);
		datDAO.insert(datVO);
		return datVO;
	}
	
	public void deleteType(String dradd_type) {
		datDAO.delete(dradd_type);
	}
	public DressAddTypeVO findByPK(String dradd_type) {
		return datDAO.findByPK(dradd_type);
	}
	public List<DressAddTypeVO> getAll(){
		return datDAO.getAll();
	}
}
