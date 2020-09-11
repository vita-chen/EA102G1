package com.wporder.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
/*地址連動 預設圖片 圖片預覽 */

public class WPOrderDAO implements WPOrderDAO_Interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_ALL_STMT = 
			"SELECT * FROM WED_PHOTO_ORDER order by WED_PHOTO_ORDER_NO";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM WED_PHOTO_ORDER WHERE WED_PHOTO_ORDER_NO = ?";
	
	private static final String INSERT_STMT = 
			"INSERT INTO wed_photo_order(wed_photo_order_no,membre_id,vender_id,filming_time,wed_photo_odtime,order_explain)\r\n" + 
			"	VALUES('WPO' || lpad(WPO_SEQ.NEXTVAL, 3, '0'),?,?,?,?,?)";
	//改訂單狀態 1成立(預設) - 2取消 - 3完成
	private static final String CANCEL = "UPDATE wed_photo_order SET order_status = 2 WHERE wed_photo_order_no = ?";
	private static final String COMPLETE = "UPDATE wed_photo_order SET order_status = 3 ,review_star=?, review_content=? WHERE wed_photo_order_no = ?";
	//會員檢舉 & 廠商檢舉
	private static final String UPDAET_MEM_REPORT ="UPDATE wed_photo_order SET WP_MREP_S = 1, WP_MREP_D =? WHERE wed_photo_order_no = ?";
	private static final String UPDAET_VEN_REPORT ="UPDATE wed_photo_order SET WP_VREP_S = 1, WP_VREP_D =? WHERE wed_photo_order_no = ?";
	
	@Override
	public void insert(WPOrderVO WPOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(GET_ALL_STMT);
//			System.out.println("Connecting to database successfully! (連線成功！)");
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
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<WPOrderVO> getOne(String wed_photo_order_no) {
		List<WPOrderVO> list = new ArrayList<WPOrderVO>();		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			System.out.println("WPOrderVO 連線成功!");
			pstmt.setString(1, wed_photo_order_no);			
			pstmt.executeUpdate();
//			System.out.println("WPOrderVO 查詢成功!");
			rs = pstmt.executeQuery();
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
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void Mem_Report(WPOrderVO WPOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDAET_MEM_REPORT);
			pstmt.setString(1, WPOrderVO.getWp_mrep_d());
			pstmt.setString(2, WPOrderVO.getWed_photo_order_no());
			pstmt.executeUpdate();
			System.out.println("會員的 - 檢舉狀態 - 檢舉內容 - 更改成功");
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDAET_VEN_REPORT);
			pstmt.setString(1, WPOrderVO.getWp_vrep_d());
			pstmt.setString(2, WPOrderVO.getWed_photo_order_no());
			pstmt.executeUpdate();
			System.out.println("廠商的 - 檢舉狀態 - 檢舉內容 - 更改成功");
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(CANCEL);
			pstmt.setString(1, wed_photo_order_no);			
			pstmt.executeUpdate();
			System.out.println("訂單狀態更改成功 - 取消訂單");
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(COMPLETE);
			if(WPOrderVO.getReview_star() == null) {
				pstmt.setString(1, "");//給空值
			}else {
				pstmt.setInt(1, WPOrderVO.getReview_star());
			}
			pstmt.setString(2, WPOrderVO.getReview_content());
			pstmt.setString(3, WPOrderVO.getWed_photo_order_no());
			pstmt.executeUpdate();
			System.out.println("訂單狀態更改成功 - 完成訂單");
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void Mem_Review(WPOrderVO WPOrderVO) { //整合在完成訂單裡了
		// TODO Auto-generated method stub
		
	}

}
