package org.myshop.shop.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.swing.plaf.basic.BasicLabelUI;

import org.myshop.shop.dao.CustomerDao;
import org.myshop.shop.model.Customer;
import org.org.myshop.shop.jpa.model.CustomerEntity;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

public class JpaCustomerDao implements CustomerDao{

	public final static String READ_QUERY_NAME = "customer.read";

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
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

	@SuppressWarnings("unchecked")
	public List<Customer> read() {
		List<CustomerEntity> entityList;
		
		try{
		entityList = entityManager.createNamedQuery(READ_QUERY_NAME).getResultList();
		}catch(PersistenceException e) {
			return null;
		}

		return entityList.stream()
						 .map(customerEntity -> customerEntity.toCustomer())
				         .collect(toList());
	}

	public Customer get(String id) {
		try {
		return entityManager.find(CustomerEntity.class, id).toCustomer();
		}catch(NullPointerException e) {
			return null;
		}
	}

	public Customer update(Customer customer) {
		entityManager = factory.createEntityManager();
		
		CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customer.getId());

		try {
		entityManager.getTransaction().begin();
		customerEntity.setName(customer.getName());
		entityManager.getTransaction().commit();

		}catch(Exception e) {
			return null;
		}
		return entityManager.find(CustomerEntity.class, customer.getId()).toCustomer();
	}

	public void delete(Customer customer) {
		entityManager = factory.createEntityManager();
		
		CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customer.getId());
		
		entityManager.getTransaction().begin();
		entityManager.remove(customerEntity);
		entityManager.getTransaction().commit();
	}
}
