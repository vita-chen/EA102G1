package com.dresscase.model;

import java.util.*;
import java.sql.*;

public class DressCaseDAO implements DressCaseDAO_interface {

	String driver= "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "EA102G1";
	String pw = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO DRESS_CASE (drcase_id,vender_id,drcase_na,drcase_br,drcase_pr,drcase_st) VALUES "
		+ "('WDC'|| LPAD(TO_CHAR(dressCase_seq.NEXTVAL),3,'0'), ?, ?, ?, ?, ?)";
	
	private static final String UPDATE = 
			"UPDATE DRESS_CASE SET drcase_na =?, drcase_br=?,drcase_pr=?,drcase_st=? WHERE drcase_id = ?";
	
	private static final String DELETE_STMT = 
			"DELETE FROM DRESS_CASE WHERE drcase_id = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT * FROM DRESS_CASE where drcase_id = ?";
	
	private static final String FindByVen_STMT = "SELECT * FROM DRESS_CASE WHERE vender_id = ?";

	private static final String FindByDrNa_STMT = "SELECT * FROM DRESS_CASE WHERE drcase_na LIKE ?";

	private static final String GET_ALL_STMT = "SELECT * FROM DRESS_CASE ";
	
	@Override
	public void insert(DressCaseVO dcVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, dcVO.getVender_id());
			pstmt.setString(2, dcVO.getDrcase_na());
			pstmt.setString(3, dcVO.getDrcase_br());
			pstmt.setInt(4, dcVO.getDrcase_pr());
			pstmt.setInt(5, dcVO.getDrcase_st());

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
	public void update(DressCaseVO dressCaseVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, dressCaseVO.getDrcase_na());
			pstmt.setString(2, dressCaseVO.getDrcase_br());
			pstmt.setInt(3, dressCaseVO.getDrcase_pr());
			pstmt.setInt(4, dressCaseVO.getDrcase_st());
			pstmt.setString(5, dressCaseVO.getDrcase_id());

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

	@Override
	public void delete(String drcase_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pw);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, drcase_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	
	public DressCaseVO findByPrimaryKey(String drcase_id) {
		
		DressCaseVO dcvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pw);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, drcase_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				dcvo = new DressCaseVO();
				dcvo.setDrcase_id(rs.getString("drcase_id"));
				dcvo.setVender_id(rs.getString("vender_id"));
				dcvo.setDrcase_na(rs.getString("drcase_na"));
				dcvo.setDrcase_br(rs.getString("drcase_br"));
				dcvo.setDrcase_pr(rs.getInt("drcase_pr"));
				dcvo.setDrcase_st(rs.getInt("drcase_st"));
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
		return dcvo;
	}
	@Override
	public List<DressCaseVO> findByVenID(String vender_id) {
		
		List<DressCaseVO> list = new ArrayList<DressCaseVO>();
		DressCaseVO dcVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(FindByVen_STMT);
			
			pstmt.setString(1, vender_id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				dcVO = new DressCaseVO();
				dcVO.setDrcase_id(rs.getString("drcase_id"));
				dcVO.setVender_id(rs.getString("vender_id"));
				dcVO.setDrcase_na(rs.getString("drcase_na"));
				dcVO.setDrcase_br(rs.getString("drcase_br"));
				dcVO.setDrcase_pr(rs.getInt("drcase_pr"));
				dcVO.setDrcase_st(rs.getInt("drcase_st"));
				list.add(dcVO);
			}

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
	
	public List<DressCaseVO> findByDrNa(String drcase_na) {
		List<DressCaseVO> list = new ArrayList<DressCaseVO>();
		DressCaseVO dcVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(FindByDrNa_STMT);
			
			pstmt.setString(1, "%"+drcase_na+"%");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				dcVO= new DressCaseVO();
				dcVO.setDrcase_id(rs.getString("drcase_id"));
				dcVO.setVender_id(rs.getString("vender_id"));
				dcVO.setDrcase_na(rs.getString("drcase_na"));
				dcVO.setDrcase_br(rs.getString("drcase_br"));
				dcVO.setDrcase_pr(rs.getInt("drcase_pr"));
				dcVO.setDrcase_st(rs.getInt("drcase_st"));
				list.add(dcVO);
			}

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
	
	@Override
	public List<DressCaseVO> getAll() {
		List<DressCaseVO> list = new ArrayList<DressCaseVO>();
		DressCaseVO dcVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dcVO = new DressCaseVO();
				dcVO.setDrcase_id(rs.getString("drcase_id"));
				dcVO.setVender_id(rs.getString("vender_id"));
				dcVO.setDrcase_na(rs.getString("drcase_na"));
				dcVO.setDrcase_br(rs.getString("drcase_br"));
				dcVO.setDrcase_pr(rs.getInt("drcase_pr"));
				dcVO.setDrcase_st(rs.getInt("drcase_st"));
				list.add(dcVO); // Store the row in the list
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
	
	
	
	public static void main(String[] args) {
		DressCaseDAO dcdao= new DressCaseDAO();
		
//		1.insert:('WDC006','V003','在婚禮上擁有女神的氣質',25000,1)
//		DressCaseVO dcvo1 = new DressCaseVO();
//		dcvo1.setVender_id("V003");
//		dcvo1.setDrcase_na("月桂女神婚紗");
//		dcvo1.setDrcase_br("在婚禮上擁有女神的氣質");
//		dcvo1.setDrcase_pr(25000);
//		dcvo1.setDrcase_st(1);
//		dcdao.insert(dcvo1);
//		System.out.println("新增成功");
		
//		2.update:(("讓你驚艷全場",30000,1,'WDC001'))
//		DressCaseVO dcvo2 = new DressCaseVO();
//		dcvo2.setDrcase_na("義式純白浪漫手工婚紗");
//		dcvo2.setDrcase_br("讓你驚艷全場");
//		dcvo2.setDrcase_pr(30000);
//		dcvo2.setDrcase_st(0);
//		dcvo2.setDrcase_id("WDC001");
//		dcdao.update(dcvo2);
//		System.out.println("修改成功");
		
//		3.delete:
//		dcdao.delete("WDC006");
//		System.out.println("刪除成功");
		
//		4.findbyPK:輸入dressCaseId，可以搜尋相對應的dressCase
//		DressCaseVO dcvo3 = dcdao.findByPrimaryKey("WDC001");
//		System.out.print(dcvo3.getDrcase_id()+",");
//		System.out.print(dcvo3.getVender_id()+",");
//		System.out.print(dcvo3.getDrcase_na()+",");
//		System.out.print(dcvo3.getDrcase_br()+",");
//		System.out.print(dcvo3.getDrcase_pr()+",");
//		System.out.print(dcvo3.getDrcase_st()+"\n");
		
//		5.findOne:findByVenID:輸入廠商id，可以搜尋出所有dressCase
//		List<DressCaseVO> list = dcdao.findByVenID("V003");
//		for(DressCaseVO dcvo4:list) {
//			System.out.print(dcvo4.getDrcase_id()+",");
//			System.out.print(dcvo4.getVender_id()+",");
//			System.out.print(dcvo4.getDrcase_na()+",");
//			System.out.print(dcvo4.getDrcase_br()+",");
//			System.out.print(dcvo4.getDrcase_pr()+",");
//			System.out.print(dcvo4.getDrcase_st()+"\n");
//		}
//		System.out.println("依據venderID查詢完成");
		
//		6.findOne:findByDrNa:輸入dressCase名稱中內含的關鍵字，可以搜尋出所有dressCase
//		List<DressCaseVO> list = dcdao.findByDrNa("白");
//		for(DressCaseVO dcvo5:list) {
//			System.out.print(dcvo5.getDrcase_id()+",");
//			System.out.print(dcvo5.getVender_id()+",");
//			System.out.print(dcvo5.getDrcase_na()+",");
//			System.out.print(dcvo5.getDrcase_br()+",");
//			System.out.print(dcvo5.getDrcase_pr()+",");
//			System.out.print(dcvo5.getDrcase_st()+"\n");
//		}
//		System.out.println("依據dressCase關鍵字查詢完成");
		
//		7.getAll:查詢所有DressCase
		List<DressCaseVO> list = dcdao.getAll();
		for(DressCaseVO dcvo6:list) {
			System.out.print(dcvo6.getDrcase_id()+",");
			System.out.print(dcvo6.getVender_id()+",");
			System.out.print(dcvo6.getDrcase_na()+",");
			System.out.print(dcvo6.getDrcase_br()+",");
			System.out.print(dcvo6.getDrcase_pr()+",");
			System.out.print(dcvo6.getDrcase_st()+"\n");
		} 
	}
}
