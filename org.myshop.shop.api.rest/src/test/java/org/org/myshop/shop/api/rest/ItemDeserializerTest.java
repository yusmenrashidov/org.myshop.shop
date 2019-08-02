package org.org.myshop.shop.api.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.myshop.shop.api.rest.servlet.utilImpl.ItemDeserializer;
import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.exc.ItemDeserializationException;

public class ItemDeserializerTest {

    private static final String INVALID_JSON = "alabala";

    private static final String TEST_ID = "test_id";

    private static final String EMPTY_BUT_VALID_JSON = "{}";
    
    private ItemDeserializer itemDeserializer;
    
    @Before
    public void setup() {
        itemDeserializer = new ItemDeserializer();
    }
    
    @Test(expected = ItemDeserializationException.class)
    public void test_null() throws ItemDeserializationException {
        itemDeserializer.deserialize(null);
    }
    
    @Test(expected = ItemDeserializationException.class)
    public void test_emptyString() throws ItemDeserializationException {
        itemDeserializer.deserialize("");
    }
    
    @Test(expected = ItemDeserializationException.class)
    public void test_invalidJson() throws ItemDeserializationException {
        itemDeserializer.deserialize(INVALID_JSON);
    }
    
    @Test
    public void test_emptyJson() throws ItemDeserializationException {
        Item item = itemDeserializer.deserialize(EMPTY_BUT_VALID_JSON);
        
        assertNotNull(item);
    }
    
    @Test
    public void test_validJson() throws ItemDeserializationException {
        Item item = itemDeserializer.deserialize("{\"id\" : \"test_id\"}");
        
        assertNotNull(item);
        assertEquals(TEST_ID, item.getId());
    }
}
