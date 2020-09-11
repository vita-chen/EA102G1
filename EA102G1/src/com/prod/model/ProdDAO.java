package com.prod.model;


import java.sql.Connection;
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

public class ProdDAO implements ProdDAO_interface{
	
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
			"insert into prod (prod_no, membre_id, type_no, price, prod_qty, prod_name, prod_status, sale_time)"
		+ "values('P'|| LPAD(TO_CHAR(PROD_SEQ.NEXTVAL),3,'0'), ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ONE = "select * from prod where prod_no = ?";
	private static final String GET_ALL_BY_ORDER_NO="select *" + 
			"from prod where prod_no in (select prod_no from order_detail where order_no = ?)";
	private static final String UPDATE = "update prod set  prod_name = ?, type_no = ?, price = ?, prod_qty = ?, prod_status = ? where prod_no=?";
	@Override
	public void insert(ProdVO prodVO) {
		int[] col = {1};
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(INSERT_STMT, col)){
			
			
			pstmt.setString(1, prodVO.getMembre_id());
			pstmt.setString(2, prodVO.getType_no());
			pstmt.setInt(3, prodVO.getPrice());
			pstmt.setInt(4, prodVO.getProd_qty());
			pstmt.setString(5, prodVO.getProd_name());
			pstmt.setString(6, prodVO.getProd_status());
			pstmt.setTimestamp(7, prodVO.getSale_time());
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				prodVO.setProd_no(rs.getString(1));
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		} 
		
	}
	@Override
	public ProdVO findByKey(String prodNo) {
		ProdVO prodvo = new ProdVO();
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(GET_ONE)) {
			pstmt.setString(1, prodNo);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				prodvo.setProd_no(prodNo);
				prodvo.setMembre_id(rs.getString("membre_id"));
				prodvo.setType_no(rs.getString("type_no"));
				prodvo.setPrice(rs.getInt("price"));
				prodvo.setProd_qty(rs.getInt("prod_qty"));
				prodvo.setProd_name(rs.getString("prod_name"));
				prodvo.setProd_status(rs.getString("prod_status"));
				prodvo.setSale_time(rs.getTimestamp("sale_time"));
			}

		} catch (SQLException se) {
				throw new RuntimeException("A database error occured" + se.getMessage());
		}
				return prodvo;
	}
	@Override
	public List<ProdVO> getAll() {
		List<ProdVO> prodList = new ArrayList<ProdVO>();
		try (Connection conn = ds.getConnection();
				Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("select * from prod");
			
			while(rs.next()) {
				ProdVO prodvo = new ProdVO();
				prodvo.setMembre_id(rs.getString("membre_id"));
				prodvo.setProd_no(rs.getString("prod_no"));
				prodvo.setType_no(rs.getString("type_no"));
				prodvo.setPrice(rs.getInt("price"));
				prodvo.setProd_qty(rs.getInt("prod_qty"));
				prodvo.setProd_name(rs.getString("prod_name"));
				prodvo.setProd_status(rs.getString("prod_status"));
				prodvo.setSale_time(rs.getTimestamp("sale_time"));
				prodList.add(prodvo);
			}

		} catch (SQLException se) {
				throw new RuntimeException("A database error occured" + se.getMessage());
		}
				return prodList;
	}
	@Override
	public List<ProdVO> getAllByOrder_no(String order_no) {
		List<ProdVO> prodList = new ArrayList<ProdVO>();
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_BY_ORDER_NO)) {
				pstmt.setString(1, order_no);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					ProdVO prodvo = new ProdVO();
					prodvo.setProd_no(rs.getString("prod_no"));
					prodvo.setPrice(rs.getInt("price"));
					prodvo.setProd_name(rs.getString("prod_name"));
					prodvo.setProd_qty(rs.getInt("prod_qty"));
					prodvo.setMembre_id(rs.getString("membre_id"));
					prodvo.setType_no(rs.getString("type_no"));
					prodvo.setProd_status(rs.getString("prod_status"));
					prodvo.setSale_time(rs.getTimestamp("sale_time"));
					prodList.add(prodvo);
				}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return prodList;
	}
	@Override
	public void update(ProdVO prodVO) {
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(UPDATE)) {
				pstmt.setString(1, prodVO.getProd_name());
				pstmt.setString(2, prodVO.getType_no());
				pstmt.setInt(3, prodVO.getPrice());
				pstmt.setInt(4, prodVO.getProd_qty());
				pstmt.setString(5, prodVO.getProd_status());
				pstmt.setString(6, prodVO.getProd_no());
				
				pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		
	}

					
}
