package org.myshop.shop.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.myshop.shop.dao.ItemCategoryDao;
import org.myshop.shop.model.ItemCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class JdbcItemCategoryDao implements ItemCategoryDao{

	private Connection sqlConnection;
	
	public JdbcItemCategoryDao(Connection sqlConnection) {
		 
		this.sqlConnection = sqlConnection;
	}
	
	
	public void create(ItemCategory category) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("INSERT INTO itemCategory VALUES(?, ?, ?");
			
			stmt.setString(1, category.getId());
			stmt.setString(2, category.getName());
			stmt.setString(3, category.getDescription());
		
			stmt.executeQuery();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public List<ItemCategory> read() {
		
		List<ItemCategory> list = new ArrayList<ItemCategory>();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("SELECT * FROM itemCategory");
		
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
					list.add(this.get(rs.getString("id")));
				}
			
		} catch (SQLException e) {
			return null;
		}
		
		return list;
	}

	public ItemCategory get(String id) {

		ItemCategory itemCategory = new ItemCategory();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("SELECT * FROM itemCategory WHERE id = ?");
			
			stmt.setString(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			itemCategory.setId(rs.getString("id"));
			itemCategory.setName(rs.getString("name"));
			itemCategory.setDescription(rs.getString("description"));
			
		} catch (SQLException e) {
			return null;
		}
		
		return itemCategory;
	}

	public ItemCategory update(ItemCategory category) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("UPDATE itemCategory SET id = ?, name = ?, description = ? WHERE id = ?");
			stmt.setString(1, category.getId());
			stmt.setString(2, category.getName());
			stmt.setString(3, category.getDescription());
			stmt.setString(4, category.getId());
		
		} catch (SQLException e) {
			return null;
		}
		
		return category;
	}

	public void delete(ItemCategory category) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement("DELETE FROM itemCategory WHERE id = ?");
			stmt.setString(1, category.getId());
			
			stmt.executeQuery();
		
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

}
