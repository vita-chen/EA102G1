package com.prod.model;

import java.util.*;

public interface ProdDAO_interface {
	public void insert(ProdVO prodVO);
	public void update(ProdVO prodVO);
	public ProdVO findByKey(String prod_no);
	public List<ProdVO> getAll();
	public List<ProdVO> getAllByOrder_no(String order_no);
}
