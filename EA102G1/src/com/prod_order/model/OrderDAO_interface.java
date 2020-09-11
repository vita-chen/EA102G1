package com.prod_order.model;

import java.util.List;

public interface OrderDAO_interface {
	public void insert(OrderVO ordervo);
	public OrderVO getOneById(String order_no);
	public List<OrderVO> getAllBuyer(String membre_id);
	public List<OrderVO> getAllSeller(String membre_id);
	public void update(OrderVO ordervo);
	public void delete(String order_no);
}
