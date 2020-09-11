package com.dresscasetrack.model;

import java.util.List;

public class DressCaseTrackService {

	private DressCaseTrackDAO_interface dctDAO;
	
	public DressCaseTrackService() {
		dctDAO = new DressCaseTrackDAO();
	}
	
	public DressCaseTrackVO addTrack(String drcase_id,String membre_id) {

		DressCaseTrackVO dctVO = new DressCaseTrackVO();

		dctVO.setDrcase_id(drcase_id);
		dctVO.setMembre_id(membre_id);
		dctDAO.insert(dctVO);
		return dctVO;
	}
	
	public void deleteTrack(String drcase_id,String membre_id) {
		dctDAO.delete(drcase_id, membre_id);
		
	}
	
	public List<DressCaseTrackVO> findByMembre(String membre_id) {
		return dctDAO.findByMembre(membre_id);
	}
	
	public List<DressCaseTrackVO> getAllTrack(){
		return dctDAO.getAll();
	}
	
	
}
