package com.dressaddorder.model;

import java.util.*;

public interface DressAddOrderDAO_interface {
          public void insert(DressAddOrderVO dressAddOrdervo);
          public List <DressAddOrderVO>findByDrde_id(String drde_id);
          public List<DressAddOrderVO> getAll();
          
}
