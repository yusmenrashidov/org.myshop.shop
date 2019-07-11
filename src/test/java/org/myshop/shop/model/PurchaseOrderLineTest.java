package org.myshop.shop.model;

import org.myshop.shop.model.PurchaseOrderLine;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PurchaseOrderLineTest {

	@Test
	public void testPurchaseOrderLineFields() {
		
		PurchaseOrderLine purchaseOrderLine = new PurchaseOrderLine();
		
		assertNotNull(purchaseOrderLine.getId());
		assertEquals(0d, purchaseOrderLine.getLineNumber(), 0);
		assertNull(purchaseOrderLine.getItem());
		assertEquals(0d, purchaseOrderLine.getAmmount(), 0);
		assertEquals(0f, purchaseOrderLine.getPrice(), 0);
		assertEquals(0d, purchaseOrderLine.getQuantity(), 0);
	}
	
}
