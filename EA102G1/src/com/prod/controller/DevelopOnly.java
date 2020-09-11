package com.prod.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DevelopOnly {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "EA102G1";
	private static final String PASSWORD = "123456";
	private static final String SQL = "insert into prod_pic values (pic_seq.nextval,?,?)";
	private static final String MEMBRESQL="update membre set photo = ? where membre_id = ?";
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} 
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL);
			
			for (int i  = 1 ; i <= 3; i++) {
				pstmt.setString(1,"P001");
				byte[] pic = getPictureByteArray("WebContent/img/prod_img/prada/prada_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			
			for (int i = 1; i <=2; i++) {
				pstmt.setString(1,"P002");
				byte[] pic = getPictureByteArray("WebContent/img/prod_img/Tshirt/Tshirt_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			for (int i = 1; i<=2; i++) {
				pstmt.setString(1,"P003");
				byte[] pic = getPictureByteArray("WebContent/img/prod_img/book/book_"+i+".jpeg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			for (int i = 1; i<=3; i++) {
				pstmt.setString(1,"P004");
				byte[] pic = getPictureByteArray("WebContent/img/prod_img/airpods/airpods_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			for (int i = 1; i<=3; i++) {
				pstmt.setString(1,"P005");
				byte[] pic = getPictureByteArray("WebContent/img/prod_img/shoes/shoes_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			for (int i = 1; i<=4; i++) {
				pstmt.setString(1,"P006");
				byte[] pic = getPictureByteArray("WebContent/img/prod_img/shoes_2/shoes_2_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			for (int i = 1; i<=2; i++) {
				pstmt.setString(1,"P007");
				byte[] pic = getPictureByteArray("WebContent/img/prod_img/shoes_3/shoes_3_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			for (int i = 1; i<=3; i++) {
				pstmt.setString(1,"P008");
				byte[] pic = getPictureByteArray("WebContent/img/prod_img/skirt/skirt_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			for (int i = 1; i<=2; i++) {
				pstmt.setString(1,"P009");
				byte[] pic = getPictureByteArray("WebContent/img/prod_img/top/top_"+i+".jpg");
				pstmt.setBytes(2, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			
			
			
			pstmt = con.prepareStatement(MEMBRESQL);
			for (int i = 1; i < 4; i++) {
				byte[] pic = getPictureByteArray("WebContent/img/membre_img/membre_"+i+".jpg");
				pstmt.setBytes(1,pic);
				pstmt.setString(2, "M00"+i);
				pstmt.executeUpdate();
			}
			
		} catch (SQLException se) {
			System.out.println(se);
		} catch (IOException ie) {
			System.out.println(ie);
		} finally {
			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
		}
		
		
	}

	

	// 使用byte[]方式
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
}
