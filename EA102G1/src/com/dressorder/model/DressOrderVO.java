package com.dressorder.model;

import java.sql.Timestamp;

public class DressOrderVO implements java.io.Serializable{
	private String drord_id;
	private String membre_id;
	private String vender_id;
	private Timestamp drord_time;
	private Integer drord_pr;
	private Integer drord_depo;
	private Integer drord_ini;
	private Integer drord_pay_st;
	private Integer drord_fin_st;
	
	private Integer dr_mrep_st;
	private Integer dr_vrep_st;
	private String dr_mrep_de;
	private String dr_vrep_de;
	private String dr_mrep_res;
	private String dr_vrep_res;
	
	private Integer dr_rev_star;
	private String dr_rev_con;
	
	public String getDrord_id() {
		return drord_id;
	}
	public String getMembre_id() {
		return membre_id;
	}
	public String getVender_id() {
		return vender_id;
	}
	public Timestamp getDrord_time() {
		return drord_time;
	}
	public Integer getDrord_pr() {
		return drord_pr;
	}
	public Integer getDrord_depo() {
		return drord_depo;
	}
	public Integer getDrord_ini() {
		return drord_ini;
	}
	public Integer getDrord_pay_st() {
		return drord_pay_st;
	}
	public Integer getDrord_fin_st() {
		return drord_fin_st;
	}
	public Integer getDr_mrep_st() {
		return dr_mrep_st;
	}
	public Integer getDr_vrep_st() {
		return dr_vrep_st;
	}
	public String getDr_mrep_de() {
		return dr_mrep_de;
	}
	public String getDr_vrep_de() {
		return dr_vrep_de;
	}
	public String getDr_mrep_res() {
		return dr_mrep_res;
	}
	public String getDr_vrep_res() {
		return dr_vrep_res;
	}
	public Integer getDr_rev_star() {
		return dr_rev_star;
	}
	public String getDr_rev_con() {
		return dr_rev_con;
	}
	public void setDrord_id(String drord_id) {
		this.drord_id = drord_id;
	}
	public void setMembre_id(String membre_id) {
		this.membre_id = membre_id;
	}
	public void setVender_id(String vender_id) {
		this.vender_id = vender_id;
	}
	public void setDrord_time(Timestamp drord_time) {
		this.drord_time = drord_time;
	}
	public void setDrord_pr(Integer drord_pr) {
		this.drord_pr = drord_pr;
	}
	public void setDrord_depo(Integer drord_depo) {
		this.drord_depo = drord_depo;
	}
	public void setDrord_ini(Integer drord_ini) {
		this.drord_ini = drord_ini;
	}
	public void setDrord_pay_st(Integer drord_pay_st) {
		this.drord_pay_st = drord_pay_st;
	}
	public void setDrord_fin_st(Integer drord_fin_st) {
		this.drord_fin_st = drord_fin_st;
	}
	public void setDr_mrep_st(Integer dr_mrep_st) {
		this.dr_mrep_st = dr_mrep_st;
	}
	public void setDr_vrep_st(Integer dr_vrep_st) {
		this.dr_vrep_st = dr_vrep_st;
	}
	public void setDr_mrep_de(String dr_mrep_de) {
		this.dr_mrep_de = dr_mrep_de;
	}
	public void setDr_vrep_de(String dr_vrep_de) {
		this.dr_vrep_de = dr_vrep_de;
	}
	public void setDr_mrep_res(String dr_mrep_res) {
		this.dr_mrep_res = dr_mrep_res;
	}
	public void setDr_vrep_res(String dr_vrep_res) {
		this.dr_vrep_res = dr_vrep_res;
	}
	public void setDr_rev_star(Integer dr_rev_star) {
		this.dr_rev_star = dr_rev_star;
	}
	public void setDr_rev_con(String dr_rev_con) {
		this.dr_rev_con = dr_rev_con;
	}
	
	
}
