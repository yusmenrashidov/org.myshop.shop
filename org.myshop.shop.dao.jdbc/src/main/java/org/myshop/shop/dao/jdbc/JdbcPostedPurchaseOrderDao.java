package org.myshop.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.myshop.shop.dao.PostedPurchaseOrderDao;
import org.myshop.shop.model.PostedPurchaseOrder;

public class JdbcPostedPurchaseOrderDao implements PostedPurchaseOrderDao{

	private Connection sqlConnection;
	protected final static String CREATE_QUERY = "INSERT INTO postedPurchaseOrders VALUES(?, ?, ?)";
	protected final static String READ_QUERY = "SELECT * FROM postedPurchaseOrders";
	protected final static String GET_QUERY = "SELECT * FROM postedPurchaseOrders WHERE id = ?";
	protected final static String UPDATE_QUERY = "UPDATE postedPurchaseOrders SET id = ?, number = ?, created = ? WHERE id = ?";
	protected final static String DELETE_QUERY = "DELETE FROM postedPurchaseOrders WHERE id = ?";
	
	
	public JdbcPostedPurchaseOrderDao(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}

	public void create(PostedPurchaseOrder order) {
		
		PreparedStatement stmt;
		try {
		
			stmt = sqlConnection.prepareStatement(CREATE_QUERY);
			
			stmt.setString(1, order.getId());
			stmt.setString(2, order.getNumber());
			stmt.setDate(3, (java.sql.Date) order.getCreated());
		
			stmt.executeUpdate();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}

	public List<PostedPurchaseOrder> read() {
		
		List<PostedPurchaseOrder> list = new ArrayList<PostedPurchaseOrder>();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(READ_QUERY);
			ResultSet resultSet = stmt.executeQuery();
			
			while(resultSet.next()) {
				PostedPurchaseOrder postedPurchaseOrder = new PostedPurchaseOrder();
				
				postedPurchaseOrder.setId(resultSet.getString("id"));
				postedPurchaseOrder.setNumber(resultSet.getString("number"));
				postedPurchaseOrder.setCreated( (java.util.Date) resultSet.getDate("created"));
				
				list.add(postedPurchaseOrder);
			}
			
		} catch (SQLException e) {
			return null;
		}
		
		return list;
	}

	public PostedPurchaseOrder get(String id) {
		
		PostedPurchaseOrder postedPurchaseOrder = new PostedPurchaseOrder();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(GET_QUERY);
			stmt.setString(1, id);
			
			ResultSet resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				postedPurchaseOrder.setId(resultSet.getString("id"));
				postedPurchaseOrder.setNumber(resultSet.getString("number"));
				postedPurchaseOrder.setCreated( (java.util.Date) resultSet.getDate("created"));
			}
			
		} catch (SQLException e) {
			return null;		
		}
		
		return postedPurchaseOrder;
	}

	public PostedPurchaseOrder update(PostedPurchaseOrder order) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(UPDATE_QUERY);
			stmt.setString(1, order.getId());
			stmt.setString(2, order.getNumber());
			stmt.setDate(3, (java.sql.Date) order.getCreated());
			
			stmt.executeUpdate();
		
		} catch (SQLException e) {
			return null;
		}
		
		return order;
	}

	public void delete(PostedPurchaseOrder order) {
			
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(DELETE_QUERY);
			stmt.setString(1, order.getId());	
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
	}
}
