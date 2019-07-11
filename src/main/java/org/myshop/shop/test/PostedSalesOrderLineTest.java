package org.myshop.shop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.myshop.shop.model.PostedSalesOrderLine;

public class PostedSalesOrderLineTest {
	
	@Test
	public void testPostedSalesOrderLineTestFields() {
		
		PostedSalesOrderLine salesOrderLine = new PostedSalesOrderLine();
		
		assertNotNull(salesOrderLine.getId());
		assertEquals(0d, salesOrderLine.getLineNumber(), 0);
		assertNull(salesOrderLine.getItem());
		assertEquals(0d, salesOrderLine.getAmmount(), 0);
		assertEquals(0f, salesOrderLine.getPrice(), 0);
		assertEquals(0d, salesOrderLine.getQuantity(), 0);
	}	
}
