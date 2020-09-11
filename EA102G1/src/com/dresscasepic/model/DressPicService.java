package com.dresscasepic.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.Part;
import com.dresscase.model.DressCaseVO;


public class DressPicService {

	private DressPicDAO_interface dpdao;
	
	public DressPicService() {
		dpdao = new DressPicDAO();
	}
	
	public DressPicVO insertdrPic(String drcase_id,byte[] drpic) {
		
		DressPicVO dpVO= new DressPicVO();
		dpVO.setDrcase_id(drcase_id);
		dpVO.setDrpic(drpic);
		dpdao.insert(dpVO);
		
		return dpVO;
	}
	
	public void deletedrPic(String drPicID) {
		dpdao.delete(drPicID);
	}
	
	public DressPicVO findPic(String drpic_id) {
		return dpdao.findByPrimaryKey(drpic_id);
	}
	public List<DressPicVO> findPics(String drcase_id) {
		return dpdao.findPics(drcase_id);
	}
	
	public List<DressPicVO> getAll(){
		return dpdao.getAll();
	}
}
