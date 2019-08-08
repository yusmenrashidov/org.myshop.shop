package org.org.myshop.shop.api.rest.servlet.exc;

public class SalesOrderLineDeserializationException extends Exception{

	private static final long serialVersionUID = 1L;

	public SalesOrderLineDeserializationException(Throwable cause) {
        super(cause);
    }

    public SalesOrderLineDeserializationException(String msg) {
        super(msg);
    }

    public SalesOrderLineDeserializationException() {
        super();
    }	
}
