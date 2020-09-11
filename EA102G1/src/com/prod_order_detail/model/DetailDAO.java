package com.prod_order_detail.model;

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

public class DetailDAO implements DetailDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private final static String INSERT = "insert into order_detail(order_no, prod_no, order_qty)"
			+"values(?, ?, ?)";
	private final static String FIND_BY_KEY="select * from order_detail where order_no = ?";
	private final static String DELETE = "delete from order_detail where order_no = ? and prod_no = ?";
	private final static String GET_ONE_DETAIL="select * from order_detail where prod_no = ? and order_no =?";
	public void insert(DetailVO detailvo) {
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(INSERT)) {
				pstmt.setString(1, detailvo.getOrder_no());
				pstmt.setString(2, detailvo.getProd_no());
				pstmt.setInt(3, detailvo.getOrder_qty());
				
				pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		
	}

	@Override
	public List<DetailVO> findByKey(String order_no) {
		List<DetailVO> detailList = new ArrayList<DetailVO>();
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(FIND_BY_KEY)) {
			  pstmt.setString(1, order_no);
			  ResultSet rs  = pstmt.executeQuery();
			  while (rs.next()) {
				  DetailVO detailvo  = new DetailVO();
				  detailvo.setOrder_no(rs.getString("order_no"));
				  detailvo.setProd_no(rs.getString("prod_no"));
				  detailvo.setOrder_qty(rs.getInt("order_qty"));
				  detailList.add(detailvo);
			  }
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return detailList;
	}

	@Override
	public void delete(String order_no, String prod_no) {
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(DELETE)) {
				pstmt.setString(1, order_no);
				pstmt.setString(2, prod_no);
				
				pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		
	}

	@Override
	public DetailVO getOneDetail(String prod_no, String order_no) {
		DetailVO detailvo = new DetailVO();
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ONE_DETAIL)) {
				pstmt.setString(1, prod_no);
				pstmt.setString(2, order_no);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					detailvo.setOrder_no(order_no);
					detailvo.setProd_no(prod_no);
					detailvo.setOrder_qty(rs.getInt("order_qty"));
				}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return detailvo;
	}

}
