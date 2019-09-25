package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
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
	
	@Test
	public void testEntityFieldsSameAsModelFields() throws SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Field [] modelFields = PurchaseOrder.class.getFields();
		Field [] jpaEntityFields = PurchaseOrderEntity.class.getFields();
		
		for(int i = 0; i < modelFields.length; i++) {
			
			modelFields[i].setAccessible(true);
			jpaEntityFields[i].setAccessible(true);
			
			try {		
				assertEquals(modelFields[i], jpaEntityFields[i]);
			
			}catch(AssertionError e) {
				System.out.print("There are unreflected fields: "+ modelFields[i].getName() + ", " + jpaEntityFields[i].getName());
			}
		}
	}
}
