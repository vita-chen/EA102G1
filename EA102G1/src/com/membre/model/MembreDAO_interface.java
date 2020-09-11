package com.membre.model;

import java.util.List;

public interface MembreDAO_interface {
	public void insert(MembreVO membrevo);
	public MembreVO getOneByAccount(String compte);
	public MembreVO getOneById(String membre_id);
	public void update(MembreVO membrevo);
	public void delete(String membre_id);
	public List<MembreVO> getAll();
	public MembreVO getSeller(String order_no);
}
