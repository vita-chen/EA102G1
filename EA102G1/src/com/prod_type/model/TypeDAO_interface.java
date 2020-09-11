package com.prod_type.model;

import java.util.List;

public interface TypeDAO_interface {
	public void insert(String type_no, String type_name);
	public void delete(String type_no, String type_name);
	public List<TypeVO> getAll();
}
