package com.adm.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vender.model.VenderVO;

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
			"";
	//刪除管理員
	private static final String DELETE_ADM = 
			"";	
	//用單個管理員編號查資料
	private static final String GET_ONE_ADM =
			"SELECT ADM_ID,ADM_ACCOUNT,ADM_PWD,ADM_NAME FROM ADM WHERE ADM_ID = ?";
	
	//用單個管理員帳號查資料(登入用)
	private static final String GET_ONO_BYADM_ACCOUNT =
			"SELECT ADM_ID,ADM_ACCOUNT,ADM_PWD,ADM_NAME FROM ADM WHERE ADM_ACCOUNT = ?";
	
	//查詢全部管理員帳號(登入用)
	private static final String GET_ALL_ADM_ACCOUNT =
			"SELECT ADM_ACCOUNT FROM ADM";
	
	//查詢全部管理員
	private static final String GET_ALL_ADM =
			"SELECT ADM_ID,ADM_ACCOUNT,ADM_PWD,ADM_NAME FROM ADM order by ADM_ID";
	
	public static void main(String[]args) {
		
		AdmJDBCDAO dao = new AdmJDBCDAO();
		
//		//新增後臺管理員
//		AdmVO admvo1 = new AdmVO();
//		
//		admvo1.setAdm_account("12345");//帳號
//		admvo1.setAdm_pwd("12345");//密碼
//		admvo1.setAdm_name("00狗");//管理員名稱
//		
//		dao.insert(admvo1);
		
//		//用單個管理員編號查資料
//		AdmVO admvo2 = dao.findByPrimaryKey("ADM001");	
//		
//		System.out.println("管理人id:"+admvo2.getAdm_id());
//		System.out.println("帳號:"+admvo2.getAdm_account());
//		System.out.println("密碼:"+admvo2.getAdm_pwd());
//		System.out.println("姓名:"+admvo2.getAdm_name());
		
//		//用單個管理員帳號查資料
//		AdmVO admvo3 = dao.getOneByAdm("777");
//		
//		System.out.println("管理人id:"+admvo3.getAdm_id());
//		System.out.println("帳號:"+admvo3.getAdm_account());
//		System.out.println("密碼:"+admvo3.getAdm_pwd());
//		System.out.println("姓名:"+admvo3.getAdm_name());
		
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
		// TODO Auto-generated method stub
		
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
