package com.dresscasetrack.model;

import java.sql.*;
import java.util.*;

public class DressCaseTrackDAO implements DressCaseTrackDAO_interface{
	
	String driver= "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "EA102G1";
	String pw = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO DRESS_CASE_TRACK(drcase_id, membre_id)"
			+ "VALUES(?,?)";
	
	private static final String DELETE = "DELETE FROM DRESS_CASE_TRACK WHERE drcase_id= ? and membre_id = ?";
	
	private static final String FIND_ONES_TRACK="SELECT * FROM DRESS_CASE_TRACK WHERE membre_id = ?";
	
	private static final String GETALL_TRACK="SELECT * FROM DRESS_CASE_TRACK";
	
	@Override
	public void insert(DressCaseTrackVO dctVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, dctVO.getDrcase_id());
			pstmt.setString(2, dctVO.getMembre_id());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void delete(String drcase_id,String membre_id) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, drcase_id);
			pstmt.setString(2, membre_id);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<DressCaseTrackVO> findByMembre(String membre_id) {
		
		List<DressCaseTrackVO> list = new ArrayList<DressCaseTrackVO>();
		DressCaseTrackVO dctVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(FIND_ONES_TRACK);
			pstmt.setString(1, membre_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dctVO = new DressCaseTrackVO();
				dctVO.setDrcase_id(rs.getString("drcase_id"));
				dctVO.setMembre_id(rs.getString("membre_id"));
				list.add(dctVO);
			}
			} catch (SQLException e) {
				e.printStackTrace();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return list;
    }
	
	public List<DressCaseTrackVO> getAll(){
		List<DressCaseTrackVO> list = new ArrayList<DressCaseTrackVO>();
		
		DressCaseTrackVO dctVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(GETALL_TRACK);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dctVO = new DressCaseTrackVO();
				dctVO.setDrcase_id(rs.getString("drcase_id"));
				dctVO.setMembre_id(rs.getString("membre_id"));
				list.add(dctVO);
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
	
//	測試:insertTrack,deleteTrack,findtrack,getAllTrack皆成功
	
	public static void main(String[] args) {
		DressCaseTrackDAO dctDAO = new DressCaseTrackDAO();
//		1.insert:(自增主鍵,'WDC001','M004')
//		DressCaseTrackVO dctVO = new DressCaseTrackVO();
//		dctVO.setDrcase_id("WDC001");
//		dctVO.setMembre_id("M004");
//		dctDAO.insertTrack(dctVO);
//		System.out.println("新增成功!");
//		
//		2.delete:('WDC001','M004')
//		dctDAO.deleteTrack("WDC001","M004");
//		System.out.println("刪除成功!");

//		3.findByMebre:('M001')
		List<DressCaseTrackVO> list = dctDAO.findByMembre("M001");
		for(DressCaseTrackVO dctVO2:list) {
			System.out.print(dctVO2.getDrcase_id()+",");
			System.out.print(dctVO2.getMembre_id()+"\n");
		}
		
//		4.getAllTracks
//		List<DressCaseTrackVO> list = dctDAO.getAll();
//		for(DressCaseTrackVO dctVO3:list) {
//			System.out.print(dctVO3.getDrcase_id()+",");
//			System.out.print(dctVO3.getMembre_id()+"\n");
//		}
	}
}
