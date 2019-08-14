package org.org.myshop.shop.api.rest.servlet.exc;

public class PostedSalesOrderDeserializationException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public PostedSalesOrderDeserializationException(Throwable cause) {
        super(cause);
    }

    public PostedSalesOrderDeserializationException(String msg) {
        super(msg);
    }

    public PostedSalesOrderDeserializationException() {
        super();
    }	
}
