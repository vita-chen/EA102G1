package com.forum.model;

import java.sql.Timestamp;

public class ForumVO implements java.io.Serializable {
	private String forum_id;      
	private String membre_id;      
	private String forum_class;    
	private String forum_title;    
	private String forum_content;   
	private Timestamp forum_addtime;
	public String getForum_id() {
		return forum_id;
	}
	public void setForum_id(String forum_id) {
		this.forum_id = forum_id;
	}
	public String getMembre_id() {
		return membre_id;
	}
	public void setMembre_id(String membre_id) {
		this.membre_id = membre_id;
	}
	public String getForum_class() {
		return forum_class;
	}
	public void setForum_class(String forum_class) {
		this.forum_class = forum_class;
	}
	public String getForum_title() {
		return forum_title;
	}
	public void setForum_title(String forum_title) {
		this.forum_title = forum_title;
	}
	public String getForum_content() {
		return forum_content;
	}
	public void setForum_content(String forum_content) {
		this.forum_content = forum_content;
	}
	public Timestamp getForum_addtime() {
		return forum_addtime;
	}
	public void setForum_addtime(Timestamp forum_addtime) {
		this.forum_addtime = forum_addtime;
	}
	
}
