package com.forum_mes.model;

import java.sql.Timestamp;

public class MesVO implements java.io.Serializable {
	private String forum_mes_id; 	//留言編號
	private String membre_id;		//會員編號   
	private String forum_id;		//文章編號  
	private String mes_text;		//留言內容
	private Timestamp mes_addtime;	//留言時間
	public String getForum_mes_id() {
		return forum_mes_id;
	}
	public void setForum_mes_id(String forum_mes_id) {
		this.forum_mes_id = forum_mes_id;
	}
	public String getMembre_id() {
		return membre_id;
	}
	public void setMembre_id(String membre_id) {
		this.membre_id = membre_id;
	}
	public String getForum_id() {
		return forum_id;
	}
	public void setForum_id(String forum_id) {
		this.forum_id = forum_id;
	}
	public String getMes_text() {
		return mes_text;
	}
	public void setMes_text(String mes_text) {
		this.mes_text = mes_text;
	}
	public Timestamp getMes_addtime() {
		return mes_addtime;
	}
	public void setMes_addtime(Timestamp mes_addtime) {
		this.mes_addtime = mes_addtime;
	}
	
}
