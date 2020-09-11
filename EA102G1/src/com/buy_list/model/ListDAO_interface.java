package com.buy_list.model;

import java.util.List;

import com.prod.model.ProdVO;

public interface ListDAO_interface {
	public void insert(String prod_no, String membre_id);
	public List<ProdVO> getAll(String membre_id);
	public void delete (String prod_no, String membre_id);
	public ListVO getOne (String prod_no, String membre_id);
}
