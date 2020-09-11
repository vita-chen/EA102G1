package com.carExt.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;




public class CarExtDAO implements CarExtDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO CAR_EXT(CEXT_ID, VENDER_ID, CEXT_CAT_ID, CEXT_NAME,CEXT_PRICE, CEXT_PIC, CEXT_ADDTIME, CEXT_STATUS) "
			+  "VALUES ('WCA'|| LPAD(TO_CHAR(CAR_EXT_SEQ.NEXTVAL),3,'0') , ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, '0' )";
	private static final String GET_ALL_BY_VENDER_ID_STMT = "SELECT CEXT_ID, VENDER_ID, CEXT_CAT_ID, CEXT_NAME, CEXT_PRICE, CEXT_ADDTIME, CEXT_STATUS FROM CAR_EXT WHERE VENDER_ID = ? ORDER BY CEXT_ID";
	private static final String DELETE_STMT = "DELETE FROM CAR_EXT WHERE CEXT_ID = ?";
	private static final String UPDATE_STMT = "UPDATE CAR_EXT SET CEXT_NAME=?, CEXT_PRICE=?, CEXT_STATUS=? WHERE CEXT_ID = ?";  //CEXT_PIC先跳過
//	private static final String UPDATE = "UPDATE CAR_EXT SET CEXT_NAME=?, CEXT_PRICE=?, CEXT_PIC=?, CEXT_STATUS=? WHERE CEXT_ID = ?";  //CEXT_PIC先跳過
	private static final String GET_ONE_CEXTPIC_STMT = "SELECT CEXT_PIC FROM CAR_EXT WHERE CEXT_ID = ?";
	private static final String GET_ONE_CEXT_STMT = "SELECT CEXT_NAME,CEXT_PRICE FROM CAR_EXT WHERE CEXT_ID = ?";
	
	
	/*************************insertCarExt************************/
	@Override
	public void insert(CarExtVO carExtVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, carExtVO.getVender_id());
			pstmt.setInt(2, carExtVO.getCext_cat_id());
			pstmt.setString(3, carExtVO.getCext_name());
			pstmt.setInt(4, carExtVO.getCext_price());
			pstmt.setBytes(5, carExtVO.getCext_pic());	
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

	

	
	/*************************updateCarExt************************/
	@Override
	public void update(CarExtVO carExtVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, carExtVO.getCext_name());
			pstmt.setInt(2, carExtVO.getCext_price());
//			pstmt.setBytes(5, carExtVO.getCext_pic());
			pstmt.setInt(3, carExtVO.getCext_status());
			pstmt.setString(4, carExtVO.getCext_id());
//			
//			pstmt.setString(1, "車頭彩米奇米妮");
//			pstmt.setInt(2, 666);
////			pstmt.setBytes(5, carExtVO.getCext_pic());
//			pstmt.setInt(3, 1);
//			pstmt.setString(4, "WCA021");
			
			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	} // end of updateCarExt
	
	
	/*************************deleteCarExt************************/

	@Override
	public void delete(CarExtVO carExtVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, carExtVO.getCext_id());

			pstmt.executeUpdate();


			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		
	}// end of deleteCarExt

	
	/*************************getAllByVenderId************************/
	
	@Override
	public List<CarExtVO> getAllByVenderId(CarExtVO carExtVO) { // 此carExtVO裡存了VendorID
		List<CarExtVO> list = new ArrayList<CarExtVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
		
			pstmt = con.prepareStatement(GET_ALL_BY_VENDER_ID_STMT);
			pstmt.setString(1, carExtVO.getVender_id()); // 拿到一開始從service拿到的carExtVO裡存的VendorID，搭配SQL指令去DB做query
													  // 餵值給「GET_ALL_BY_VENDER_ID_STMT」的「VENDER_ID = ?」
			
			rs = pstmt.executeQuery();
			
			// 以下是將SQL下完，從DB中query出來的資料，一筆一筆存到新的CarExtVO(即carExtVO1)，最後塞到list裡。
			while (rs.next()) {
				CarExtVO carExtVO1 = new CarExtVO(); //宣告一個新VO暫存從DB中query出來的資料，以便加到list中
				carExtVO1.setCext_id(rs.getString("cext_id"));
				carExtVO1.setVender_id(rs.getString("vender_id"));
				carExtVO1.setCext_cat_id(rs.getInt("cext_cat_id"));
				carExtVO1.setCext_name(rs.getString("cext_name"));
				carExtVO1.setCext_price(rs.getInt("cext_price"));
				carExtVO1.setCext_addtime(rs.getTimestamp("cext_addtime"));
				carExtVO1.setCext_status(rs.getInt("cext_status"));
				list.add(carExtVO1); // Store the row in the list(最後要return回去service)
				
			}


			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	} // end of getAllByVenderId()
	
	/******************* getOneCExtPic(透過加購品編號取得該加購品的照片)********************/
	@Override
	public void getOneCExtPic(CarExtVO carExtVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_CEXTPIC_STMT);
			pstmt.setString(1, carExtVO.getCext_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				carExtVO.setCext_pic(rs.getBytes("cext_pic")); // 取照片，用setCpic方法塞值給原本沒有值的carPicVO，再return給CarService，最後丟給servlet去轉換圖片輸出
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
	}// end of getOneCarExtPic


	/******************* getOneCExt(透過加購品編號取得該加購品資料)********************/

	@Override
	public void getOneCExt(CarExtVO carExtVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_CEXT_STMT);

			pstmt.setString(1, carExtVO.getCext_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				carExtVO.setCext_name(rs.getString("cext_name"));
				carExtVO.setCext_price(rs.getInt("cext_price"));
			}
			

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		
	}// end of getOneCarExtPic

	


}// end of CarExtDAO
