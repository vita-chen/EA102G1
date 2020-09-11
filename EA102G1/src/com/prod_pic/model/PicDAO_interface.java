package com.prod_pic.model;

import java.util.List;

public interface PicDAO_interface {
	public void insert(PicVO picvo);
	public List<PicVO> findByKey(String prodno);
}
