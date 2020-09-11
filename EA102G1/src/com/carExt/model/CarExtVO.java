package com.carExt.model;

public class CarExtVO implements java.io.Serializable{
	
	private String cext_id;                   // 禮車加購品編號
	private String vender_id;                 // 廠商編號
	private Integer cext_cat_id;              // 禮車加購品類別編號
	private String cext_name;                 // 加購品名稱
	private	Integer cext_price;               // 加購品價格
	private byte[]cext_pic;       			  // 加購品圖片 
	private java.sql.Timestamp cext_addtime;  // 加購品上架時間
	private Integer cext_status;              // 加購品上架/下架狀態
	
	
	public String getCext_id() {
		return cext_id;
	}
	public void setCext_id(String cext_id) {
		this.cext_id = cext_id;
	}
	public String getVender_id() {
		return vender_id;
	}
	public void setVender_id(String vender_id) {
		this.vender_id = vender_id;
	}
	public Integer getCext_cat_id() {
		return cext_cat_id;
	}
	public void setCext_cat_id(Integer cext_cat_id) {
		this.cext_cat_id = cext_cat_id;
	}
	public String getCext_name() {
		return cext_name;
	}
	public void setCext_name(String cext_name) {
		this.cext_name = cext_name;
	}
	public Integer getCext_price() {
		return cext_price;
	}
	public void setCext_price(Integer cext_price) {
		this.cext_price = cext_price;
	}
	public byte[] getCext_pic() {
		return cext_pic;
	}
	public void setCext_pic(byte[] cext_pic) {
		this.cext_pic = cext_pic;
	}
	public java.sql.Timestamp getCext_addtime() {
		return cext_addtime;
	}
	public void setCext_addtime(java.sql.Timestamp cext_addtime) {
		this.cext_addtime = cext_addtime;
	}
	public Integer getCext_status() {
		return cext_status;
	}
	public void setCext_status(Integer cext_status) {
		this.cext_status = cext_status;
	}
	
} //end of CarExtVO
