package org.myshop.shop.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.myshop.shop.dao.VendorDao;
import org.myshop.shop.model.Vendor;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class JdbcVendorDao implements VendorDao {

	private Connection sqlConnection;
	
	public JdbcVendorDao(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}
	
	public void create(Vendor vendor) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("INSERT INTO vendor VALUES(?, ?)");
			stmt.setString(1, vendor.getId());
			stmt.setString(2, vendor.getName());
			
			stmt.executeUpdate();
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public List<Vendor> read() {
		
		List<Vendor>list = new ArrayList<Vendor>();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("SELECT * FROM vendor");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) 
				list.add(this.get(rs.getString("id")));
			
		
		} catch (SQLException e) {
			return null;
		}
		
		return list;
	}

	public Vendor get(String id) {
		
		Vendor vendor = new Vendor();
			try {
			PreparedStatement stmt = sqlConnection.prepareStatement("SELECT * FROM vendor WHERE id = ?");
			stmt.setString(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			vendor.setId(rs.getString("id"));
			vendor.setName(rs.getString("name"));
			
		} catch (SQLException e) {
			return null;
		}
		return vendor;
	}

	public Vendor update(Vendor vendor) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("UPDATE vendor SET id = ?, name = ? WHERE id = ?");
			
			stmt.setString(1, vendor.getId());
			stmt.setString(2, vendor.getName());
			stmt.setString(3, vendor.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			return null;
		}
		return vendor;
	}

	public void delete(Vendor vendor) {
		
		try{
		PreparedStatement stmt = sqlConnection.prepareStatement("DELETE FROM vendor WHERE id = ?");
		stmt.setString(1, vendor.getName());
		
		stmt.executeUpdate();
	
		}catch(SQLException e) {
				e.printStackTrace();
		}
	}
}
