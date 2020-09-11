package com.forum_mes.model;

import java.sql.Timestamp;
import java.util.List;

public class MesService {
	
	private MesDAO_interface dao;
	
	public MesService() {
		dao = new MesDAO();
	}
	
	public MesVO addMes(String membre_id, String forum_id, 
			String mes_text, Timestamp mes_addtime) {
		
		MesVO mesVO = new MesVO();
		
		mesVO.setMembre_id(membre_id);
		mesVO.setForum_id(forum_id);
		mesVO.setMes_text(mes_text);
		mesVO.setMes_addtime(mes_addtime);
		dao.insert(mesVO);
		
		return mesVO;
	}
	
	public MesVO updateMes(String forum_mes_id,String membre_id, String forum_id, 
			String mes_text, Timestamp mes_addtime) {
		
		MesVO mesVO = new MesVO();
		
		mesVO.setForum_mes_id(forum_mes_id);
		mesVO.setMembre_id(membre_id);
		mesVO.setForum_id(forum_id);
		mesVO.setMes_text(mes_text);
		mesVO.setMes_addtime(mes_addtime);
		dao.update(mesVO);
		
		return mesVO;
	}
	
	public void deleteMes(String forum_mes_id) {
		dao.delete(forum_mes_id);
	}
	
	public MesVO getOneMes(String forum_mes_id) {
		return dao.findByPrimaryKey(forum_mes_id);	
	}
	
	public List<MesVO> getOneByForum_id(String forum_id) {
		return dao.getMesByForum(forum_id);
	}

}
