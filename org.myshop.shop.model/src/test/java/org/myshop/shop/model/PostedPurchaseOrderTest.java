package org.myshop.shop.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.myshop.shop.model.PostedPurchaseOrder;

public class PostedPurchaseOrderTest {

	@Test
	public void PostedPurchaseOrderFieldsNotNull() {
		PostedPurchaseOrder purchaseOrder = new PostedPurchaseOrder();
		
		assertNotNull("Id shoud not be null on new PostedPurchaseOrder", purchaseOrder.getId());
		assertEquals("Id shoudl be empty on new PostedPurchaseOrder", "", purchaseOrder.getId());
		
		assertNotNull("Number should not be null on new PostedPurchaseOrder", purchaseOrder.getNumber());
		assertEquals("Number should be empty on new PostedPurchaseOrder", "", purchaseOrder.getNumber());
		
		assertNull("Date created should be null on new PostedPurchaseOrder", purchaseOrder.getCreated());
	
	}
	
}
