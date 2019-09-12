package org.myshop.shop.dao.jpa.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;
import org.myshop.shop.dao.jpa.JpaCustomerDao;
import org.myshop.shop.dao.jpa.JpaPostedSalesOrderDao;
import org.myshop.shop.model.Customer;
import org.myshop.shop.model.PostedSalesOrder;

public class JpaPostedSalesOrderDaoIntegrationIT {

	private JpaPostedSalesOrderDao postedSalesOrderDao;
	private JpaCustomerDao customerDao;
	private EntityManagerFactory factory;
	
	@Before
	public void setup() {
		
		factory = Persistence.createEntityManagerFactory("myshopDB");
		postedSalesOrderDao = new JpaPostedSalesOrderDao(factory);
		customerDao = new JpaCustomerDao(factory);
	}
	
	@Test
	public void testCreate() {
		
		Customer customer = new Customer();
		
		customer.setId("test_id_postedSalesOrder");
		customer.setName("test_name_postedSalesOrder");
	
		customerDao.create(customer);
		
		PostedSalesOrder order = new PostedSalesOrder();
		
		order.setId("test_id");
		order.setCreated(new Date(0));
		order.setCustomer(customer);
		
		postedSalesOrderDao.create(order);
	}
	
	@Test
	public void testRead() {
		
		Customer customer = new Customer();
		
		customer.setId("test_id_postedSalesOrder_1");
		customer.setName("test_name_postedSalesOrder");
		
		customerDao.create(customer);
		
		PostedSalesOrder order = new PostedSalesOrder();
		
		order.setId("test_id_1");
		order.setCreated(new Date(0));
		order.setCustomer(customer);
		
		postedSalesOrderDao.create(order);
		
		order.setId("test_id_2");
		order.setCreated(new Date(0));
		order.setCustomer(customer);
		
		postedSalesOrderDao.create(order);
		
		List<PostedSalesOrder> orderList = postedSalesOrderDao.read();
		
		assertNotNull(orderList);
	}
	
	@Test
	public void testGet() {
		
		Customer customer = new Customer();
		
		customer.setId("customer_postedSalesOrder_id_get");
		customer.setName("test_name_postedSalesOrder");
		
		customerDao.create(customer);
		
		PostedSalesOrder order = new PostedSalesOrder();
		
		order.setId("test_id_get");
		order.setCreated(new Date(0));
		order.setCustomer(customer);
		
		postedSalesOrderDao.create(order);
		
		order = postedSalesOrderDao.get("test_id_get");
		
		assertNotNull(order);
		assertEquals(order.getId(), "test_id_get");
		assertEquals(order.getCreated(), new Date(0));
		assertEquals(order.getCustomer().getId(), "customer_postedSalesOrder_id_get");
	}
	
	@Test
	public void testUpdate() {
	
		Customer customer = new Customer();
		
		customer.setId("customer_postedSalesOrder_id_update_1");
		customer.setName("test_name_postedSalesOrder");
		
		customerDao.create(customer);
		
		Customer customerToUpdate = new Customer();
		
		customerToUpdate.setId("customer_postedSalesOrder_id_update_2");
		customerToUpdate.setName("test_name_2");
		
		customerDao.create(customerToUpdate);
		
		PostedSalesOrder order = new PostedSalesOrder();
		
		order.setId("test_id_update");
		order.setCreated(new Date(0));
		order.setCustomer(customer);
		
		postedSalesOrderDao.create(order);
		
		order.setCustomer(customerToUpdate);
		
		order = postedSalesOrderDao.update(order);
		
		assertEquals(order.getCustomer().getId(), "customer_postedSalesOrder_id_update_2");
	}
	
	@Test
	public void testDelete() {
		
		Customer customer = new Customer();
		
		customer.setId("customer_postedSalesOrder_id_delete");
		customer.setName("test_name_postedSalesOrder");
		
		customerDao.create(customer);
		
		PostedSalesOrder order = new PostedSalesOrder();
		
		order.setId("test_id_delete");
		order.setCustomer(customer);
		order.setCreated(new Date(0));
		
		postedSalesOrderDao.create(order);
		
		postedSalesOrderDao.delete(order);
		
		order = postedSalesOrderDao.get("test_id_delete");
		
		assertNull(order);
	}
}
