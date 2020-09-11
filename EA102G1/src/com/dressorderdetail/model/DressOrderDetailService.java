package com.dressorderdetail.model;

import java.util.List;

public class DressOrderDetailService {

	private DressOrderDetailDAO_interface dodDAO;
	
	public DressOrderDetailService() {
		dodDAO = new DressOrderDetailDAO();
	}

	public DressOrderDetailVO addDressOrder(String drorder_id,String drcase_id,Integer drde_st,Integer drcase_totpr) {

		DressOrderDetailVO dodVO = new DressOrderDetailVO();

		dodVO.setDrord_id(drorder_id);
		dodVO.setDrcase_id(drcase_id);
		dodVO.setDrde_st(drde_st);
		dodVO.setDrcase_totpr(drcase_totpr);
		
		dodDAO.insert(dodVO);
		return dodVO;
	}

	public DressOrderDetailVO updateDressOrder(String drde_id,Integer drde_st) {

		DressOrderDetailVO dodVO = new DressOrderDetailVO();

		dodVO.setDrde_id(drde_id);
		dodVO.setDrde_st(drde_st);
		dodDAO.update(dodVO);

		return dodVO;
	}
	
	public DressOrderDetailVO findByPrimaryKey(String drde_id) {
		return dodDAO.findByPrimaryKey(drde_id);
	}
	
	public DressOrderDetailVO findByDrcaseAndOrder(String drcase_id,String drord_id) {
		return dodDAO.findByDrcaseAndOrder(drcase_id,drord_id);
	}
	public List<DressOrderDetailVO> getAllDressOrder(){
		return dodDAO.getAll();
	}
	public List<DressOrderDetailVO> findByOrder(String drord_id){
		return dodDAO.findByOrder(drord_id);
	}
	
}
