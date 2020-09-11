package com.prod_pic.model;

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

public class PicDAO implements PicDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "insert into prod_pic(pic_no, prod_no, pic) values(pic_seq.nextval, ?, ?) ";
	private static final String FIND_ONES_PIC = "select pic_no, pic from prod_pic where prod_no = ?";
	private static final String DELETE = "delete from prod_pic where pic_no = ?";

	public void insert(PicVO picvo) {
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(INSERT_STMT);) {

			pstmt.setString(1, picvo.getProd_no());
			pstmt.setBytes(2, picvo.getPic());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
	}

	public void delete(Integer picno) {
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(DELETE)) {
			pstmt.setInt(1, picno);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
	}

	public List<PicVO> findByKey(String prodNo) {
		List<PicVO> piclist = new ArrayList<PicVO>();
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(FIND_ONES_PIC);) {
			pstmt.setString(1, prodNo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PicVO pic = new PicVO();
				pic.setPic(rs.getBytes("pic"));
				pic.setPic_no(rs.getInt("pic_no"));
				pic.setProd_no(prodNo);
				piclist.add(pic);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return piclist;
	}

}
