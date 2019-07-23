package org.myshop.shop.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.myshop.shop.dao.ProductGroupDao;
import org.myshop.shop.model.ProductGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JdbcProductGroupDao implements ProductGroupDao{

	private Connection sqlConnection;
	
	protected static final String CREATE_QUERY = "INSERT INTO productGroup VALUES(?, ?, ?)";
	protected static final String READ_QUERY = "SELECT * FROM productGroup";
	protected static final String GET_QUERY = "SELECT * FROM productGroup WHERE id = ?";
	protected static final String UPDATE_QUERY = "UPDATE productGroup SET id = ?, description = ?, itemCategory_id = ? WHERE id = ?";
	protected static final String DELETE_QUERY = "DELETE FROM productGroup WHERE id = ?";
	
	public JdbcProductGroupDao(Connection sqlConnection) {
		
		this.sqlConnection = sqlConnection;
	}
	
	public void create(ProductGroup productGroup) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(CREATE_QUERY);
			
			stmt.setString(1, productGroup.getId());
			stmt.setString(2, productGroup.getDescription());
			stmt.setString(3, productGroup.getItemCategory().getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public List<ProductGroup> read() {
		
		List <ProductGroup> list = new ArrayList<ProductGroup>();
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(READ_QUERY);
		
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				list.add(this.get(rs.getString("id")));
			}
			
		} catch (SQLException e) {
			return null;
		}
		
		
		return list;
	}

	public ProductGroup get(String id) {
		
		ProductGroup productGroup = new ProductGroup();
		JdbcItemCategoryDao dao = new JdbcItemCategoryDao(sqlConnection);
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(GET_QUERY);
			stmt.setString(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			productGroup.setId(rs.getString("id"));
			productGroup.setDescription(rs.getString("description"));
			productGroup.setItemCategory(dao.get(rs.getString("itemCategory_id")));
		} catch (SQLException e) {
			return null;
		}
		
		
		return productGroup;
	}

	public ProductGroup update(ProductGroup productGroup) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(UPDATE_QUERY);
				
			stmt.setString(1, productGroup.getId());
			stmt.setString(2, productGroup.getDescription());
			stmt.setString(3, productGroup.getItemCategory().getId());
			stmt.setString(4, productGroup.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			return null;
		}
		
		return productGroup;
	}

	public void delete(ProductGroup productGroup) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(DELETE_QUERY);
			stmt.setString(1, productGroup.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

}
