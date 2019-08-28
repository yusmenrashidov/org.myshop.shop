package org.org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.myshop.shop.dao.CustomerDao;
import org.myshop.shop.model.Customer;
import org.org.myshop.shop.jpa.model.CustomerEntity;

public class JpaCustomerDao implements CustomerDao{

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	
	public JpaCustomerDao() {
		factory = Persistence.createEntityManagerFactory("CustomerEntity");
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
		List<CustomerEntity> entityList = entityManager.createNativeQuery("SELECT * FROM customer").getResultList();
		
			for(int i=0; i < entityList.size(); i++) {
			
			Customer customer = new Customer();
			customer.setId(entityList.get(i).getId());
			customer.setName(entityList.get(i).getName());
			
			customerList.add(customer);
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
