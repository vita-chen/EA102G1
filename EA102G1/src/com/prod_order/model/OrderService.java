package com.prod_order.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.prod_order_detail.model.DetailDAO;
import com.prod_order_detail.model.DetailDAO_interface;
import com.prod_order_detail.model.DetailVO;
public class OrderService {
	private OrderDAO_interface orderdao;
	private DetailDAO_interface detaildao;
	
	public OrderService() {
		this.orderdao = new OrderDAO();
		this.detaildao = new DetailDAO();
	}
	
	public OrderVO addOrder(String membre_id, Integer total, Timestamp order_date, Timestamp ship_date, String order_status) {
		OrderVO ordervo = new OrderVO();
		ordervo.setMembre_id(membre_id);
		ordervo.setTotal(total);
		ordervo.setOrder_date(order_date);
		ordervo.setShip_date(ship_date);
		ordervo.setOrder_status(order_status);
		
		orderdao.insert(ordervo);
		
		return ordervo;
	}
	
	public void updateOrder(OrderVO ordervo) {
		orderdao.update(ordervo);
	}
	
	public void deleteOrder(String order_no) {
		orderdao.delete(order_no);
	}
	
	public OrderVO getOneOrder(String order_no) {
		return orderdao.getOneById(order_no);
	}
	
	public List<OrderVO> getAllOrder(String membre_id) {
		return orderdao.getAllBuyer(membre_id);
	}
	public List<OrderVO> getAllBuyerOrderByStatus(String membre_id, String order_status) {
		List<OrderVO> orderList= orderdao.getAllBuyer(membre_id);
		orderList = orderList.stream()
										.filter(vo->vo.getOrder_status().equals(order_status))
										.collect(Collectors.toList());
		return orderList;
	}
	public List<OrderVO> getAllSellerOrder(String membre_id) {
		return orderdao.getAllSeller(membre_id);
	}
	public List<OrderVO> getAllSellerOrderByStatus(String membre_id, String order_status) {
		List<OrderVO> orderList = orderdao.getAllSeller(membre_id);
		orderList = orderList.stream()
										.filter(vo -> vo.getOrder_status().equals(order_status))
										.collect(Collectors.toList());
		return orderList;
	}
	public DetailVO addOrderDetail(String order_no, String prod_no, Integer order_qty) {
		DetailVO detailvo = new DetailVO();
		detailvo.setOrder_no(order_no);
		detailvo.setProd_no(prod_no);
		detailvo.setOrder_qty(order_qty);
		detaildao.insert(detailvo);
		return detailvo;
	}
	
	public List<DetailVO> getAllDetail(String order_no) {
		return detaildao.findByKey(order_no);
	}
	
	public void deleteDetail(String order_no, String prod_no) {
		detaildao.delete(order_no, prod_no);
	}
	public OrderVO getLatestOrder(String membre_id) {
		List<OrderVO> orderList = orderdao.getAllBuyer(membre_id);
		return orderList.get(0);
	}
	public DetailVO getOneDetail(String prod_no, String order_no) {
		return detaildao.getOneDetail(prod_no, order_no);
	}
}
