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
import org.myshop.shop.dao.jpa.JpaPostedPurchaseOrderDao;
import org.myshop.shop.model.PostedPurchaseOrder;

public class JpaPostedPurchaseOrderDaoIntegrationIT {

	private JpaPostedPurchaseOrderDao postedPurchaseOrderDao;
	private EntityManagerFactory factory;
	
	@Before
	public void setup() {
		
		factory = Persistence.createEntityManagerFactory("myshopDB");
		
		postedPurchaseOrderDao = new JpaPostedPurchaseOrderDao(factory);
	}
	
	@Test
	public void testCreate() {
		
		PostedPurchaseOrder postedPurchaseOrder = new PostedPurchaseOrder();
		
		postedPurchaseOrder.setId("test_id");
		postedPurchaseOrder.setNumber("test_number");
		postedPurchaseOrder.setCreated(new Date(0));
		
		postedPurchaseOrderDao.create(postedPurchaseOrder);
	}
	
	@Test
	public void testRead() {

		PostedPurchaseOrder postedPurchaseOrder = new PostedPurchaseOrder();
		
		postedPurchaseOrder.setId("test_id_1");
		postedPurchaseOrder.setNumber("test_number_1");
		postedPurchaseOrder.setCreated(new Date(0));
		
		postedPurchaseOrderDao.create(postedPurchaseOrder);
		
		postedPurchaseOrder = new PostedPurchaseOrder();
		postedPurchaseOrder.setId("test_id_2");
		postedPurchaseOrder.setNumber("test_number_2");
		postedPurchaseOrder.setCreated(new Date(0));
		
		postedPurchaseOrderDao.create(postedPurchaseOrder);
		
		List<PostedPurchaseOrder> postedPurchaseOrderList = postedPurchaseOrderDao.read();
		
		assertNotNull(postedPurchaseOrderList);
	}
	
	@Test
	public void testGet() {
		
		PostedPurchaseOrder postedPurchaseOrder = new PostedPurchaseOrder();
		
		postedPurchaseOrder.setId("test_id_get");
		postedPurchaseOrder.setNumber("test_number_get");
		postedPurchaseOrder.setCreated(new Date(0));
		
		postedPurchaseOrderDao.create(postedPurchaseOrder);
		
		PostedPurchaseOrder order = postedPurchaseOrderDao.get("test_id_get");
		
		assertNotNull(order);
		
		assertEquals(order.getId(), "test_id_get");
		assertEquals(order.getNumber(), "test_number_get");
		assertEquals(order.getCreated(), new Date(0));
	}
	
	@Test
	public void testUpdate() {
		
		PostedPurchaseOrder postedPurchaseOrder = new PostedPurchaseOrder();
		
		postedPurchaseOrder.setId("test_id_update");
		postedPurchaseOrder.setNumber("test_number");
		postedPurchaseOrder.setCreated(new Date(0));
		
		postedPurchaseOrderDao.create(postedPurchaseOrder);
		
		postedPurchaseOrder.setNumber("test_number_update");
		
		postedPurchaseOrder = postedPurchaseOrderDao.update(postedPurchaseOrder);
		
		assertEquals(postedPurchaseOrder.getNumber(), "test_number_update");
	}
	
	@Test
	public void testDelete() {
		
		PostedPurchaseOrder postedPurchaseOrder = new PostedPurchaseOrder();
		
		postedPurchaseOrder.setId("test_id_delete");
		postedPurchaseOrder.setNumber("test_number");
		postedPurchaseOrder.setCreated(new Date(0));
		
		postedPurchaseOrderDao.create(postedPurchaseOrder);
		
		postedPurchaseOrderDao.delete(postedPurchaseOrder);
		
		postedPurchaseOrder = postedPurchaseOrderDao.get("test_id_delete");
		
		assertNull(postedPurchaseOrder);
	}
}
