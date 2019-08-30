package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.myshop.shop.model.Customer;
import org.myshop.shop.model.SalesOrder;

public class SalesOrderEntityTest {

	@Test
	public void testCreateSalesOrderEntity(){
		
		Customer customer = new Customer();
		customer.setId("test_customer_id");
		customer.setName("test_customer_name");
		
		SalesOrder order = new SalesOrder();
		
		order.setId("test_id");
		order.setCustomer(customer);
		order.setCreated(new Date(0));
		
		SalesOrderEntity entity = new SalesOrderEntity(order);
		
		assertNotNull(entity);
		
		assertEquals(entity.getId(), order.getId());
		assertEquals(entity.getCreated(), order.getCreated());
		assertEquals(entity.getCustomer().getId(), order.getCustomer().getId());
	}
}
