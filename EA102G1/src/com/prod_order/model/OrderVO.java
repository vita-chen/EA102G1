package com.prod_order.model;

import java.sql.Timestamp ;
public class OrderVO implements java.io.Serializable {
	private String order_no;
	private String membre_id;
	private Integer total;
	private String order_status;
	private Timestamp order_date;
	private Timestamp ship_date;
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getMembre_id() {
		return membre_id;
	}
	public void setMembre_id(String membre_id) {
		this.membre_id = membre_id;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public Timestamp getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Timestamp order_date) {
		this.order_date = order_date;
	}
	public Timestamp getShip_date() {
		return ship_date;
	}
	public void setShip_date(Timestamp ship_date) {
		this.ship_date = ship_date;
	}
	
}
