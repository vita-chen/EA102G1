package com.dresscase.model;
import java.sql.Date;

public class DressCaseVO implements java.io.Serializable{
	private String drcase_id;
	private String vender_id;
	private String drcase_na;
	private String drcase_br;
	private Integer drcase_pr;
	private Integer drcase_st;

	public String getDrcase_id() {
		return drcase_id;
	}

	public String getVender_id() {
		return vender_id;
	}

	public String getDrcase_na() {
		return drcase_na;
	}

	public String getDrcase_br() {
		return drcase_br;
	}

	public Integer getDrcase_pr() {
		return drcase_pr;
	}

	public Integer getDrcase_st() {
		return drcase_st;
	}

	public void setDrcase_id(String drcase_id) {
		this.drcase_id = drcase_id;
	}

	public void setVender_id(String vender_id) {
		this.vender_id = vender_id;
	}

	public void setDrcase_na(String drcase_na) {
		this.drcase_na = drcase_na;
	}

	public void setDrcase_br(String drcase_br) {
		this.drcase_br = drcase_br;
	}

	public void setDrcase_pr(Integer drcase_pr) {
		this.drcase_pr = drcase_pr;
	}

	public void setDrcase_st(Integer drcase_st) {
		this.drcase_st = drcase_st;
	}
	
}
