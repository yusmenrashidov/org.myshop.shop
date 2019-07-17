package org.org.myshop.shop.dao.jdbc;

import org.myshop.shop.dao.jdbc.connection.*;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ConnectorTest {
	
	@Test
	public void testDatabaseConector() {
		
		Connector conn = new Connector();
		
		assertNotNull("Database connection should be established", conn.getConnection());
		
	}
	
}
