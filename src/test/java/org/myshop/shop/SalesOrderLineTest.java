package org.myshop.shop;

import org.myshop.shop.model.SalesOrderLine;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SalesOrderLineTest {

	@Test
	public void testSalesOrderLineTestFields() {
		
		SalesOrderLine salesOrderLine = new SalesOrderLine();
		
		assertNotNull(salesOrderLine.getId());
		assertEquals(0d, salesOrderLine.getLineNumber(), 0);
		assertNull(salesOrderLine.getItem());
		assertEquals(0d, salesOrderLine.getAmmount(), 0);
		assertEquals(0f, salesOrderLine.getPrice(), 0);
		assertEquals(0d, salesOrderLine.getQuantity(), 0);
	}	
	
	
}
