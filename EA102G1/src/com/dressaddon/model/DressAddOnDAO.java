package com.dressaddon.model;

import java.util.*;
import java.sql.*;

public class DressAddOnDAO implements DressAddOnDAO_interface {

	String driver= "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "EA102G1";
	String pw = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO DRESS_ADD_ON (dradd_id,vender_id,dradd_type,dradd_na,dradd_pr,dradd_st) VALUES "
		+ "('WDA'|| LPAD(TO_CHAR(dressAddOn_seq.NEXTVAL),3,'0'), ?, ?, ?, ?,?)";
	
	private static final String UPDATE = 
			"UPDATE DRESS_ADD_ON SET dradd_type=?,dradd_na=?,dradd_pr=?,dradd_st=? WHERE dradd_id = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT * FROM DRESS_ADD_ON where dradd_id = ?";
	
	private static final String FIND_BY_VEN =
			"SELECT * FROM DRESS_ADD_ON where vender_id = ?";
	
	private static final String GET_ALL_STMT = "SELECT * FROM DRESS_ADD_ON ";
	
	@Override
	public void insert(DressAddOnVO daoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, daoVO.getVender_id());
			pstmt.setString(2, daoVO.getDradd_type());
			pstmt.setString(3, daoVO.getDradd_na());
			pstmt.setInt(4, daoVO.getDradd_pr());
			pstmt.setInt(5, daoVO.getDradd_st());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
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
	}

	@Override
	public void update(DressAddOnVO daoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, daoVO.getDradd_type());
			pstmt.setString(2, daoVO.getDradd_na());
			pstmt.setInt(3, daoVO.getDradd_pr());
			pstmt.setInt(4, daoVO.getDradd_st());
			pstmt.setString(5, daoVO.getDradd_id());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
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
	}


	public DressAddOnVO findByPrimaryKey(String dradd_type) {
		
		DressAddOnVO daoVO2 = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pw);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, dradd_type);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				daoVO2 = new DressAddOnVO();
				daoVO2.setDradd_id(rs.getString("dradd_id"));
				daoVO2.setVender_id(rs.getString("vender_id"));
				daoVO2.setDradd_type(rs.getString("dradd_type"));
				daoVO2.setDradd_na(rs.getString("dradd_na"));
				daoVO2.setDradd_pr(rs.getInt("dradd_pr"));
				daoVO2.setDradd_st(rs.getInt("dradd_st"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
		return daoVO2;
	}
	
	@Override
	public List<DressAddOnVO> getAll() {
		List<DressAddOnVO> list = new ArrayList<DressAddOnVO>();
		DressAddOnVO dcVO3 = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dcVO3 = new DressAddOnVO();
				dcVO3.setDradd_id(rs.getString("dradd_id"));
				dcVO3.setVender_id(rs.getString("vender_id"));
				dcVO3.setDradd_type(rs.getString("dradd_type"));
				dcVO3.setDradd_na(rs.getString("dradd_na"));
				dcVO3.setDradd_pr(rs.getInt("dradd_pr"));
				dcVO3.setDradd_st(rs.getInt("dradd_st"));
				list.add(dcVO3); 
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
	
public List<DressAddOnVO> findByVender(String vender_id) {
		
		List<DressAddOnVO> list = new ArrayList<DressAddOnVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pw);
			pstmt = con.prepareStatement(FIND_BY_VEN);

			pstmt.setString(1, vender_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				DressAddOnVO daoVO2 = new DressAddOnVO();
				daoVO2.setDradd_id(rs.getString("dradd_id"));
				daoVO2.setVender_id(rs.getString("vender_id"));
				daoVO2.setDradd_type(rs.getString("dradd_type"));
				daoVO2.setDradd_na(rs.getString("dradd_na"));
				daoVO2.setDradd_pr(rs.getInt("dradd_pr"));
				daoVO2.setDradd_st(rs.getInt("dradd_st"));
				list.add(daoVO2);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	
	public static void main(String[] args) {
		DressAddOnDAO daoDAO= new DressAddOnDAO();
		
//		1.insert:('auto-increment','V003','�憌�','蝎咿��憌�')
//		DressAddOnVO daoVO = new DressAddOnVO();
//		daoVO.setVender_id("V003");
//		daoVO.setDradd_type("�憌�");
//		daoVO.setDradd_na("蝎咿��憌�");
//		daoVO.setDradd_pr(980);
//		daoVO.setDradd_st(1);
//		daoDAO.insert(daoVO);
//		System.out.println("�憓���");
		
//		2.update:(("��","",'WDA005'))
//		DressAddOnVO dcvo2 = new DressAddOnVO();
//		dcvo2.setDradd_id("WDA005");
//		dcvo2.setDradd_type("��");
//		dcvo2.setDradd_na("瘚芣憤蝢拙�����");
//		dcvo2.setDradd_pr(3000);
//		dcvo2.setDradd_st(1);
//		daoDAO.update(dcvo2);
//		System.out.println("靽格����");
	
//		3.findbyPK:
//		DressAddOnVO dcvo3 = daoDAO.findByPrimaryKey("WDA005");
//		System.out.print(dcvo3.getDradd_id()+",");
//		System.out.print(dcvo3.getVender_id()+",");
//		System.out.print(dcvo3.getDradd_type()+",");
//		System.out.print(dcvo3.getDradd_na()+",");
//		System.out.print(dcvo3.getDradd_pr()+",");
//		System.out.print(dcvo3.getDradd_st()+"\n");
		
//		4.getAll:�閰Ｘ���ressCase
//		List<DressAddOnVO> list = daoDAO.getAll();
//		for(DressAddOnVO dcvo6:list) {
//			System.out.print(dcvo6.getDradd_id()+",");
//			System.out.print(dcvo6.getVender_id()+",");
//			System.out.print(dcvo6.getDradd_type()+",");
//			System.out.print(dcvo6.getDradd_na()+",");
//			System.out.print(dcvo6.getDradd_pr()+",");
//			System.out.print(dcvo6.getDradd_st()+"\n");
//		}
//		
//		5.findByVenderid
		List<DressAddOnVO> list2 = daoDAO.findByVender("V007");
		for(DressAddOnVO dcvo:list2) {
			System.out.print(dcvo.getDradd_id()+",");
			System.out.print(dcvo.getVender_id()+",");
			System.out.print(dcvo.getDradd_type()+",");
			System.out.print(dcvo.getDradd_na()+",");
			System.out.print(dcvo.getDradd_pr()+",");
			System.out.print(dcvo.getDradd_st()+"\n");
		}
	}
}
