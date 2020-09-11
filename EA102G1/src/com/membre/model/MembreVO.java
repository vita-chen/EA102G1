package com.membre.model;

public class MembreVO implements java.io.Serializable{
	private String membre_id;
	private String mem_name;
	private String sex;
	private String address;
	private String phone;
	private String email;
	private String status;
	private String compte;
	private String passe;
	private byte[] photo;
	private java.sql.Timestamp regis_time;
	
	public MembreVO() {};

	public String getMembre_id() {
		return membre_id;
	}


	public void setMembre_id(String membre_id) {
		this.membre_id = membre_id;
	}

	public String getMem_name() {
		return mem_name;
	}

	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getCompte() {
		return compte;
	}
	public void setCompte(String account) {
		this.compte = account;
	}
	public String getPasse() {
		return passe;
	}
	public void setPasse(String pwd) {
		this.passe = pwd;
	}
	public java.sql.Timestamp getRegis_time() {
		return regis_time;
	}
	public void setRegis_time(java.sql.Timestamp regis_time) {
		this.regis_time = regis_time;
	}
	
}
