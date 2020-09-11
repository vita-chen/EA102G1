package com.wpimg.model;

import java.util.List;

public interface WPImgDAO_Interface {
	
	/*定義 方案照片的方法 
	 *  上傳 刪除
	 * */
	public void addImg(WPImgVO WPImgVO);
	
	public void delImg(WPImgVO WPImgVO);
	
	public List<WPImgVO> selImg(String wed_photo_case_no);

}
