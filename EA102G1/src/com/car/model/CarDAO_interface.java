package com.car.model;
import java.util.*;


public interface CarDAO_interface {
	public void insertCar(CarVO carVO);
	public void updateCar(CarVO carVO);
	public void deleteCar(CarVO carVO);
	public void getOneCar(CarVO carVO);
	public List<CarVO> getAllByVenderId(CarVO carVO);
	public List<CarVO> getAllCar();
}
