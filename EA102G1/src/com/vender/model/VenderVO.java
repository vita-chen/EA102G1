package com.vender.model;

import java.util.Date;

public class VenderVO implements java.io.Serializable{
	
	private String vender_id;
	private String ven_name;
	private String ven_addr;
	private String ven_phone;
	private String ven_contact;
	private String ven_mail;
	private Integer is_vaild;
	private Integer is_enable;
	private byte[] ven_evidence_pic;
	private String ven_account;
	private String ven_pwd;
	private Integer ven_sponsor;
	private Date ven_regis_time;
	private Integer ven_review_count;
	private Integer ven_stars_total;
	public String getVender_id() {
		return vender_id;
	}
	public void setVender_id(String vender_id) {
		this.vender_id = vender_id;
	}
	public String getVen_name() {
		return ven_name;
	}
	public void setVen_name(String ven_name) {
		this.ven_name = ven_name;
	}
	public String getVen_addr() {
		return ven_addr;
	}
	public void setVen_addr(String ven_addr) {
		this.ven_addr = ven_addr;
	}
	public String getVen_phone() {
		return ven_phone;
	}
	public void setVen_phone(String ven_phone) {
		this.ven_phone = ven_phone;
	}
	public String getVen_contact() {
		return ven_contact;
	}
	public void setVen_contact(String ven_contact) {
		this.ven_contact = ven_contact;
	}
	public String getVen_mail() {
		return ven_mail;
	}
	public void setVen_mail(String ven_mail) {
		this.ven_mail = ven_mail;
	}
	public Integer getIs_vaild() {
		return is_vaild;
	}
	public void setIs_vaild(Integer is_vaild) {
		this.is_vaild = is_vaild;
	}
	public Integer getIs_enable() {
		return is_enable;
	}
	public void setIs_enable(Integer is_enable) {
		this.is_enable = is_enable;
	}
	public byte[] getVen_evidence_pic() {
		return ven_evidence_pic;
	}
	public void setVen_evidence_pic(byte[] ven_evidence_pic) {
		this.ven_evidence_pic = ven_evidence_pic;
	}
	public String getVen_account() {
		return ven_account;
	}
	public void setVen_account(String ven_account) {
		this.ven_account = ven_account;
	}
	public String getVen_pwd() {
		return ven_pwd;
	}
	public void setVen_pwd(String ven_pwd) {
		this.ven_pwd = ven_pwd;
	}
	public Integer getVen_sponsor() {
		return ven_sponsor;
	}
	public void setVen_sponsor(Integer ven_sponsor) {
		this.ven_sponsor = ven_sponsor;
	}
	public Date getVen_regis_time() {
		return ven_regis_time;
	}
	public void setVen_regis_time(Date ven_regis_time) {
		this.ven_regis_time = ven_regis_time;
	}
	public Integer getVen_review_count() {
		return ven_review_count;
	}
	public void setVen_review_count(Integer ven_review_count) {
		this.ven_review_count = ven_review_count;
	}
	public Integer getVen_stars_total() {
		return ven_stars_total;
	}
	public void setVen_stars_total(Integer ven_stars_total) {
		this.ven_stars_total = ven_stars_total;
	}
	
	
}
