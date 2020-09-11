package com.dresscasepic.model;

import java.sql.*;
import java.util.*;

public class DressPicDAO implements DressPicDAO_interface{
	
	String driver= "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "EA102G1";
	String pw = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO DRESS_CASE_PIC(drpic_id, drcase_id, drpic)"
			+ "VALUES('WDP'|| LPAD(TO_CHAR(dressCasePic_seq.NEXTVAL),3,'0'),?,?)";
	
	private static final String DELETE = "DELETE FROM DRESS_CASE_PIC WHERE drpic_id= ?";
	
	private static final String FIND_ONE_PIC = "SELECT * FROM DRESS_CASE_PIC WHERE drpic_id = ?";
	private static final String FIND_ONES_PIC="SELECT * FROM DRESS_CASE_PIC WHERE drcase_id = ?";
	
	private static final String GETALL_PIC="SELECT * FROM DRESS_CASE_PIC";
	
	@Override
	public void insert(DressPicVO drpicvo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, drpicvo.getDrcase_id());
			pstmt.setBytes(2, drpicvo.getDrpic());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void delete(String drpic_id) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, drpic_id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public DressPicVO findByPrimaryKey(String drpic_id){
		
		DressPicVO dpvo = null;
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(FIND_ONE_PIC);
			
			pstmt.setString(1,drpic_id);
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				dpvo = new DressPicVO();
				dpvo.setDrpic_id(rs.getString("Drpic_id"));
				dpvo.setDrcase_id(rs.getString("drcase_id"));
				dpvo.setDrpic(rs.getBytes("Drpic"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dpvo;
	}
	public List<DressPicVO> findPics(String drcase_id) {
		
		List<DressPicVO> list = new ArrayList<DressPicVO>();
		DressPicVO dpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(FIND_ONES_PIC);
			pstmt.setString(1, drcase_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dpVO = new DressPicVO();
				dpVO.setDrpic_id(rs.getString("drpic_id"));
				dpVO.setDrcase_id(rs.getString("drcase_id"));
				dpVO.setDrpic(rs.getBytes("drpic"));
				list.add(dpVO);
			}
			} catch (SQLException e) {
				e.printStackTrace();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return list;
    }
	public List<DressPicVO> getAll(){
		List<DressPicVO> list = new ArrayList<DressPicVO>();
		
		DressPicVO dpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(GETALL_PIC);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dpVO = new DressPicVO();
				dpVO.setDrpic_id(rs.getString("drpic_id"));
				dpVO.setDrcase_id(rs.getString("drcase_id"));
				dpVO.setDrpic(rs.getBytes("drpic"));
				list.add(dpVO);
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
	
//	測試:insert,delete,findpic,findpics,getAll皆成功
	
	public static void main(String[] args) {
		DressPicDAO dpdao = new DressPicDAO();
//		1.insert:(自增主鍵,'WDC001',pic)
		DressPicVO dpVO = new DressPicVO();
		dpVO.setDrcase_id("WDC001");
		dpdao.insert(dpVO);
		System.out.println("新增成功!");
		
//		2.delete:('WDP005','WDC002')
//		dpdao.delete("WDP005");
//		System.out.println("刪除成功!");

//		3.findPic:('WDP001')
//		DressPicVO dpvo = dpdao.findByPrimaryKey("WDP002");
//		System.out.print(dpvo.getDrpic_id()+",");
//		System.out.print(dpvo.getDrcase_id()+",");
//		System.out.print(dpvo.getDrpic()+"\n");
//		System.out.println("查詢成功!");
		
//		4.findPics:('WDC001'):Drcase_id
		List<DressPicVO> list = dpdao.findPics("WDC001");
		for(DressPicVO dpvo:list) {
			System.out.print(dpvo.getDrpic_id()+",");
			System.out.print(dpvo.getDrcase_id()+",");
			System.out.print(dpvo.getDrpic()+"\n");
		}
		
//		4.getAllPic
//		List<DressPicVO> list2 = dpdao.getAll();
//		for(DressPicVO dpVO:list2) {
//			System.out.print(dpVO.getDrpic_id()+",");
//			System.out.print(dpVO.getDrcase_id()+",");
//			System.out.print(dpVO.getDrpic()+"\n");
//		}
	}
}
