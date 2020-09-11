package com.pinthepost.model;

import java.sql.Timestamp;

public class PtpVO implements java.io.Serializable {
	private String ptp_id;
	private String ptp_detail;
	private String ptp_subject;
	private Timestamp ptp_date;
	
	public String getPtp_id() {
		return ptp_id;
	}
	public void setPtp_id(String ptp_id) {
		this.ptp_id = ptp_id;
	}
	public String getPtp_detail() {
		return ptp_detail;
	}
	public void setPtp_detail(String ptp_detail) {
		this.ptp_detail = ptp_detail;
	}
	public String getPtp_subject() {
		return ptp_subject;
	}
	public void setPtp_subject(String ptp_subject) {
		this.ptp_subject = ptp_subject;
	}
	public Timestamp getPtp_date() {
		return ptp_date;
	}
	public void setPtp_date(Timestamp ptp_date) {
		this.ptp_date = ptp_date;
	}
	

}
