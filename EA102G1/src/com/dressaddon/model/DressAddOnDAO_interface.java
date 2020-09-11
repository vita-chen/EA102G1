package com.dressaddon.model;

import java.util.*;

public interface DressAddOnDAO_interface {
          public void insert(DressAddOnVO daoVO);
          public void update(DressAddOnVO daoVO);
          
          public DressAddOnVO findByPrimaryKey(String drAddOn_id);
          public List<DressAddOnVO> findByVender(String vender_id);
          public List<DressAddOnVO> getAll();
          
}
