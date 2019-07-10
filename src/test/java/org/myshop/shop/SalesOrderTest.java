package org.myshop.shop;

import org.myshop.shop.model.SalesOrder;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SalesOrderTest {

	
	@Test
	public void testSalesOrderField() {
		SalesOrder salesOrder = new SalesOrder();
		
		assertNotNull(salesOrder.getId());
		assertNull(salesOrder.getCreated());
		assertNull(salesOrder.getCustomer());
	
	}
}
