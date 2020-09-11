package com.forum.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ForumDAO implements ForumDAO_interface {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G1";
	String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO FORUM (forum_id, membre_id, forum_class, forum_title, forum_content, forum_addtime) "
			+ "VALUES('F'|| LPAD(TO_CHAR(FORUM_SEQ.NEXTVAL),3 ,'0'), ?, ?, ?, ?, SYSDATE)";
	private static final String GET_ALL_STMT = "SELECT * FROM FORUM ORDER BY forum_id DESC";
	private static final String GET_ONE_STMT = "SELECT * FROM FORUM WHERE forum_id = ?";
	private static final String DELETE = "DELETE FROM FORUM WHERE forum_id=?";
	private static final String UPDATE = "UPDATE FORUM SET membre_id=?, forum_class=?, forum_title=?, forum_content=?  WHERE forum_id=?";

	@Override
	public void insert(ForumVO forumVO) {
		
		Connection  con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, forumVO.getMembre_id());
			pstmt.setString(2, forumVO.getForum_class());
			pstmt.setString(3, forumVO.getForum_title());
			pstmt.setString(4, forumVO.getForum_content());
			
			pstmt.executeUpdate();
			System.out.println("insert success");
			
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
	public void update(ForumVO forumVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, forumVO.getMembre_id());
			pstmt.setString(2, forumVO.getForum_class());
			pstmt.setString(3, forumVO.getForum_title());
			pstmt.setString(4, forumVO.getForum_content());
			pstmt.setString(5, forumVO.getForum_id());
			
			pstmt.executeUpdate();
			System.out.println("update success");
			
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
	public void delete(String forum_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, forum_id);
			
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			
			System.out.println("delete success");
			
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
	public ForumVO findByPrimaryKey(String forum_id) {
		
		ForumVO forumVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, forum_id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				forumVO = new ForumVO();
				forumVO.setForum_id(rs.getString("forum_id"));
				forumVO.setMembre_id(rs.getString("membre_id"));
				forumVO.setForum_class(rs.getString("forum_class"));
				forumVO.setForum_title(rs.getString("forum_title"));
				forumVO.setForum_content(rs.getString("forum_content"));
				forumVO.setForum_addtime(rs.getTimestamp("forum_addtime"));
			}
			System.out.println("getOne success");
			
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
		return forumVO;
	}

	@Override
	public List<ForumVO> getAll() {
		
		List<ForumVO> list = new ArrayList<ForumVO>();
		ForumVO forumVO =null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				forumVO = new ForumVO();
				forumVO.setForum_id(rs.getString("forum_id"));
				forumVO.setMembre_id(rs.getString("membre_id"));
				forumVO.setForum_class(rs.getString("forum_class"));
				forumVO.setForum_title(rs.getString("forum_title"));
				forumVO.setForum_content(rs.getString("forum_content"));
				forumVO.setForum_addtime(rs.getTimestamp("forum_addtime"));
				list.add(forumVO);
			}
//			System.out.println("getAll success");
			
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
		
		ForumDAO dao = new ForumDAO();
		
//insert
//		ForumVO forumVO1 = new ForumVO();
//		forumVO1.setMembre_id("M001");
//		forumVO1.setForum_class("test");
//		forumVO1.setForum_title("title");
//		forumVO1.setForum_content("content");
//		dao.insert(forumVO1);

		//update
//		ForumVO forumVO2 = new ForumVO();
//		forumVO2.setForum_id("F001");
//		forumVO2.setMembre_id("M001");
//		forumVO2.setForum_class("update");
//		forumVO2.setForum_title("update");
//		forumVO2.setForum_content("content");
//		dao.update(forumVO2);
				
		
//getAll
	List<ForumVO> list	= dao.getAll();
	for(ForumVO flist :list) {
		System.out.println(flist.getForum_id());
		System.out.println(flist.getMembre_id());
		System.out.println(flist.getForum_class());
		System.out.println(flist.getForum_title());
		System.out.println(flist.getForum_content());
		System.out.println(flist.getForum_addtime());
		System.out.println("====================");
		}
	

	}
}
