package com.wporder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class WPOrderJDBCDAO implements WPOrderDAO_Interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "vita";
	String passwd = "123456";
	
	//sql指令區
	private static final String GET_ALL_STMT = 
			"SELECT * FROM WED_PHOTO_ORDER order by WED_PHOTO_ORDER_NO";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM WED_PHOTO_ORDER WHERE WED_PHOTO_ORDER_NO = ?";
	private static final String INSERT_STMT = 
			"INSERT INTO wed_photo_order(wed_photo_order_no,membre_id,vender_id,filming_time,wed_photo_odtime,order_explain)\r\n" + 
			"	VALUES('WPO' || lpad(WPO_SEQ.NEXTVAL, 3, '0'),?,?,?,?,?)";
	
	//改訂單狀態 1成立(預設) - 2取消 - 3完成
	private static final String CANCEL = "UPDATE wed_photo_order SET order_status = 2 WHERE wed_photo_order_no = ?";
	private static final String COMPLETE = "UPDATE wed_photo_order SET order_status = 3 WHERE wed_photo_order_no = ?";
	
	private static final String UPDAET_MEM_REPORT ="UPDATE wed_photo_order SET WP_MREP_S = 1, WP_MREP_D =? WHERE wed_photo_order_no = ?";
	private static final String UPDAET_VEN_REPORT ="UPDATE wed_photo_order SET WP_VREP_S = 1, WP_VREP_D =? WHERE wed_photo_order_no = ?";
	@Override
	public void insert(WPOrderVO WPOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			String[] cols = { "WED_PHOTO_ORDER_NO" }; // 或 int cols[] = {1};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			pstmt.setString(1, WPOrderVO.getMembre_id());
			pstmt.setString(2, WPOrderVO.getVender_id());
			pstmt.setTimestamp(3, WPOrderVO.getFilming_time());
			pstmt.setTimestamp(4, WPOrderVO.getWed_photo_odtime());
			pstmt.setString(5, WPOrderVO.getOrder_explain());
			pstmt.executeUpdate();
			System.out.println("WPOrderDAO : 新增訂單成功!");
			
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						String key = rs.getString(i);
						WPOrderVO.setWed_photo_order_no(key);
						System.out.println("自增主鍵值 = " + key + "(剛新增成功的訂單編號)");
					}
				} while (rs.next());
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}

			rs.close();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public List<WPOrderVO> getAll() {

		List<WPOrderVO> list = new ArrayList<WPOrderVO>();		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			stmt = con.createStatement();
			rs = stmt.executeQuery(GET_ALL_STMT);
			System.out.println("Connecting to database successfully! (連線成功！)");
			while (rs.next()) {
				
				WPOrderVO WPOrderVO = new WPOrderVO();
				WPOrderVO.setWed_photo_order_no(rs.getString("wed_photo_order_no"));
				WPOrderVO.setMembre_id(rs.getString("membre_id"));
				WPOrderVO.setVender_id(rs.getString("vender_id"));
				WPOrderVO.setFilming_time(rs.getTimestamp("filming_time"));
				WPOrderVO.setWed_photo_odtime(rs.getTimestamp("wed_photo_odtime"));	//5
				WPOrderVO.setOrder_status(rs.getInt("order_status"));
				WPOrderVO.setOrder_explain(rs.getString("order_explain"));
				WPOrderVO.setReview_star(rs.getInt("review_star"));
				WPOrderVO.setReview_content(rs.getString("review_content"));
				WPOrderVO.setWp_pay_s(rs.getInt("wp_pay_s")); //10
				WPOrderVO.setWp_vrep_s(rs.getInt("wp_vrep_s"));
				WPOrderVO.setWp_mrep_s(rs.getInt("wp_mrep_s"));
				WPOrderVO.setWp_vrep_d(rs.getString("wp_vrep_d"));
				WPOrderVO.setWp_mrep_d(rs.getString("wp_mrep_d"));	
				WPOrderVO.setWp_vrep_r(rs.getString("wp_vrep_r"));
				WPOrderVO.setWp_mrep_r(rs.getString("wp_mrep_r"));
				
				list.add(WPOrderVO); // Store the row in the list
			}
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
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
	public WPOrderVO getOne(String wed_photo_order_no) {
		WPOrderVO WPOrderVO = new WPOrderVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			System.out.println("WPOrderVO 連線成功!");
			pstmt.setString(1, wed_photo_order_no);			
			pstmt.executeUpdate();
			System.out.println("WPOrderVO 查詢成功!");
			rs = pstmt.executeQuery();
			while (rs.next()) {				
				WPOrderVO.setWed_photo_order_no(rs.getString("wed_photo_order_no"));
				WPOrderVO.setMembre_id(rs.getString("membre_id"));
				WPOrderVO.setVender_id(rs.getString("vender_id"));
				WPOrderVO.setFilming_time(rs.getTimestamp("filming_time"));
				WPOrderVO.setWed_photo_odtime(rs.getTimestamp("wed_photo_odtime"));	//5
				WPOrderVO.setOrder_status(rs.getInt("order_status"));
				WPOrderVO.setOrder_explain(rs.getString("order_explain"));
				WPOrderVO.setReview_star(rs.getInt("review_star"));
				WPOrderVO.setReview_content(rs.getString("review_content"));
				WPOrderVO.setWp_pay_s(rs.getInt("wp_pay_s")); //10
				WPOrderVO.setWp_vrep_s(rs.getInt("wp_vrep_s"));
				WPOrderVO.setWp_mrep_s(rs.getInt("wp_mrep_s"));
				WPOrderVO.setWp_vrep_d(rs.getString("wp_vrep_d"));
				WPOrderVO.setWp_mrep_d(rs.getString("wp_mrep_d"));	
				WPOrderVO.setWp_vrep_r(rs.getString("wp_vrep_r"));
				WPOrderVO.setWp_mrep_r(rs.getString("wp_mrep_r"));
				
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
		return WPOrderVO;
	}


	@Override
	public void Mem_Report(WPOrderVO WPOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDAET_MEM_REPORT);
			pstmt.setString(1, WPOrderVO.getWp_mrep_d());
			pstmt.setString(2, WPOrderVO.getWed_photo_order_no());
			pstmt.executeUpdate();
			System.out.println("會員的 - 檢舉狀態 - 檢舉內容 - 更改成功");
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void Ven_Report(WPOrderVO WPOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDAET_VEN_REPORT);
			pstmt.setString(1, WPOrderVO.getWp_vrep_d());
			pstmt.setString(2, WPOrderVO.getWed_photo_order_no());
			pstmt.executeUpdate();
			System.out.println("廠商的 - 檢舉狀態 - 檢舉內容 - 更改成功");
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void cancel_order(String wed_photo_order_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(CANCEL);
			pstmt.setString(1, wed_photo_order_no);			
			pstmt.executeUpdate();
			System.out.println("訂單狀態更改成功");
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void complete_order(WPOrderVO WPOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(COMPLETE);
			pstmt.setString(1, WPOrderVO.getWed_photo_order_no());			
			pstmt.executeUpdate();
			System.out.println("訂單狀態更改成功 - 完成");
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	
	public static void main(String args[]) {
		WPOrderJDBCDAO dao = new WPOrderJDBCDAO();
		
		//查詢一筆訂單
//		List<WPOrderVO> list = dao.getOne("WPO001");	
//		for (WPOrderVO vo : list) {
//			System.out.print(vo.getWed_photo_order_no() + ",");
//			System.out.print(vo.getMembre_id() + ",");
//			System.out.print(vo.getVender_id() + ",");
//			System.out.print(vo.getFilming_time() + ",");
//			System.out.print(vo.getWed_photo_odtime() + ",");
//			System.out.print(vo.getOrder_status() + ",");
//			System.out.print(vo.getOrder_explain());
//		}
		
		//取消訂單
//		dao.cancel_order("WPO028");
		
		//會員檢舉
		WPOrderVO vo = new WPOrderVO();
		vo.setWp_mrep_d("123");
		vo.setWed_photo_order_no("WPO019");
		dao.Mem_Report(vo);
		
		//廠商檢舉
		WPOrderVO vo1 = new WPOrderVO();
		vo1.setWp_vrep_d("12345");
		vo1.setWed_photo_order_no("WPO019");
		dao.Ven_Report(vo1);
	}

	
	@Override
	public void Mem_Review(WPOrderVO WPOrderVO) {
		// TODO Auto-generated method stub
		
	}

}
