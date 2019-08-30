package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.myshop.shop.model.PostedPurchaseOrder;

public class PostedPurchaseOrderEntityTest {

	@Test
	public void testCreatePostedPurchaseOrderEntity() {
		
		PostedPurchaseOrder order = new PostedPurchaseOrder();
		
		order.setId("test_id");
		order.setNumber("test_number");
		order.setCreated(new Date(0));
		
		PostedPurchaseOrderEntity entity = new PostedPurchaseOrderEntity(order);		
			
		assertNotNull(entity);
		
		assertEquals(entity.getId(), "test_id");
		assertEquals(entity.getNumber(), "test_number");
		assertEquals(entity.getCreated(), new Date(0));
	
	}
	
}
