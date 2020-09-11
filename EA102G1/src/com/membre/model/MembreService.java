package com.membre.model;

import java.util.List;
import java.util.stream.Collectors;

public class MembreService {
	private MembreDAO_interface membredao;
	
	public MembreService() {
		membredao = new MembreDAO();
	}
	
	public MembreVO regis(String mem_name, String sex,
										 String address, String phone, String email, String status,
			                             String compte, String passe, byte[] photo, java.sql.Timestamp regis_time) {
		MembreVO membrevo = new MembreVO();
		membrevo.setMem_name(mem_name);
		membrevo.setSex(sex);
		membrevo.setAddress(address);
		membrevo.setPhone(phone);
		membrevo.setEmail(email);
		membrevo.setStatus(status);
		membrevo.setCompte(compte);
		membrevo.setPasse(passe);
		membrevo.setPhoto(photo);
		membrevo.setRegis_time(regis_time);
		
		membredao.insert(membrevo);
		
		return membrevo;
	}
	
	public MembreVO login(String compte) {
		MembreVO membrevo = membredao.getOneByAccount(compte);
		return membrevo;
	}
	
	public List<String> getAllAccount() {
		List<MembreVO> membrevoList = membredao.getAll();
		List<String> accountList =membrevoList.stream()
												.map(vo ->vo.getCompte())
												.collect(Collectors.toList());
		return accountList;
	}
	
	public void update(MembreVO membrevo) {
		membredao.update(membrevo);
	}
	
	public List<MembreVO> getAll(){
		return membredao.getAll();
	}
	public List<MembreVO> getAllWithShop(){
		List<MembreVO> membreList =membredao.getAll();
		membreList = membreList.stream()
												.filter(vo -> vo.getStatus().equals("1"))
												.collect(Collectors.toList());
		return membreList;
	}
	
	public MembreVO getOneById(String membre_id) {
		return membredao.getOneById(membre_id);
	}
	
	public void delete(String membre_id) {
		membredao.delete(membre_id);
	}
	
	public MembreVO getSeller(String order_no) {
		return membredao.getSeller(order_no);
	}
}

