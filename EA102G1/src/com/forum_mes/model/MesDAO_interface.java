package com.forum_mes.model;

import java.util.List;

import com.forum.model.ForumVO;

public interface MesDAO_interface {
	
	public void insert(MesVO mesVO);
	
	public void update(MesVO mesVO);
	
	public void delete(String forum_mes_id);
	
	//用主鍵查詢
	public MesVO findByPrimaryKey(String forum_mes_id);
	
	//列出文章的所有回覆(以文章編號)
	public List<MesVO> getMesByForum(String forum_id);

}
