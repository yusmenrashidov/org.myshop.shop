package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.myshop.shop.dao.CustomerDao;
import org.myshop.shop.model.Customer;
import org.org.myshop.shop.jpa.model.CustomerEntity;

public class JpaCustomerDao implements CustomerDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	protected static final String READ_QUERY = "SELECT * FROM customer";
	
	public JpaCustomerDao(EntityManagerFactory factory) {
		this.factory = factory;
		entityManager = factory.createEntityManager();
	}
	
	public void create(Customer customer) {
		
		CustomerEntity customerEntity = new CustomerEntity(customer);
		
		entityManager.getTransaction().begin();
		entityManager.persist(customerEntity);
		entityManager.getTransaction().commit();
	}

	public List<Customer> read() {
		
		List<Customer> customerList = new ArrayList<Customer>();
				
		@SuppressWarnings("unchecked")
		List<CustomerEntity> entityList = entityManager.createNativeQuery(READ_QUERY, CustomerEntity.class).getResultList();
		
			for(int i=0; i < entityList.size(); i++) {
			
			customerList.add(entityList.get(i).toCustomer());
		}
		
		return customerList;
	}

	public Customer get(String id) {
		
		try {
		CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, id);
		return customerEntity.toCustomer();
	
		}catch(NullPointerException e) {
			return null;
		}
	}

	public Customer update(Customer customer) {
		
		entityManager = factory.createEntityManager();
		
		CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customer.getId());
		
		entityManager.getTransaction().begin();
		customerEntity.setName(customer.getName());
		entityManager.getTransaction().commit();
		
		customerEntity = entityManager.find(CustomerEntity.class, customer.getId());
		
		return customerEntity.toCustomer();
	}

	public void delete(Customer customer) {
		
		entityManager = factory.createEntityManager();
		
		CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customer.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(customerEntity);
		entityManager.getTransaction().commit();
		
	}

}
