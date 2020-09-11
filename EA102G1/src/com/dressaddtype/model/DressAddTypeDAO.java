package com.dressaddtype.model;

import java.sql.*;
import java.util.*;

public class DressAddTypeDAO implements DressAddTypeDAO_interface{
	
	String driver= "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "EA102G1";
	String pw = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO DRESS_ADD_TYPE(dradd_type) VALUES (?)";
	private static final String DELETE = "DELETE FROM DRESS_ADD_TYPE WHERE dradd_type = ?";
	
	private static final String FIND_ONE_TYPE="SELECT * FROM DRESS_ADD_TYPE WHERE dradd_type = ?";
	
	private static final String GETALL_TYPE="SELECT * FROM DRESS_ADD_TYPE";
	
	@Override
	public void insert(DressAddTypeVO datVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, datVO.getDradd_type());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void delete(String dradd_type) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, dradd_type);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public DressAddTypeVO findByPK(String dradd_type) {
		
		DressAddTypeVO datVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(FIND_ONE_TYPE);
			pstmt.setString(1, dradd_type);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				datVO = new DressAddTypeVO();
				datVO.setDradd_type(rs.getString("dradd_type"));
			}
			} catch (SQLException e) {
				e.printStackTrace();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return datVO;
    }
	
	public List<DressAddTypeVO> getAll(){
		List<DressAddTypeVO> list = new ArrayList<DressAddTypeVO>();
		DressAddTypeVO datVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(GETALL_TYPE);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				datVO = new DressAddTypeVO();
				datVO.setDradd_type(rs.getString("dradd_type"));
				list.add(datVO);
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
	
//	測試:insert,delete,find,getAll皆成功
	
	public static void main(String[] args) {
		DressAddTypeDAO datDAO = new DressAddTypeDAO();
//		1.insert:("第六種")
//		DressAddTypeVO datVO = new DressAddTypeVO();
//		datVO.setDradd_type("第六種");
//		datDAO.insert(datVO);
//		System.out.println("新增成功!");
//		
//		2.delete:("第六種")
//		datDAO.delete("第六種");
//		System.out.println("刪除成功!");

//		3.findByPK:('nubra')
//		DressAddTypeVO datVO2 = datDAO.findByPK("nubra");
//		System.out.print(datVO2.getDradd_type()+"\n");
		
//		4.getAll
		List<DressAddTypeVO> list = datDAO.getAll();
		for(DressAddTypeVO datVO3:list) {
			System.out.print(datVO3.getDradd_type()+"\n");
		}
	}
}
