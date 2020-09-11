package com.dressorderdetail.model;
public class DressOrderDetailVO implements java.io.Serializable{
	private String drde_id;
	private String drord_id;
	private String drcase_id;
	private Integer drde_st;
	private Integer drcase_totpr;
	public String getDrde_id() {
		return drde_id;
	}
	public String getDrord_id() {
		return drord_id;
	}
	public String getDrcase_id() {
		return drcase_id;
	}
	public Integer getDrde_st() {
		return drde_st;
	}
	public Integer getDrcase_totpr() {
		return drcase_totpr;
	}
	public void setDrde_id(String drde_id) {
		this.drde_id = drde_id;
	}
	public void setDrord_id(String drord_id) {
		this.drord_id = drord_id;
	}
	public void setDrcase_id(String drcase_id) {
		this.drcase_id = drcase_id;
	}
	public void setDrde_st(Integer drde_st) {
		this.drde_st = drde_st;
	}
	public void setDrcase_totpr(Integer drcase_totpr) {
		this.drcase_totpr = drcase_totpr;
	}

	
}
