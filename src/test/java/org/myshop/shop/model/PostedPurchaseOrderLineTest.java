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
		
		assertNotNull(purchaseOrderLine.getId());
		assertEquals(0d, purchaseOrderLine.getLineNumber(), 0);
		assertNull(purchaseOrderLine.getItem());
		assertEquals(0d, purchaseOrderLine.getAmmount(), 0);
		assertEquals(0f, purchaseOrderLine.getPrice(), 0);
		assertEquals(0d, purchaseOrderLine.getQuantity(), 0);
	}
}
