package com.adm.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdmJDBCDAO implements AdmDAO_interface{

	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url="jdbc:oracle:thin:@localhost:1521:XE";
	private static final String userid = "EA102G1";
	private static final String passwd = "123456";

	//新增管理員
	private static final String ADD_ADM =
			"INSERT INTO ADM(ADM_ID, ADM_ACCOUNT, ADM_PWD, ADM_NAME,ADM_0,ADM_1,ADM_2,ADM_3)"+
			"VALUES('ADM'|| LPAD(SEQ_ADM_ID.NEXTVAL, 3,'0'),?,?,?,0,0,0,0)";
	//修改管理員
	private static final String UPDATE_ADM =
			"UPDATE ADM SET ADM_ACCOUNT=?,ADM_PWD=?,ADM_NAME=?,ADM_1=?,ADM_2=?,ADM_3=? WHERE ADM_ID=?";
	//刪除管理員
	private static final String DELETE_ADM = 
			"";	
	//用單個管理員編號查資料
	private static final String GET_ONE_ADM =
			"SELECT ADM_ID,ADM_ACCOUNT,ADM_PWD,ADM_NAME FROM ADM WHERE ADM_ID = ?";
	
	//用單個管理員帳號查資料(登入用)
	private static final String GET_ONO_BYADM_ACCOUNT =
			"SELECT ADM_ID,ADM_ACCOUNT,ADM_PWD,ADM_NAME,ADM_0,ADM_1,ADM_2,ADM_3 FROM ADM WHERE ADM_ACCOUNT = ?";
	
	//查詢全部管理員帳號(登入用)
	private static final String GET_ALL_ADM_ACCOUNT =
			"SELECT ADM_ACCOUNT FROM ADM";
	
	//查詢全部管理員
	private static final String GET_ALL_ADM =
			"SELECT ADM_ID,ADM_ACCOUNT,ADM_PWD,ADM_NAME,ADM_0,ADM_1,ADM_2,ADM_3 FROM ADM order by ADM_ID";
	
	public static void main(String[]args) {
		
//		String adm_account = "87";
//		String adm_pwd = "234";
//		String adm_name ="145";
//		String adm_id ="ADM002";
//		String adm1 = "1";
//		String adm2 = "1";
//		String adm3 = "1";
//		int adm_1 = Integer.parseInt(adm1);
//		int adm_2 = Integer.parseInt(adm2);
//		int adm_3 = Integer.parseInt(adm3);
//		
		AdmJDBCDAO dao = new AdmJDBCDAO();
//		
//		System.out.println(adm_account);
//		System.out.println(adm_pwd);
//		System.out.println(adm_name);
//		System.out.println(adm_1);
//		System.out.println(adm_2);
//		System.out.println(adm_3);
//		AdmVO admVO = new AdmVO();
//		admVO.setAdm_account(adm_account);
//		admVO.setAdm_pwd(adm_pwd);
//		admVO.setAdm_name(adm_name);
//		admVO.setAdm_1(adm_1);
//		admVO.setAdm_2(adm_2);
//		admVO.setAdm_3(adm_3);	
//		admVO.setAdm_id(adm_id);
//		
//		AdmService admSvc = new AdmService();
//		admVO = admSvc.update_adm(adm_account,adm_pwd,adm_name,adm_1,adm_2,adm_3,adm_id);

		
//		//新增後臺管理員
//		AdmVO admvo1 = new AdmVO();
//		
//		admvo1.setAdm_account("12345");//帳號
//		admvo1.setAdm_pwd("12345");//密碼
//		admvo1.setAdm_name("00狗");//管理員名稱
//		
//		dao.insert(admvo1);
		
//		//修改後臺管理員
//		AdmVO admVO0 = new AdmVO();
//		
//		admVO0.setAdm_account("12345");
//		admVO0.setAdm_pwd("123");
//		admVO0.setAdm_name("123");
//		admVO0.setAdm_1(0);
//		admVO0.setAdm_2(1);
//		admVO0.setAdm_3(0);
//		admVO0.setAdm_id("ADM002");
//		dao.update(admVO0);
		
//		//用單個管理員編號查資料
//		AdmVO admvo2 = dao.findByPrimaryKey("ADM001");	
//		
//		System.out.println("管理人id:"+admvo2.getAdm_id());
//		System.out.println("帳號:"+admvo2.getAdm_account());
//		System.out.println("密碼:"+admvo2.getAdm_pwd());
//		System.out.println("姓名:"+admvo2.getAdm_name());
		
//		//用單個管理員帳號查資料
//		AdmVO admvo3 = dao.getOneByAdm("8777");
//		
//		System.out.println("管理人id:"+admvo3.getAdm_id());
//		System.out.println("帳號:"+admvo3.getAdm_account());
//		System.out.println("密碼:"+admvo3.getAdm_pwd());
//		System.out.println("姓名:"+admvo3.getAdm_name());
//		System.out.println("權限:"+admvo3.getAdm_0());
//		System.out.println("權限:"+admvo3.getAdm_1());
//		System.out.println("權限:"+admvo3.getAdm_2());
//		System.out.println("權限:"+admvo3.getAdm_3());
		
//		//查全部管理員帳號
//		
//		System.out.println(dao.getAlladm_account());
		
//		//查全部管理員
//		List<AdmVO> list = dao.getAll();
//	
//		for(AdmVO admvo : list) {
//			System.out.println("管理人id:"+admvo.getAdm_id());
//			System.out.println("帳號:"+admvo.getAdm_account());
//			System.out.println("密碼:"+admvo.getAdm_pwd());
//			System.out.println("姓名:"+admvo.getAdm_name());
//			System.out.println("權限:"+admvo.getAdm_0());
//			System.out.println("權限:"+admvo.getAdm_1());
//			System.out.println("權限:"+admvo.getAdm_2());
//			System.out.println("權限:"+admvo.getAdm_3());
//			System.out.println();
//		}		
		
	}
	
	
	@Override
	public void insert(AdmVO admVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(ADD_ADM);

//ADM_ID, ADM_ACCOUNT, ADM_PWD, ADM_NAME		
			pstmt.setString(1, admVO.getAdm_account());
			pstmt.setString(2, admVO.getAdm_pwd());
			pstmt.setString(3, admVO.getAdm_name());

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

	@Override
	public void update(AdmVO admVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_ADM);
//UPDATE ADM SET ADM_ACCOUNT=?,ADM_PWD=?,ADM_NAME=?,ADM_0=?,ADM_1=?,ADM_2=?,ADM_3=? WHERE ADM_ID=?"
			pstmt.setString(1, admVO.getAdm_account());
			pstmt.setString(2, admVO.getAdm_pwd());
			pstmt.setString(3, admVO.getAdm_name());
			pstmt.setInt(4, admVO.getAdm_1());
			pstmt.setInt(5, admVO.getAdm_2());
			pstmt.setInt(6, admVO.getAdm_3());
			pstmt.setString(7, admVO.getAdm_id());
					
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
		}
		  finally {
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
	public void delete(AdmVO admVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AdmVO findByPrimaryKey(String adm_id) {
		AdmVO admVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_ADM);

			pstmt.setString(1, adm_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				admVO = new AdmVO();
				admVO.setAdm_id(rs.getString("adm_id"));
				admVO.setAdm_account(rs.getString("adm_account"));
				admVO.setAdm_pwd(rs.getString("adm_pwd"));
				admVO.setAdm_name(rs.getString("adm_name"));				
			}

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
		return admVO;
	}
	
	@Override
	public AdmVO getOneByAdm(String adm_account) {
		  //ven_account Get_One_ByVender
		  AdmVO admVO =null;
	
		  Connection con = null;
		  PreparedStatement stmt = null;
		  ResultSet rs = null;

		  try {  Class.forName(driver);
		    con = DriverManager.getConnection(url, userid, passwd);
		    stmt = con.prepareStatement(GET_ONO_BYADM_ACCOUNT);
		   stmt.setString(1, adm_account);
		   rs = stmt.executeQuery();

			while (rs.next()) {
				admVO = new AdmVO();
				admVO.setAdm_id(rs.getString("adm_id"));
				admVO.setAdm_account(rs.getString("adm_account"));
				admVO.setAdm_pwd(rs.getString("adm_pwd"));
				admVO.setAdm_name(rs.getString("adm_name"));
				admVO.setAdm_0(rs.getInt("adm_0"));
				admVO.setAdm_1(rs.getInt("adm_1"));
				admVO.setAdm_2(rs.getInt("adm_2"));
				admVO.setAdm_3(rs.getInt("adm_3"));
			}
			
		   return admVO;

		   // Handle any driver errors
		  } catch (ClassNotFoundException e) {
		   throw new RuntimeException("Couldn't load database driver. "
		     + e.getMessage());
		   // Handle any SQL errors
		  } catch (SQLException se) {
		   throw new RuntimeException("A database error occured. "
		     + se.getMessage());
		  }
	}
	
	@Override
	public List<String> getAlladm_account() {

		  List<String> idlist = new ArrayList<String>();
		  Connection con = null;
		  PreparedStatement stmt = null;
		  ResultSet rs = null;

		  try {

		   Class.forName(driver);
		   con = DriverManager.getConnection(url, userid, passwd);
		   stmt = con.prepareStatement(GET_ALL_ADM_ACCOUNT);
		   rs = stmt.executeQuery();
		   while (rs.next()) {
		    idlist.add(rs.getString("adm_account"));
		   }
		   
		   

		  } catch (ClassNotFoundException e) {
		   throw new RuntimeException("Couldn't load database driver. "
		     + e.getMessage());
		   // Handle any SQL errors
		  } catch (SQLException se) {
		   throw new RuntimeException("A database error occured. "
		     + se.getMessage());
		  }
		  return idlist;
	}

	@Override
	public List<AdmVO> getAll() {
		List<AdmVO> list = new ArrayList<AdmVO>();	
		AdmVO admVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_ADM);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				admVO = new AdmVO();
				admVO.setAdm_id(rs.getString("adm_id"));
				admVO.setAdm_account(rs.getString("adm_account"));
				admVO.setAdm_pwd(rs.getString("adm_pwd"));
				admVO.setAdm_name(rs.getString("adm_name"));
				admVO.setAdm_0(rs.getInt("adm_0"));
				admVO.setAdm_1(rs.getInt("adm_1"));
				admVO.setAdm_2(rs.getInt("adm_2"));
				admVO.setAdm_3(rs.getInt("adm_3"));
				list.add(admVO);
			}

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



}
