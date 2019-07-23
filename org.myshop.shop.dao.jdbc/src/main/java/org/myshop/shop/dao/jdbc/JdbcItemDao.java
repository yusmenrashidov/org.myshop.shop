package org.myshop.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;

public class JdbcItemDao implements ItemDao {

    private Connection sqlConnection;
    
    protected static final String CREATE_QUERY = "INSERT INTO item VALUES (?,?,?,?,?,?,?)";
    protected static final String READ_QUERY = "SELECT * FROM item";
    protected static final String GET_QUERY = "SELECT * FROM item WHERE id = ?";
    protected static final String UPDATE_QUERY = "UPDATE item SET id = ?, name = ?, description = ?, productGroup_id = ?, itemCategory_id = ?, purchasePrice = ?, salesPrice = ? WHERE id = ?";
    protected static final String DELETE_QUERY = "DELETE FROM item WHERE id = ?";
   
    public JdbcItemDao(Connection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    public void create(Item item) {
        try {
            PreparedStatement stmt = sqlConnection.prepareStatement(CREATE_QUERY);
            
            stmt.setString(1, item.getId());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getDescription());
            stmt.setString(4, item.getProductGroup().getId());
            stmt.setString(5, item.getItemCategory().getId());
            stmt.setFloat(6, item.getPurchasePrice());
            stmt.setFloat(7, item.getSalesPrice());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
          
            e.printStackTrace();
        }
    }

    public List<Item> read() {
        	
    	List<Item>list = new ArrayList<Item>();
    	
    	Item item = new Item();
    	
    	JdbcProductGroupDao productGroupDao = new JdbcProductGroupDao(sqlConnection);
    	JdbcItemCategoryDao itemCategoryDao = new JdbcItemCategoryDao(sqlConnection);
    	
    	try {
			PreparedStatement preparedStatement = sqlConnection.prepareStatement(READ_QUERY);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				item.setId(resultSet.getString("id"));
				item.setName(resultSet.getString("name"));
				item.setDescription(resultSet.getString("description"));
				item.setProductGroup(productGroupDao.get(resultSet.getString("productGroup_id")));
				item.setItemCategory(itemCategoryDao.get(resultSet.getString("itemCategory_id")));
				item.setPurchasePrice(resultSet.getFloat("purchasePrice"));
				item.setSalesPrice(resultSet.getFloat("salesPrice"));
				list.add(item);
			}
			
		} catch (SQLException e) {
			return null;
		}
    	
    	return list;
    }

    public Item get(String id) {
      
    	Item item = new Item();
    	
    	JdbcProductGroupDao productGroupDao = new JdbcProductGroupDao(sqlConnection);
    	JdbcItemCategoryDao itemCategoryDao = new JdbcItemCategoryDao(sqlConnection);
    	
    	try {
			PreparedStatement stmt = sqlConnection.prepareStatement(GET_QUERY);
			stmt.setString(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			item.setId(rs.getString("id"));
			item.setName(rs.getString("name"));
			item.setDescription(rs.getString("description"));
			item.setProductGroup(productGroupDao.get(rs.getString("productGroup_id")));
			item.setItemCategory(itemCategoryDao.get(rs.getString("itemCategory_id")));
			item.setPurchasePrice(rs.getFloat("purchasePrice"));
			item.setSalesPrice(rs.getFloat("salesPrice"));
			
    	} catch (SQLException e) {
			
    		return null;
		}
    	
    	return item;
    }

    public Item update(Item item) {
    	try {
			PreparedStatement stmt = sqlConnection.prepareStatement(UPDATE_QUERY);
			
			stmt.setString(1, item.getId());
			stmt.setString(2, item.getName());
			stmt.setString(3, item.getDescription());
			stmt.setString(4, item.getProductGroup().getId());
			stmt.setString(5, item.getItemCategory().getId());
			stmt.setFloat(6, item.getPurchasePrice());
			stmt.setFloat(7, item.getSalesPrice());
			stmt.setString(8, item.getId());
			
			stmt.executeUpdate();
			
    	} catch (SQLException e) {
			
			return null;
		}
    	
    	return item;
    }

    public void delete(Item item) {
    	
    	try {
			PreparedStatement stmt = sqlConnection.prepareStatement(DELETE_QUERY);
			
			stmt.setString(1, item.getId());
			
			stmt.executeUpdate();
    	
    	} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }

}
