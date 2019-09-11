package org.org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.myshop.shop.dao.VendorDao;
import org.myshop.shop.model.Vendor;

import org.org.myshop.shop.jpa.model.VendorEntity;

public class JpaVendorDao implements VendorDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	protected final static String READ_QUERY = "SELECT * FROM vendor";
	
	public JpaVendorDao(EntityManagerFactory factory) {
		this.factory = factory;
		this.entityManager = factory.createEntityManager();
	}
	
	public void create(Vendor vendor) {
		
		VendorEntity vendorEntity = new VendorEntity(vendor);
		
		entityManager.getTransaction().begin();
		entityManager.persist(vendorEntity);
		entityManager.getTransaction().commit();
	}

	public List<Vendor> read() {

		List<Vendor> vendorList = new ArrayList<Vendor>();
		
		@SuppressWarnings("unchecked")
		List<VendorEntity> entityList = entityManager.createNativeQuery(READ_QUERY, VendorEntity.class).getResultList();
		
		for(int i=0; i < entityList.size();i++) {
			vendorList.add(entityList.get(i).toVendor());
		}
		
		return vendorList;
	}

	public Vendor get(String id) {
		try {
		VendorEntity entity = entityManager.find(VendorEntity.class, id);
		return entity.toVendor();
		
		}catch(NullPointerException e) {
		return null;
		}
	}

	public Vendor update(Vendor vendor) {
		
		entityManager = factory.createEntityManager();
		
		VendorEntity vendorEntity = entityManager.find(VendorEntity.class, vendor.getId());
		
		entityManager.getTransaction().begin();
		vendorEntity.setName(vendor.getName());
		entityManager.getTransaction().commit();
		
		vendorEntity = entityManager.find(VendorEntity.class, vendor.getId());
		
		return vendorEntity.toVendor();
	}

	public void delete(Vendor vendor) {
		
		entityManager = factory.createEntityManager();
		
		VendorEntity vendorEntity = entityManager.find(VendorEntity.class, vendor.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(vendorEntity);
		entityManager.getTransaction().commit();
	}
}
