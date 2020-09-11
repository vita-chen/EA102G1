package com.wpcollect.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.wpcase.model.WPCaseVO;

public class WPCollectDAO implements WPCollectDAO_Interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO wed_photo_collect VALUES( ?, ?)";
	private static final String DELETE = "DELETE FROM WED_PHOTO_COLLECT WHERE WED_PHOTO_CASE_NO = ? AND MEMBRE_ID = ? ";
	private static final String SELECT = "SELECT wed_photo_case_no FROM wed_photo_collect WHERE membre_id=? ";
	@Override
	public void addCollect(WPCollectVO WPCollectVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
//			System.out.println("WPCollectJDBCDAO 連線成功!");
			pstmt.setString(1, WPCollectVO.getWed_photo_case_no());
			pstmt.setString(2, WPCollectVO.getMembre_id());

			pstmt.executeUpdate();
//			System.out.println("WPCollectJDBCDAO 新增成功!");			
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
	public void disCollect(WPCollectVO WPCollectVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			System.out.println("WPCollectJDBCDAO 連線成功!");
			pstmt.setString(1, WPCollectVO.getWed_photo_case_no());
			pstmt.setString(2, WPCollectVO.getMembre_id());
			pstmt.executeUpdate();
			System.out.println("WPCollectJDBCDAO 刪除成功!");
			
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
	public List<WPCollectVO> selCollect(String membre_id) {
		List<WPCollectVO> list = new ArrayList<WPCollectVO>();		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT);
			System.out.println("WPCollectJDBCDAO 連線成功!");
			pstmt.setString(1, membre_id);
			
			pstmt.executeUpdate();
			System.out.println("WPCollectJDBCDAO 查詢成功!");
			rs = pstmt.executeQuery();
			while (rs.next()) {				
				WPCollectVO wpcollectvo = new WPCollectVO();
				wpcollectvo.setWed_photo_case_no(rs.getString("wed_photo_case_no"));				
				list.add(wpcollectvo);
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
		return list;
	}	
	
}
