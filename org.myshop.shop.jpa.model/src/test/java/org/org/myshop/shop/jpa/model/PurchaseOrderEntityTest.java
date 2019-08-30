package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.myshop.shop.model.PurchaseOrder;

public class PurchaseOrderEntityTest {

	@Test
	public void testCreatePurchaseOrderEntity() {
		
		PurchaseOrder order = new PurchaseOrder();
		
		order.setId("test_id");
		order.setNumber("test_number");
		order.setCreated(new Date(0));
		
		PurchaseOrderEntity entity = new PurchaseOrderEntity(order);		
			
		assertNotNull(entity);
		
		assertEquals(entity.getId(), "test_id");
		assertEquals(entity.getNumber(), "test_number");
		assertEquals(entity.getCreated(), new Date(0));
	}
}
