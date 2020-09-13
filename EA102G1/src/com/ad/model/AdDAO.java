package com.ad.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ad.model.AdVO;

public class AdDAO implements AdDAO_interface {

	private static DataSource ds = null;
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G1";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO AD (ad_id, ad_pic, ad_detail, ad_start_time, ad_end_time) "
			+ "VALUES('AD'|| LPAD(TO_CHAR(SEQ_AD_ID.NEXTVAL),3 ,'0'), ?,?,"
			+ "TO_DATE('2020-09-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), "
			+ "TO_DATE('2020-09-30 00:00:00', 'yyyy-mm-dd hh24:mi:ss'))";
	private static final String GET_ALL_STMT = "SELECT * FROM AD order by ad_id";
	private static final String GET_ONE_STMT = "SELECT * FROM AD where ad_id = ?";
	private static final String UPDATE = "UPDATE AD set ad_pic=?, ad_detail=? where ad_id = ?";

//	public static void main(String[] args) {
//
//		AdDAO dao = new AdDAO();
//
//		// INSERT
//		AdVO adVO1 = new AdVO();
//		adVO1.setAd_id("AD500");
//		adVO1.setAd_pic(null);
//		adVO1.setAd_detail("五百五百");
//		adVO1.setAd_start_time(null);
//		adVO1.setAd_end_time(null);
//		
//		dao.insert(adVO1);
//		System.out.println("=====================");
//
//		// 查全部
//		List<AdVO> list = dao.getAll();
//		for (AdVO ad : list) {
//			System.out.print(ad.getAd_id() + ",");
//			System.out.print(ad.getAd_detail() + ",");
//			System.out.print(ad.getAd_start_time() + ",");
//			System.out.print(ad.getAd_end_time() + ",");
//			System.out.println();
//		}
//
//	}

	@Override
	public void insert(AdVO adVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setBytes(1, adVO.getAd_pic());
			pstmt.setString(2, adVO.getAd_detail());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(AdVO adVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setBytes(1, adVO.getAd_pic());
			pstmt.setString(2, adVO.getAd_detail());
			pstmt.setString(3, adVO.getAd_id());

			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	}

	@Override
	public AdVO findByPrimaryKey(String ad_id) {

		AdVO adVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ad_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				adVO = new AdVO();
				adVO.setAd_id(rs.getString("ad_id"));
				adVO.setAd_pic(rs.getBytes("ad_pic"));
				adVO.setAd_detail(rs.getString("ad_detail"));
				adVO.setAd_start_time(rs.getTimestamp("ad_start_time"));
				adVO.setAd_end_time(rs.getTimestamp("ad_end_time"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

		return adVO;
	}

	@Override
	public List<AdVO> getAll() {
		List<AdVO> list = new ArrayList<AdVO>();
		AdVO adVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// AdVO 銋迂� Domain objects
				adVO = new AdVO();
				adVO.setAd_id(rs.getString("ad_id"));
				adVO.setAd_detail(rs.getString("ad_detail"));
				adVO.setAd_start_time(rs.getTimestamp("ad_start_time"));
				adVO.setAd_end_time(rs.getTimestamp("ad_end_time"));

				list.add(adVO); // Store the row in the list
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	}

}
