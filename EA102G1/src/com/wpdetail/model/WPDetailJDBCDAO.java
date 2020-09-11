package com.wpdetail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.wporder.model.WPOrderVO;

public class WPDetailJDBCDAO implements WPDetailDAO_Interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G1";
	String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO wed_photo_order_detail VALUES(?, ?)";
	private static final String DELETE = "DELETE FROM wed_photo_order_detail WHERE wed_photo_order_no = ?";
	private static final String SELECT = "SELECT * FROM wed_photo_order_detail";
	
	@Override
	public void insert(WPDetailVO WPDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);			
			pstmt.setString(1, WPDetailVO.getWed_photo_order_no());
			pstmt.setString(2, WPDetailVO.getWed_photo_case_no());
			pstmt.executeUpdate();
			System.out.println("WPDetailDAO : 新增訂單明細成功!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void delete(WPDetailVO WPDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, WPDetailVO.getWed_photo_order_no());
			pstmt.executeUpdate();
			System.out.println("WPDetailDAO : 刪除訂單明細成功!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public List<WPDetailVO> getAll() {
		List<WPDetailVO> list = new ArrayList<WPDetailVO>();		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			stmt = con.createStatement();
			System.out.println("WPCollectJDBCDAO 連線成功!");			
			rs = stmt.executeQuery(SELECT);
			System.out.println("WPCollectJDBCDAO 查詢成功!");
			
			while (rs.next()) {				
				WPDetailVO wpdetailvo = new WPDetailVO();
				wpdetailvo.setWed_photo_order_no(rs.getString("wed_photo_order_no"));
				wpdetailvo.setWed_photo_case_no(rs.getString("wed_photo_case_no"));
				list.add(wpdetailvo);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public static void main(String args[]) {
		
		
		WPDetailJDBCDAO dao = new WPDetailJDBCDAO();
		//新增		
//		WPDetailVO vo = new WPDetailVO();
//		vo.setWed_photo_order_no("WPO044");
//		vo.setWed_photo_case_no("WPC014");
//		dao.insert(vo);
		
		//刪除
//		WPDetailJDBCDAO dao = new WPDetailJDBCDAO();
//		WPDetailVO vo = new WPDetailVO();
//		vo.setWed_photo_order_no("WPO049");
//		dao.delete(vo);
		
		//查詢
		List<WPDetailVO>list = dao.getAll();
		for (WPDetailVO vo : list) {
			System.out.println(vo.getWed_photo_order_no() + " " + vo.getWed_photo_case_no());
			
		}
	}	
}
