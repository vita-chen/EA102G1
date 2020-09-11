package com.carPic.model;

public class CarPicVO {

	private String cpic_id; // 禮車照片編號
	private String cid;		// 禮車編號
	private byte[] cpic;	// 禮車照片

	public String getCpic_id() {
		return cpic_id;
	}

	public void setCpic_id(String cpic_id) {
		this.cpic_id = cpic_id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public byte[] getCpic() {
		return cpic;
	}

	public void setCpic(byte[] cpic) {
		this.cpic = cpic;
	}

}
