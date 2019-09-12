package org.myshop.shop.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.myshop.shop.model.PostedSalesOrderLine;

public class PostedSalesOrderLineTest {

	@Test
	public void testPostedSalesOrderLineTestFields() {

		PostedSalesOrderLine salesOrderLine = new PostedSalesOrderLine();

		assertNotNull("Id should not be null on new PostedSalesOrderLine", salesOrderLine.getId());
		assertEquals("Id should be empty on new PostedSalesOrderLine", "", salesOrderLine.getId());

		assertEquals("Line Number should be zero on noew PostedSalesOrderLine", 0d, salesOrderLine.getLineNumber(), 0);

		assertNull("Item should be null on new PostedSalesOrderLine", salesOrderLine.getItem());

		assertEquals("Ammount should be zero on new PostedSalesOrderLine", 0d, salesOrderLine.getAmmount(), 0);

		assertEquals("Price should be zero on new PostedSalesOrderLine", 0f, salesOrderLine.getPrice(), 0);

		assertEquals("Quantity should be zero on new PostedSalesOrderLine", 0d, salesOrderLine.getQuantity(), 0);
	}
}
