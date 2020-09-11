package com.buy_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.prod.model.ProdVO;

public class ListDAO implements ListDAO_interface{
	private static DataSource ds;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private final static String INSERT = "insert into buy_list(prod_no, membre_id) values(?,?)";
	private final static String GETALL="select * from prod where prod_no in (select prod_no from buy_list where membre_id = ?) and prod_status = '1'";
	private final static String DELETE="delete buy_list where prod_no = ? and membre_id = ?";
	private final static String GETONE="select * from buy_list where prod_no = ? and membre_id=?";
	
	
	
	
	
	@Override
	public void insert(String prod_no, String membre_id) {
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(INSERT)) {
			  pstmt.setString(1, prod_no);
			  pstmt.setString(2, membre_id);
			  
			  pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		
	}

	@Override
	public List<ProdVO> getAll(String membre_id) {
		List<ProdVO> prodList = new ArrayList<ProdVO>();
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GETALL)) {
				pstmt.setString(1, membre_id);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					ProdVO prodvo = new ProdVO();
					prodvo.setProd_no(rs.getString("prod_no"));
					prodvo.setMembre_id(rs.getString("membre_id"));
					prodvo.setType_no(rs.getString("type_no"));
					prodvo.setPrice(rs.getInt("price"));
					prodvo.setProd_name(rs.getString("prod_name"));
					prodvo.setProd_qty(rs.getInt("prod_qty"));
					prodList.add(prodvo);
				}
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return prodList;
	}

	@Override
	public void delete(String prod_no, String membre_id) {
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(DELETE)) {
				pstmt.setString(1, prod_no);
				pstmt.setString(2, membre_id);
				
				pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		
	}

	@Override
	public ListVO getOne(String prod_no, String membre_id) {
		ListVO listvo = new ListVO();
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GETONE)) {
				pstmt.setString(1, prod_no);
				pstmt.setString(2, membre_id);
				ResultSet rs = pstmt.executeQuery();
				
				if (rs.next()) {
					listvo.setProd_no(rs.getString("prod_no"));
					listvo.setMembre_id(rs.getString("membre_id"));
				}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
			
		}	
			return listvo;
	}

} // end of class
