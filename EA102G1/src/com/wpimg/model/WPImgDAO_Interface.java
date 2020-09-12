package com.wpimg.model;

import java.util.List;

public interface WPImgDAO_Interface {
	
	
	public void addImg(WPImgVO WPImgVO);
	
	public void delImg(WPImgVO WPImgVO);
	
	public void delImg(String[] wp_imgs_no);
	
	public List<WPImgVO> selImg(String wed_photo_case_no);

}
