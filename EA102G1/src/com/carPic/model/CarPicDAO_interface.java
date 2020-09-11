package com.carPic.model;

import java.util.List;

public interface CarPicDAO_interface {

	public void insertCarPic(CarPicVO carPicVO);

	public void deleteCarPic(CarPicVO carPicVO);

	public List<CarPicVO> getAllCarPic(); // 拿CPIC_ID而已(也就是不透過CID去取照片，因為如果有100台車，就得call資料庫100次。所以必須透過CPIC_ID直接去取所有的照片)

	public void getOneCarPic(CarPicVO carPicVO); // 獨立取每張照片(給CPIC_ID，要DB吐相應的照片)
											     // 因為在listAllCar中已經湊出特定圖片的路徑了，所以很明確地知道要取哪張照片，所以就明確指定CPIC_ID去拿出那張照片即可

	public List<CarPicVO> getAllCarPicByCID(CarPicVO carPicVO);
	
	
	

}
