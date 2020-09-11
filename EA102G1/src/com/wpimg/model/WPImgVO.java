package com.wpimg.model;

import oracle.sql.BLOB;

public class WPImgVO implements java.io.Serializable{
	
	private String wed_photo_imgno;
	private String wed_photo_case_no;
	private byte[] wed_photo_img;
	
	public String getWed_photo_imgno() {
		return wed_photo_imgno;
	}
	public void setWed_photo_imgno(String wed_photo_imgno) {
		this.wed_photo_imgno = wed_photo_imgno;
	}
	public String getWed_photo_case_no() {
		return wed_photo_case_no;
	}
	public void setWed_photo_case_no(String wed_photo_case_no) {
		this.wed_photo_case_no = wed_photo_case_no;
	}
	public byte[] getWed_photo_img() {
		return wed_photo_img;
	}
	public void setWed_photo_img(byte[] wed_photo_img) {
		this.wed_photo_img = wed_photo_img;
	}
		
}
