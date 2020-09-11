package com.prod_pic.model;

public class PicVO implements java.io.Serializable{
	private Integer pic_no;
	private String prod_no;
	public Integer getPic_no() {
		return pic_no;
	}
	public void setPic_no(Integer pic_no) {
		this.pic_no = pic_no;
	}

	public String getProd_no() {
		return prod_no;
	}
	public void setProd_no(String prod_no) {
		this.prod_no = prod_no;
	}

	private byte[] pic;
	
	public PicVO() {};
	

	public byte[] getPic() {
		return pic;
	}
	public void setPic(byte[] pic) {
		this.pic = pic;
	}

}
