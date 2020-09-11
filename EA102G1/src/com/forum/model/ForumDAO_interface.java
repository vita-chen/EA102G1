package com.forum.model;

import java.util.List;


public interface ForumDAO_interface {
	public void insert(ForumVO forumVO);
	public void update(ForumVO forumVO);
	public void delete(String forum_id);
	public ForumVO findByPrimaryKey(String forum_id);
	public List<ForumVO> getAll();
}
