package com.dressorderdetail.model;

import java.util.*;
import java.sql.*;

public class DressOrderDetailDAO implements DressOrderDetailDAO_interface {

	String driver= "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "EA102G1";
	String pw = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO DRESS_ORDER_DETAIL (drde_id,drord_id,drcase_id,drde_st,drcase_totpr) VALUES "
		+ "('WDD'|| LPAD(TO_CHAR(dressDetail_seq.NEXTVAL),3,'0'), ?, ?, ?, ?)";
	
	private static final String UPDATE = 
			"UPDATE DRESS_ORDER_DETAIL SET drde_st =? WHERE drde_id = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT * FROM DRESS_ORDER_DETAIL WHERE drde_id = ?";
	
	private static final String GET_ALL_STMT = "SELECT * FROM DRESS_ORDER_DETAIL ";
	
	private static final String GET_BY_CASE_ORDER = "SELECT * FROM DRESS_ORDER_DETAIL WHERE drcase_id = ? and drord_id =? ORDER BY drde_id asc";
	
	private static final String GET_BY_ORDER = "SELECT * FROM DRESS_ORDER_DETAIL WHERE drord_id = ?";
	@Override
	public void insert(DressOrderDetailVO dodVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, dodVO.getDrord_id());
			pstmt.setString(2, dodVO.getDrcase_id());
			pstmt.setInt(3, dodVO.getDrde_st());
			pstmt.setInt(4, dodVO.getDrcase_totpr());

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
	public void update(DressOrderDetailVO dodVO2) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, dodVO2.getDrde_st());
			pstmt.setString(2, dodVO2.getDrde_id());

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

	public DressOrderDetailVO findByPrimaryKey(String drde_id) {

		DressOrderDetailVO dodVO3 = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pw);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, drde_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dodVO3 = new DressOrderDetailVO();
				dodVO3.setDrde_id(rs.getString("drde_id"));
				dodVO3.setDrord_id(rs.getString("drord_id"));
				dodVO3.setDrcase_id(rs.getString("drcase_id"));
				dodVO3.setDrde_st(rs.getInt("drde_st"));
				dodVO3.setDrcase_totpr(rs.getInt("drcase_totpr"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return dodVO3;
	}

	
	@Override
	public List<DressOrderDetailVO> getAll() {
		List<DressOrderDetailVO> list = new ArrayList<DressOrderDetailVO>();
		DressOrderDetailVO dodVO4 = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dodVO4 = new DressOrderDetailVO();
				dodVO4.setDrde_id(rs.getString("drde_id"));
				dodVO4.setDrord_id(rs.getString("drord_id"));
				dodVO4.setDrcase_id(rs.getString("drcase_id"));
				dodVO4.setDrde_st(rs.getInt("drde_st"));
				dodVO4.setDrcase_totpr(rs.getInt("drcase_totpr"));
				list.add(dodVO4); // Store the row in the list
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
	
	public DressOrderDetailVO findByDrcaseAndOrder(String drcase_id,String drord_id) {
		DressOrderDetailVO dodVO3 = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pw);
			pstmt = con.prepareStatement(GET_BY_CASE_ORDER);

			pstmt.setString(1, drcase_id);
			pstmt.setString(2, drord_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dodVO3 = new DressOrderDetailVO();
				dodVO3.setDrde_id(rs.getString("drde_id"));
				dodVO3.setDrord_id(rs.getString("drord_id"));
				dodVO3.setDrcase_id(rs.getString("drcase_id"));
				dodVO3.setDrde_st(rs.getInt("drde_st"));
				dodVO3.setDrcase_totpr(rs.getInt("drcase_totpr"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return dodVO3;
	}

	public List<DressOrderDetailVO> findByOrder(String drord_id) {
		List<DressOrderDetailVO> list = new ArrayList<DressOrderDetailVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pw);
			pstmt = con.prepareStatement(GET_BY_ORDER);

			pstmt.setString(1, drord_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				DressOrderDetailVO dodVO3 = new DressOrderDetailVO();
				dodVO3.setDrde_id(rs.getString("drde_id"));
				dodVO3.setDrord_id(rs.getString("drord_id"));
				dodVO3.setDrcase_id(rs.getString("drcase_id"));
				dodVO3.setDrde_st(rs.getInt("drde_st"));
				dodVO3.setDrcase_totpr(rs.getInt("drcase_totpr"));
				list.add(dodVO3);
				
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		DressOrderDetailDAO dodDAO= new DressOrderDetailDAO();
		
//		1.insert:('auto-increment','WDO001','WDC003',0,26000)
//		DressOrderDetailVO dodVO = new DressOrderDetailVO();
//		dodVO.setDrord_id("WDO001");
//		dodVO.setDrcase_id("WDC003");
//		dodVO.setDrde_st(0);
//		dodVO.setDrcase_totpr(26000);
//		doDAO.insert(dodVO);
//		System.out.println("新增成功");
		
//		2.update: 輸入drde_id,可更新該筆資料的drde_st
//		DressOrderDetailVO dodVO2 = new DressOrderDetailVO();
//		dodVO2.setDrde_id("WDD003");
//		dodVO2.setDrde_st(1);
//		doDAO.update(dodVO2);
//		System.out.println("修改成功");
		
//		3.findbyPK:輸入drdeId，可以搜尋相對應的dressOrderDetail
//		DressOrderDetailVO dodVO3 = dodDAO.findByPrimaryKey("WDD001");
//		System.out.print(dodVO3.getDrde_id()+",");
//		System.out.print(dodVO3.getDrord_id()+",");
//		System.out.print(dodVO3.getDrcase_id()+",");
//		System.out.print(dodVO3.getDrde_st()+",");
//		System.out.print(dodVO3.getDrcase_totpr()+"\n");
		
//		4.getAll:查詢所有DressOrderDetail
		List<DressOrderDetailVO> list = dodDAO.getAll();
		for(DressOrderDetailVO dodVO4:list) {
			System.out.print(dodVO4.getDrde_id());
			System.out.print(dodVO4.getDrord_id()+",");
			System.out.print(dodVO4.getDrcase_id()+",");
			System.out.print(dodVO4.getDrde_st()+",");
			System.out.print(dodVO4.getDrcase_totpr()+"\n");
		}
		
//		5.findByDrcaseAndOrder
//		DressOrderDetailVO dodVO = dodDAO.findByDrcaseAndOrder("WDC001", "WDO025");
//
//		System.out.print(dodVO.getDrde_id()+",");
//		System.out.print(dodVO.getDrord_id()+",");
//		System.out.print(dodVO.getDrcase_id()+",");
//		System.out.print(dodVO.getDrde_st()+",");
//		System.out.print(dodVO.getDrcase_totpr()+"\n");
		
//		6.findByOrder
//		List<DressOrderDetailVO> list = dodDAO.findByOrder("WDO001");
//		for(DressOrderDetailVO dodVO4:list) {
//			System.out.print(dodVO4.getDrde_id());
//			System.out.print(dodVO4.getDrord_id()+",");
//			System.out.print(dodVO4.getDrcase_id()+",");
//			System.out.print(dodVO4.getDrde_st()+",");
//			System.out.print(dodVO4.getDrcase_totpr()+"\n");
//		}
		
	}
}
