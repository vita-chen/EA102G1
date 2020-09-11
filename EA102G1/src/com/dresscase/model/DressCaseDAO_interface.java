package com.dresscase.model;

import java.util.*;

public interface DressCaseDAO_interface {
          public void insert(DressCaseVO dressCaseVO);
          public void update(DressCaseVO dressCaseVO);
          
          public void delete(String drcase_id);
          public DressCaseVO findByPrimaryKey(String drcase_id);
          public List<DressCaseVO> findByVenID(String vender_id);
          public List<DressCaseVO> findByDrNa(String drCase_na);
          public List<DressCaseVO> getAll();
          
}
