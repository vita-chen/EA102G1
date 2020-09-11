package com.pinthepost.model;

import java.util.*;

public interface PtpDAO_interface {
	public void insert(PtpVO ptpVO);
	public void update(PtpVO ptpVO);
	public PtpVO findByPrimaryKey(String ptp_id);
	public List<PtpVO> getAll();

}
