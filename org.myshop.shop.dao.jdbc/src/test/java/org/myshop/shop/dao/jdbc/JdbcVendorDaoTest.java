package org.myshop.shop.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.model.Vendor;

public class JdbcVendorDaoTest {

    private static final String TEST_ID = "test_id";
    private static final String TEST_NAME = "test_name";
    
    private static final String TEST_ID_1 = "test_id_1";
    private static final String TEST_NAME_1 = "test_name_1";
    private static final String TEST_ID_2 = "test_id_2";
    private static final String TEST_NAME_2 = "test_name_2";

    @Mock
    private Connection sqlConnectionMock;

    @Mock
    private Vendor vendorMock;
    
    @Mock
    private PreparedStatement createStatementMock;
    
    @Mock
    private PreparedStatement readStatementMock;
    
    @Mock
    private PreparedStatement getStatementMock;
    
    @Mock
    private PreparedStatement updateStatementMock;
    
    @Mock
    private PreparedStatement deleteStatementMock;
    
    @Mock
    private ResultSet readResultSetMock;
    
    @Mock
    private ResultSet getResultSetMock;

    private JdbcVendorDao jdbcVendorDao;

    @Before
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        
        when(vendorMock.getId()).thenReturn(TEST_ID);
        when(vendorMock.getName()).thenReturn(TEST_NAME);
        
        when(getResultSetMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        when(getResultSetMock.getString("id")).thenReturn(TEST_ID);
        when(getResultSetMock.getString("name")).thenReturn(TEST_NAME);
        
        when(readResultSetMock.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        when(readResultSetMock.getString("id")).thenReturn(TEST_ID_1).thenReturn(TEST_ID_2);
        when(readResultSetMock.getString("name")).thenReturn(TEST_NAME_1).thenReturn(TEST_NAME_2);
        
        when(readStatementMock.executeQuery()).thenReturn(readResultSetMock);
        
        when(getStatementMock.executeQuery()).thenReturn(getResultSetMock);
        
        when(sqlConnectionMock.prepareStatement(JdbcVendorDao.CREATE_QUERY)).thenReturn(createStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcVendorDao.READ_QUERY)).thenReturn(readStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcVendorDao.GET_QUERY)).thenReturn(getStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcVendorDao.UPDATE_QUERY)).thenReturn(updateStatementMock);
        when(sqlConnectionMock.prepareStatement(JdbcVendorDao.DELETE_QUERY)).thenReturn(deleteStatementMock);
        
        jdbcVendorDao = new JdbcVendorDao(sqlConnectionMock);
    }
    
    @Test
    public void testCreate() throws SQLException {
        jdbcVendorDao.create(vendorMock);
        
        verify(sqlConnectionMock).prepareStatement(JdbcVendorDao.CREATE_QUERY);
        
        verify(createStatementMock).setString(1, TEST_ID);
        verify(createStatementMock).setString(2, TEST_NAME);
        verify(createStatementMock).executeUpdate();
    }
    
    @Test
    public void testRead() throws SQLException {
        List<Vendor> vendorList = jdbcVendorDao.read();
        
        assertNotNull(vendorList);
        assertEquals(2, vendorList.size());
        
        verify(sqlConnectionMock).prepareStatement(JdbcVendorDao.READ_QUERY);
        
        verify(readResultSetMock, times(3)).next();
        verify(readResultSetMock, times(2)).getString("id");
        verify(readResultSetMock, times(2)).getString("name");
        
        Vendor vendor1 = vendorList.get(0);
        assertEquals(TEST_ID_1, vendor1.getId());
        assertEquals(TEST_NAME_1, vendor1.getName());
        
        Vendor vendor2 = vendorList.get(1);
        assertEquals(TEST_ID_2, vendor2.getId());
        assertEquals(TEST_NAME_2, vendor2.getName());
    }
    
    @Test
    public void testGet() throws SQLException {
        Vendor vendor = jdbcVendorDao.get(TEST_ID);
        
        verify(sqlConnectionMock).prepareStatement(JdbcVendorDao.GET_QUERY);

        verify(getStatementMock).setString(1,  TEST_ID);
        verify(getStatementMock).executeQuery();
        
        verify(getResultSetMock).next();
        verify(getResultSetMock).getString("id");
        verify(getResultSetMock).getString("name");
        
        assertNotNull(vendor);
        
        assertEquals(TEST_ID, vendor.getId());
        assertEquals(TEST_NAME, vendor.getName());
    }
    
    @Test
    public void testUpdate() throws SQLException {
        jdbcVendorDao.update(vendorMock);
        
        verify(sqlConnectionMock).prepareStatement(JdbcVendorDao.UPDATE_QUERY);
        
        verify(updateStatementMock).setString(1,  TEST_ID);
        verify(updateStatementMock).setString(2,  TEST_NAME);
        verify(updateStatementMock).setString(3,  TEST_ID);
        verify(updateStatementMock).executeUpdate();
    }
    
    @Test
    public void testDelete() throws SQLException {
        jdbcVendorDao.delete(vendorMock);
        
        verify(sqlConnectionMock).prepareStatement(JdbcVendorDao.DELETE_QUERY);
        
        verify(deleteStatementMock).setString(1, TEST_ID);
        verify(deleteStatementMock).executeUpdate();
    }
}
