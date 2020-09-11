package com.wpcollect.model;

import java.util.List;

public interface WPCollectDAO_Interface {
	
	public void addCollect(WPCollectVO WPCollectVO);
	
	public void disCollect(WPCollectVO WPCollectVO);

	public List<WPCollectVO> selCollect(String membre_id);

}
