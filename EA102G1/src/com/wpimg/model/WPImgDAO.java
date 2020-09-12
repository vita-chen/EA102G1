package com.wpimg.model;

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

import com.wpcollect.model.WPCollectVO;

public class WPImgDAO implements WPImgDAO_Interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			System.out.println("連線成功!");
			pstmt.setString(1, WPImgVO.getWed_photo_case_no());
			pstmt.setBytes(2, WPImgVO.getWed_photo_img());
			pstmt.executeUpdate();
			System.out.println("新增成功!");
		} catch (SQLException e) {
			e.printStackTrace();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DEL_IMGNO);
			System.out.println("連線成功!");
			pstmt.setString(1, WPImgVO.getWed_photo_imgno());
			pstmt.executeUpdate();
			System.out.println("刪除成功!");
		} catch (SQLException e) {
			e.printStackTrace();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEL_IMGNO);			
			pstmt.setString(1, wed_photo_case_no);			
			pstmt.executeUpdate();	
			
			rs = pstmt.executeQuery();
			while (rs.next()) {				
				WPImgVO wpimgvo = new WPImgVO();
				wpimgvo.setWed_photo_imgno(rs.getString("wed_photo_imgno"));
//				System.out.println(rs.getString("wed_photo_imgno"));
				wpimgvo.setWed_photo_img(rs.getBytes("wed_photo_img"));
				list.add(wpimgvo); // Store the row in the list
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
//		System.out.println("WPImgDAO 圖片 查詢成功!");
		return list;
	}

	@Override
	public void delImg(String[] wp_imgs_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DEL_IMGNO);
			for(String imgno : wp_imgs_no) {
				pstmt.setString(1, imgno);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

}
