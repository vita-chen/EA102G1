package com.car.model;

import com.carPic.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarService {

	private CarDAO_interface carDAO;
	private CarPicDAO_interface carPicDAO;

	public CarService() {
		carDAO = new CarDAO();
		carPicDAO = new CarPicDAO();
	}

	/********************* function for Car *********************/

	public void insertCar(CarVO carVO) {
		carDAO.insertCar(carVO);
	}

	public void updateCar(CarVO carVO) {
		carDAO.updateCar(carVO);
	}

	public void deleteCar(CarVO carVO) {
		carDAO.deleteCar(carVO);
	}

	public void getOneCar(CarVO carVO) {
		carDAO.getOneCar(carVO);
	}

	// for listAllCar.jsp使用(以VenderId來filter出該廠商的禮車一覽)
	public List<CarVO> getAllByVenderId(CarVO carVO) {
		return carDAO.getAllByVenderId(carVO);
	}

	// for 禮車列表頁使用(撈出所有禮車(不分廠商))
	public List<CarVO> getAllCar() {
		return carDAO.getAllCar();
	}

	public Map<String, CarVO> getAllCarMap() {
		Map<String, CarVO> allCarMap = new HashMap<String, CarVO>();
		for (CarVO carVO : carDAO.getAllCar()) {
			allCarMap.put(carVO.getCid(), carVO);
		}
		return allCarMap;
	}

	/********************* function for CarPic **********************/
	public void insertCarPic(CarPicVO carPicVO) {
		carPicDAO.insertCarPic(carPicVO);
	}

	public void deleteCarPic(CarPicVO carPicVO) {
		carPicDAO.deleteCarPic(carPicVO);
	}

	public List<CarPicVO> getAllCarPic() {
		return carPicDAO.getAllCarPic(); // 因為沒有where的條件篩選，所以不需要傳物件(「CarPicVO carPicVO」)
	}

	public void getOneCarPic(CarPicVO carPicVO) {
		carPicDAO.getOneCarPic(carPicVO);
	}

	public List<CarPicVO> getAllCarPicByCID(CarPicVO carPicVO) {
		return carPicDAO.getAllCarPicByCID(carPicVO);
	}

	public Map<String, CarPicVO> getAllCarPicMap() {
		Map<String, CarPicVO> allCarPicMap = new HashMap<String, CarPicVO>();
		for (CarPicVO carPicVO : carPicDAO.getAllCarPic()) {
			String key = carPicVO.getCid();
			if (allCarPicMap.get(key) == null) {
				allCarPicMap.put(key, carPicVO);
			}
		}
		return allCarPicMap;
	}

} // end of CarService
