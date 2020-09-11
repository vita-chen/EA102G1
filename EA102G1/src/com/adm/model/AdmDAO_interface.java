package com.adm.model;

import java.util.List;


public interface AdmDAO_interface {
	
	public void insert(AdmVO admVO); //新增
	public void update(AdmVO admVO); //修改
	public void delete(AdmVO admVO); //刪除
    public AdmVO findByPrimaryKey(String adm_id); //查單個
    public AdmVO getOneByAdm(String adm_account); //查帳號內密碼(登入用)
    public List<String> getAlladm_account(); //查全部管理員帳號(登入用)

    public List<AdmVO> getAll(); //查全部

}
