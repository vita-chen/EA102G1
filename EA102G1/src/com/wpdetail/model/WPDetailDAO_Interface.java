package com.wpdetail.model;

import java.util.List;

public interface WPDetailDAO_Interface {

	public void insert(WPDetailVO WPDetailVO);
    public void delete(WPDetailVO WPDetailVO);
    public List<WPDetailVO> getAll();
}
