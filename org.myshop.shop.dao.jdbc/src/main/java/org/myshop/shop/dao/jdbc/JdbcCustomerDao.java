package org.myshop.shop.dao.jdbc;

import org.myshop.shop.model.Customer;
import org.myshop.shop.dao.CustomerDao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.List;


public class JdbcCustomerDao implements CustomerDao {

	private Connection sqlConnection;
	
	protected static final String CREATE_QUERY = "INSERT INTO customer VALUES(?, ?)";
	protected static final String READ_QUERY = "SELECT * FROM customer";
	protected static final String GET_QUERY = "SELECT * FROM customer WHERE id = ?";
	protected static final String UPDATE_QUERY = "UPDATE customer SET id = ?, name = ? WHERE id = ?";
	protected static final String DELETE_QUERY = "DELETE FROM customer WHERE id = ?";
	
	public JdbcCustomerDao(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}
	
	public void create(Customer customer) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(CREATE_QUERY);

			stmt.setString(1, customer.getId());
			stmt.setString(2, customer.getName());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public List<Customer> read() {
		
		List<Customer> list = new ArrayList<Customer>();
		Customer customer = new Customer();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(READ_QUERY);
			ResultSet rs = stmt.executeQuery();
		
			while(rs.next()) {
				
				customer.setId(rs.getString("id"));
				customer.setName(rs.getString("name"));
				
				list.add(customer);
			}
		
		} catch (SQLException e) {
			return null;
		}
		
		return list;
	}

	public Customer get(String id) {
		
		Customer customer = new Customer();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(GET_QUERY);
			
			stmt.setString(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
			customer.setId(rs.getString("id"));
			customer.setName(rs.getString("name"));
			}
			
		} catch (SQLException e) {
			return null;
		}
		
		return customer;
	}

	public Customer update(Customer customer) {
		
		
		
		try {
		
			PreparedStatement stmt = sqlConnection.prepareStatement(UPDATE_QUERY);
			
			stmt.setString(1, customer.getId());
			stmt.setString(2,  customer.getName());
			stmt.setString(3, customer.getId());
			stmt.executeUpdate();
		
		} catch (SQLException e) {
			return null;
		}
		
		return customer;
	}

	public void delete(Customer customer) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(DELETE_QUERY);
			stmt.setString(1, customer.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

}
