package com.dressaddorder.model;

import java.sql.*;
import java.util.*;

public class DressAddOrderDAO implements DressAddOrderDAO_interface{
	
	String driver= "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "EA102G1";
	String pw = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO DRESS_ADD_ORDER(dradd_id, drde_id)VALUES(?,?)";
	
	private static final String FIND_ONE="SELECT * FROM DRESS_ADD_ORDER WHERE drde_id =?";
	
	private static final String GETALL="SELECT * FROM DRESS_ADD_ORDER";
	
	@Override
	public void insert(DressAddOrderVO daoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, daoVO.getDradd_id());
			pstmt.setString(2, daoVO.getDrde_id());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	public List<DressAddOrderVO> findByDrde_id(String drde_id) {
		
		List<DressAddOrderVO> list = new ArrayList<DressAddOrderVO>();
		
		DressAddOrderVO daoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(FIND_ONE);
			pstmt.setString(1, drde_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				daoVO = new DressAddOrderVO();
				daoVO.setDradd_id(rs.getString("dradd_id"));
				daoVO.setDrde_id(rs.getString("drde_id"));
				list.add(daoVO);
			}
			} catch (SQLException e) {
				e.printStackTrace();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return list;
    }
	
	public List<DressAddOrderVO> getAll(){
		List<DressAddOrderVO> list = new ArrayList<DressAddOrderVO>();
		
		DressAddOrderVO daoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				daoVO = new DressAddOrderVO();
				daoVO.setDradd_id(rs.getString("dradd_id"));
				daoVO.setDrde_id(rs.getString("drde_id"));
				list.add(daoVO);
				}
			}
		catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
		}
		catch (SQLException se)  {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
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
	
//	測試:insertAddOrder,findAddOrder,getAllAddOrder皆成功
	
	public static void main(String[] args) {
		DressAddOrderDAO daoDAO = new DressAddOrderDAO();
//		1.insert:(自增主鍵,'WDA004','WDD004')
//		DressAddOrderVO daoVO = new DressAddOrderVO();
//		daoVO.setDradd_id("WDA004");
//		daoVO.setDrde_id("WDD004");
//		daoDAO.insert(daoVO);
//		System.out.println("新增成功!");

//		2.findByComPK:('M001')
//		List<DressAddOrderVO> list = daoDAO.findByDrde_id("WDD001");
//		for(DressAddOrderVO daoVO2:list) {
//		System.out.print(daoVO2.getDradd_id()+",");
//		System.out.print(daoVO2.getDrde_id()+"\n");
//		}
//		3.getAll
		List<DressAddOrderVO> list = daoDAO.getAll();
		for(DressAddOrderVO daoVO3:list) {
			System.out.print(daoVO3.getDradd_id()+",");
			System.out.print(daoVO3.getDrde_id()+"\n");
		}
	}
}
