package com.forum.model;

import java.sql.Timestamp;
import java.util.List;


public class ForumService {
	
	private ForumDAO_interface dao;
	
	public ForumService() {
		dao = new ForumDAO();
	}
	
	public ForumVO addForum(String membre_id, String forum_class, 
			String forum_title, String forum_content, Timestamp forum_addtime) {
		
	ForumVO forumVO	= new ForumVO();
	
	forumVO.setMembre_id(membre_id);
	forumVO.setForum_class(forum_class);
	forumVO.setForum_title(forum_title);
	forumVO.setForum_content(forum_content);
	forumVO.setForum_addtime(forum_addtime);
	
	dao.insert(forumVO);
	
	return forumVO;
	}
	
	public ForumVO updateForum(String forum_id, String membre_id, String forum_class, 
			String forum_title, String forum_content, Timestamp forum_addtime) {
		
		ForumVO forumVO	= new ForumVO();
		
		forumVO.setForum_id(forum_id);
		forumVO.setMembre_id(membre_id);
		forumVO.setForum_class(forum_class);
		forumVO.setForum_title(forum_title);
		forumVO.setForum_content(forum_content);
		forumVO.setForum_addtime(forum_addtime);
		dao.update(forumVO);
		
		return getOneForum(forum_id);
	}
	
	public void deleteForum(String forum_id) {
		dao.delete(forum_id);
	}

	public ForumVO getOneForum(String forum_id) {
		return dao.findByPrimaryKey(forum_id);
	}

	public List<ForumVO> getAll() {
		return dao.getAll();
	}
}
