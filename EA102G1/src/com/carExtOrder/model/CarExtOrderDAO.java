package com.carExtOrder.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class CarExtOrderDAO implements CarExtOrderDAO_interface {
	
	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_CAR_EXT_ORDER_STMT = "INSERT INTO CAR_EXT_ORDER(CEXT_ID, COD_ID)"+ "VALUES ( ?, ?)";	
	private static final String GET_ALL_CAR_EXT_STMT = "SELECT * FROM CAR_EXT_ORDER";
	
	
	
	/*************************getAllCarExt(把一筆訂單裡的所有加購品都query出來)************************/
	@Override
	public List<CarExtOrderVO> getAllCarExt() {
		List<CarExtOrderVO> list = new ArrayList<CarExtOrderVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_CAR_EXT_STMT);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CarExtOrderVO carExtOrderVO1 = new CarExtOrderVO();
				carExtOrderVO1.setCext_id(rs.getString("cext_id"));
				carExtOrderVO1.setCod_id(rs.getString("cod_id"));
				list.add(carExtOrderVO1);
			}
			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}// end of getAllCarExt
	
	/*******************insertCarExtOrder********************/

	@Override
	public void insertCarExtOrder(CarExtOrderVO carExtOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_CAR_EXT_ORDER_STMT);

			pstmt.setString(1, carExtOrderVO.getCext_id());
			pstmt.setString(2, carExtOrderVO.getCod_id());
			
//			System.out.println(carExtOrderVO.getCext_id());
//			System.out.println(carExtOrderVO.getCod_id());
			
//			pstmt.setString(1, "WCA014");
//			pstmt.setString(2, "WCO006");
			
			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	} // end of insertCarExtOrder





	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
} // end of CarExtOrderDAO
