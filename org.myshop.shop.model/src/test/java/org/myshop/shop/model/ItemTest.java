package org.myshop.shop.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.myshop.shop.model.Item;

public class ItemTest {

    @Test
    public void testItemFieldsNotNullOnNewItem() {
        Item item = new Item();

        assertNotNull("Id should not be empty on new Item", item.getId());
        assertEquals("Id should be empty on new Item", "", item.getId());

        assertNotNull("Name should not be null on new Item", item.getName());
        assertEquals("Name should be empty on new Item", "", item.getName());

        assertNotNull("Description should not be null on new Item", item.getDescription());
        assertEquals("Description should be empty on new Item", "", item.getDescription());

        assertNull("ItemCategory should be null on new Item", item.getItemCategory());
        assertNull("ProductGroup should be null on new Item", item.getProductGroup());

        assertEquals("Sales price should be zero on new Item", 0f, item.getSalesPrice(), 0);
        assertEquals("Purchase price should be zero on new Item", 0f, item.getSalesPrice(), 0);
    }
}
