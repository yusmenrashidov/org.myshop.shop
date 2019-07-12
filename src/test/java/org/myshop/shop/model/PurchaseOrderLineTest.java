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
		
		assertNotNull("Id should not be null on new PurchaseOrderLine", purchaseOrderLine.getId());
		assertEquals("Id should be empty on new PurchaseOrderLine", "", purchaseOrderLine.getId());
		
		assertEquals("Line number should be zero on new PurchaseOrderLine", 0d, purchaseOrderLine.getLineNumber(), 0);
		
		assertNull("Item should be null on new PurchaseOrderLine", purchaseOrderLine.getItem());
		
		assertEquals("Ammount should be zero on new PurchaseOrderLine", 0d, purchaseOrderLine.getAmmount(), 0);
		
		assertEquals("Price should be zero on new PurchaseOrderLine", 0f, purchaseOrderLine.getPrice(), 0);
		
		assertEquals("Quantity should be zero on new PurchaseOrderLine", 0d, purchaseOrderLine.getQuantity(), 0);
	}
	
}
