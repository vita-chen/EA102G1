package com.dressorderdetail.model;

import java.util.*;

public interface DressOrderDetailDAO_interface {
          public void insert(DressOrderDetailVO dressCaseVO);
          public void update(DressOrderDetailVO dressCaseVO);
          
          public DressOrderDetailVO findByPrimaryKey(String drde_id);
          public DressOrderDetailVO findByDrcaseAndOrder(String drcase_id,String membre_id);
          public List<DressOrderDetailVO> findByOrder(String drord_id);
          public List<DressOrderDetailVO> getAll();
          
}
