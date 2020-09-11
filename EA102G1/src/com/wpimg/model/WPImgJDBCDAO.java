package com.wpimg.model;

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

import com.wpcollect.model.WPCollectVO;

public class WPImgJDBCDAO implements WPImgDAO_Interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G1";
	String passwd = "123456";

	private static final String INSERT_STMT = "insert into wed_photo_img(wed_photo_imgno, wed_photo_case_no, wed_photo_img) values('WPP' || lpad(WPP_SEQ.NEXTVAL, 3, '0'), ?, ?) ";
	// 從方案編號查全部圖片編號
	private static final String SEL_IMGNO = "select * from wed_photo_img where wed_photo_case_no = ?";
	// 刪除指定圖片編號之圖片
	private static final String DEL_IMGNO = "delete from wed_photo_img where wed_photo_imgno = ?";

	@Override
	public void addImg(WPImgVO WPImgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			System.out.println("連線成功!");
			pstmt.setString(1, WPImgVO.getWed_photo_case_no());
			pstmt.setBytes(2, WPImgVO.getWed_photo_img());
			pstmt.executeUpdate();
			System.out.println("新增成功!");
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

	@Override
	public void delImg(WPImgVO WPImgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DEL_IMGNO);
			System.out.println("連線成功!");
			pstmt.setString(1, WPImgVO.getWed_photo_imgno());
			pstmt.executeUpdate();
			System.out.println("刪除成功!");
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

	@Override
	public List<WPImgVO> selImg(String wed_photo_case_no) {
		List<WPImgVO> list = new ArrayList<WPImgVO>();	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SEL_IMGNO);
			System.out.println("連線成功!");
			pstmt.setString(1, wed_photo_case_no);			
			pstmt.executeUpdate();				
			rs = pstmt.executeQuery();
			
			while (rs.next()) {				
				WPImgVO wpimgvo = new WPImgVO();
				wpimgvo.setWed_photo_imgno(rs.getString("wed_photo_imgno"));
//				System.out.println(rs.getString("wed_photo_imgno"));				
				list.add(wpimgvo); // Store the row in the list
			}
			System.out.println("查詢成功!");
		} catch (SQLException e) {
			e.printStackTrace();
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
		return list;
	}
	public static void main(String args[]) {
		//查詢 用方案編號查全部圖片編號
		WPImgJDBCDAO dao = new WPImgJDBCDAO();
		List<WPImgVO> wpimg = dao.selImg("WPC001");
		System.out.println(wpimg.get(0).getWed_photo_imgno());
		//刪除 丟圖片編號		
//		WPImgJDBCDAO dao = new WPImgJDBCDAO();
//		WPImgVO vo = new WPImgVO();
//		vo.setWed_photo_imgno("WPP004");
//		dao.delImg(vo);
	}

}
