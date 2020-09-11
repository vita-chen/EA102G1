package com.wpcase.model;

import java.sql.Date;
import java.sql.Timestamp;

public class WPCaseVO implements java.io.Serializable{
	
	private String wed_photo_case_no;
	private String vender_id;
	private String wed_photo_name;     
	private String wed_photo_intro;
	private Integer wed_photo_price;
	private Timestamp wed_photo_addtime;
	private Integer wed_photo_status;
	
	public String getWed_photo_case_no() {
		return wed_photo_case_no;
	}
	public void setWed_photo_case_no(String wed_photo_case_no) {
		this.wed_photo_case_no = wed_photo_case_no;
	}
	public String getVender_id() {
		return vender_id;
	}
	public void setVender_id(String vender_id) {
		this.vender_id = vender_id;
	}
	public String getWed_photo_name() {
		return wed_photo_name;
	}
	public void setWed_photo_name(String wed_photo_name) {
		this.wed_photo_name = wed_photo_name;
	}
	public String getWed_photo_intro() {
		return wed_photo_intro;
	}
	public void setWed_photo_intro(String wed_photo_intro) {
		this.wed_photo_intro = wed_photo_intro;
	}
	public Integer getWed_photo_price() {
		return wed_photo_price;
	}
	public void setWed_photo_price(Integer wed_photo_price) {
		this.wed_photo_price = wed_photo_price;
	}
	public Timestamp getWed_photo_addtime() {
		return wed_photo_addtime;
	}
	public void setWed_photo_addtime(Timestamp wed_photo_addtime) {
		this.wed_photo_addtime = wed_photo_addtime;
	}
	public Integer getWed_photo_status() {
		return wed_photo_status;
	}
	public void setWed_photo_status(Integer wed_photo_status) {
		this.wed_photo_status = wed_photo_status;
	}	
}
