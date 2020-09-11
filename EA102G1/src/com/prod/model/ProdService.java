package com.prod.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Part;

import com.prod_pic.model.PicDAO;
import com.prod_pic.model.PicDAO_interface;
import com.prod_pic.model.PicVO;

public class ProdService {
	private ProdDAO_interface proddao;
	private PicDAO_interface picdao;
	public ProdService() {
		proddao = new ProdDAO();
		picdao = new PicDAO();
	}
	
	public ProdVO addProd(ProdVO prodvo) {

		proddao.insert(prodvo);
		return prodvo;
	}
	
	public void updateProd(ProdVO prodvo) {
		proddao.update(prodvo);	
	}
	
	public void insertPic(String prod_no, Part part) {
		PicVO picvo = new PicVO();
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
				InputStream is = part.getInputStream()){
			if(is.available() ==0) {
				return;
			}
			byte[] buffer = new byte[8192];
			int i;
			while((i = is.read(buffer)) != -1) {
				bos.write(buffer, 0, i);
			}
			
			picvo.setPic(bos.toByteArray());
			picvo.setProd_no(prod_no);
			picdao.insert(picvo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ProdVO findByKey(String prodNo) {
		return proddao.findByKey(prodNo);
	}
	
	public List<PicVO> getPics(String prodNo) {
		return picdao.findByKey(prodNo);
	}
	
	public List<ProdVO> getAll() {
		return proddao.getAll();
	}
	
	public List<ProdVO> getAllByOrder_no(String order_no) {

		return proddao.getAllByOrder_no(order_no);
	}
	public List<ProdVO> getAllByMembre_id(String membre_id) {
		List<ProdVO> prodList = proddao.getAll();
		 prodList = prodList.stream()
			.filter(p -> p.getMembre_id().equals(membre_id))
			.filter(p -> p.getProd_status().equals("1"))
			.sorted(Comparator.comparing(ProdVO::getSale_time).reversed())
			.collect(Collectors.toList());

		return prodList;
	}
	
	public List<ProdVO> getAllMyProds(String membre_id) {
		List<ProdVO> prodList = proddao.getAll();
		 prodList = prodList.stream()
			.filter(p -> p.getMembre_id().equals(membre_id))
			.sorted(Comparator.comparing(ProdVO::getSale_time).reversed())
			.collect(Collectors.toList());

		return prodList;
	}
	
	public List<ProdVO> filterProduct(String membre_id, String type_no) {
		List<ProdVO> prodList = proddao.getAll();
		prodList  = prodList.stream()
									   .filter(vo -> vo.getMembre_id().equals(membre_id))
									   .filter(vo -> vo.getType_no().equals(type_no))
									   .collect(Collectors.toList());
		return prodList;
	}
	public List<ProdVO> query(String shopper, String query) {
		List<ProdVO> prodList = getAllByMembre_id(shopper);
		prodList = prodList.stream()
									 .filter(vo -> vo.getProd_name().indexOf(query)!= -1)
									 .collect(Collectors.toList());
		return prodList;
	}
	
	public List<ProdVO> sorting (String shopper, String type, String order) {
		List<ProdVO> prodList = getAllByMembre_id(shopper);
		System.out.println(type);
		if (type.isEmpty() && order.equals("asc")) {
			prodList = prodList.stream()
											.sorted(Comparator.comparing(ProdVO::getPrice))
											.collect(Collectors.toList());
		} 
		if (type.isEmpty() && order.equals("desc")) {
			prodList = prodList.stream()
											.sorted(Comparator.comparing(ProdVO::getPrice).reversed())
											.collect(Collectors.toList());
		} 
		if (type.length() > 0&& order.equals("asc")) {
			prodList = prodList.stream()
										 .filter(vo -> vo.getType_no().equals(type))
										 .sorted(Comparator.comparing(ProdVO::getPrice))
											.collect(Collectors.toList());
		}
		if (type.length() > 0 && order.equals("desc")) {
			prodList = prodList.stream()
										 .filter(vo -> vo.getType_no().equals(type))
										 .sorted(Comparator.comparing(ProdVO::getPrice).reversed())
											.collect(Collectors.toList());
		}
		return prodList;
	}
}
