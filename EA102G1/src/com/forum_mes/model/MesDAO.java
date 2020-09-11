package com.forum_mes.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MesDAO implements MesDAO_interface {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G1";
	String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO FORUM_MES (forum_mes_id, membre_id, forum_id, mes_text, mes_addtime) "
			+ "VALUES('MES'|| LPAD(TO_CHAR(FORUM_MES_SEQ.NEXTVAL),3 ,'0'), ?, ?, ?, SYSDATE)";
	private static final String GET_ALL_STMT = "SELECT * FROM FORUM_MES ORDER BY forum_mes_id";
	private static final String GET_ONE_STMT = "SELECT * FROM FORUM_MES WHERE forum_mes_id = ?";
	private static final String DELETE = "DELETE FROM FORUM_MES WHERE forum_mes_id=?";
	private static final String UPDATE = "UPDATE FORUM_MES SET membre_id=?, forum_id=?, mes_text=?, mes_addtime=SYSDATE WHERE forum_mes_id=?";
	private static final String GET_MES_BY_FORUM_ID =
			"SELECT * FROM FORUM_MES WHERE FORUM_ID=? ORDER BY FORUM_ID";
	
	@Override
	public void insert(MesVO mesVO) {
		
		Connection  con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, mesVO.getMembre_id());
			pstmt.setString(2, mesVO.getForum_id());
			pstmt.setString(3, mesVO.getMes_text());
			
			pstmt.executeUpdate();
			
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
	public void update(MesVO mesVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, mesVO.getMembre_id());
			pstmt.setString(2, mesVO.getForum_id());
			pstmt.setString(3, mesVO.getMes_text());
			pstmt.setString(4, mesVO.getForum_mes_id());
			
			pstmt.executeUpdate();
			
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
	public void delete(String forum_mes_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, forum_mes_id);
			
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
		
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
	public MesVO findByPrimaryKey(String forum_mes_id) {
		
		MesVO mesVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, forum_mes_id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				mesVO = new MesVO();
				mesVO.setForum_mes_id(rs.getString("forum_mes_id"));
				mesVO.setMembre_id(rs.getString("membre_id"));
				mesVO.setForum_id(rs.getString("forum_id"));
				mesVO.setMes_text(rs.getString("mes_text"));
				mesVO.setMes_addtime(rs.getTimestamp("mes_addtime"));
			}
			
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
		return mesVO;
	}

	@Override
	public List<MesVO> getMesByForum(String forum_id) {
		
		List<MesVO> list = new ArrayList<MesVO>();
		MesVO mesVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_MES_BY_FORUM_ID);
			
			pstmt.setString(1, forum_id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				mesVO = new MesVO();
				mesVO.setForum_mes_id(rs.getString("forum_mes_id"));
				mesVO.setMembre_id(rs.getString("membre_id"));
				mesVO.setForum_id(rs.getString("forum_id"));
				mesVO.setMes_text(rs.getString("mes_text"));
				mesVO.setMes_addtime(rs.getTimestamp("mes_addtime"));
				list.add(mesVO);
			}
			System.out.println("get success");
			
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
	
	public static void main(String[] args) {
		
		MesDAO dao = new MesDAO();
		
		//查單筆
		MesVO mesVO = dao.findByPrimaryKey("MES001");
		System.out.println(mesVO.getForum_mes_id());
		System.out.println(mesVO.getMembre_id());
		System.out.println(mesVO.getForum_id());
		System.out.println(mesVO.getMes_text());
		System.out.println(mesVO.getMes_addtime());
		System.out.println("------------------------");
		

		//getAll
		
		String forum_id = "F001";
		List<MesVO> list = dao.getMesByForum(forum_id);
		for(MesVO mlist : list) {
			System.out.println(mlist.getForum_mes_id());
			System.out.println(mlist.getMembre_id());
			System.out.println(mlist.getForum_id());
			System.out.println(mlist.getMes_text());
			System.out.println(mlist.getMes_addtime());
			System.out.println("------------------------");
			
		}
	}

}
