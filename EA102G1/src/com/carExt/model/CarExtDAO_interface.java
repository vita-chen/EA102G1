package com.carExt.model;
import java.util.*;

public interface CarExtDAO_interface {
	public void insert(CarExtVO carExtVO);
	public void update(CarExtVO carExtVO);
	public void delete(CarExtVO carExtVO);
	public void getOneCExtPic(CarExtVO carExtVO); // 透過加購品編號取得該加購品的照片
	public void getOneCExt(CarExtVO carExtVO);    // 透過加購品編號取得該加購品資料
	public List<CarExtVO> getAllByVenderId(CarExtVO carExtVO);
	
	

}
