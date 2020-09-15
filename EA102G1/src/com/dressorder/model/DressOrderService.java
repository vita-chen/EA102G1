package com.dressorder.model;

import java.sql.Timestamp;
import java.util.List;

public class DressOrderService {

	private DressOrderDAO_interface dodao;
	
	public DressOrderService() {
		dodao = new DressOrderDAO();
	}

	public DressOrderVO addDrOrder(String membre_id,String vender_id,Integer drord_pr,Integer drord_depo,
			Integer drord_ini,Integer drord_pay_st,Integer drord_fin_st,Integer dr_mrep_st,Integer dr_vrep_st
			) {

		DressOrderVO doVO = new DressOrderVO();
		
		doVO.setMembre_id(membre_id);
		doVO.setVender_id(vender_id);
		doVO.setDrord_pr(drord_pr);
		doVO.setDrord_depo(drord_depo);
		doVO.setDrord_ini(drord_ini);
		doVO.setDrord_pay_st(drord_pay_st);
		doVO.setDrord_fin_st(drord_fin_st);
		doVO.setDr_mrep_st(dr_mrep_st);
		doVO.setDr_vrep_st(dr_vrep_st);
		
		doVO.setDr_mrep_de("未檢舉");
		doVO.setDr_vrep_de("未檢舉");
		doVO.setDr_mrep_res("未檢舉");
		doVO.setDr_vrep_de("未檢舉");
		doVO.setDr_vrep_de("未檢舉");
		doVO.setDr_rev_con("未評價");
		dodao.insert(doVO);
		return doVO;
	}

	public DressOrderVO updateDrOrder(String drord_id, String membre_id,String vender_id,Timestamp drord_time,Integer drord_pr,Integer drord_depo,
			Integer drord_ini,Integer drord_pay_st,Integer drord_fin_st,Integer dr_mrep_st,Integer dr_vrep_st,
			String dr_mrep_de,String dr_vrep_de,String dr_mrep_res, String dr_vrep_res,Integer dr_rev_star,String dr_rev_con) {

		DressOrderVO doVO2 = new DressOrderVO();

		doVO2.setDrord_id(drord_id);
		doVO2.setMembre_id(membre_id);
		doVO2.setVender_id(vender_id);
		doVO2.setDrord_time(drord_time);
		doVO2.setDrord_pr(drord_pr);
		doVO2.setDrord_depo(drord_depo);
		doVO2.setDrord_ini(drord_ini);
		
		doVO2.setDrord_pay_st(drord_pay_st);
		doVO2.setDrord_fin_st(drord_fin_st);
		doVO2.setDr_mrep_st(dr_mrep_st);
		doVO2.setDr_vrep_st(dr_vrep_st);
		doVO2.setDr_mrep_de(dr_mrep_de);
		doVO2.setDr_vrep_de(dr_vrep_de);
		
		doVO2.setDr_mrep_res(dr_mrep_res);
		doVO2.setDr_vrep_res(dr_vrep_res);
		doVO2.setDr_rev_star(dr_rev_star);
		doVO2.setDr_rev_con(dr_rev_con);
		dodao.update(doVO2);

		return doVO2;
	}
	
	public DressOrderVO findByPrimaryKey(String drord_id) {
		return dodao.findByPrimaryKey(drord_id);
	}
	
	public List<DressOrderVO> getAllDrOrder(){
		return dodao.getAll();
	}
	
	public DressOrderVO findLatestOrder(String membre_id) {
		return dodao.findLatestOrder(membre_id);
	}
	public List<DressOrderVO> findByMembre(String membre_id){
		return dodao.findByMembre(membre_id);
	}
	public List<DressOrderVO> findByVender(String vender_id){
		return dodao.findByVender(vender_id);
	}
	public List<DressOrderVO> findByVenderRev(String vender_id){
		return dodao.findByVenderRev(vender_id);
	}
}
