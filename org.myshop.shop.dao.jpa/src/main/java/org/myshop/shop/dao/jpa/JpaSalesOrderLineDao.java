package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.myshop.shop.dao.SalesOrderLineDao;
import org.myshop.shop.model.SalesOrderLine;

import org.org.myshop.shop.jpa.model.ItemEntity;
import org.org.myshop.shop.jpa.model.SalesOrderLineEntity;

public class JpaSalesOrderLineDao implements SalesOrderLineDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	public final static String READ_QUERY_NAME = "salesOrderLine.read";
	
	public JpaSalesOrderLineDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(SalesOrderLine line) {
		SalesOrderLineEntity entity = new SalesOrderLineEntity(line);
		
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<SalesOrderLine> read() {
		List<SalesOrderLineEntity> entityList;
		
		try {
		entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		
		}catch(PersistenceException e) {
			return null;
		}

		return entityList.stream()
				.map(salesOrderLineEntity -> salesOrderLineEntity.toSalesOrderLine())
				.collect(Collectors.toList());
	}

	public SalesOrderLine get(String id) {
		try {
		return entityManager.find(SalesOrderLineEntity.class, id).toSalesOrderLine();
		}catch(NullPointerException e) {
			return null;
		}
	}

	public SalesOrderLine update(SalesOrderLine line) {
		entityManager = factory.createEntityManager();
		
		SalesOrderLineEntity entity = entityManager.find(SalesOrderLineEntity.class, line.getId());
		
		try {
		entityManager.getTransaction().begin();
		entity.setItem(new ItemEntity(line.getItem()));
		entity.setAmmount(line.getAmmount());
		entity.setLineNumber(line.getLineNumber());
		entity.setPrice(line.getPrice());
		entity.setQuantity(line.getLineNumber());
		entityManager.getTransaction().commit();
		
		}catch(Exception e) {
			return null;
		}
		
		entity = entityManager.find(SalesOrderLineEntity.class, line.getId());
		
		return entity.toSalesOrderLine();
	}

	public void delete(SalesOrderLine line) {
		SalesOrderLineEntity entity = entityManager.find(SalesOrderLineEntity.class, line.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
