package com.dressaddon.model;

public class DressAddOnVO implements java.io.Serializable{
	private String dradd_id;
	private String vender_id;
	private String dradd_type;
	private String dradd_na;
	private Integer dradd_pr;
	private Integer dradd_st;
	
	public String getDradd_id() {
		return dradd_id;
	}
	public String getVender_id() {
		return vender_id;
	}
	public String getDradd_type() {
		return dradd_type;
	}
	public String getDradd_na() {
		return dradd_na;
	}
	public Integer getDradd_pr() {
		return dradd_pr;
	}
	public Integer getDradd_st() {
		return dradd_st;
	}
	public void setDradd_id(String dradd_id) {
		this.dradd_id = dradd_id;
	}
	public void setVender_id(String vender_id) {
		this.vender_id = vender_id;
	}
	public void setDradd_type(String dradd_type) {
		this.dradd_type = dradd_type;
	}
	public void setDradd_na(String dradd_na) {
		this.dradd_na = dradd_na;
	}
	public void setDradd_pr(Integer dradd_pr) {
		this.dradd_pr = dradd_pr;
	}
	
	public void setDradd_st(Integer dradd_st) {
		this.dradd_st = dradd_st;
	}
	
	
}
