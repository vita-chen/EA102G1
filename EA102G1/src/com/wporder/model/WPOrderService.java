package com.wporder.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.vender.model.VenderService;
import com.wpcase.model.WPCaseDAO;
import com.wpcase.model.WPCaseDAO_Interface;
import com.wpcase.model.WPCaseVO;
import com.wpdetail.model.WPDetailDAO;
import com.wpdetail.model.WPDetailDAO_Interface;
import com.wpdetail.model.WPDetailVO;

public class WPOrderService {
	
	private WPCaseDAO_Interface wpcasedao;
	private WPDetailDAO_Interface wpdetaildao;
	private WPOrderDAO_Interface wporderdao;

	public WPOrderService(){
		wpcasedao = new WPCaseDAO();		
		wpdetaildao = new WPDetailDAO();
		wporderdao = new WPOrderDAO();		
	}
	
	public WPOrderVO addWPOrder(String membre_id, String vender_id, Timestamp filming_time, String order_explain) {

		WPOrderVO WPOrderVO = new WPOrderVO();
		WPOrderVO.setMembre_id(membre_id);
		WPOrderVO.setVender_id(vender_id);
		WPOrderVO.setFilming_time(filming_time);
		WPOrderVO.setOrder_explain(order_explain);

		java.sql.Timestamp wed_photo_odtime = new Timestamp(System.currentTimeMillis());
		WPOrderVO.setWed_photo_odtime(wed_photo_odtime);
		wporderdao.insert(WPOrderVO);		
		return WPOrderVO;
	}
	
	public WPDetailVO addWPDetail(String wed_photo_order_no, String wed_photo_case_no) {

		WPDetailVO WPDetailVO = new WPDetailVO();
		WPDetailVO.setWed_photo_order_no(wed_photo_order_no);
		WPDetailVO.setWed_photo_case_no(wed_photo_case_no);
		wpdetaildao.insert(WPDetailVO);
		return WPDetailVO;
	}

	public List<WPOrderVO> getAll() {
		return wporderdao.getAll();
	}

	public WPOrderVO getOne(String wed_photo_order_no) {
		return wporderdao.getOne(wed_photo_order_no);
	}
	public void cancel_order(String wed_photo_order_no) {
		wporderdao.cancel_order(wed_photo_order_no);
	}
	public void complete_order(WPOrderVO WPOrderVO) {
		VenderService vendersvc = new VenderService();
		WPOrderVO detail = getOne(WPOrderVO.getWed_photo_order_no());
		
		if(detail.getOrder_status() == 1) {
			//新訂單  wp改內容與分數 vender改廠商評價數&total *小心廠商帶來null 數字+null=null
			wporderdao.complete_order(WPOrderVO);
			vendersvc.update_review(WPOrderVO.getVender_id(), WPOrderVO.getReview_star(), true);
		}else {
			//舊訂單 wp改內容與分數 vender改total&評價數不+1 *會員來修改舊訂單 舊訂單的分數可能為null
			wporderdao.complete_order(WPOrderVO);
			Integer ii = (detail.getReview_star() == null) ? 0: detail.getReview_star();
			Integer gap = WPOrderVO.getReview_star() - ii;
			vendersvc.update_review(WPOrderVO.getVender_id(), gap, false);
		}
	}
	public void Mem_Report(WPOrderVO WPOrderVO) {
		wporderdao.Mem_Report(WPOrderVO);
	}
	public void Ven_Report(WPOrderVO WPOrderVO) {
		wporderdao.Ven_Report(WPOrderVO);
	}
	public void Mem_Explain(WPOrderVO WPOrderVO) {
		wporderdao.Mem_Explain(WPOrderVO);
	}
	public String orderChange(String wed_photo_order_no) {
		List<WPDetailVO> list = wpdetaildao.getAll();
		list = list.stream()
				.filter(vo -> vo.getWed_photo_order_no().equals(wed_photo_order_no))
				.collect(Collectors.toList());
		
		return list.get(0).getWed_photo_case_no();
	}
	public List<WPDetailVO> caseChange(String wed_photo_case_no) {
		List<WPDetailVO> list = wpdetaildao.getAll();
		list = list.stream()
				.filter(vo -> vo.getWed_photo_case_no().equals(wed_photo_case_no))
				.collect(Collectors.toList());
		
		return list;
	}
}
