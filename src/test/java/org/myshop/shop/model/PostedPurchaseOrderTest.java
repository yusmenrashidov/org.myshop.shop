package org.myshop.shop.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.myshop.shop.model.PostedPurchaseOrder;

public class PostedPurchaseOrderTest {

	@Test
	public void PostedPurchaseOrderFieldsNotNull() {
		PostedPurchaseOrder purchaseOrder = new PostedPurchaseOrder();
		
		assertNotNull(purchaseOrder.getId());
		assertNotNull(purchaseOrder.getNumber());
		assertNull(purchaseOrder.getCreated());
	
	}
	
}
