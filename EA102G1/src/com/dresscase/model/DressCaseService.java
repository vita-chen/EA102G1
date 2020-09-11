package com.dresscase.model;

import java.util.List;

public class DressCaseService {

	private DressCaseDAO_interface dcdao;
	
	public DressCaseService() {
		dcdao = new DressCaseDAO();
	}

	public DressCaseVO addDress(String vender_id,String drcase_na,String drcase_br,Integer drcase_pr,Integer drcase_st) {

		DressCaseVO dcVO = new DressCaseVO();

		dcVO.setVender_id(vender_id);
		dcVO.setDrcase_na(drcase_na);
		dcVO.setDrcase_br(drcase_br);
		dcVO.setDrcase_pr(drcase_pr);
		dcVO.setDrcase_st(drcase_st);
		dcdao.insert(dcVO);
		return dcVO;
	}

	public DressCaseVO updateDress(String drcase_id, String vender_id,String drcase_na,String drcase_br,Integer drcase_pr,Integer drcase_st) {

		DressCaseVO dcVO = new DressCaseVO();
		
		dcVO.setDrcase_id(drcase_id);
		dcVO.setVender_id(vender_id);
		dcVO.setDrcase_na(drcase_na);
		dcVO.setDrcase_br(drcase_br);
		dcVO.setDrcase_pr(drcase_pr);
		dcVO.setDrcase_st(drcase_st);
		dcdao.update(dcVO);

		return dcVO;
	}
	
	public void deleteDress(String drcase_id) {
		dcdao.delete(drcase_id);
	}
	public DressCaseVO findByPrimaryKey(String drcase_id) {
		return dcdao.findByPrimaryKey(drcase_id);
	}
	public List<DressCaseVO> findByVenID(String vender_id) {
		return dcdao.findByVenID(vender_id);
	}
	public List<DressCaseVO> findByDrNa(String drcase_na){
		return dcdao.findByDrNa(drcase_na);
	}
	
	public List<DressCaseVO> getAll(){
		return dcdao.getAll();
	}
}
