package org.myshop.shop;

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
		assertNotNull(purchaseOrderLine.getLineNumber());
		assertNull(purchaseOrderLine.getItem());
		assertEquals(0f, purchaseOrderLine.getAmmount(), 0);
		assertEquals(0f, purchaseOrderLine.getPrice(), 0);
		assertEquals(0f, purchaseOrderLine.getQuantity(), 0);
	}
	
}
