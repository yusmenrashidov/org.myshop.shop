package org.myshop.shop.dao.jdbc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.anyString;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.myshop.shop.model.Item;

public class JdbcItemDaoTest {

    private static final String TEST_ITEM_ID = "test_item_id";
    private static final String TEST_ITEM_NAME = "test_item_name";

    @Mock
    private Connection sqlConnectionMock;
    
    @Mock
    private Item itemMock;
    
    @Mock
    private PreparedStatement preparedStatementMock;

    private JdbcItemDao itemDao;

    @Before
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        
        when(sqlConnectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        
        when(itemMock.getId()).thenReturn(TEST_ITEM_ID);
        when(itemMock.getName()).thenReturn(TEST_ITEM_NAME);
        
        itemDao = new JdbcItemDao(sqlConnectionMock);
    }
    
    @Test
    public void testCreate() throws SQLException {
        itemDao.create(itemMock);
        
        verify(sqlConnectionMock).prepareStatement(anyString());
        verify(preparedStatementMock).setString(1, TEST_ITEM_ID);
        verify(preparedStatementMock).setString(2, TEST_ITEM_NAME);
        verify(preparedStatementMock).executeUpdate();
    }
}
