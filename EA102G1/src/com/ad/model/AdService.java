package com.ad.model;

import java.util.List;

public class AdService {
	
	private AdDAO_interface dao;
	
	public AdService() {
		dao = new AdDAO();
	}
	
	public AdVO addAd(String ad_detail, byte[] ad_pic) {
		
		AdVO adVO = new AdVO();
		
		adVO.setAd_pic(ad_pic);
		adVO.setAd_detail(ad_detail);
		
		dao.insert(adVO);
		
		return adVO;
	}
	
	public AdVO updateAd(byte[] ad_pic, String ad_detail, String ad_id) {
		
		AdVO adVO = new AdVO();
		
		adVO.setAd_pic(ad_pic);
		adVO.setAd_detail(ad_detail);
		adVO.setAd_id(ad_id);
		
		System.out.println("123");
		dao.update(adVO);
		
		return adVO;
	}
	
	public AdVO getOneAd(String ad_id) {
		return dao.findByPrimaryKey(ad_id);
	}
	
	public List<AdVO> getAll() {
		System.out.println("0815");
		return dao.getAll();
	}

}
