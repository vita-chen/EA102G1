package com.prod.model;

public class ProdVO implements java.io.Serializable{
	private String prod_no;
	private String membre_id;
	private String type_no;
	private Integer price;
	private Integer prod_qty;
	private String prod_name;
	private String prod_status;
	private java.sql.Timestamp sale_time;
	
	public java.sql.Timestamp getSale_time() {
		return sale_time;
	}

	public void setSale_time(java.sql.Timestamp sale_time) {
		this.sale_time = sale_time;
	}

	public ProdVO() {};

	public String getProd_no() {
		return prod_no;
	}
	public void setProd_no(String prod_no) {
		this.prod_no = prod_no;
	}
	public String getMembre_id() {
		return membre_id;
	}
	public void setMembre_id(String membre_id) {
		this.membre_id = membre_id;
	}

	

	public String getType_no() {
		return type_no;
	}

	public void setType_no(String type_no) {
		this.type_no = type_no;
	}

	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getProd_qty() {
		return prod_qty;
	}
	public void setProd_qty(Integer prod_qty) {
		this.prod_qty = prod_qty;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getProd_status() {
		return prod_status;
	}
	public void setProd_status(String prod_status) {
		this.prod_status = prod_status;
	}

	@Override
	public boolean equals(Object obj) {
		ProdVO vo = (ProdVO) obj;
		return getProd_no().equals(vo.getProd_no());
	}
}
