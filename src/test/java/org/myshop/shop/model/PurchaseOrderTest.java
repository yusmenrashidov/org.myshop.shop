package org.myshop.shop.model;

import org.junit.Test;
import org.myshop.shop.model.PurchaseOrder;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PurchaseOrderTest {
	
	@Test
	public void prurchaseOrderFieldsNotNull() {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		assertNotNull(purchaseOrder.getId());
		assertNotNull(purchaseOrder.getNumber());
		assertNull(purchaseOrder.getCreated());
	
	}
	
}
