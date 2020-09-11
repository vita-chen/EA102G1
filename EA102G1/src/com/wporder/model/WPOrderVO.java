package com.wporder.model;

import java.sql.Date;
import java.sql.Timestamp;

public class WPOrderVO implements java.io.Serializable{

	private String wed_photo_order_no;
	private String membre_id;
	private String vender_id;
	private Timestamp filming_time;
	private Timestamp wed_photo_odtime; //5
	private Integer order_status;
	private String order_explain;
	private Integer review_star;
	private String review_content;
	
	private Integer wp_pay_s; //10
	private Integer wp_vrep_s;
	private Integer wp_mrep_s;
	private String wp_vrep_d;
	private String wp_mrep_d;
	private String wp_vrep_r;
	private String wp_mrep_r; //16
	
	
	public String getWed_photo_order_no() {
		return wed_photo_order_no;
	}
	public void setWed_photo_order_no(String wed_photo_order_no) {
		this.wed_photo_order_no = wed_photo_order_no;
	}
	public String getMembre_id() {
		return membre_id;
	}
	public void setMembre_id(String membre_id) {
		this.membre_id = membre_id;
	}
	public String getVender_id() {
		return vender_id;
	}
	public void setVender_id(String vender_id) {
		this.vender_id = vender_id;
	}
	public Timestamp getFilming_time() {
		return filming_time;
	}
	public void setFilming_time(Timestamp filming_time) {
		this.filming_time = filming_time;
	}
	public Timestamp getWed_photo_odtime() {
		return wed_photo_odtime;
	}
	public void setWed_photo_odtime(Timestamp wed_photo_odtime) {
		this.wed_photo_odtime = wed_photo_odtime;
	}
	public Integer getOrder_status() {
		return order_status;
	}
	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}
	public String getOrder_explain() {
		return order_explain;
	}
	public void setOrder_explain(String order_explain) {
		this.order_explain = order_explain;
	}
	public Integer getReview_star() {
		return review_star;
	}
	public void setReview_star(Integer review_star) {
		this.review_star = review_star;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public Integer getWp_pay_s() {
		return wp_pay_s;
	}
	public void setWp_pay_s(Integer wp_pay_s) {
		this.wp_pay_s = wp_pay_s;
	}
	public Integer getWp_vrep_s() {
		return wp_vrep_s;
	}
	public void setWp_vrep_s(Integer wp_vrep_s) {
		this.wp_vrep_s = wp_vrep_s;
	}
	public Integer getWp_mrep_s() {
		return wp_mrep_s;
	}
	public void setWp_mrep_s(Integer wp_mrep_s) {
		this.wp_mrep_s = wp_mrep_s;
	}
	public String getWp_vrep_d() {
		return wp_vrep_d;
	}
	public void setWp_vrep_d(String wp_vrep_d) {
		this.wp_vrep_d = wp_vrep_d;
	}
	public String getWp_mrep_d() {
		return wp_mrep_d;
	}
	public void setWp_mrep_d(String wp_mrep_d) {
		this.wp_mrep_d = wp_mrep_d;
	}
	public String getWp_vrep_r() {
		return wp_vrep_r;
	}
	public void setWp_vrep_r(String wp_vrep_r) {
		this.wp_vrep_r = wp_vrep_r;
	}
	public String getWp_mrep_r() {
		return wp_mrep_r;
	}
	public void setWp_mrep_r(String wp_mrep_r) {
		this.wp_mrep_r = wp_mrep_r;
	}
	
	
	
	
}
