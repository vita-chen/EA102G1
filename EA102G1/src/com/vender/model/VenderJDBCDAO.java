package com.vender.model;

import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.vender.model.VenderVO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;


public class VenderJDBCDAO implements VenderDAO_interface{
	
//	private static DataSource ds;
//	static {
//		try {
//			Context ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestJDBC");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//	}
	
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url="jdbc:oracle:thin:@localhost:1521:XE";
	private static final String userid = "EA102G1";
	private static final String passwd = "123456";
	
	//新增
	private static final String INSERT_STMT =
			"INSERT INTO VENDER(VENDER_ID, VEN_NAME, VEN_ADDR, VEN_PHONE, VEN_CONTACT, VEN_MAIL, IS_VAILD, IS_ENABLE, VEN_EVIDENCE_PIC, VEN_ACCOUNT, VEN_PWD, VEN_SPONSOR, VEN_REGIS_TIME, VEN_REVIEW_COUNT, VEN_STARS_TOTAL)" + 
			"VALUES ('V'|| LPAD(TO_CHAR(VENDER_SEQ.NEXTVAL),3,'0'), ?,?,?,?,?, 1, 0, ?,?,?, 0, CURRENT_TIMESTAMP, 0, 0 )";

	//修改廠商資料(名稱,住址,電話,負責人,EMAIL,廠商圖片,密碼)
	private static final String UPDATE = 
			"UPDATE VENDER SET VEN_NAME=?, VEN_ADDR=?, VEN_PHONE=?, VEN_CONTACT=?, VEN_MAIL=?, VEN_EVIDENCE_PIC=?, VEN_PWD=? WHERE VENDER_ID = ?";
	
	//修改廠商驗證狀態
	private static final String UPDATE_ENABLE = 
			"UPDATE VENDER SET IS_ENABLE= 1 where VENDER_ID = ?";

	//刪除(把廠商IS_VAILD改成0停權)
	private static final String DELETE = 
			"UPDATE VENDER SET IS_VAILD = 0 where VENDER_ID = ?";

	//查單個廠商
	private static final String GET_ONE_STMT = 
			"SELECT VENDER_ID,ven_name,ven_addr,ven_phone,ven_contact,ven_mail,to_char(ven_regis_time,'yyyy-mm-dd') ven_regis_time,is_vaild,is_enable,VEN_EVIDENCE_PIC,VEN_REVIEW_COUNT,VEN_STARS_TOTAL FROM VENDER WHERE VENDER_ID = ?";
		
	//查全部廠商
	private static final String GET_ALL_STMT = 
			"SELECT vender_id,ven_name,ven_addr,ven_phone,ven_contact,ven_mail,to_char(ven_regis_time,'yyyy-mm-dd') ven_regis_time,is_vaild,is_enable FROM vender order by vender_id";
	//查全部廠商
	private static final String GET_ALL_ENABLE = 
			"SELECT * FROM VENDER WHERE is_enable = 0";
	//查全部廠商
	private static final String GET_ALL_VAILD = 
			"SELECT * FROM VENDER WHERE is_vaild = 0";
	//getOneByVender
	private static final String Get_One_ByVender=
			"select * from vender where ven_account = ?";
	//查有幾個未開通廠商
	private static final String GET_ALL_OFF=
			"select COUNT(IS_ENABLE) from vender WHERE IS_ENABLE = 0";
	private static final String GET_ALL_ADM_OFF=
			"select COUNT(ADM_1) from ADM WHERE ADM_1 = 0";
	//更新評價分數
	private static final String UPDATE_REV = "UPDATE vender set ven_review_count = ? , ven_stars_total = ? where vender_id =?";
	public static void main (String[]args) {
			
//		VenderJDBCDAO dao = new VenderJDBCDAO();
//		
//		VenderService venderSvc = new VenderService();
//		List<String> phoneList = venderSvc.getAllVen_phone();
//		if (phoneList.contains("0973-318-520")) {
//			System.out.println("被註冊囉");
//		}
//		Iterator objs = phoneList.iterator();
//		while (objs.hasNext())
//		System.out.println(objs.next());
		
//		System.out.println(dao.get_all_off());
		
//		System.out.println(dao.get_all_adm_off());
		
//		//新增廠商
//		VenderVO venderVO1 = new VenderVO();
//		
//        VenderJDBCDAO image = new VenderJDBCDAO();
//        //imageToByte是的轉圖片方法
//        //圖片路徑
//        String path = "C:\\Users\\young\\Desktop\\images\\123456.jpg";//廠商圖片
//      
//		try {
//			byte[] by = image.imageToByte(path);
//			venderVO1.setVen_name("123");//名稱
//			venderVO1.setVen_addr("123");//住址
//			venderVO1.setVen_phone("123");//電話
//			venderVO1.setVen_contact("123");//負責人
//			venderVO1.setVen_mail("123");//EMAIL
//			venderVO1.setVen_evidence_pic(by);
//			venderVO1.setVen_account("123");//帳號
//			venderVO1.setVen_pwd("123");//密碼
//			dao.insert(venderVO1);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   
		
//		// 修改廠商驗證狀態
//		VenderVO venderVO4 = new VenderVO();
//		venderVO4.setVender_id("V004");
//		dao.update_vender_enable(venderVO4);

//		//刪除(把廠商IS_VAILD改成0停權)
//		VenderVO venderVO2 = new VenderVO();
//		venderVO2.setVender_id("V001");
//		dao.delete(venderVO2);
		
		
//		//修改廠商資料(名稱,住址,電話,負責人,EMAIL,廠商圖片,密碼)      
//        VenderVO venderVO4 = new VenderVO();
//        //imageToByte是的轉圖片方法
//        VenderJDBCDAO image = new VenderJDBCDAO();
//        //圖片路徑
//        String path = "C:\\Users\\young\\Desktop\\images\\123456.jpg";//廠商圖片
//        
//        try {
//        	byte[] by = image.imageToByte(path);
//			venderVO4.setVender_id("V001");
//			venderVO4.setVen_name("12444444444444");
//			venderVO4.setVen_addr("123");
//			venderVO4.setVen_phone("123");
//			venderVO4.setVen_contact("123");
//			venderVO4.setVen_evidence_pic(by);//廠商圖片
//			venderVO4.setVen_mail("123");
//			venderVO4.setVen_pwd("123");
//			dao.update(venderVO4);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		
//		//查單個廠商
//		VenderVO venderVO3 = dao.findByPrimaryKey("V003");
//		System.out.println("廠商編號:"+venderVO3.getVender_id());
//		System.out.println("名稱:"+venderVO3.getVen_name());
//		System.out.println("住址:"+venderVO3.getVen_addr());
//		System.out.println("電話:"+venderVO3.getVen_phone());
//		System.out.println("註冊時間:"+venderVO3.getVen_regis_time());
//		System.out.println("圖:"+venderVO3.getVen_evidence_pic());
//		System.out.println("次數:"+venderVO3.getVen_review_count());
//		System.out.println("星數:"+venderVO3.getVen_stars_total()+"\n");
		
	
		

//		//查被封鎖的全部廠商
//		List<VenderVO> list = dao.get_all_blockade();
//	
//		for(VenderVO vender : list) {
//			System.out.println("名稱:"+vender.getVender_id());
//			System.out.println("住址:"+vender.getVen_name());
//			System.out.println("電話:"+vender.getVen_addr());
//			System.out.println("負責人:"+vender.getVen_phone());
//			System.out.println("EMAIL:"+vender.getVen_regis_time());
//			System.out.println("次數:"+vender.getVen_review_count());
//			System.out.println("星數:"+vender.getVen_stars_total()+"\n");
//		}
		//查所有廠商帳號
//		System.out.println(dao.getAllVender());
		
		

}

	
	@Override
	public void insert(VenderVO venderVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

//"INSERT INTO VENDER(VENDER_ID, VEN_NAME, VEN_ADDR, VEN_PHONE, VEN_CONTACT, VEN_MAIL, IS_VAILD, IS_ENABLE, VEN_EVIDENCE_PIC, VEN_ACCOUNT, VEN_PWD, VEN_SPONSOR, VEN_REGIS_TIME, VEN_REVIEW_COUNT, VEN_STARS_TOTAL)" + 
//"VALUES ('V'|| LPAD(TO_CHAR(VENDER_SEQ.NEXTVAL),3,'0'), ?,?,?,?,?, 0, 0, EMPTY_BLOB(),?,?, 0, CURRENT_TIMESTAMP, 0, 0 )";

			
			pstmt.setString(1, venderVO.getVen_name());
			pstmt.setString(2, venderVO.getVen_addr());
			pstmt.setString(3, venderVO.getVen_phone());
			pstmt.setString(4, venderVO.getVen_contact());
			pstmt.setString(5, venderVO.getVen_mail());
			pstmt.setBytes(6, venderVO.getVen_evidence_pic());	
			pstmt.setString(7, venderVO.getVen_account());
			pstmt.setString(8, venderVO.getVen_pwd());

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
	public void update(VenderVO venderVO) {


        
		Connection con = null;
		PreparedStatement pstmt = null;

		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

//"UPDATE VENDER SET VEN_NAME=?, VEN_ADDR=?, VEN_PHONE=?, VEN_CONTACT=?, VEN_MAIL=?, VEN_EVIDENCE_PIC=?, VEN_PWD=? where VENDER_ID = ?";

			pstmt.setString(1, venderVO.getVen_name());
			pstmt.setString(2, venderVO.getVen_addr());
			pstmt.setString(3, venderVO.getVen_phone());
			pstmt.setString(4, venderVO.getVen_contact());
			pstmt.setString(5, venderVO.getVen_mail());
			pstmt.setBytes(6, venderVO.getVen_evidence_pic());
			pstmt.setString(7, venderVO.getVen_pwd());
			pstmt.setString(8, venderVO.getVender_id());		

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
	public void update_vender_enable(VenderVO venderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_ENABLE);
			pstmt.setString(1, venderVO.getVender_id());
			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}  finally {
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
	public void delete(VenderVO venderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, venderVO.getVender_id());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public VenderVO findByPrimaryKey(String vender_id) {
		
		VenderVO venderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, vender_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				venderVO = new VenderVO();
				venderVO.setVender_id(rs.getString("vender_id"));
				venderVO.setVen_name(rs.getString("ven_name"));
				venderVO.setVen_addr(rs.getString("ven_addr"));
				venderVO.setVen_phone(rs.getString("ven_phone"));
				venderVO.setVen_contact(rs.getString("ven_contact"));
				venderVO.setVen_mail(rs.getString("ven_mail"));
				venderVO.setIs_vaild(rs.getInt("is_vaild"));
				venderVO.setIs_enable(rs.getInt("is_enable"));
				venderVO.setVen_regis_time(rs.getDate("ven_regis_time"));
				venderVO.setVen_evidence_pic(rs.getBytes("Ven_evidence_pic"));
				venderVO.setVen_review_count(rs.getInt("ven_review_count"));
				venderVO.setVen_stars_total(rs.getInt("ven_stars_total"));
				
				
				
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
		return venderVO;
	}

	@Override
	public List<VenderVO> getAll() {
		List<VenderVO> list = new ArrayList<VenderVO>();	
		VenderVO venderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				venderVO = new VenderVO();
				venderVO.setVender_id(rs.getString("vender_id"));
				venderVO.setVen_name(rs.getString("ven_name"));
				venderVO.setVen_addr(rs.getString("ven_addr"));
				venderVO.setVen_phone(rs.getString("ven_phone"));
				venderVO.setVen_contact(rs.getString("ven_contact"));
				venderVO.setVen_mail(rs.getString("ven_mail"));
				venderVO.setIs_vaild(rs.getInt("is_vaild"));
				venderVO.setIs_enable(rs.getInt("is_enable"));
				venderVO.setVen_regis_time(rs.getDate("ven_regis_time"));
				list.add(venderVO);
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
	
	 @Override
	 public List<String> getAllVender() {
	  List<String> idlist = new ArrayList<String>();
	  Connection con = null;
	  PreparedStatement stmt = null;
	  ResultSet rs = null;

	  try {

	   Class.forName(driver);
	   con = DriverManager.getConnection(url, userid, passwd);
	   stmt = con.prepareStatement("select ven_account from vender");
	   rs = stmt.executeQuery();
	   while (rs.next()) {
	    idlist.add(rs.getString("ven_account"));
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
	 

	 private byte[] imageToByte(String path) throws Exception {
	  FileInputStream fileInputStream = new FileInputStream(new File(path));
	  ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	  byte[] bytes = new byte[1024];
	  int len = 0;
	  while ((len = fileInputStream.read(bytes)) != -1) {
	   byteArrayOutputStream.write(bytes, 0, len);
	  }
	  fileInputStream.close();
	  return byteArrayOutputStream.toByteArray();
	 }



	 @Override
	 public VenderVO getOneByVender(String ven_account) { 
	  //ven_account Get_One_ByVender
	  VenderVO vendervo = new VenderVO();
	  
	  Connection con = null;
	  PreparedStatement stmt = null;
	  ResultSet rs = null;

	  try {  Class.forName(driver);
	    con = DriverManager.getConnection(url, userid, passwd);
	    stmt = con.prepareStatement(Get_One_ByVender);
	   stmt.setString(1, ven_account);
	   rs = stmt.executeQuery();

	   rs.next();
	   vendervo.setVender_id(rs.getString("vender_id"));
	   vendervo.setVen_name(rs.getString("ven_name"));
	   vendervo.setVen_addr(rs.getString("ven_addr"));
	   vendervo.setVen_phone(rs.getString("ven_phone"));
	   vendervo.setVen_contact(rs.getString("ven_contact"));
	   vendervo.setVen_mail(rs.getString("ven_mail"));
	   vendervo.setVen_regis_time(rs.getDate("ven_regis_time"));
	   vendervo.setVen_account(rs.getString("ven_account"));
	   vendervo.setVen_pwd(rs.getString("ven_pwd"));
	   vendervo.setIs_enable(rs.getInt("is_enable"));
	   vendervo.setIs_vaild(rs.getInt("is_vaild"));
	      
	   return vendervo;

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
	public List<VenderVO> get_all_verification() {
		List<VenderVO> list = new ArrayList<VenderVO>();	
		VenderVO venderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_ENABLE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				venderVO = new VenderVO();
				venderVO.setVender_id(rs.getString("vender_id"));
				venderVO.setVen_name(rs.getString("ven_name"));
				venderVO.setVen_addr(rs.getString("ven_addr"));
				venderVO.setVen_phone(rs.getString("ven_phone"));
				venderVO.setVen_contact(rs.getString("ven_contact"));
				venderVO.setVen_mail(rs.getString("ven_mail"));
				venderVO.setIs_vaild(rs.getInt("is_vaild"));
				venderVO.setIs_enable(rs.getInt("is_enable"));
				venderVO.setVen_regis_time(rs.getDate("ven_regis_time"));
				list.add(venderVO);
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


	@Override 
	public List<VenderVO> get_all_blockade() {
		List<VenderVO> list = new ArrayList<VenderVO>();	
		VenderVO venderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_VAILD);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				venderVO = new VenderVO();
				venderVO.setVender_id(rs.getString("vender_id"));
				venderVO.setVen_name(rs.getString("ven_name"));
				venderVO.setVen_addr(rs.getString("ven_addr"));
				venderVO.setVen_phone(rs.getString("ven_phone"));
				venderVO.setVen_contact(rs.getString("ven_contact"));
				venderVO.setVen_mail(rs.getString("ven_mail"));
				venderVO.setIs_vaild(rs.getInt("is_vaild"));
				venderVO.setIs_enable(rs.getInt("is_enable"));
				venderVO.setVen_regis_time(rs.getDate("ven_regis_time"));
				list.add(venderVO);
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
	
	
	public int get_all_off() {
		int off = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_OFF);

			
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				off=rs.getInt("COUNT(IS_ENABLE)");
		
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
		return off;
	}
	
	public int get_all_adm_off() {
		int offf = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_ADM_OFF);

			
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				offf=rs.getInt("COUNT(ADM_1)");
		
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
		return offf;
	}


	@Override
	public void update_review(VenderVO vendervo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_REV);			
			pstmt.setInt(1, vendervo.getVen_review_count());
			pstmt.setInt(2, vendervo.getVen_stars_total());
			pstmt.setString(3, vendervo.getVender_id());			
			pstmt.executeQuery();
			
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
}
