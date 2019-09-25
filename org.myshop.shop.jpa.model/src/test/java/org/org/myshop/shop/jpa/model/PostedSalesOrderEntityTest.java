package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.Test;
import org.myshop.shop.model.Customer;
import org.myshop.shop.model.PostedSalesOrder;

public class PostedSalesOrderEntityTest {

	
	@Test
	public void testCreatePostedSalesOrderEntity() {
		
		Customer customer = new Customer();
		customer.setId("test_customer_id");
		customer.setName("test_customer_name");
		
		PostedSalesOrder order = new PostedSalesOrder();
		
		order.setId("test_id");
		order.setCustomer(customer);
		order.setCreated(new Date(0));
		
		PostedSalesOrderEntity entity = new PostedSalesOrderEntity(order);
		
		assertNotNull(entity);
		
		assertEquals(entity.getId(), order.getId());
		assertEquals(entity.getCreated(), order.getCreated());
		assertEquals(entity.getCustomer().getId(), order.getCustomer().getId());
		
	}

	@Test
	public void testEntityFieldsSameAsModelFields() throws SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Field [] modelFields = PostedSalesOrder.class.getFields();
		Field [] jpaEntityFields = PostedSalesOrderEntity.class.getFields();
		
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
