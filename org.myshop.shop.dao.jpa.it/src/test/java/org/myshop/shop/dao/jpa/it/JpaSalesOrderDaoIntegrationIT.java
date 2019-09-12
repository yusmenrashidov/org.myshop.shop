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
import org.myshop.shop.dao.jpa.JpaSalesOrderDao;
import org.myshop.shop.model.Customer;
import org.myshop.shop.model.SalesOrder;

public class JpaSalesOrderDaoIntegrationIT {

	private JpaSalesOrderDao salesOrderDao;
	private JpaCustomerDao customerDao;
	private EntityManagerFactory factory;
	
	@Before
	public void setup() {
		
		factory = Persistence.createEntityManagerFactory("myshopDB");
		salesOrderDao = new JpaSalesOrderDao(factory);
		customerDao = new JpaCustomerDao(factory);
	}
	
	@Test
	public void testCreate() {
		
		Customer customer = new Customer();
		
		customer.setId("test_id_salesOrder");
		customer.setName("test_name_salesOrder");
	
		customerDao.create(customer);
		
		SalesOrder order = new SalesOrder();
		
		order.setId("test_salesOrder_id");
		order.setCreated(new Date(0));
		order.setCustomer(customer);
		
		salesOrderDao.create(order);
	}
	
	@Test
	public void testRead() {
		
		Customer customer = new Customer();
		
		customer.setId("test_id_salesOrder_1");
		customer.setName("test_name_salesOrder");
		
		customerDao.create(customer);
		
		SalesOrder order = new SalesOrder();
		
		order.setId("test_salesOrder_id_1");
		order.setCreated(new Date(0));
		order.setCustomer(customer);
		
		salesOrderDao.create(order);
		
		order.setId("test_salesOrder_id_2");
		order.setCreated(new Date(0));
		order.setCustomer(customer);
		
		salesOrderDao.create(order);
		
		List<SalesOrder> orderList = salesOrderDao.read();
		
		assertNotNull(orderList);
	}
	
	@Test
	public void testGet() {
		
		Customer customer = new Customer();
		
		customer.setId("customer_salesOrder_id_get");
		customer.setName("test_name_salesOrder");
		
		customerDao.create(customer);
		
		SalesOrder order = new SalesOrder();
		
		order.setId("test_salesOrder_id_get");
		order.setCreated(new Date(0));
		order.setCustomer(customer);
		
		salesOrderDao.create(order);
		
		order = salesOrderDao.get("test_salesOrder_id_get");
		
		assertNotNull(order);
		assertEquals(order.getId(), "test_salesOrder_id_get");
		assertEquals(order.getCreated(), new Date(0));
		assertEquals(order.getCustomer().getId(), "customer_salesOrder_id_get");
	}
	
	@Test
	public void testUpdate() {
	
		Customer customer = new Customer();
		
		customer.setId("customer_salesOrder_id_update_1");
		customer.setName("test_name_salesOrder");
		
		customerDao.create(customer);
		
		Customer customerToUpdate = new Customer();
		
		customerToUpdate.setId("customer_salesOrder_id_update_2");
		customerToUpdate.setName("test_name_2");
		
		customerDao.create(customerToUpdate);
		
		SalesOrder order = new SalesOrder();
		
		order.setId("test_salesOrder_id_update");
		order.setCreated(new Date(0));
		order.setCustomer(customer);
		
		salesOrderDao.create(order);
		
		order.setCustomer(customerToUpdate);
		
		order = salesOrderDao.update(order);
		
		assertEquals(order.getCustomer().getId(), "customer_salesOrder_id_update_2");
	}
	
	@Test
	public void testDelete() {
		
		Customer customer = new Customer();
		
		customer.setId("customer_salesOrder_id_delete");
		customer.setName("test_name_salesOrder");
		
		customerDao.create(customer);
		
		SalesOrder order = new SalesOrder();
		
		order.setId("test_salesOrder_id_delete");
		order.setCustomer(customer);
		order.setCreated(new Date(0));
		
		salesOrderDao.create(order);
		
		salesOrderDao.delete(order);
		
		order = salesOrderDao.get("test_salesOrder_id_delete");
		
		assertNull(order);
	}
}
