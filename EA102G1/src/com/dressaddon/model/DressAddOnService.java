package com.dressaddon.model;

import java.util.List;

public class DressAddOnService {

	private DressAddOnDAO_interface daDAO;
	
	public DressAddOnService() {
		daDAO = new DressAddOnDAO();
	}

	public DressAddOnVO addDraddOn(String vender_id,String dradd_type,String dradd_na,Integer dradd_pr,Integer dradd_st) {

		DressAddOnVO daoVO = new DressAddOnVO();
		
		daoVO.setVender_id(vender_id);
		daoVO.setDradd_type(dradd_type);
		daoVO.setDradd_na(dradd_na);
		daoVO.setDradd_pr(dradd_pr);
		daoVO.setDradd_st(dradd_st);
		daDAO.insert(daoVO);
		return daoVO;
	}

	public DressAddOnVO updateDraddOn(String dradd_id, String vender_id,String dradd_na,String dradd_type,Integer dradd_pr,Integer dradd_st) {

		DressAddOnVO daoVO = new DressAddOnVO();

		daoVO.setDradd_id(dradd_id);
		daoVO.setVender_id(vender_id);
		daoVO.setDradd_type(dradd_type);
		daoVO.setDradd_na(dradd_na);
		daoVO.setDradd_pr(dradd_pr);
		daoVO.setDradd_st(dradd_st);
		daDAO.update(daoVO);

		return daoVO;
	}
	
	public DressAddOnVO findByPrimaryKey(String dradd_id) {
		return daDAO.findByPrimaryKey(dradd_id);
	}
	public List<DressAddOnVO> findByVender(String vender_id) {
		return daDAO.findByVender(vender_id);
	}
	
	public List<DressAddOnVO> getAll(){
		return daDAO.getAll();
	}
}
