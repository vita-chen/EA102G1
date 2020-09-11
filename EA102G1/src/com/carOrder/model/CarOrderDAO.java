package com.carOrder.model;

import java.util.*;
import java.sql.*;
import java.math.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CarOrderDAO implements CarOrderDAO_interface {

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

	private static final String INSERT_CAR_ORDER_STMT = "INSERT INTO CAR_ORDER(COD_ID, MEMBRE_ID, VENDER_ID, CID, COD_TIME, CFINAL_PRICE, CDEPOSIT, CRETURN_PAY, CNEED_DATE, CRETURN_DATE, CSTART, CDEST, COD_STATUS)"
			+ "VALUES ('WCO'|| LPAD(TO_CHAR(CAR_ORDER_SEQ.NEXTVAL),3,'0'),?, ?, ?,CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, 1)";
	private static final String GET_INSERT_COD_STMT = "SELECT 'WCO'|| LPAD(TO_CHAR(CAR_ORDER_SEQ.CURRVAL),3,'0') FROM DUAL";
	private static final String GET_ALL_BY_VENDER_ID_STMT = "SELECT * FROM CAR_ORDER WHERE VENDER_ID = ? ORDER BY COD_ID"; // 讓廠商可以從資料庫撈出自己的禮車訂單一覽(所以需要VENDER_ID來filter)
	private static final String GET_ALL_BY_MEMBER_ID_STMT = "SELECT * FROM CAR_ORDER WHERE MEMBRE_ID = ? ORDER BY COD_ID"; // 讓會員可以從資料庫撈出自己的禮車訂單一覽(所以需要MEMBRE_ID來filter)
	private static final String UPDATE_ORDER_STS_STMT = "UPDATE CAR_ORDER SET COD_STATUS= ? where COD_ID = ?";
	private static final String SUBMIT_REVIEW_STMT = "UPDATE CAR_ORDER SET CREV_STAR= ?, CREV_MSGS=? where COD_ID = ?";
	private static final String GET_CAR_AVG_STAR_STMT = "SELECT CID, AVG(CREV_STAR) FROM CAR_ORDER GROUP BY CID";
	
	
	
	
	/******************* 透過存在每筆訂單中的星數，來算出每台車的平均星數 ********************/
	@Override
	public List<CarOrderVO> getCarAvgStar() {
		List<CarOrderVO> list = new ArrayList<CarOrderVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_CAR_AVG_STAR_STMT);
			rs = pstmt.executeQuery();

			// 以下是將SQL下完，從DB中query出來的資料，一筆一筆存到carOrderVO，最後塞到list裡。(因為最開始沒有傳參數，所以可以直接宣告一個carOrderVO)
			while (rs.next()) {
				CarOrderVO carOrderVO = new CarOrderVO(); // 宣告一個VO暫存從DB中query出來的資料，以便加到list中
				
				carOrderVO.setCid(rs.getString("cid"));   // 禮車編號
				carOrderVO.setCrev_star(Math.round(rs.getFloat("avg(crev_star)")));    // 該台車的評價星數(四捨五入)

				list.add(carOrderVO); // Store the row in the list(最後要return回去service)

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
	} // end of getCarAvgStar()

	
	
	/******************* 更新訂單狀態 ********************/
	@Override
	public void updateCarOrderStatus(CarOrderVO carOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ORDER_STS_STMT);

			pstmt.setInt(1, carOrderVO.getCod_status());
			pstmt.setString(2, carOrderVO.getCod_id());

//			pstmt.setInt(1, 2);
//			pstmt.setString(2, "WCO004");

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

	}// end of updateCarOrderStatus()

	/******************* for客戶下訂 ********************/

	@Override
	public void insertCarOrder(CarOrderVO carOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_CAR_ORDER_STMT);

			pstmt.setString(1, carOrderVO.getMembre_id());
			pstmt.setString(2, carOrderVO.getVender_id());
			pstmt.setString(3, carOrderVO.getCid());
			pstmt.setInt(4, carOrderVO.getCfinal_price());
			pstmt.setInt(5, carOrderVO.getCdeposit());
			pstmt.setInt(6, carOrderVO.getCreturn_pay());
			pstmt.setDate(7, carOrderVO.getCneed_date());
			pstmt.setDate(8, carOrderVO.getCreturn_date());
			pstmt.setString(9, carOrderVO.getCstart());
			pstmt.setString(10, carOrderVO.getCdest());

//			pstmt.setString(1, "M001");
//			pstmt.setString(2, "V001");
//			pstmt.setString(3, "WCC022");
//			pstmt.setInt(4, 2000);
//			pstmt.setInt(5, 200);
//			pstmt.setInt(6, 200);
//			pstmt.setDate(7, java.sql.Date.valueOf("2020-08-15"));
//			pstmt.setDate(8, java.sql.Date.valueOf("2020-08-16"));
//			pstmt.setString(9, "台北");
//			pstmt.setString(10, "台南");

			pstmt.executeUpdate();

			// 取得此次新增 COD
			pstmt = con.prepareStatement(GET_INSERT_COD_STMT);
			rs = pstmt.executeQuery();
			if (rs.next())
				carOrderVO.setCod_id(rs.getString(1));

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

	} // end of insertCarOrder

	/************************* 查詢特定廠商之所有禮車訂單 ************************/

	@Override
	public List<CarOrderVO> getAllByVenderId(CarOrderVO carOrderVO) {
		List<CarOrderVO> list = new ArrayList<CarOrderVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_BY_VENDER_ID_STMT);
			pstmt.setString(1, carOrderVO.getVender_id()); // 拿到一開始從service拿到的carOrderVO裡存的VendorID，搭配SQL指令去DB做query
			// 餵值給「GET_ALL_BY_VENDER_ID_STMT」的「VENDER_ID = ?」

			rs = pstmt.executeQuery();

			// 以下是將SQL下完，從DB中query出來的資料，一筆一筆存到新的carOrderVO(即carOrderVO1)，最後塞到list裡。
			while (rs.next()) {
				CarOrderVO carOrderVO1 = new CarOrderVO(); // 宣告一個新VO暫存從DB中query出來的資料，以便加到list中
				carOrderVO1.setCid(rs.getString("cid")); 				 // 禮車編號
				carOrderVO1.setCod_time(rs.getTimestamp("cod_time")); 	 // 訂單成立時間
				carOrderVO1.setCod_id(rs.getString("cod_id")); 			 // 訂單編號
				carOrderVO1.setVender_id(rs.getString("vender_id")); 	 // 廠商編號
				carOrderVO1.setMembre_id(rs.getString("membre_id")); 	 // 會員編號
				carOrderVO1.setCfinal_price(rs.getInt("cfinal_price"));  // 最終價格
				carOrderVO1.setCdeposit(rs.getInt("cdeposit"));  		 // 禮車訂單訂金
				carOrderVO1.setCreturn_pay(rs.getInt("creturn_pay"));    // 禮車訂單押金
				carOrderVO1.setCod_status(rs.getInt("cod_status")); 	 // 禮車訂單狀態
				carOrderVO1.setCneed_date(rs.getDate("cneed_date")); 	 // 租車日
				carOrderVO1.setCreturn_date(rs.getDate("creturn_date")); // 租車日
				carOrderVO1.setCstart(rs.getString("cstart")); 			 // 起點
				carOrderVO1.setCdest(rs.getString("cdest"));             // 終點
				carOrderVO1.setCrev_star(rs.getInt("crev_star"));        // 評價星數
				carOrderVO1.setCrev_msgs(rs.getString("crev_msgs"));     // 評價內容

				list.add(carOrderVO1); // Store the row in the list(最後要return回去service)

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
	} // end of getAllByVenderId()
	
	
	/************************* 查詢特定會員之所有禮車訂單 ************************/	
	@Override
	public List<CarOrderVO> getAllByMemberId(CarOrderVO carOrderVO) {
		List<CarOrderVO> list = new ArrayList<CarOrderVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_BY_MEMBER_ID_STMT);
			pstmt.setString(1, carOrderVO.getMembre_id()); // 拿到一開始從service拿到的carOrderVO裡存的MemberID，搭配SQL指令去DB做query
			// 餵值給「GET_ALL_BY_MEMBER_ID_STMT」的「MEMBRE_ID = ?」

			rs = pstmt.executeQuery();

			// 以下是將SQL下完，從DB中query出來的資料，一筆一筆存到新的carOrderVO(即carOrderVO1)，最後塞到list裡。
			while (rs.next()) {
				CarOrderVO carOrderVO1 = new CarOrderVO(); // 宣告一個新VO暫存從DB中query出來的資料，以便加到list中
				carOrderVO1.setCid(rs.getString("cid")); 				 // 禮車編號
				carOrderVO1.setCod_time(rs.getTimestamp("cod_time")); 	 // 訂單成立時間
				carOrderVO1.setCod_id(rs.getString("cod_id")); 			 // 訂單編號
				carOrderVO1.setVender_id(rs.getString("vender_id")); 	 // 廠商編號
				carOrderVO1.setMembre_id(rs.getString("membre_id")); 	 // 會員編號
				carOrderVO1.setCfinal_price(rs.getInt("cfinal_price"));  // 最終價格
				carOrderVO1.setCdeposit(rs.getInt("cdeposit"));  		 // 禮車訂單訂金
				carOrderVO1.setCreturn_pay(rs.getInt("creturn_pay"));    // 禮車訂單押金
				carOrderVO1.setCstart(rs.getString("cstart")); 			 // 起點
				carOrderVO1.setCod_status(rs.getInt("cod_status")); 	 // 禮車訂單狀態
				carOrderVO1.setCneed_date(rs.getDate("cneed_date")); 	 // 租車日
				carOrderVO1.setCreturn_date(rs.getDate("creturn_date")); // 租車日
				carOrderVO1.setCstart(rs.getString("cstart")); 			 // 起點
				carOrderVO1.setCdest(rs.getString("cdest"));             // 終點
				carOrderVO1.setCrev_star(rs.getInt("crev_star"));        // 評價星數
				carOrderVO1.setCrev_msgs(rs.getString("crev_msgs"));     // 評價內容

				list.add(carOrderVO1); // Store the row in the list(最後要return回去service)

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
	}// end of getAllByMemberId()
	
	
	
	

	/************************* 會員提交評價星數&心得 ************************/
	@Override
	public void submitReview(CarOrderVO carOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SUBMIT_REVIEW_STMT);

			pstmt.setInt(1, carOrderVO.getCrev_star());
			pstmt.setString(2, carOrderVO.getCrev_msgs());
			pstmt.setString(3, carOrderVO.getCod_id());
			
//			pstmt.setString(1, carOrderVO.getCrev_msgs());
//			pstmt.setString(2, carOrderVO.getCod_id());
			
//			pstmt.setString(1, "大推");
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

	} // end of submitReview



}// end of CarOrderDAO
