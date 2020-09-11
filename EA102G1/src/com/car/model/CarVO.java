package com.car.model;

public class CarVO implements java.io.Serializable{
	
	private String cid; 					// 禮車編號
	private String vender_id;				// 廠商編號
	private String cbrand;					// 車子品牌
	private String cmodel; 					// 車型
	private String cintro; 					// 車子介紹
	private Integer cprice;					// 車子價格
	private java.sql.Timestamp caddtime; 	// 車子上架時間
	private Integer cstatus;				// 車子上/下架狀態
	
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getVender_id() {
		return vender_id;
	}
	public void setVender_id(String vender_id) {
		this.vender_id = vender_id;
	}
	public String getCbrand() {
		return cbrand;
	}
	public void setCbrand(String cbrand) {
		this.cbrand = cbrand;
	}
	public String getCmodel() {
		return cmodel;
	}
	public void setCmodel(String cmodel) {
		this.cmodel = cmodel;
	}
	public String getCintro() {
		return cintro;
	}
	public void setCintro(String cintro) {
		this.cintro = cintro;
	}
	public Integer getCprice() {
		return cprice;
	}
	public void setCprice(Integer cprice) {
		this.cprice = cprice;
	}
	public java.sql.Timestamp getCaddtime() {
		return caddtime;
	}
	public void setCaddtime(java.sql.Timestamp caddtime) {
		this.caddtime = caddtime;
	}
	public Integer getCstatus() {
		return cstatus;
	}
	public void setCstatus(Integer cstatus) {
		this.cstatus = cstatus;
	}
	
	
}// end of CarVO
