package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.myshop.shop.model.Customer;

import java.lang.reflect.Field;;

public class CustomerEntityTest {

	
	@Test
	public void  testCreateCustomerEntity() {
		Customer customer = new Customer();
		customer.setId("test_id");
		customer.setName("test_name");
		
		CustomerEntity customerEntity = new CustomerEntity(customer);
		
		assertNotNull(customerEntity);
	
		assertNotNull(customerEntity.getId());
		assertNotNull(customerEntity.getName());
		
		assertEquals(customerEntity.getId(), "test_id");
		assertEquals(customerEntity.getName(), "test_name");
		
		customer = customerEntity.toCustomer();
		
		assertEquals(customer.getId(), "test_id");
		assertEquals(customer.getName(), "test_name");
	}
	
	@Test
	public void testEntityFieldsSameAsModelFields() throws SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Field [] modelFields = new Customer().getClass().getDeclaredFields();
		Field [] jpaEntityFields = new CustomerEntity(new Customer()).getClass().getDeclaredFields();
		
		for(int i=0; i< modelFields.length; i++) {
			
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
