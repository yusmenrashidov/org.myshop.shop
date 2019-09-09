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

import org.myshop.shop.model.PurchaseOrder;
import org.org.myshop.shop.dao.jpa.JpaPurchaseOrderDao;

public class JpaPurchaseOrderDaoIntegrationIT {

	private JpaPurchaseOrderDao purchaseOrderDao;
	private EntityManagerFactory factory;
	
	@Before
	public void setup() {
		
		factory = Persistence.createEntityManagerFactory("myshopDB");
		
		purchaseOrderDao = new JpaPurchaseOrderDao(factory);
	}
	
	@Test
	public void testCreate() {
		
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		purchaseOrder.setId("test_id");
		purchaseOrder.setNumber("test_number");
		purchaseOrder.setCreated(new Date(0));
		
		purchaseOrderDao.create(purchaseOrder);
	}
	
	@Test
	public void testRead() {

		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		purchaseOrder.setId("test_id_1");
		purchaseOrder.setNumber("test_number_1");
		purchaseOrder.setCreated(new Date(0));
		
		purchaseOrderDao.create(purchaseOrder);
		
		purchaseOrder = new PurchaseOrder();
		purchaseOrder.setId("test_id_2");
		purchaseOrder.setNumber("test_number_2");
		purchaseOrder.setCreated(new Date(0));
		
		purchaseOrderDao.create(purchaseOrder);
		
		List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.read();
		
		assertNotNull(purchaseOrderList);
	}
	
	@Test
	public void testGet() {
		
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		purchaseOrder.setId("test_id_get");
		purchaseOrder.setNumber("test_number_get");
		purchaseOrder.setCreated(new Date(0));
		
		purchaseOrderDao.create(purchaseOrder);
		
		PurchaseOrder order = purchaseOrderDao.get("test_id_get");
		
		assertNotNull(order);
		
		assertEquals(order.getId(), "test_id_get");
		assertEquals(order.getNumber(), "test_number_get");
		assertEquals(order.getCreated(), new Date(0));
	}
	
	@Test
	public void testUpdate() {
		
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		purchaseOrder.setId("test_id_update");
		purchaseOrder.setNumber("test_number");
		purchaseOrder.setCreated(new Date(0));
		
		purchaseOrderDao.create(purchaseOrder);
		
		purchaseOrder.setNumber("test_number_update");
		
		purchaseOrder = purchaseOrderDao.update(purchaseOrder);
		
		assertEquals(purchaseOrder.getNumber(), "test_number_update");
	}
	
	@Test
	public void testDelete() {
		
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		purchaseOrder.setId("test_id_delete");
		purchaseOrder.setNumber("test_number");
		purchaseOrder.setCreated(new Date(0));
		
		purchaseOrderDao.create(purchaseOrder);
		
		purchaseOrderDao.delete(purchaseOrder);
		
		purchaseOrder = purchaseOrderDao.get("test_id_delete");
		
		assertNull(purchaseOrder);
	}
}
