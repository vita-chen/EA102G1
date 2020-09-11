package com.dressaddtype.model;

import java.util.List;

public interface DressAddTypeDAO_interface {
	
	public void insert(DressAddTypeVO datVO);
	public void delete(String dradd_type);

	public DressAddTypeVO findByPK(String dradd_type);
	public List<DressAddTypeVO> getAll();
}
