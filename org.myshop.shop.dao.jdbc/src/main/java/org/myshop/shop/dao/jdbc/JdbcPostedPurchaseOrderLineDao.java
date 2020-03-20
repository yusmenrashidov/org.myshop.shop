package org.myshop.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.myshop.shop.dao.PostedPurchaseOrderLineDao;
import org.myshop.shop.model.PostedPurchaseOrderLine;


public class JdbcPostedPurchaseOrderLineDao implements PostedPurchaseOrderLineDao {

	private Connection sqlConnection;
	
	protected static final String CREATE_QUERY = "INSERT INTO postedPurchaseOrderLine VALUES (?, ?, ?, ?, ?, ?)";
	protected static final String READ_QUERY = "SELECT * FROM postedPurchaseOrderLine";
	protected static final String GET_QUERY = "SELECT * FROM postedPurchaseOrderLine WHERE id = ?";
	protected static final String UPDATE_QUERY = "UPDATE postedPurchaseOrderLine SET id = ?, lineNumber = ?, item_id = ?, quantity = ?, price = ?, ammount = ? WHERE id = ?";
	protected static final String DELETE_QUERY = "DELETE FROM postedPurchaseOrderLine WHERE id = ?";
	
	public JdbcPostedPurchaseOrderLineDao(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}
	
	public void create(PostedPurchaseOrderLine line) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(CREATE_QUERY);
			stmt.setString(1, line.getId());
			stmt.setInt(2, line.getLineNumber());
			stmt.setString(3, line.getItem().getId());
			stmt.setInt(4, line.getQuantity());
			stmt.setFloat(5, line.getPrice());
			stmt.setInt(6, line.getAmmount());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public List<PostedPurchaseOrderLine> read() {
		
		List<PostedPurchaseOrderLine> list = new ArrayList<PostedPurchaseOrderLine>();
		
		JdbcItemDao itemDao = new JdbcItemDao(sqlConnection);
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(READ_QUERY);
			ResultSet resultSet = stmt.executeQuery();
			
			while(resultSet.next()) {
				PostedPurchaseOrderLine line = new PostedPurchaseOrderLine();
				
				line.setId(resultSet.getString("id"));
				line.setLineNumber(resultSet.getInt("lineNumber"));
				line.setItem(itemDao.get(resultSet.getString("item_id")));
				line.setQuantity(resultSet.getInt("quantity"));
				line.setPrice(resultSet.getFloat("price"));
				line.setAmmount(resultSet.getInt("ammount"));
				
				list.add(line);
			}
			
		} catch (SQLException e) {
			return null;
		}
		return list;
	}

	public PostedPurchaseOrderLine get(String id) {
		
		PostedPurchaseOrderLine line = null;
		JdbcItemDao itemDao = new JdbcItemDao(sqlConnection);
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(GET_QUERY);
			stmt.setString(1, id);
			
			ResultSet resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				line = new PostedPurchaseOrderLine();
				line.setId(resultSet.getString("id"));
				line.setLineNumber(resultSet.getInt("lineNumber"));
				line.setItem(itemDao.get(resultSet.getString("item_id")));
				line.setQuantity(resultSet.getInt("quantity"));
				line.setPrice(resultSet.getFloat("price"));
				line.setAmmount(resultSet.getInt("ammount"));
			}
			
		} catch (SQLException e) {
			return null;
		}
		
		return line;
	}

	public PostedPurchaseOrderLine update(PostedPurchaseOrderLine line) {
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(UPDATE_QUERY);
			stmt.setString(1, line.getId());
			stmt.setInt(2, line.getLineNumber());
			stmt.setString(3, line.getItem().getId());
			stmt.setInt(4, line.getQuantity());
			stmt.setFloat(5, line.getPrice());
			stmt.setInt(6, line.getAmmount());
			stmt.setString(7, line.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			return null;
		}
		return line;
	}

	public void delete(PostedPurchaseOrderLine line) {
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(DELETE_QUERY);
			stmt.setString(1, line.getId());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
