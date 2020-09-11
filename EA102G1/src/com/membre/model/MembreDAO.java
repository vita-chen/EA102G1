package com.membre.model;
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

public class MembreDAO implements MembreDAO_interface{
	
	private static DataSource ds;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private final String INSERT_STMT = "insert into membre (membre_id, mem_name, sex, address, phone, email, status,  compte, passe, photo, regis_time)"
			+"values('M'|| LPAD(TO_CHAR(MEMBRE_SEQ.NEXTVAL),3,'0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String GET_ONE_BY_ACCOUNT = "select * from membre where compte = ?";
	private final String GET_ONE_BY_ID = "select * from membre where membre_id = ?";
	private final String UPDATE = "update membre set mem_name = ?, sex = ?, address = ?, phone = ?, email = ?, "
			+ "status = ?, passe = ? where membre_id=?";
	private final String DELETE = "delete membre where membre_id = ?";
	private final String GET_ALL="select * from membre";
	private final String GET_SELLER="select * from membre where membre_id = (select distinct membre_id from prod where prod_no in \r\n" + 
			"(select prod_no from order_detail where order_no = ?))";
	@Override
	public void insert(MembreVO membrevo) {
		try(Connection conn = ds.getConnection();){
			int[] col = {1};
			PreparedStatement pstmt = conn.prepareStatement(INSERT_STMT, col);
			pstmt.setString(1,membrevo.getMem_name());
			pstmt.setString(2, membrevo.getSex());
			pstmt.setString(3, membrevo.getAddress());
			pstmt.setString(4, membrevo.getPhone());
			pstmt.setString(5, membrevo.getEmail());
			pstmt.setString(6, membrevo.getStatus());
			pstmt.setString(7, membrevo.getCompte());
			pstmt.setString(8, membrevo.getPasse());
			pstmt.setBytes(9, membrevo.getPhoto());
			pstmt.setTimestamp(10, membrevo.getRegis_time());
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			membrevo.setMembre_id(rs.getString(1));
			rs.close();
			pstmt.close();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		
	}
	@Override
	public MembreVO getOneByAccount(String compte) {
		MembreVO membrevo = new MembreVO();
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ONE_BY_ACCOUNT)){
			pstmt.setString(1, compte);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				membrevo.setMembre_id(rs.getString("membre_id"));
				membrevo.setMem_name(rs.getString("mem_name"));
				membrevo.setSex(rs.getString("sex"));
				membrevo.setAddress(rs.getString("address"));
				membrevo.setPhone(rs.getString("phone"));
				membrevo.setEmail(rs.getString("email"));
				membrevo.setStatus(rs.getString("status"));
				membrevo.setCompte(rs.getString("compte"));
				membrevo.setPasse(rs.getString("passe"));
				membrevo.setPhoto(rs.getBytes("photo"));
				membrevo.setRegis_time(rs.getTimestamp("regis_time"));
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return membrevo;
	}

	@Override
	public void update(MembreVO membrevo) {
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(UPDATE)) {
				pstmt.setString(1, membrevo.getMem_name());
				pstmt.setString(2, membrevo.getSex());
				pstmt.setString(3, membrevo.getAddress());
				pstmt.setString(4, membrevo.getPhone());
				pstmt.setString(5, membrevo.getEmail());
				pstmt.setString(6, membrevo.getStatus());
				pstmt.setString(7, membrevo.getPasse());
				pstmt.setString(8, membrevo.getMembre_id());
				
				pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
	}
	
	public void delete(String membre_id) {
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(DELETE)){
			pstmt.setString(1, membre_id);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
	}
	@Override
	public List<MembreVO> getAll() {
		List<MembreVO> membreList = new ArrayList<MembreVO>();
		try (Connection conn = ds.getConnection();
				Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(GET_ALL);
			while(rs.next()) {
				MembreVO membrevo = new MembreVO();
				membrevo.setMembre_id(rs.getString("membre_id"));
				membrevo.setMem_name(rs.getString("mem_name"));
				membrevo.setSex(rs.getString("sex"));
				membrevo.setAddress(rs.getString("address"));
				membrevo.setPhone(rs.getString("phone"));
				membrevo.setEmail(rs.getString("email"));
				membrevo.setStatus(rs.getString("status"));
				membrevo.setCompte(rs.getString("compte"));
				membrevo.setPasse(rs.getString("passe"));
				membrevo.setPhoto(rs.getBytes("photo"));
				membrevo.setRegis_time(rs.getTimestamp("regis_time"));
				membreList.add(membrevo);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return membreList;
	}
	@Override
	public MembreVO getOneById(String membre_id) {
		MembreVO membrevo = new MembreVO();
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(GET_ONE_BY_ID);) {
			pstmt.setString(1, membre_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				membrevo.setMembre_id(rs.getString("membre_id"));
				membrevo.setMem_name(rs.getString("mem_name"));
				membrevo.setSex(rs.getString("sex"));
				membrevo.setAddress(rs.getString("address"));
				membrevo.setPhone(rs.getString("phone"));
				membrevo.setEmail(rs.getString("email"));
				membrevo.setStatus(rs.getString("status"));
				membrevo.setCompte(rs.getString("compte"));
				membrevo.setPasse(rs.getString("passe"));
				membrevo.setPhoto(rs.getBytes("photo"));
				membrevo.setRegis_time(rs.getTimestamp("regis_time"));
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return membrevo;
	}
	@Override
	public MembreVO getSeller(String order_no) {
		MembreVO membrevo = new MembreVO();
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_SELLER)) {
			 	pstmt.setString(1, order_no);
			 	ResultSet rs = pstmt.executeQuery();
			 	if (rs.next()) {
			 		membrevo.setMembre_id(rs.getString("membre_id"));
			 		membrevo.setMem_name(rs.getString("mem_name"));
			 		membrevo.setStatus(rs.getString("status"));
			 	}
			 		
			 	
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return membrevo;
	}
}
