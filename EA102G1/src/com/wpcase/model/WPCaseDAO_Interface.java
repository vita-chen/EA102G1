package com.wpcase.model;
import java.util.List;
import com.wpcase.model.WPCaseVO;

public interface WPCaseDAO_Interface {

	public void insert(WPCaseVO WPCaseVO);
    public void delete(String wed_photo_case_no);
    public List<WPCaseVO> getAll();
	public WPCaseVO getOneWPno(String wed_photo_case_no);
	public void updateWPCase(WPCaseVO WPCaseVO);

}
