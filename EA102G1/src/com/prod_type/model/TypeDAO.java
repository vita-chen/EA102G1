package com.prod_type.model;

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

public class TypeDAO implements TypeDAO_interface{
	private static DataSource ds;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private final static String INSERT = "insert into prod_type(type_no, type_name) values(?,?)";
	private final static String DELETE = "delete prod_type where type_no = ? and type_name = ?";
	
	public void insert(String type_no, String type_name) {
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(INSERT)) {
				pstmt.setString(1, type_no);
				pstmt.setString(2, type_name);
				
				pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		
	}

	@Override
	public void delete(String type_no, String type_name) {
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(DELETE)) {
				pstmt.setString(1, type_no);
				pstmt.setString(2, type_name);
				
				pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		
	}

	@Override
	public List<TypeVO> getAll() {
		List<TypeVO> typeList = new ArrayList<TypeVO>();
		try (Connection conn = ds.getConnection(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("select * from prod_type");
			while (rs.next()) {
				TypeVO typevo = new TypeVO();
				typevo.setType_no(rs.getString("type_no"));
				typevo.setType_name(rs.getString("type_name"));
				typeList.add(typevo);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return typeList;
	}

}
