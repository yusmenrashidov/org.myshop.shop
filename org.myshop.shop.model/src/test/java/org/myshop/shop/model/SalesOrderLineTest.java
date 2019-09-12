package org.myshop.shop.model;

import org.myshop.shop.model.SalesOrderLine;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SalesOrderLineTest {

	@Test
	public void testSalesOrderLineTestFields() {

		SalesOrderLine salesOrderLine = new SalesOrderLine();

		assertNotNull("Id should not be null on new SalesOrderLine", salesOrderLine.getId());
		assertEquals("Id should be empty on new SalesOrderLine", "", salesOrderLine.getId());

		assertEquals("Line number should be zero on new SalesOrderLine", 0d, salesOrderLine.getLineNumber(), 0);

		assertNull("Item should be null on new SalesOrderLine", salesOrderLine.getItem());

		assertEquals("Ammount should be zero on new SalesOrderLine", 0d, salesOrderLine.getAmmount(), 0);

		assertEquals("Price should be zero on new SalesOrderLine", 0f, salesOrderLine.getPrice(), 0);

		assertEquals("Quantity should be zero on new SalesOrderLine", 0d, salesOrderLine.getQuantity(), 0);
	}

}
