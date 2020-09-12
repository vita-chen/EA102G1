package com.wpcase.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.Part;

import com.vender.model.VenderJDBCDAO;
import com.vender.model.VenderVO;
import com.wpcollect.model.WPCollectDAO;
import com.wpcollect.model.WPCollectDAO_Interface;
import com.wpcollect.model.WPCollectVO;
import com.wpimg.model.WPImgDAO;
import com.wpimg.model.WPImgDAO_Interface;
import com.wpimg.model.WPImgVO;

public class WPCaseService {

	private WPCaseDAO_Interface wpcasedao;
	private WPImgDAO_Interface wpimgdao;
	private WPCollectDAO_Interface wpcollectdao;

	public WPCaseService() {
		wpcasedao = new WPCaseDAO();
		wpimgdao = new WPImgDAO();
		wpcollectdao = new WPCollectDAO();
	}

	public WPCaseVO addWPCase(String vender_id, String wed_photo_name, String wed_photo_intro,
			Integer wed_photo_price, Integer wed_photo_status) {

		WPCaseVO WPCaseVO = new WPCaseVO();
		WPCaseVO.setVender_id(vender_id);
		WPCaseVO.setWed_photo_name(wed_photo_name);
		WPCaseVO.setWed_photo_intro(wed_photo_intro);
		WPCaseVO.setWed_photo_price(wed_photo_price);
		WPCaseVO.setWed_photo_status(wed_photo_status);

		java.sql.Timestamp wed_photo_addtime = new Timestamp(System.currentTimeMillis());
		WPCaseVO.setWed_photo_addtime(wed_photo_addtime);

		wpcasedao.insert(WPCaseVO);

		return WPCaseVO;
	}

	public void addCaseImg(WPCaseVO wpcasevo, Part part) {
		WPImgVO wpimgvo = new WPImgVO();
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			InputStream is = part.getInputStream();
			if(is.available()==0) return;
			byte[] buffer = new byte[8192];
			int i;
			while ((i = is.read(buffer)) != -1) {
				bos.write(buffer, 0, i);
			}
				wpimgvo.setWed_photo_case_no(wpcasevo.getWed_photo_case_no());
				wpimgvo.setWed_photo_img(bos.toByteArray());
				wpimgdao.addImg(wpimgvo);
			is.close();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<WPCaseVO> getAll() {
		return wpcasedao.getAll();
	}
	
	public List<WPImgVO> getAllImg(String wed_photo_case_no) {
		return wpimgdao.selImg(wed_photo_case_no);
	}
	public void deleteWPCaseNo(String wed_photo_case_no) {
		wpcasedao.delete(wed_photo_case_no);
	}
	public void deleteWPImg(String[] wp_imgs_no) {
		wpimgdao.delImg(wp_imgs_no);
	}
	public WPCaseVO getOneWPno(String wed_photo_case_no) {
		return wpcasedao.getOneWPno(wed_photo_case_no);
	}

	public WPCaseVO updateWPCase(String wed_photo_name, String wed_photo_intro, Integer wed_photo_status, Integer wed_photo_price,
			String wed_photo_case_no) {

		WPCaseVO WPCaseVO = new WPCaseVO();

		WPCaseVO.setWed_photo_name(wed_photo_name);
		WPCaseVO.setWed_photo_intro(wed_photo_intro);
		WPCaseVO.setWed_photo_status(wed_photo_status);
		WPCaseVO.setWed_photo_price(wed_photo_price);
		WPCaseVO.setWed_photo_case_no(wed_photo_case_no);

		wpcasedao.updateWPCase(WPCaseVO);

		return WPCaseVO;
	}
	public void addCollect(WPCollectVO WPCollectVO) {
		wpcollectdao.addCollect(WPCollectVO);
	}
	public void disCollect(WPCollectVO WPCollectVO) {
		wpcollectdao.disCollect(WPCollectVO);
	}
	public List<WPCollectVO> selCollect(String membre_id) {
		return wpcollectdao.selCollect(membre_id);
	}
	public List<WPCaseVO> search_case(List<WPCollectVO> list){ //由收藏VO取得方案VO
		List<WPCaseVO> wpcaselist = new ArrayList<WPCaseVO>();
		for(WPCollectVO vo : list) {			
			wpcaselist.add(wpcasedao.getOneWPno(vo.getWed_photo_case_no()));
		}
		return wpcaselist;
	}
	public List<WPCaseVO> search_case(String search_case_name){
		List<WPCaseVO> casename_list = wpcasedao.getAll();
		casename_list = casename_list.stream()
				.filter(vo -> vo.getWed_photo_name().indexOf(search_case_name)!= -1)
				.collect(Collectors.toList());

		return casename_list;
	}
	public List<WPCaseVO> search_case(Integer x, Integer y){
		List<WPCaseVO> casename_list = wpcasedao.getAll();
		casename_list = casename_list.stream()
				.filter(vo -> vo.getWed_photo_price() > x && vo.getWed_photo_price() < y)
				.collect(Collectors.toList());

		return casename_list;
	}
	public List<WPCaseVO> search_case_addr(String addr){
		
		List<WPCaseVO> wpcase_list = wpcasedao.getAll();
		List<WPCaseVO> result_list = new ArrayList<WPCaseVO>();
		
		VenderJDBCDAO ven = new VenderJDBCDAO();
		List<VenderVO> vendervoList = ven.getAll();
		
		vendervoList = vendervoList.stream()
				.filter(vo -> vo.getVen_addr().indexOf(addr) != -1)
				.collect(Collectors.toList());
		
		for (VenderVO vendervo : vendervoList) {
			for (WPCaseVO casevo : wpcase_list) {
				if (casevo.getVender_id().equals(vendervo.getVender_id())) {
					result_list.add(casevo);
				}
			}
		}
		
		return result_list;
	}
	public List<VenderVO> list_vender(){
		List<WPCaseVO> list_case = getAll(); //從全部婚攝方案查廠商編號
		
		Set<String> set = new HashSet<String>(); //利用set不重複特性 找出全部廠商
		for(WPCaseVO list : list_case){
			set.add(list.getVender_id());
		}
		VenderJDBCDAO venderdao = new VenderJDBCDAO();
		List<VenderVO> list_vender = new ArrayList<VenderVO>();
		
		Iterator<String> objs = set.iterator();
		while (objs.hasNext()){		
			VenderVO vendervo = venderdao.findByPrimaryKey(objs.next());
			list_vender.add(vendervo);		
		}		
		return list_vender;		
	}
	public List<VenderVO> search_vender(String str){ //廠商名稱篩選
		List<VenderVO> list_vender = list_vender();
		list_vender = list_vender.stream()
				.filter(vo -> vo.getVen_name().indexOf(str)!= -1)
				.collect(Collectors.toList());
		return list_vender;
	}
	
	public List<VenderVO> search_vender_addr(String addr){ //廠商地區篩選
		List<VenderVO> list_vender = list_vender();
		list_vender = list_vender.stream()
				.filter(vo -> vo.getVen_addr().indexOf(addr)!= -1)
				.collect(Collectors.toList());
		return list_vender;
	}
	public List<VenderVO> search_vender_star(Double star){ //廠商評價篩選
		List<VenderVO> list_vender = list_vender();
		Function<VenderVO, Double> toDoubleFunction = vo -> ven_star(vo.getVender_id());
		list_vender = list_vender.stream()
				.filter(vo -> ven_star(vo.getVender_id()) <= star)
				.sorted(Comparator.comparing(toDoubleFunction).reversed())
				.collect(Collectors.toList());
						
		return list_vender;
	}
	public Integer countCase(String vender_id) {//回傳廠商總共方案數
		List<WPCaseVO> list = getAll();
		list = list.stream()
				.filter(vo -> vo.getVender_id().equals(vender_id))
				.collect(Collectors.toList());
		return list.size();
	}
	public Double ven_star(String vender_id) {//回傳廠商星星分數 (總分/評價數)
		VenderJDBCDAO venderdao = new VenderJDBCDAO();
		VenderVO vendervo = venderdao.findByPrimaryKey(vender_id);
		Double star =  (double)vendervo.getVen_stars_total() / vendervo.getVen_review_count();
		
		BigDecimal bg = new BigDecimal(star).setScale(1, RoundingMode.UP);
		 
		return bg.doubleValue();
	}
	
}
