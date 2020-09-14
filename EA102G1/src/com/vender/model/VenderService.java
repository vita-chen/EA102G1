package com.vender.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VenderService {
	
	private VenderDAO_interface dao;
	
	public VenderService() {
		dao = new VenderJDBCDAO();
	}
	
	public VenderVO add_vender(String ven_name, String ven_addr, String ven_phone, String ven_contact, String ven_mail,
			 byte[] ven_evidence_pic, String ven_account,String ven_pwd) {
		VenderVO vendervo = new VenderVO();
		vendervo.setVen_name(ven_name);
		vendervo.setVen_addr(ven_addr);
		vendervo.setVen_phone(ven_phone);
		vendervo.setVen_contact(ven_contact);
		vendervo.setVen_mail(ven_mail);
		vendervo.setVen_evidence_pic(ven_evidence_pic);
		vendervo.setVen_account(ven_account);
		vendervo.setVen_pwd(ven_pwd);

		dao.insert(vendervo);

		return vendervo;
	}
	
	public VenderVO update_vender(String ven_name, String ven_addr, String ven_phone, String ven_contact, String ven_mail,
			byte[] ven_evidence_pic, String ven_pwd, String vender_id) {
		VenderVO vendervo = new VenderVO();
		
		vendervo.setVen_name(ven_name);
		vendervo.setVen_addr(ven_addr);
		vendervo.setVen_phone(ven_phone);
		vendervo.setVen_contact(ven_contact);
		vendervo.setVen_mail(ven_mail);
		vendervo.setVen_evidence_pic(ven_evidence_pic);

		vendervo.setVen_pwd(ven_pwd);
		vendervo.setVender_id(vender_id);
		
		dao.update(vendervo);

		return vendervo;
	}
	
	public VenderVO update_vender_enable(String vender_id) {
		VenderVO vendervo = new VenderVO();
		
		vendervo.setVender_id(vender_id);
		
		dao.update_vender_enable(vendervo);

		return vendervo;
	}
	
	public VenderVO delete_vender(String vender_id) {
		VenderVO vendervo = new VenderVO();
		
		vendervo.setVender_id(vender_id);
		
		dao.delete(vendervo);

		return vendervo;
	}
	
	public VenderVO findByPrimaryKey(String vender_id) {
		return dao.findByPrimaryKey(vender_id);	
	}
	
	public List<VenderVO> getAll() {
		return dao.getAll();
	}
	
	public List<VenderVO> get_all_verification() {
		return dao.get_all_verification();
	}
	
	public List<VenderVO> get_all_blockade() {
		return dao.get_all_blockade();
	}
	
	public VenderVO vender_login(String ven_account) {
		VenderVO vendervo = dao.getOneByVender(ven_account);
		return vendervo;
	}
	
	public List<String> getAllVender() {
		return dao.getAllVender();
	}
	
	// 搭配getAll()查所有廠商名字
	public Map<String, VenderVO> getAllVenderMap() {
		Map<String, VenderVO> allVenderMap = new HashMap<String, VenderVO>();
		for (VenderVO vendervo : dao.getAll()) {
			allVenderMap.put(vendervo.getVender_id(), vendervo);
		}
		return allVenderMap;
	}
	
	public void update_review(String vender_id,Integer review_star,Boolean isnew) {
		VenderVO vendervo = dao.findByPrimaryKey(vender_id);
		if(isnew) {
			Integer count = vendervo.getVen_review_count();
			vendervo.setVen_review_count(count+1);
		}
		if(review_star == null) {
			review_star = 0;
		}
		Integer total = vendervo.getVen_stars_total();
		vendervo.setVen_stars_total(total+review_star);
		dao.update_review(vendervo);		
	}
}
