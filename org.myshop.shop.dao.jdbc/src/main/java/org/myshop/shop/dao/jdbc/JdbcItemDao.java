package org.myshop.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;

public class JdbcItemDao implements ItemDao {

    private Connection sqlConnection;
    
    public JdbcItemDao(Connection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    public void create(Item item) {
        try {
            PreparedStatement stmt = sqlConnection.prepareStatement("INSERT INTO item VALUES (?,?)");
            
            stmt.setString(1, item.getId());
            stmt.setString(2, item.getName());

            stmt.executeUpdate();
        } catch (SQLException e) {
            //TODO Improve exception handling
            e.printStackTrace();
        }
    }

    public List<Item> read() {
        // TODO Auto-generated method stub
        return null;
    }

    public Item get(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    public Item update(Item item) {
        // TODO Auto-generated method stub
        return null;
    }

    public void delete(Item item) {
        // TODO Auto-generated method stub

    }

}
