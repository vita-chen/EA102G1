package com.buy_list.model;

import java.util.List;

import com.prod.model.ProdVO;

public class ListService {
	private ListDAO_interface listdao;

	public ListService() {
		this.listdao = new ListDAO();
	}

	public ListVO add(String prod_no, String membre_id) {
		ListVO listvo = new ListVO();
		listvo.setProd_no(prod_no);
		listvo.setMembre_id(membre_id);
		listdao.insert(prod_no, membre_id);

		return listvo;
	}

	public List<ProdVO> getAll(String membre_id) {
		return listdao.getAll(membre_id);
	}

	public void delete(String prod_no, String membre_id) {
		listdao.delete(prod_no, membre_id);
	}
	
	public ListVO getOne(String prod_no, String membre_id) {
		return listdao.getOne(prod_no, membre_id);
	}

}// end of class
