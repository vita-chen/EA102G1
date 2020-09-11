package com.dressorder.model;

import java.util.*;

public interface DressOrderDAO_interface {
          public void insert(DressOrderVO doVO);
          public void update(DressOrderVO doVO);
          
          public DressOrderVO findByPrimaryKey(String drorder_id);
          public List<DressOrderVO> getAll();
          public DressOrderVO findLatestOrder(String membre_id);
          public List<DressOrderVO> findByMembre(String membre_id);
          public List<DressOrderVO> findByVender(String vender_id);
          
}
