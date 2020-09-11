package com.pinthepost.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ad.model.AdVO;
import com.pinthepost.model.PtpDAO_interface;
import com.pinthepost.model.PtpVO;


public class PtpDAO implements PtpDAO_interface {
	
	private static DataSource ds = null;
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G1";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO PINTHEPOST (ptp_id, ptp_detail, ptp_subject, ptp_date) "
			+ "VALUES ('PTP'|| LPAD(TO_CHAR(SEQ_PTP_ID.NEXTVAL),3 ,'0'), ?,?,CURRENT_TIMESTAMP)";
	private static final String UPDATE = 
			"UPDATE PINTHEPOST set ptp_detail=?, ptp_subject=? where ptp_id= ?";
	private static final String GET_ONE_STMT =
			"SELECT * FROM PINTHEPOST where ptp_id= ?";
	private static final String GET_ALL_STMT =
			"SELECT * FROM PINTHEPOST ORDER BY ptp_id";
	
public static void main(String[] args) {
	
		PtpDAO dao = new PtpDAO();
		
		//insert
//		PtpVO ptpVO1 = new PtpVO();
//		ptpVO1.setPtp_id("PTP500");
//		ptpVO1.setPtp_detail("夏夜晚風演唱會");
//		ptpVO1.setPtp_subject("五百來了");
//		ptpVO1.setPtp_date(null);
//		
//		dao.insert(ptpVO1);
//		System.out.println("=====================");
		
		//update
//		PtpVO ptpVO2 = new PtpVO();
//		ptpVO2.setPtp_id("PTP004");
//		ptpVO2.setPtp_detail("演唱會");
//		ptpVO2.setPtp_subject("來了五百");
//		
//		
//		dao.update(ptpVO2);
		
		//get one
//		PtpVO ptpVO3 = dao.findByPrimaryKey("PTP001");
//		System.out.println(ptpVO3.getPtp_id()+",");
//		System.out.println(ptpVO3.getPtp_detail()+",");
//		System.out.println(ptpVO3.getPtp_subject()+",");
//		System.out.println(ptpVO3.getPtp_date()+",");
//		System.out.println("=====================");
		
		
		
		//get all
		List<PtpVO> list = dao.getAll();
		for (PtpVO ptp : list) {
			System.out.print(ptp.getPtp_id() + ",");
			System.out.print(ptp.getPtp_detail() + ",");
			System.out.print(ptp.getPtp_subject() + ",");
			System.out.print(ptp.getPtp_date() + ",");
			System.out.println();
		}
	}
	
	
	@Override
	public void insert(PtpVO ptpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, ptpVO.getPtp_detail());
			pstmt.setString(2, ptpVO.getPtp_subject());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(PtpVO ptpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, ptpVO.getPtp_detail());
			pstmt.setString(2, ptpVO.getPtp_subject());
			pstmt.setString(3, ptpVO.getPtp_id());

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
	public PtpVO findByPrimaryKey(String ptp_id) {
		PtpVO ptpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ptp_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ptpVO = new PtpVO();
				ptpVO.setPtp_id(rs.getString("ptp_id"));
				ptpVO.setPtp_detail(rs.getString("ptp_detail"));
				ptpVO.setPtp_subject(rs.getString("ptp_subject"));
				ptpVO.setPtp_date(rs.getTimestamp("ptp_date"));
				
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

		return ptpVO;
	}
	
	@Override
	public List<PtpVO> getAll() {
			List<PtpVO> list = new ArrayList<PtpVO>();
			PtpVO ptpVO = null;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					ptpVO = new PtpVO();
					ptpVO.setPtp_id(rs.getString("ptp_id"));
					ptpVO.setPtp_detail(rs.getString("ptp_detail"));
					ptpVO.setPtp_subject(rs.getString("ptp_subject"));
					ptpVO.setPtp_date(rs.getTimestamp("ptp_date"));
					
					list.add(ptpVO);
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
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
