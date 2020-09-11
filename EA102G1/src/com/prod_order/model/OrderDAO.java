package com.prod_order.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
public class OrderDAO implements OrderDAO_interface{
	private static DataSource ds;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private final static String INSERT= "insert into prod_order(order_no, membre_id, total, order_date, ship_date, order_status)values('ON'|| LPAD(TO_CHAR(ORDER_SEQ.NEXTVAL),3,'0'),?,?,?,?,?)";
	private final static String GET_ONE_BY_ID="select * from prod_order where order_no = ?";
	private final static String GET_ALL = "select * from prod_order where membre_id = ? order by order_date desc";
	private final static String UPDATE = "update prod_order set total = ?, ship_date = ?, order_status = ? where order_no = ?";
	private final static String DELETE ="delete prod_order where order_no = ?";
	private final static String GET_ALL_SELLER="select * from prod_order  where order_no in\r\n" + 
																		"(select order_no from order_detail where prod_no in\r\n" + 
																		"(select prod_no from prod where membre_id =?)) order by order_date desc";
	@Override
	public void insert(OrderVO ordervo) {
		int[] col = {1};
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(INSERT, col)) {
				pstmt.setString(1,ordervo.getMembre_id());
				pstmt.setInt(2, ordervo.getTotal());
				pstmt.setTimestamp(3, ordervo.getOrder_date());
				pstmt.setTimestamp(4, ordervo.getShip_date());
				pstmt.setString(5, ordervo.getOrder_status());
				
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					ordervo.setOrder_no(rs.getString(1));
				}
				
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		
	}


	@Override
	public void update(OrderVO ordervo) {
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(UPDATE)) {
			pstmt.setInt(1, ordervo.getTotal());
			pstmt.setTimestamp(2, ordervo.getShip_date());
			pstmt.setString(3, ordervo.getOrder_status());
			pstmt.setString(4, ordervo.getOrder_no());
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		
	}

	@Override
	public void delete(String order_no) {
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(DELETE)) {
			pstmt.setString(1, order_no);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}

	}


	@Override
	public OrderVO getOneById(String order_no) {
		OrderVO ordervo = new OrderVO();
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ONE_BY_ID)) {
			pstmt.setString(1, order_no);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				ordervo.setMembre_id(rs.getString("membre_id"));
				ordervo.setOrder_no(rs.getString("order_no"));
				ordervo.setTotal(rs.getInt("total"));
				ordervo.setShip_date(rs.getTimestamp("ship_date"));
				ordervo.setOrder_date(rs.getTimestamp("order_date"));
				ordervo.setOrder_status(rs.getString("order_status"));
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return ordervo;
	}


	@Override
	public List<OrderVO> getAllSeller(String membre_id) {
		List<OrderVO> orderList = new ArrayList<OrderVO>();
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SELLER)) {
			  	pstmt.setString(1, membre_id);
			  	ResultSet rs = pstmt.executeQuery();
			  	while (rs.next()) {
			  		OrderVO ordervo = new OrderVO();
			  		ordervo.setMembre_id(rs.getString("membre_id"));
			  		ordervo.setOrder_no(rs.getString("order_no"));
			  		ordervo.setTotal(rs.getInt("total"));
			  		ordervo.setOrder_date(rs.getTimestamp("order_date"));
			  		ordervo.setShip_date(rs.getTimestamp("ship_date"));
			  		ordervo.setOrder_status(rs.getString("order_status"));
			  		orderList.add(ordervo);
			  	}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return orderList;
	}


	@Override
	public List<OrderVO> getAllBuyer(String membre_id) {
		List<OrderVO> orderList = new ArrayList<OrderVO>();
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL)) {
			pstmt.setString(1, membre_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderVO ordervo = new OrderVO();
				ordervo.setMembre_id(rs.getString("membre_id"));
				ordervo.setOrder_no(rs.getString("order_no"));
				ordervo.setTotal(rs.getInt("total"));
				ordervo.setShip_date(rs.getTimestamp("ship_date"));
				ordervo.setOrder_date(rs.getTimestamp("order_date"));
				ordervo.setOrder_status(rs.getString("order_status"));
				
				orderList.add(ordervo);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
		}
		return orderList;
	}
}
