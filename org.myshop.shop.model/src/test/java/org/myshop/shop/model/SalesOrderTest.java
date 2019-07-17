package org.myshop.shop.model;

import org.myshop.shop.model.SalesOrder;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SalesOrderTest {

	@Test
	public void testSalesOrderField() {
		SalesOrder salesOrder = new SalesOrder();
		
		assertNotNull("Id should not be null on new SalesOrder", salesOrder.getId());
		assertEquals("Id should be empty on new SalesOrder", "", salesOrder.getId());
		
		assertNull("Date created should be null on new SalesOrder", salesOrder.getCreated());
		
		assertNull("Customer should be null on new SalesOrder", salesOrder.getCustomer());
	
	}
}
