package com.ad.model;

import java.sql.Timestamp ;

public class AdVO implements java.io.Serializable {
	private String ad_id;
	private byte[] ad_pic;
	private String ad_detail;
	private Timestamp ad_start_time;
	private Timestamp ad_end_time;
	public String getAd_id() {
		return ad_id;
	}
	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}
	public byte[] getAd_pic() {
		return ad_pic;
	}
	public void setAd_pic(byte[] ad_pic) {
		this.ad_pic = ad_pic;
	}
	public String getAd_detail() {
		return ad_detail;
	}
	public void setAd_detail(String ad_detail) {
		this.ad_detail = ad_detail;
	}
	public Timestamp getAd_start_time() {
		return ad_start_time;
	}
	public void setAd_start_time(Timestamp timestamp) {
		this.ad_start_time = timestamp;
	}
	public Timestamp getAd_end_time() {
		return ad_end_time;
	}
	public void setAd_end_time(Timestamp ad_end_time) {
		this.ad_end_time = ad_end_time;
	}
	
	
	
	
	
}
