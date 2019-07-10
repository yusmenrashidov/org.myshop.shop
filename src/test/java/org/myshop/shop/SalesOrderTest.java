package org.myshop.shop;

import org.myshop.shop.model.SalesOrder;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SalesOrderTest {

	@SuppressWarnings("deprecation")
	@Test
	public void prurchaseOrderFieldsNotNull() {
		SalesOrder salesOrder = new SalesOrder();
		
		assertNotNull(salesOrder.getId());
		assertNull(salesOrder.getCreated());
		assertNull(salesOrder.getCustomer());
		assertEquals(0d, salesOrder.getLineNumber());
	
	}
}
