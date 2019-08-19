package org.org.myshop.shop.jpa;

import java.util.List;

import org.myshop.shop.dao.CustomerDao;
import org.myshop.shop.model.Customer;

import javax.persistence.*;

public class JpaCustomerDao implements CustomerDao{

	private EntityManagerFactory factory;
	private EntityManager entityManager;
	
	protected static final String READ_QUERY = "SELECT * FROM customer";
	
	public JpaCustomerDao(String persistenceUnitName) {
		
		factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		entityManager = factory.createEntityManager();
	}
	
	public void create(Customer customer) {
		
		entityManager.getTransaction().begin();
		
		entityManager.persist(customer);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@SuppressWarnings("unchecked")
	public List<Customer> read() {
		
		entityManager.getTransaction().begin();
		
		List<Customer> customerList = entityManager.createQuery(READ_QUERY).getResultList();
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return customerList;
	}

	public Customer get(String id) {
		
		Customer customer = null;
		
		customer = entityManager.find(Customer.class, id);
		
		return customer;
	}

	public Customer update(Customer customer) {
		
		Customer customerToUpdate = entityManager.find(Customer.class, customer.getId());
		
		entityManager.getTransaction().begin();
		
		customerToUpdate.setId(customer.getId());
		customerToUpdate.setName(customer.getName());
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return customerToUpdate;
	}

	public void delete(Customer customer) {
		
		entityManager.getTransaction().begin();
		
		entityManager.remove(customer);
		entityManager.getTransaction().commit();
		entityManager.close();	
	}
	
	

}
