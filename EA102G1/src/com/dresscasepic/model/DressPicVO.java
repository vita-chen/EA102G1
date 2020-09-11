package com.dresscasepic.model;

public class DressPicVO implements java.io.Serializable{
	private String drpic_id;
	private String drcase_id;
	private byte[] drpic;

	public String getDrpic_id() {
		return drpic_id;
	}

	public String getDrcase_id() {
		return drcase_id;
	}

	public byte[] getDrpic() {
		return drpic;
	}

	public void setDrpic_id(String drpic_id) {
		this.drpic_id = drpic_id;
	}

	public void setDrcase_id(String drcase_id) {
		this.drcase_id = drcase_id;
	}

	public void setDrpic(byte[] drpic) {
		this.drpic = drpic;
	}

	
}
