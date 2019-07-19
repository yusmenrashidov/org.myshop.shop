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
	
	public JdbcCustomerDao(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}
	
	public void create(Customer customer) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("INSERT INTO customer VALUES(?, ?)");

			stmt.setString(1, customer.getId());
			stmt.setString(2, customer.getName());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public List<Customer> read() {
		
		List<Customer> list = new ArrayList<Customer>();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("SELECT * FROM customer");
			ResultSet rs = stmt.executeQuery();
		
			while(rs.next()) {
				
				list.add(this.get(rs.getString("id")));
			}
		
		} catch (SQLException e) {
			return null;
		}
		
		return list;
	}

	public Customer get(String id) {
		
		Customer customer = new Customer();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("SELECT * FROM customer WHERE id = ?");
			
			stmt.setString(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			customer.setId(rs.getString("id"));
			customer.setName(rs.getString("name"));
			
		} catch (SQLException e) {
			return null;
		}
		
		return customer;
	}

	public Customer update(Customer customer) {
		
		String querry = "UPDATE customer SET id = ?, name = ? WHERE id = ?";
		
		try {
		
			PreparedStatement stmt = sqlConnection.prepareStatement(querry);
			
			stmt.setString(1, customer.getId());
			stmt.setString(2,  customer.getName());
			
			stmt.executeUpdate();
		
		} catch (SQLException e) {
			return null;
		}
		
		return customer;
	}

	public void delete(Customer customer) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("DELETE FROM customer WHERE id = ?");
			stmt.setString(1, customer.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

}
