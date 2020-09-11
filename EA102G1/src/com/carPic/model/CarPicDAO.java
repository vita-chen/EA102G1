package com.carPic.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CarPicDAO implements CarPicDAO_interface {
		
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
	
	private static final String INSERT_CARPIC_STMT = "INSERT INTO CAR_PIC(CPIC_ID, CID, C_PIC)"
			+ "VALUES ('WCP'|| LPAD(TO_CHAR(CAR_PIC_SEQ.NEXTVAL),3,'0'), ?, ?)";
	private static final String DELETE_CARPIC_STMT = "DELETE FROM CAR_PIC WHERE CID = ?"; //該禮車有幾張照片就會都刪掉(因為是用禮車編號來搜尋)
	private static final String GET_ALL_CARPIC_STMT = "SELECT CPIC_ID, CID FROM CAR_PIC ORDER BY CPIC_ID";
	private static final String GET_ONE_CARPIC_STMT = "SELECT C_PIC FROM CAR_PIC WHERE CPIC_ID = ?";
	private static final String GET_ALL_CARPIC_BY_CID_STMT ="SELECT CPIC_ID FROM CAR_PIC WHERE CID =?";// 透過禮車編號取出該禮車的所有圖片

	
	/*******************insertCarPic********************/
	@Override
	public void insertCarPic(CarPicVO carPicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_CARPIC_STMT);

			pstmt.setString(1, carPicVO.getCid());
			pstmt.setBytes(2, carPicVO.getCpic());
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
	}// end of insert

	
	/*******************deleteCarPic********************/
	@Override
	public void deleteCarPic(CarPicVO carPicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_CARPIC_STMT);
			pstmt.setString(1, carPicVO.getCid()); // 刪除所有該 CID 所有的圖片
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
	}// end of delete

	
	/******************* getAllCarPic(拼湊出圖片路徑即可)********************/
	@Override
	public List<CarPicVO> getAllCarPic() {
		List<CarPicVO> list = new ArrayList<CarPicVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_CARPIC_STMT);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CarPicVO carPicVO1 = new CarPicVO();
				carPicVO1.setCpic_id(rs.getString("cpic_id"));
				carPicVO1.setCid(rs.getString("cid"));
				list.add(carPicVO1);
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
	}// end of getAllCarPic()
	
	/******************* getOneCarPic********************/
	@Override
	public void getOneCarPic(CarPicVO carPicVO) {		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_CARPIC_STMT);
			pstmt.setString(1, carPicVO.getCpic_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				carPicVO.setCpic(rs.getBytes("c_pic")); // 取照片，用setCpic方法塞值給原本沒有值的carPicVO，再return給CarService，最後丟給servlet去轉換圖片輸出
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

	}// end of getOneCarPic

	/*******************getAllCarPicByCID********************/
	@Override
	public List<CarPicVO> getAllCarPicByCID(CarPicVO carPicVO) {
		List<CarPicVO> list = new ArrayList<CarPicVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_CARPIC_BY_CID_STMT);
			pstmt.setString(1,carPicVO.getCid());
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CarPicVO carPicVO1 = new CarPicVO();
				carPicVO1.setCpic_id(rs.getString("cpic_id"));
				list.add(carPicVO1);
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

	}// end of getAllCarPicByCID
	
	
}// end of CarPicDAO
