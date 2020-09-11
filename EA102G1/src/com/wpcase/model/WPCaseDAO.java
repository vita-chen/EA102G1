package com.wpcase.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class WPCaseDAO implements WPCaseDAO_Interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"INSERT INTO WED_PHOTO_CASE (WED_PHOTO_CASE_NO,VENDER_ID,WED_PHOTO_NAME,WED_PHOTO_INTRO,WED_PHOTO_PRICE,WED_PHOTO_ADDTIME,WED_PHOTO_STATUS) VALUES ('WPC' || lpad(WPC_SEQ.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL = 
			"SELECT * FROM WED_PHOTO_CASE order by WED_PHOTO_CASE_NO";
	private static final String DELETE = 
			"DELETE FROM WED_PHOTO_CASE where WED_PHOTO_CASE_NO = ?";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM WED_PHOTO_CASE where WED_PHOTO_CASE_NO = ?";	
	private static final String UPDATE = 
			"UPDATE WED_PHOTO_CASE set WED_PHOTO_NAME= ?, WED_PHOTO_INTRO= ?, WED_PHOTO_STATUS= ?, WED_PHOTO_PRICE= ? where WED_PHOTO_CASE_NO= ?";
	
	private static final String SEL_IMGNO = "select wed_photo_imgno from wed_photo_img where wed_photo_case_no = ?";	
	private static final String DEL_IMGNO = "delete from wed_photo_img where wed_photo_imgno = ?";
	
	@Override
	public void insert(WPCaseVO WPCaseVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			String[] cols = { "WED_PHOTO_CASE_NO" }; // 或 int cols[] = {1};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			pstmt.setString(1, WPCaseVO.getVender_id());
			pstmt.setString(2, WPCaseVO.getWed_photo_name());
			pstmt.setString(3, WPCaseVO.getWed_photo_intro());
			pstmt.setInt(4, WPCaseVO.getWed_photo_price());
			pstmt.setTimestamp(5, WPCaseVO.getWed_photo_addtime());
			pstmt.setInt(6, WPCaseVO.getWed_photo_status());
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						String key = rs.getString(i);
						WPCaseVO.setWed_photo_case_no(key);
						System.out.println("自增主鍵值 = " + key + "(剛新增成功的方案編號)");
					}
				} while (rs.next());
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}

			rs.close();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	}
	@Override
	public List<WPCaseVO> getAll() {		
		List<WPCaseVO> list = new ArrayList<WPCaseVO>();		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(GET_ALL);
			
			while (rs.next()) {				
				WPCaseVO WPCaseVO = new WPCaseVO();
				WPCaseVO.setWed_photo_case_no(rs.getString("wed_photo_case_no"));
				WPCaseVO.setVender_id(rs.getString("vender_id"));
				WPCaseVO.setWed_photo_name(rs.getString("wed_photo_name"));
				WPCaseVO.setWed_photo_intro(rs.getString("wed_photo_intro"));
				WPCaseVO.setWed_photo_price(rs.getInt("wed_photo_price"));				
				WPCaseVO.setWed_photo_addtime(rs.getTimestamp("wed_photo_addtime"));
				WPCaseVO.setWed_photo_status(rs.getInt("wed_photo_status"));
				list.add(WPCaseVO); // Store the row in the list
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
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
	}
	public void delete(String wed_photo_case_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEL_IMGNO);	
			pstmt.setString(1, wed_photo_case_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {				
				String imgno = rs.getString("wed_photo_imgno");
				pstmt = con.prepareStatement(DEL_IMGNO);
				pstmt.setString(1, imgno);
				pstmt.executeUpdate();
			}
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, wed_photo_case_no);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	}
	@Override
	public WPCaseVO getOneWPno(String wed_photo_case_no) {
		WPCaseVO WPCaseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, wed_photo_case_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {				
				WPCaseVO = new WPCaseVO();
				WPCaseVO.setWed_photo_case_no(rs.getString("wed_photo_case_no"));
				WPCaseVO.setVender_id(rs.getString("vender_id"));
				WPCaseVO.setWed_photo_name(rs.getString("wed_photo_name"));
				WPCaseVO.setWed_photo_intro(rs.getString("wed_photo_intro"));
				WPCaseVO.setWed_photo_price(rs.getInt("wed_photo_price"));
				WPCaseVO.setWed_photo_addtime(rs.getTimestamp("wed_photo_addtime"));
				WPCaseVO.setWed_photo_status(rs.getInt("wed_photo_status"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return WPCaseVO;
	}
	@Override
	public void updateWPCase(WPCaseVO WPCaseVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, WPCaseVO.getWed_photo_name());
			pstmt.setString(2, WPCaseVO.getWed_photo_intro());
			pstmt.setInt(3, WPCaseVO.getWed_photo_status());
			pstmt.setInt(4, WPCaseVO.getWed_photo_price());			
			pstmt.setString(5, WPCaseVO.getWed_photo_case_no());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	}

}
