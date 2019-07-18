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
import org.myshop.shop.model.ProductGroup;

public class JdbcItemDaoTest {

    private static final String TEST_ITEM_ID = "test_item_id";
    private static final String TEST_ITEM_NAME = "test_item_name";
    private static final String TEST_ITEM_DESCRIPTION = "test_item_description";
    private static final String TEST_ITEM_PRODUCT_GROUP_ID = "test_item_productGroup_id";
	private static final String TEST_ITEM_ITEM_CATEGORY_ID = "test_item_itemCategory_id";
    
    @Mock
    private Connection sqlConnectionMock;
    
    @Mock
    private Item itemMock = new Item();
    
    @Mock
    private ProductGroup productGroup;
    
    @Mock
    private PreparedStatement preparedStatementMock;

    private JdbcItemDao itemDao;

    @Before
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        
        when(sqlConnectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        
        when(itemMock.getId()).thenReturn(TEST_ITEM_ID);
        when(itemMock.getName()).thenReturn(TEST_ITEM_NAME);
        when(itemMock.getDescription()).thenReturn(TEST_ITEM_DESCRIPTION);
        when(itemMock.getProductGroup().getId()).thenReturn(TEST_ITEM_PRODUCT_GROUP_ID);
        when(itemMock.getItemCategory().getId()).thenReturn(TEST_ITEM_ITEM_CATEGORY_ID);
        
        itemDao = new JdbcItemDao(sqlConnectionMock);
    }
    
    @Test
    public void testCreate() throws SQLException {
        itemDao.create(itemMock);
        
        verify(sqlConnectionMock).prepareStatement(anyString());
        verify(preparedStatementMock).setString(1, TEST_ITEM_ID);
        verify(preparedStatementMock).setString(2, TEST_ITEM_NAME);
        verify(preparedStatementMock).setString(3, TEST_ITEM_DESCRIPTION);
        verify(preparedStatementMock).setString(4, TEST_ITEM_PRODUCT_GROUP_ID);
        verify(preparedStatementMock).setString(5, TEST_ITEM_ITEM_CATEGORY_ID);
        verify(preparedStatementMock).executeUpdate();
    }
}
