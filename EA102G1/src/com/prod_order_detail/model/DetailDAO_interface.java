package com.prod_order_detail.model;

import java.util.List;

public interface DetailDAO_interface {
	public void insert(DetailVO detailvo);
	public List<DetailVO> findByKey(String order_no);
	public void delete(String order_no, String prod_no);
	public DetailVO getOneDetail(String prod_no, String order_no);
}
