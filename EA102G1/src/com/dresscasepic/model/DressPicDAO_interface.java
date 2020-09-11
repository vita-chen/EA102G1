package com.dresscasepic.model;

import java.util.List;

public interface DressPicDAO_interface {
	
	public void insert(DressPicVO drpicvo);
	public void delete(String drpic_id);
	
	public DressPicVO findByPrimaryKey(String drpic_id);
	public List<DressPicVO> findPics(String drcase_id);
	public List<DressPicVO> getAll();
}
