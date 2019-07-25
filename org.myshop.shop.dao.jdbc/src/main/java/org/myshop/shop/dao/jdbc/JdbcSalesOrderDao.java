package org.myshop.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.myshop.shop.dao.SalesOrderDao;
import org.myshop.shop.model.SalesOrder;

public class JdbcSalesOrderDao implements SalesOrderDao{

	private Connection sqlConnection;
	
	protected static final String CREATE_QUERY = "INSERT INTO salesOrder VALUES (?, ?, ?)";
	protected static final String READ_QUERY = "SELECT * FROM salesOrder";
	protected static final String GET_QUERY = "SELECT * FROM salesOrder WHERE id = ?";
	protected static final String UPDATE_QUERY = "UPDATE salesOrder SET id = ?, created = ?, customer_id = ? WHERE id = ?";
	protected static final String DELETE_QUERY = "DELETE FROM salesOrder WHERE id = ?";
	
	public JdbcSalesOrderDao(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}
	
	
	public void create(SalesOrder order) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(CREATE_QUERY);
			
			stmt.setString(1, order.getId());
			stmt.setDate(2, (java.sql.Date) order.getCreated());
			stmt.setString(3, order.getCustomer().getId());
			
			stmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
	  }
	}

	public List<SalesOrder> read() {
		
		List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();
		
		JdbcCustomerDao customerDao = new JdbcCustomerDao(sqlConnection);
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(READ_QUERY);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				SalesOrder salesOrder = new SalesOrder();
				
				salesOrder.setId(rs.getString("id"));
				salesOrder.setCreated((java.util.Date)rs.getDate("created"));
				salesOrder.setCustomer(customerDao.get(rs.getString("customer_id")));
				
				salesOrderList.add(salesOrder);
			}
		
		} catch (SQLException e) {
			return null;
		}
		
		return salesOrderList;
	}

	public SalesOrder get(String id) {
		
		SalesOrder salesOrder = null;
		JdbcCustomerDao customerDao = new JdbcCustomerDao(sqlConnection);
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(GET_QUERY);
			
			stmt.setString(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				salesOrder = new SalesOrder();
				salesOrder.setId(rs.getString("id"));
				salesOrder.setCreated((java.util.Date)rs.getDate("created"));
				salesOrder.setCustomer(customerDao.get(rs.getString("customer_id")));
			}
			
		} catch (SQLException e) {

			return null;
		}
		
		return salesOrder;
	}

	public SalesOrder update(SalesOrder order) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(UPDATE_QUERY);
			
			stmt.setString(1, order.getId());
			stmt.setDate(2, (java.sql.Date) order.getCreated());
			stmt.setString(3, order.getCustomer().getId());
			stmt.setString(4, order.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			
			return null;
		}	
		return order;
	}

	public void delete(SalesOrder order) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(DELETE_QUERY);
			
			stmt.setString(1, order.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
