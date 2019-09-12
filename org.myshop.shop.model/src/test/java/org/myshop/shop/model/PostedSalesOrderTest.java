package org.myshop.shop.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.myshop.shop.model.PostedSalesOrder;

public class PostedSalesOrderTest {

	@Test
	public void testPostedSalesOrderLineTestFields() {

		PostedSalesOrder salesOrder = new PostedSalesOrder();

		assertNotNull("Id should not be null on new PostedSalesOrder", salesOrder.getId());
		assertEquals("Id should be empty on new PostedSalesOrder", "", salesOrder.getId());

		assertNull("Date created shoud be null on new PostedSalesOrder", salesOrder.getCreated());

		assertNull("Customer should be null on new PostedSalesOrder", salesOrder.getCustomer());

	}
}
