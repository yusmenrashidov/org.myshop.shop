package org.myshop.shop.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.myshop.shop.model.PostedPurchaseOrderLine;

public class PostedPurchaseOrderLineTest {

	@Test
	public void testPostedPurchaseOrderLineFields() {

		PostedPurchaseOrderLine purchaseOrderLine = new PostedPurchaseOrderLine();

		assertNotNull("Id shoud not be null on new PostedPurchaseOrderLine", purchaseOrderLine.getId());
		assertEquals("Id should be empty on new PostedPurchaseOrderLine", "", purchaseOrderLine.getId());

		assertEquals("Line number should be zero on new PostedPurchaseOrderLine", 0d, purchaseOrderLine.getLineNumber(),
				0);

		assertNull("Item should be null on new PostedPurchaseOrderLine", purchaseOrderLine.getItem());

		assertEquals("Ammount should be zero on new PostedPurchaseOrderLine", 0d, purchaseOrderLine.getAmmount(), 0);

		assertEquals("Price should be zero on new PostedPurchaseOrderLine", 0f, purchaseOrderLine.getPrice(), 0);

		assertEquals("Quantity should be zero on new PostedPurchaseOrderLine", 0d, purchaseOrderLine.getQuantity(), 0);
	}
}
