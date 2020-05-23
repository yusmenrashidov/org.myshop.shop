package org.myshop.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.myshop.shop.dao.PurchaseOrderLineDao;
import org.myshop.shop.model.PurchaseOrderLine;;

public class JdbcPurchaseOrderLineDao implements PurchaseOrderLineDao{

	private Connection sqlConnection;
	
	protected static final String CREATE_QUERY = "INSERT INTO purchaseOrderLine VALUES (?, ?, ?, ?, ?, ?)";
	protected static final String READ_QUERY = "SELECT * FROM purchaseOrderLine";
	protected static final String GET_QUERY = "SELECT * FROM purchaseOrderLine WHERE id = ?";
	protected static final String UPDATE_QUERY = "UPDATE purchaseOrderLine SET id = ?, lineNumber = ?, item_id = ?, quantity = ?, price = ?, ammount = ? WHERE id = ?";
	protected static final String DELETE_QUERY = "DELETE FROM purchaseOrderLine WHERE id = ?";
	
	public JdbcPurchaseOrderLineDao(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}
	
	public void create(PurchaseOrderLine line) {
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(CREATE_QUERY);
			stmt.setString(1, line.getId());
			stmt.setInt(2, line.getLineNumber());
			stmt.setString(3, line.getItem().getId());
			stmt.setInt(4, line.getQuantity());
			stmt.setFloat(5, line.getPrice());
			stmt.setInt(6, line.getAmount());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<PurchaseOrderLine> read() {
		List<PurchaseOrderLine> list = new ArrayList<PurchaseOrderLine>();
		
		JdbcItemDao itemDao = new JdbcItemDao(sqlConnection);
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(READ_QUERY);
			ResultSet resultSet = stmt.executeQuery();
			
			while(resultSet.next()) {
				PurchaseOrderLine line = new PurchaseOrderLine();
				
				line.setId(resultSet.getString("id"));
				line.setLineNumber(resultSet.getInt("lineNumber"));
				line.setItem(itemDao.get(resultSet.getString("item_id")));
				line.setQuantity(resultSet.getInt("quantity"));
				line.setPrice(resultSet.getFloat("price"));
				line.setAmount(resultSet.getInt("ammount"));
				
				list.add(line);
			}
		} catch (SQLException e) {
			return null;
		}
		return list;
	}

	public PurchaseOrderLine get(String id) {
		PurchaseOrderLine line = null;
		JdbcItemDao itemDao = new JdbcItemDao(sqlConnection);
		
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(GET_QUERY);
			stmt.setString(1, id);
			
			ResultSet resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				line = new PurchaseOrderLine();
				line.setId(resultSet.getString("id"));
				line.setLineNumber(resultSet.getInt("lineNumber"));
				line.setItem(itemDao.get(resultSet.getString("item_id")));
				line.setQuantity(resultSet.getInt("quantity"));
				line.setPrice(resultSet.getFloat("price"));
				line.setAmount(resultSet.getInt("ammount"));
			}
			
		} catch (SQLException e) {
			return null;
		}
		return line;
	}

	public PurchaseOrderLine update(PurchaseOrderLine line) {
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(UPDATE_QUERY);
			stmt.setString(1, line.getId());
			stmt.setInt(2, line.getLineNumber());
			stmt.setString(3, line.getItem().getId());
			stmt.setInt(4, line.getQuantity());
			stmt.setFloat(5, line.getPrice());
			stmt.setInt(6, line.getAmount());
			stmt.setString(7, line.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			return null;
		}
		return line;
	}

	public void delete(PurchaseOrderLine line) {
		try {
			PreparedStatement stmt = sqlConnection.prepareStatement(DELETE_QUERY);
			stmt.setString(1, line.getId());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
