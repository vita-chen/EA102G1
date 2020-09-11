package com.pinthepost.model;

import java.util.List;

public class PtpService {

	private PtpDAO_interface dao;

	public PtpService() {
		dao = new PtpDAO();
	}

	public PtpVO addPtp(String ptp_detail, String ptp_subject) {
		PtpVO ptpVO = new PtpVO();

		ptpVO.setPtp_detail(ptp_detail);
		ptpVO.setPtp_subject(ptp_subject);
		dao.insert(ptpVO);

		return ptpVO;
	}

	public PtpVO updatePtp(String ptp_detail, String ptp_subject, String ptp_id) {
		PtpVO ptpVO = new PtpVO();

		ptpVO.setPtp_detail(ptp_detail);
		ptpVO.setPtp_subject(ptp_subject);
		ptpVO.setPtp_id(ptp_id);
		dao.update(ptpVO);

		return ptpVO;
		
	}
	
	public PtpVO getOnePtp(String ptp_id) {
		return dao.findByPrimaryKey(ptp_id);
		
	}
	
	public List<PtpVO> getAll() {
		System.out.println("0825");
		return dao.getAll();
	}
	
}
