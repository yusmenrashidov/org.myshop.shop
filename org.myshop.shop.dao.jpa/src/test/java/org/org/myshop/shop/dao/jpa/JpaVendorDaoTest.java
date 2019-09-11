package org.org.myshop.shop.dao.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.myshop.shop.model.Vendor;
import org.org.myshop.shop.jpa.model.VendorEntity;

public class JpaVendorDaoTest {

	private static final String TEST_VENDOR_ID = "TEST_VENDOR_ID";
	private static final String TEST_VENDOR_NAME = "TEST_VENDOR_NAME";

	@Mock
	private EntityManagerFactory factoryMock;
	
	@Mock
	private EntityManager entityManagerMock;
	
	@Mock
	private VendorEntity vendorEntityMock;
	
	@Mock
	private Vendor vendorMock;
	
	@Mock
	private JpaVendorDao vendorDaoMock;
	
	@Mock
	private Query queryMock;
	
	@Mock
	private List<VendorEntity> entityListMock;
	
	@Mock
	private EntityTransaction entityTransactionMock;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		when(entityManagerMock.createNativeQuery(JpaVendorDao.READ_QUERY, VendorEntity.class)).thenReturn(queryMock);
		when(queryMock.getResultList()).thenReturn(entityListMock);
		
		when(entityManagerMock.find(VendorEntity.class, TEST_VENDOR_ID)).thenReturn(vendorEntityMock);
		when(vendorEntityMock.toVendor()).thenReturn(vendorMock);
		
		when(vendorMock.getId()).thenReturn(TEST_VENDOR_ID);
		when(vendorMock.getName()).thenReturn(TEST_VENDOR_NAME);
		
		when(vendorEntityMock.getId()).thenReturn(TEST_VENDOR_ID);
		when(vendorEntityMock.getName()).thenReturn(TEST_VENDOR_NAME);
				
		vendorDaoMock = new JpaVendorDao(factoryMock);
		vendorEntityMock = new VendorEntity(vendorMock);
	}
	
	@Test
	public void testCreate() {
		vendorDaoMock.create(vendorMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testRead() {
		List<Vendor> vendorList = vendorDaoMock.read();
		
		verify(entityManagerMock).createNativeQuery(JpaVendorDao.READ_QUERY, VendorEntity.class);
		verify(queryMock).getResultList();
		
		assertNotNull(vendorList);
	}
	
	@Test
	public void testGet() {
		vendorMock = vendorDaoMock.get(TEST_VENDOR_ID);
		
		verify(entityManagerMock).find(VendorEntity.class, TEST_VENDOR_ID);
		
		assertNotNull(vendorMock);
		
		assertEquals(vendorMock.getId(), TEST_VENDOR_ID);
		assertEquals(vendorMock.getName(), TEST_VENDOR_NAME);
	}
	
	@Test
	public void testUpdate() {
		vendorDaoMock.update(vendorMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testDelete() {
		vendorDaoMock.delete(vendorMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testGet_failed() {
		when(entityManagerMock.find(VendorEntity.class, TEST_VENDOR_ID)).thenThrow(new NullPointerException());
		
		vendorMock = vendorDaoMock.get(TEST_VENDOR_ID);
		
		assertNull(vendorMock);
	}
}