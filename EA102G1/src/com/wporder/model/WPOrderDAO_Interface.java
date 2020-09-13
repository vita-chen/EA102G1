package com.wporder.model;

import java.util.List;

public interface WPOrderDAO_Interface {	
	
	public void insert(WPOrderVO WPOrderVO);
	
	public void cancel_order(String wed_photo_order_no);
	
	public void complete_order(WPOrderVO WPOrderVO);
	
	public List<WPOrderVO> getAll();
	
	public WPOrderVO getOne(String wed_photo_order_no);
	
	public void Mem_Report(WPOrderVO WPOrderVO);//會員檢舉
	
	public void Ven_Report(WPOrderVO WPOrderVO);//廠商檢舉
	
	public void Mem_Review(WPOrderVO WPOrderVO);//會員才可以評價
	
	public void Mem_Explain(WPOrderVO WPOrderVO);//會員改訂單備註
}
