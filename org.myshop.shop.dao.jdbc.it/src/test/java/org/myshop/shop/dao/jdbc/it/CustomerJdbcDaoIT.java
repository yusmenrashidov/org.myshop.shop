package org.myshop.shop.dao.jdbc.it;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.myshop.shop.dao.CustomerDao;
import org.myshop.shop.dao.jdbc.JdbcCustomerDao;
import org.myshop.shop.model.Customer;

public class CustomerJdbcDaoIT {
    
    private static final String TEST_CUSTOMER_ID = "test_customer_id";
    private static final String TEST_CUSTOMER_NAME = "test_customer_name";
    private static final String CHANGED_CUSTOMER_NAME = "changed_customer_name";

    private static String dbURL = "jdbc:derby://localhost:1527/memory:myshop;create=true;user=myshop;password=myshop";

    private Connection connection;

    private CustomerDao customerDao;
    
    @Before
    public void setup() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            // Get a connection
            connection = DriverManager.getConnection(dbURL);
        } catch (Exception except) {
            except.printStackTrace();
        }

        
        customerDao = new JdbcCustomerDao(connection);
    }
    
    @Test
    public void test() {
        Customer customer = new Customer();
        customer.setId(TEST_CUSTOMER_ID);
        customer.setName(TEST_CUSTOMER_NAME);
        
        customerDao.create(customer);
        
        List<Customer> customerList = customerDao.read();
        
        assertEquals(1, customerList.size());
        
        Customer customerRetrievedFromDb = customerList.get(0);
        assertEquals(TEST_CUSTOMER_ID, customerRetrievedFromDb.getId());
        assertEquals(TEST_CUSTOMER_NAME, customerRetrievedFromDb.getName());
        
        customerRetrievedFromDb.setName(CHANGED_CUSTOMER_NAME);
        
        customerDao.update(customerRetrievedFromDb);
        
        customerList = customerDao.read();
        customerRetrievedFromDb = customerList.get(0);
        assertEquals(CHANGED_CUSTOMER_NAME, customerRetrievedFromDb.getName());
        
        customerDao.delete(customerRetrievedFromDb);
        
        customerList = customerDao.read();
        assertEquals(0, customerList.size());
    }
}
