package org.myshop.shop.dao.jdbc;

import org.myshop.shop.dao.PurchaseOrderDao;
import org.myshop.shop.model.PurchaseOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

public class JdbcPurchaseOrderDao implements PurchaseOrderDao {
	
	private Connection sqlConnection;

	protected final static String CREATE_QUERY = "INSERT INTO purchaseOrders VALUES(?, ?, ?)";
	protected final static String READ_QUERY = "SELECT * FROM purchaseOrders";
	protected final static String GET_QUERY = "SELECT * FROM purchaseOrders WHERE id = ?";
	protected final static String UPDATE_QUERY = "UPDATE purchaseOrders SET id = ?, number = ?, created = ? WHERE id = ?";
	protected final static String DELETE_QUERY = "DELETE FROM purchaseOrders WHERE id = ?";

	public JdbcPurchaseOrderDao(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}

	public void create(PurchaseOrder order) {
		
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

	public List<PurchaseOrder> read() {
		
		List<PurchaseOrder> list = new ArrayList<PurchaseOrder>();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(READ_QUERY);
			ResultSet resultSet = stmt.executeQuery();
			
			while(resultSet.next()) {
				PurchaseOrder purchaseOrder = new PurchaseOrder();
				
				purchaseOrder.setId(resultSet.getString("id"));
				purchaseOrder.setNumber(resultSet.getString("number"));
				purchaseOrder.setCreated( (java.util.Date) resultSet.getDate("created"));
				
				list.add(purchaseOrder);
			}
			
		} catch (SQLException e) {
			return null;
		}
		
		return list;
	}

	public PurchaseOrder get(String id) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(GET_QUERY);
			stmt.setString(1, id);
			
			ResultSet resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				purchaseOrder.setId(resultSet.getString("id"));
				purchaseOrder.setNumber(resultSet.getString("number"));
				purchaseOrder.setCreated( (java.util.Date) resultSet.getDate("created"));
			}
		} catch (SQLException e) {
			return null;		
		}
		return purchaseOrder;
	}

	public PurchaseOrder update(PurchaseOrder order) {
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

	public void delete(PurchaseOrder order) {
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(DELETE_QUERY);
			stmt.setString(1, order.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
