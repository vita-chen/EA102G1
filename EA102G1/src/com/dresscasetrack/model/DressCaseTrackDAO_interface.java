package com.dresscasetrack.model;

import java.util.List;

public interface DressCaseTrackDAO_interface {
	
	public void insert(DressCaseTrackVO dctVO);
	public void delete(String drcase_id,String membre_id);
	
	public List<DressCaseTrackVO> findByMembre(String membre_id);
	public List<DressCaseTrackVO> getAll();
	
}
