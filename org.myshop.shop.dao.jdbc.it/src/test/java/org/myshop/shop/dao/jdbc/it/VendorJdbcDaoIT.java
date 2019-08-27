package org.myshop.shop.dao.jdbc.it;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.myshop.shop.dao.VendorDao;
import org.myshop.shop.dao.jdbc.JdbcVendorDao;
import org.myshop.shop.model.Vendor;

public class VendorJdbcDaoIT {
    
    private static final String TEST_VENDOR_ID = "test_vendor_id";
    private static final String TEST_VENDOR_NAME = "test_vendor_name";
    private static final String CHANGED_VENDOR_NAME = "changed_vendor_name";
    
    private static final int TEST_VENDOR_COUNT = 100;

    private static String dbURL = "jdbc:derby://localhost:1527/memory:myshop;create=true;user=myshop;password=myshop";

    private Connection connection;

    private VendorDao vendorDao;
    
    @Before
    public void setup() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            // Get a connection
            connection = DriverManager.getConnection(dbURL);
        } catch (Exception except) {
            except.printStackTrace();
        }

        vendorDao = new JdbcVendorDao(connection);
        
        cleanup();
    }
    
    @Test
    public void testCreate() {
        Vendor vendor = new Vendor();
        vendor.setId(TEST_VENDOR_ID);
        vendor.setName(TEST_VENDOR_NAME);
        
        vendorDao.create(vendor);
        
        List<Vendor> vedorList = vendorDao.read();
        assertEquals(1, vedorList.size());
        
        Vendor vendorRetrievedFromDb = vedorList.get(0);
        assertEquals(TEST_VENDOR_ID, vendorRetrievedFromDb.getId());
        assertEquals(TEST_VENDOR_NAME, vendorRetrievedFromDb.getName());
    }
    
    @Test
    public void testRead() {
        for (int i = 0; i < TEST_VENDOR_COUNT; i++) {
            Vendor vendor = new Vendor();
            vendor.setId(String.valueOf(i));
            vendor.setName("Vendor " + i);
            
            vendorDao.create(vendor);
        }
        
        List<Vendor> vendorList = vendorDao.read();
        
        assertEquals(TEST_VENDOR_COUNT, vendorList.size());
    }
    
    @Test
    public void testUpdate() {
        Vendor vendor = new Vendor();
        vendor.setId(TEST_VENDOR_ID);
        vendor.setName(TEST_VENDOR_NAME);
        
        vendorDao.create(vendor);
        
        vendor.setName(CHANGED_VENDOR_NAME);
        
        vendorDao.update(vendor);
        
        Vendor vendorReadFromDb = vendorDao.get(TEST_VENDOR_ID);
        
        assertEquals(CHANGED_VENDOR_NAME, vendorReadFromDb.getName());
    }
    
    @Test
    public void testDelete() {
        Vendor vendor = new Vendor();
        vendor.setId(TEST_VENDOR_ID);
        vendor.setName(TEST_VENDOR_NAME);
        
        vendorDao.create(vendor);
        
        List<Vendor> vendorList = vendorDao.read();
        
        assertEquals(1, vendorList.size());
        
        vendorDao.delete(vendor);
        
        vendorList = vendorDao.read();
        
        assertEquals(0, vendorList.size());
    }
    
    private void cleanup() {
        List<Vendor> vendorList = vendorDao.read();
        
        for (Vendor vendor : vendorList) {
            vendorDao.delete(vendor);
        }
    }
}
