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

    static final String CREATE_QUERY = "INSERT INTO vendor VALUES(?, ?)";
    static final String READ_QUERY = "SELECT * FROM vendor";
    static final String GET_QUERY = "SELECT * FROM vendor WHERE id = ?";
    static final String UPDATE_QUERY = "UPDATE vendor SET id = ?, name = ? WHERE id = ?";
    static final String DELETE_QUERY = "DELETE FROM vendor WHERE id = ?";

    public JdbcVendorDao(Connection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    public void create(Vendor vendor) {
        try {
            PreparedStatement stmt = sqlConnection.prepareStatement(CREATE_QUERY);
            stmt.setString(1, vendor.getId());
            stmt.setString(2, vendor.getName());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vendor> read() {
        List<Vendor> list = new ArrayList<Vendor>();

        try {
            PreparedStatement stmt = sqlConnection.prepareStatement(READ_QUERY);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Vendor vendor = new Vendor();

                vendor.setId(rs.getString("id"));
                vendor.setName(rs.getString("name"));

                list.add(vendor);
            }
        } catch (SQLException e) {
            return null;
        }
        return list;
    }

    public Vendor get(String id) {
        Vendor vendor = new Vendor();
        try {
            PreparedStatement stmt = sqlConnection.prepareStatement(GET_QUERY);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                vendor.setId(rs.getString("id"));
                vendor.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            return null;
        }
        return vendor;
    }

    public Vendor update(Vendor vendor) {
        try {
            PreparedStatement stmt = sqlConnection.prepareStatement(UPDATE_QUERY);

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
        try {
            PreparedStatement stmt = sqlConnection.prepareStatement(DELETE_QUERY);
            stmt.setString(1, vendor.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
