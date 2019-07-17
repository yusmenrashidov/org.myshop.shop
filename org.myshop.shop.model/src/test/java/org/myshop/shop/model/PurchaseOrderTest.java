package org.myshop.shop.model;

import org.junit.Test;
import org.myshop.shop.model.PurchaseOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PurchaseOrderTest {
	
	@Test
	public void prurchaseOrderFieldsNotNull() {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		assertNotNull("Id should not be null on new PurchaseOrder", purchaseOrder.getId());
		assertEquals("Id should be empty on new PurchaseOrder", "", purchaseOrder.getId());
		
		assertNotNull("Number should not be null on new PurchaseOrder", purchaseOrder.getNumber());
		assertEquals("Number should be empty on new PurchaseOrder", "", purchaseOrder.getNumber());
		
		assertNull("Date created should be null on new PurchaseOrder", purchaseOrder.getCreated());
	
	}
	
}
