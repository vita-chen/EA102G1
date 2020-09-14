package com.vender.model;

import java.util.*;


public interface VenderDAO_interface {
	
	public void insert(VenderVO venderVO); //新增
	public void update(VenderVO venderVO); //修改
	public void update_vender_enable(VenderVO venderVO); //修改(is_enable狀態修改為0)
	public void delete(VenderVO venderVO); //刪除(is_vaild狀態修改為0)
    public VenderVO findByPrimaryKey(String vender_id); //查單個
    public VenderVO getOneByVender(String ven_account);
    public List<VenderVO> getAll(); //查全部
    public List<VenderVO> get_all_verification(); //查全部未驗證廠商
    public List<VenderVO> get_all_blockade(); //查全部已封鎖廠商
    public List<String> getAllVender();//查所有廠商帳號

    public void update_review(VenderVO vendervo);//修改評價數與總分數
    
    //  public List<EmpVO> getAll(Map<String, String[]> map); 


}
