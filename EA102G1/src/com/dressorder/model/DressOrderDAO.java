package com.dressorder.model;

import java.util.*;
import java.sql.*;

public class DressOrderDAO implements DressOrderDAO_interface {

	String driver= "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "EA102G1";
	String pw = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO DRESS_ORDER (drord_id,membre_id,vender_id,drord_time,drord_pr,drord_depo,drord_ini,drord_pay_st,drord_fin_st,"
		+ "dr_mrep_st,dr_vrep_st,dr_mrep_de,dr_vrep_de,dr_mrep_res,dr_vrep_res,dr_rev_con) VALUES "
		+ "('WDO'|| LPAD(TO_CHAR(dressOrder_seq.NEXTVAL),3,'0'), ?, ?, CURRENT_TIMESTAMP, ?,?,?,?,?,?,?"
		+ ",'未檢舉','未檢舉','未檢舉','未檢舉','未評價')";
	
	private static final String UPDATE = 
			"UPDATE DRESS_ORDER SET drord_pay_st=? ,drord_fin_st=? ,dr_mrep_st=? ,dr_vrep_st=? ,dr_mrep_de=? ,dr_vrep_de=? ,dr_mrep_res=? ,dr_vrep_res=? ,dr_rev_star=? ,dr_rev_con=? WHERE drord_id = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT * FROM DRESS_ORDER where drord_id = ?";
	
	private static final String FIND_BY_MEMBRE_STMT =
			"SELECT * FROM DRESS_ORDER where membre_id = ? ORDER BY drord_id desc";
	
	private static final String FIND_BY_VENDER_STMT =
			"SELECT * FROM DRESS_ORDER where vender_id = ? ORDER BY drord_id desc";

	private static final String GET_LATEST = "SELECT* FROM DRESS_ORDER WHERE membre_id = ? ORDER BY drord_time asc";
	
	private static final String GET_ALL_STMT = "SELECT * FROM DRESS_ORDER ORDER BY drord_id desc";
	
	
	@Override
	public void insert(DressOrderVO daoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, daoVO.getMembre_id());
			pstmt.setString(2, daoVO.getVender_id());
			pstmt.setInt(3, daoVO.getDrord_pr());
			pstmt.setInt(4, daoVO.getDrord_depo());
			pstmt.setInt(5, daoVO.getDrord_ini());
			pstmt.setInt(6,daoVO.getDrord_pay_st());
			pstmt.setInt(7, daoVO.getDrord_fin_st());
			pstmt.setInt(8,daoVO.getDr_mrep_st());
			pstmt.setInt(9, daoVO.getDr_vrep_st());

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
	public void update(DressOrderVO daoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, daoVO.getDrord_pay_st());
			pstmt.setInt(2, daoVO.getDrord_fin_st());
			pstmt.setInt(3, daoVO.getDr_mrep_st());
			pstmt.setInt(4, daoVO.getDr_vrep_st());
			pstmt.setString(5, daoVO.getDr_mrep_de());
			pstmt.setString(6, daoVO.getDr_vrep_de());
			pstmt.setString(7, daoVO.getDr_mrep_res());
			pstmt.setString(8, daoVO.getDr_vrep_res());
			pstmt.setInt(9, daoVO.getDr_rev_star());
			pstmt.setString(10, daoVO.getDr_rev_con());
			pstmt.setString(11, daoVO.getDrord_id());			

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

	public DressOrderVO findByPrimaryKey(String drord_id) {
		
		DressOrderVO doVO2 = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pw);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, drord_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				doVO2 = new DressOrderVO();
				doVO2.setDrord_id(rs.getString("drord_id"));
				doVO2.setMembre_id(rs.getString("membre_id"));
				doVO2.setVender_id(rs.getString("vender_id"));
				doVO2.setDrord_time(rs.getTimestamp("drord_time"));
				doVO2.setDrord_pr(rs.getInt("drord_pr"));
				doVO2.setDrord_depo(rs.getInt("drord_depo"));
				doVO2.setDrord_ini(rs.getInt("drord_ini"));
				doVO2.setDrord_pay_st(rs.getInt("drord_pay_st"));
				doVO2.setDrord_fin_st(rs.getInt("drord_fin_st"));
				doVO2.setDr_mrep_st(rs.getInt("dr_mrep_st"));
				doVO2.setDr_vrep_st(rs.getInt("dr_vrep_st"));
				doVO2.setDr_mrep_de(rs.getString("dr_mrep_de"));
				doVO2.setDr_vrep_de(rs.getString("dr_vrep_de"));
				doVO2.setDr_mrep_res(rs.getString("dr_mrep_res"));
				doVO2.setDr_vrep_res(rs.getString("dr_vrep_res"));
				doVO2.setDr_rev_star(rs.getInt("dr_rev_star"));
				doVO2.setDr_rev_con(rs.getString("dr_rev_con"));
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
		return doVO2;
	}
	
public DressOrderVO findLatestOrder(String membre_id) {
		
		DressOrderVO doVO2 = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pw);
			pstmt = con.prepareStatement(GET_LATEST);

			pstmt.setString(1, membre_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				doVO2 = new DressOrderVO();
				doVO2.setDrord_id(rs.getString("drord_id"));
				doVO2.setMembre_id(rs.getString("membre_id"));
				doVO2.setVender_id(rs.getString("vender_id"));
				doVO2.setDrord_time(rs.getTimestamp("drord_time"));
				doVO2.setDrord_pr(rs.getInt("drord_pr"));
				doVO2.setDrord_depo(rs.getInt("drord_depo"));
				doVO2.setDrord_ini(rs.getInt("drord_ini"));
				doVO2.setDrord_pay_st(rs.getInt("drord_pay_st"));
				doVO2.setDrord_fin_st(rs.getInt("drord_fin_st"));
				doVO2.setDr_mrep_st(rs.getInt("dr_mrep_st"));
				doVO2.setDr_vrep_st(rs.getInt("dr_vrep_st"));
				doVO2.setDr_mrep_de(rs.getString("dr_mrep_de"));
				doVO2.setDr_vrep_de(rs.getString("dr_vrep_de"));
				doVO2.setDr_mrep_res(rs.getString("dr_mrep_res"));
				doVO2.setDr_vrep_res(rs.getString("dr_vrep_res"));
				doVO2.setDr_rev_star(rs.getInt("dr_rev_star"));
				doVO2.setDr_rev_con(rs.getString("dr_rev_con"));
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
		return doVO2;
	}
	
	
	@Override
	public List<DressOrderVO> getAll() {
		List<DressOrderVO> list = new ArrayList<DressOrderVO>();
		DressOrderVO doVO3 = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				doVO3 = new DressOrderVO();
				doVO3.setDrord_id(rs.getString("drord_id"));
				doVO3.setMembre_id(rs.getString("membre_id"));
				doVO3.setVender_id(rs.getString("vender_id"));
				doVO3.setDrord_time(rs.getTimestamp("drord_time"));
				doVO3.setDrord_pr(rs.getInt("drord_pr"));
				doVO3.setDrord_depo(rs.getInt("drord_depo"));
				doVO3.setDrord_ini(rs.getInt("drord_ini"));
				doVO3.setDrord_pay_st(rs.getInt("drord_pay_st"));
				doVO3.setDrord_fin_st(rs.getInt("drord_fin_st"));
				doVO3.setDr_mrep_st(rs.getInt("dr_mrep_st"));
				doVO3.setDr_vrep_st(rs.getInt("dr_vrep_st"));
				doVO3.setDr_mrep_de(rs.getString("dr_mrep_de"));
				doVO3.setDr_vrep_de(rs.getString("dr_vrep_de"));
				doVO3.setDr_mrep_res(rs.getString("dr_mrep_res"));
				doVO3.setDr_vrep_res(rs.getString("dr_vrep_res"));
				doVO3.setDr_rev_star(rs.getInt("dr_rev_star"));
				doVO3.setDr_rev_con(rs.getString("dr_rev_con"));
				list.add(doVO3);
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

	public List<DressOrderVO> findByMembre(String membre_id) {
		List<DressOrderVO> list = new ArrayList<DressOrderVO>();
		DressOrderVO doVO3 = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			
			pstmt = con.prepareStatement(FIND_BY_MEMBRE_STMT);
			pstmt.setString(1, membre_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				doVO3 = new DressOrderVO();
				doVO3.setDrord_id(rs.getString("drord_id"));
				doVO3.setMembre_id(rs.getString("membre_id"));
				doVO3.setVender_id(rs.getString("vender_id"));
				doVO3.setDrord_time(rs.getTimestamp("drord_time"));
				doVO3.setDrord_pr(rs.getInt("drord_pr"));
				doVO3.setDrord_depo(rs.getInt("drord_depo"));
				doVO3.setDrord_ini(rs.getInt("drord_ini"));
				doVO3.setDrord_pay_st(rs.getInt("drord_pay_st"));
				doVO3.setDrord_fin_st(rs.getInt("drord_fin_st"));
				doVO3.setDr_mrep_st(rs.getInt("dr_mrep_st"));
				doVO3.setDr_vrep_st(rs.getInt("dr_vrep_st"));
				doVO3.setDr_mrep_de(rs.getString("dr_mrep_de"));
				doVO3.setDr_vrep_de(rs.getString("dr_vrep_de"));
				doVO3.setDr_mrep_res(rs.getString("dr_mrep_res"));
				doVO3.setDr_vrep_res(rs.getString("dr_vrep_res"));
				doVO3.setDr_rev_star(rs.getInt("dr_rev_star"));
				doVO3.setDr_rev_con(rs.getString("dr_rev_con"));
				list.add(doVO3);
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
	
	public List<DressOrderVO> findByVender(String vender_id) {
		List<DressOrderVO> list = new ArrayList<DressOrderVO>();
		DressOrderVO doVO3 = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			
			pstmt = con.prepareStatement(FIND_BY_VENDER_STMT);
			pstmt.setString(1, vender_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				doVO3 = new DressOrderVO();
				doVO3.setDrord_id(rs.getString("drord_id"));
				doVO3.setMembre_id(rs.getString("membre_id"));
				doVO3.setVender_id(rs.getString("vender_id"));
				doVO3.setDrord_time(rs.getTimestamp("drord_time"));
				doVO3.setDrord_pr(rs.getInt("drord_pr"));
				doVO3.setDrord_depo(rs.getInt("drord_depo"));
				doVO3.setDrord_ini(rs.getInt("drord_ini"));
				doVO3.setDrord_pay_st(rs.getInt("drord_pay_st"));
				doVO3.setDrord_fin_st(rs.getInt("drord_fin_st"));
				doVO3.setDr_mrep_st(rs.getInt("dr_mrep_st"));
				doVO3.setDr_vrep_st(rs.getInt("dr_vrep_st"));
				doVO3.setDr_mrep_de(rs.getString("dr_mrep_de"));
				doVO3.setDr_vrep_de(rs.getString("dr_vrep_de"));
				doVO3.setDr_mrep_res(rs.getString("dr_mrep_res"));
				doVO3.setDr_vrep_res(rs.getString("dr_vrep_res"));
				doVO3.setDr_rev_star(rs.getInt("dr_rev_star"));
				doVO3.setDr_rev_con(rs.getString("dr_rev_con"));
				list.add(doVO3);
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
		DressOrderDAO doDAO= new DressOrderDAO();
		
//		1.insert:
//		DressOrderVO doVO = new DressOrderVO();
//		doVO.setMembre_id("M003");
//		doVO.setVender_id("V003");
//		doVO.setDrord_pr(50000);
//		doVO.setDrord_depo(15000);
//		doVO.setDrord_ini(15000);
//		doVO.setDrord_pay_st(0);
//		doVO.setDrord_fin_st(1);
//		doVO.setDr_mrep_st(0);
//		doVO.setDr_vrep_st(0);
//		doDAO.insert(doVO);
//		System.out.println("新增成功");
		
//		2.update:(針對WDO002修改狀態)
//		DressOrderVO doVO2 = new DressOrderVO();
//		doVO2.setDrord_id("WDO002");
//		doVO2.setDrord_pay_st(8);
//		doVO2.setDrord_fin_st(3);
//		doVO2.setDr_mrep_st(2);
//		doVO2.setDr_vrep_st(3);
//		doVO2.setDr_mrep_de("會員檢舉細節");
//		doVO2.setDr_vrep_de("廠商檢舉細節");
//		doVO2.setDr_mrep_res("會員檢舉成功");
//		doVO2.setDr_vrep_res("廠商檢舉失敗");
//		doVO2.setDr_rev_star(3);
//		doVO2.setDr_rev_con("廠商的服務態度不是很好，提出檢舉。");
//		doDAO.update(doVO2);
//		System.out.println("修改成功");
	
//		3.findbyPK:
//		DressOrderVO doVO3 = doDAO.findByPrimaryKey("WDO001");
//		System.out.print(doVO3.getDrord_id()+",");
//		System.out.print(doVO3.getMembre_id()+",");
//		System.out.print(doVO3.getVender_id()+",");
//		System.out.print(doVO3.getDrord_time()+",");
//		System.out.print(doVO3.getDrord_pr()+",");
//		System.out.print(doVO3.getDrord_depo()+"\n");
//		System.out.print(doVO3.getDrord_ini()+",");
//		System.out.print(doVO3.getDrord_pay_st()+",");
//		System.out.print(doVO3.getDrord_fin_st()+",");
//		System.out.print(doVO3.getDr_mrep_st()+",");
//		System.out.print(doVO3.getDr_vrep_st()+",");
//		System.out.print(doVO3.getDr_mrep_de()+",");
//		System.out.print(doVO3.getDr_vrep_de()+",");
//		System.out.print(doVO3.getDr_mrep_res()+",");
//		System.out.print(doVO3.getDr_vrep_res()+",");
//		
//		System.out.print(doVO3.getDr_rev_star()+",");
//		System.out.print(doVO3.getDr_rev_con()+"\n");
		
//		4.getAll:查詢所有DressOrder
//		List<DressOrderVO> list = doDAO.getAll();
//		for(DressOrderVO doVO4:list) {
//			System.out.print(doVO4.getDrord_id()+",");
//			System.out.print(doVO4.getMembre_id()+",");
//			System.out.print(doVO4.getVender_id()+",");
//			System.out.print(doVO4.getDrord_time()+",");
//			System.out.print(doVO4.getDrord_pr()+",");
//			System.out.print(doVO4.getDrord_depo()+",");
//			
//			System.out.print(doVO4.getDrord_ini()+",");
//			System.out.print(doVO4.getDrord_pay_st()+",");
//			System.out.print(doVO4.getDrord_fin_st()+",");
//			System.out.print(doVO4.getDr_mrep_st()+",");
//			System.out.print(doVO4.getDr_vrep_st()+",");
//			System.out.print(doVO4.getDr_mrep_de()+",");
//			System.out.print(doVO4.getDr_vrep_de()+",");
//			System.out.print(doVO4.getDr_mrep_res()+",");
//			System.out.print(doVO4.getDr_vrep_res()+",");
//			
//			System.out.print(doVO4.getDr_rev_star()+",");
//			System.out.print(doVO4.getDr_rev_con()+"\n");
//		} 
//		System.out.println("查詢成功!");
		
//		5.getLatest
		DressOrderVO doVO4 = doDAO.findLatestOrder("M004");
		System.out.print(doVO4.getDrord_id()+",");
		System.out.print(doVO4.getMembre_id()+",");
		System.out.print(doVO4.getVender_id()+",");
		System.out.print(doVO4.getDrord_time()+",");
		System.out.print(doVO4.getDrord_pr()+",");
		
//		6.getByMembre
//		List<DressOrderVO> list = doDAO.findByMembre("M004");
//		for(DressOrderVO doVO4:list) {
//		System.out.print(doVO4.getDrord_id()+",");
//		System.out.print(doVO4.getMembre_id()+",");
//		System.out.print(doVO4.getVender_id()+",");
//		System.out.print(doVO4.getDrord_time()+",");
//		System.out.print(doVO4.getDrord_pr()+","+"\n");
//		}
		
//		7.getByVender
//		List<DressOrderVO> list = doDAO.findByVender("V007");
//		for(DressOrderVO doVO4:list) {
//		System.out.print(doVO4.getDrord_id()+",");
//		System.out.print(doVO4.getMembre_id()+",");
//		System.out.print(doVO4.getVender_id()+",");
//		System.out.print(doVO4.getDrord_time()+",");
//		System.out.print(doVO4.getDrord_pr()+","+"\n");
//		}
	}
}
