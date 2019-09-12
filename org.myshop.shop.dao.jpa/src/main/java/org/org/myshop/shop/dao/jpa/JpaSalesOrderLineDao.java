package org.org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.myshop.shop.dao.SalesOrderLineDao;
import org.myshop.shop.model.SalesOrderLine;

import org.org.myshop.shop.jpa.model.ItemEntity;
import org.org.myshop.shop.jpa.model.SalesOrderLineEntity;

public class JpaSalesOrderLineDao implements SalesOrderLineDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
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

	public List<SalesOrderLine> read() {
		
		List<SalesOrderLine> lineList = new ArrayList<SalesOrderLine>();
		
		@SuppressWarnings("unchecked")
		List<SalesOrderLineEntity> entityList = entityManager.createNativeQuery("SELECT * FROM salesOrderLine", SalesOrderLineEntity.class).getResultList();
		
		for(int i=0; i<entityList.size(); i++) {
			lineList.add(entityList.get(i).toSalesOrderLine());
		}
		
		return lineList;
	}

	public SalesOrderLine get(String id) {
		
		try {
		SalesOrderLineEntity entity = entityManager.find(SalesOrderLineEntity.class, id);
		return entity.toSalesOrderLine();
		
		}catch(NullPointerException e) {
			return null;
		}
		
	}

	public SalesOrderLine update(SalesOrderLine line) {
		
		entityManager = factory.createEntityManager();
		
		SalesOrderLineEntity entity = entityManager.find(SalesOrderLineEntity.class, line.getId());
		
		entityManager.getTransaction().begin();
		entity.setItem(new ItemEntity(line.getItem()));
		entity.setAmmount(line.getAmmount());
		entity.setLineNumber(line.getLineNumber());
		entity.setPrice(line.getPrice());
		entity.setQuantity(line.getLineNumber());
		entityManager.getTransaction().commit();
		
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