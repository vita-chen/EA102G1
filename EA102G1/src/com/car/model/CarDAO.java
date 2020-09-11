package com.car.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CarDAO implements CarDAO_interface {

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

	private static final String INSERT_CAR_STMT = "INSERT INTO CAR (CID, VENDER_ID, CBRAND, CMODEL, CINTRO, CPRICE, CADDTIME, CSTATUS) "
			+ "VALUES ('WCC'|| LPAD(TO_CHAR(CAR_SEQ.NEXTVAL),3,'0'),?, ?, ?, ?, ?, CURRENT_TIMESTAMP, '0' )";
	private static final String GET_INSERT_CID_STMT = "SELECT 'WCC'|| LPAD(TO_CHAR(CAR_SEQ.CURRVAL),3,'0') FROM DUAL";
	private static final String GET_ALL_BY_VENDER_ID_STMT = "SELECT * FROM CAR WHERE VENDER_ID = ? ORDER BY CID"; // 讓廠商可以從資料庫撈出自己的禮車一覽(所以需要VENDER_ID來filter)
	private static final String GET_ONE_CAR_STMT = "SELECT VENDER_ID, CBRAND, CMODEL, CINTRO, CPRICE, CADDTIME, CSTATUS FROM CAR WHERE CID = ?";
	private static final String DELETE_CAR_STMT = "DELETE FROM CAR WHERE CID = ?";
	private static final String UPDATE_CAR_STMT = "UPDATE CAR SET CBRAND=?, CMODEL=?, CINTRO=?, CPRICE=?, CSTATUS=? WHERE CID = ?";
	private static final String GET_ALL_CAR_STMT = "SELECT * FROM CAR ORDER BY CID";
	/*****測試SQL指令對不對****/
	//	private static final String UPDATE = "UPDATE CAR SET CBRAND='BENZ', CMODEL='E200', CINTRO='賓士', CPRICE=666, CSTATUS='1' WHERE CID = 'WCC001'";
	
	
	/*************************getAllCar(不分廠商)************************/
	@Override
	public List<CarVO> getAllCar() {
		List<CarVO> list = new ArrayList<CarVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_CAR_STMT);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CarVO carVO1 = new CarVO();
				carVO1.setCid(rs.getString("cid"));
				carVO1.setVender_id(rs.getString("vender_id"));
				carVO1.setCbrand(rs.getString("cbrand"));
				carVO1.setCmodel(rs.getString("cmodel"));
				carVO1.setCprice(rs.getInt("cprice"));
				list.add(carVO1);
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
	}// end of getAllCar()

	
	
	

	/*************************insertCar************************/
	
	@Override
	public void insertCar(CarVO carVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_CAR_STMT);

			pstmt.setString(1, carVO.getVender_id());
			pstmt.setString(2, carVO.getCbrand());
			pstmt.setString(3, carVO.getCmodel());
			pstmt.setString(4, carVO.getCintro());
			pstmt.setInt(5, carVO.getCprice());

			/*****測試有沒有進資料庫****/
//			pstmt.setString(1, "V001");
//			pstmt.setString(2, "特斯拉");
//			pstmt.setString(3, "Model3");
//			pstmt.setString(4, "推薦");
//			pstmt.setInt(5, 10000);
			/****測試End*****/
			
			pstmt.executeUpdate();
			
			// 取得此次新增 CID
			pstmt = con.prepareStatement(GET_INSERT_CID_STMT);
			rs = pstmt.executeQuery();
			if(rs.next())
				carVO.setCid(rs.getString(1));
			
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
	} // end of insertCar

	/*************************updateCar************************/
	@Override
	public void updateCar(CarVO carVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_CAR_STMT);

			pstmt.setString(1, carVO.getCbrand());
			pstmt.setString(2, carVO.getCmodel());
			pstmt.setString(3, carVO.getCintro());
			pstmt.setInt(4, carVO.getCprice());
			pstmt.setInt(5, carVO.getCstatus());
			pstmt.setString(6, carVO.getCid());
			
			/*****測試有沒有進資料庫****/
//			pstmt.setString(1, "BENZ");
//			pstmt.setString(2, "S350");
//			pstmt.setString(3, "賓士高階商務車型的代表");
//			pstmt.setInt(4, 55);
//			pstmt.setInt(5, 1);
//			pstmt.setString(6, "WCC002");
			/****測試End*****/

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
	} // end of updateCar

	/*************************deleteCar************************/
	@Override
	public void deleteCar(CarVO carVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_CAR_STMT);

			pstmt.setString(1, carVO.getCid());

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
	} // end of deleteCar
	

	
	/*************************getOneCar************************/
	@Override
	public void getOneCar(CarVO carVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_CAR_STMT);

			pstmt.setString(1, carVO.getCid());

			rs = pstmt.executeQuery();

			if (rs.next()) {

				carVO.setVender_id(rs.getString("vender_id"));
				carVO.setCbrand(rs.getString("cbrand"));
				carVO.setCmodel(rs.getString("cmodel"));
				carVO.setCintro(rs.getString("cintro"));
				carVO.setCprice(rs.getInt("cprice"));
				carVO.setCaddtime(rs.getTimestamp("caddtime"));
				carVO.setCstatus(rs.getInt("cstatus"));

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
	
	} // end of getOneCar

	/*************************getAllByVenderId************************/
	
	@Override
	public List<CarVO> getAllByVenderId(CarVO carVO) { // 此carVO裡存了VendorID
		List<CarVO> list = new ArrayList<CarVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
		
			pstmt = con.prepareStatement(GET_ALL_BY_VENDER_ID_STMT);
			pstmt.setString(1, carVO.getVender_id()); // 拿到一開始從service拿到的carVO裡存的VendorID，搭配SQL指令去DB做query
													  // 餵值給「GET_ALL_BY_VENDER_ID_STMT」的「VENDER_ID = ?」
			
			rs = pstmt.executeQuery();
			
			// 以下是將SQL下完，從DB中query出來的資料，一筆一筆存到新的carVO(即carVO1)，最後塞到list裡。
			while (rs.next()) {
				CarVO carVO1 = new CarVO(); //宣告一個新VO暫存從DB中query出來的資料，以便加到list中
				carVO1.setCid(rs.getString("cid"));
				carVO1.setVender_id(rs.getString("vender_id"));
				carVO1.setCbrand(rs.getString("cbrand"));
				carVO1.setCmodel(rs.getString("cmodel"));
				carVO1.setCintro(rs.getString("cintro"));
				carVO1.setCprice(rs.getInt("cprice"));
				carVO1.setCaddtime(rs.getTimestamp("caddtime"));
				carVO1.setCstatus(rs.getInt("cstatus"));
				list.add(carVO1); // Store the row in the list(最後要return回去service)
				
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


}// end of CarDAO
