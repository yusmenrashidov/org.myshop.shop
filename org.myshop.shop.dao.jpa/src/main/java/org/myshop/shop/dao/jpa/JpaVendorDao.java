package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.myshop.shop.dao.VendorDao;
import org.myshop.shop.model.Vendor;

import org.org.myshop.shop.jpa.model.VendorEntity;

public class JpaVendorDao implements VendorDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	public final static String READ_QUERY_NAME = "vendor.read";
	
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

	@SuppressWarnings("unchecked")
	public List<Vendor> read() {
		List<VendorEntity> entityList;

		try {
		entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		}catch(PersistenceException e) {
			return null;
		}

		return entityList.stream()
						.map(vendorEntity -> vendorEntity.toVendor())
						.collect(Collectors.toList());
	}

	public Vendor get(String id) {
		try {
		return entityManager.find(VendorEntity.class, id).toVendor();
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
