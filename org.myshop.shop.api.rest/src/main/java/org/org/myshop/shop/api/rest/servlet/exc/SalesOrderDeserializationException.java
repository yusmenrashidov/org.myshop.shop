package org.org.myshop.shop.api.rest.servlet.exc;

public class SalesOrderDeserializationException extends Exception{

	private static final long serialVersionUID = 1L;

    public SalesOrderDeserializationException(Throwable cause) {
        super(cause);
    }

    public SalesOrderDeserializationException(String msg) {
        super(msg);
    }

    public SalesOrderDeserializationException() {
        super();
    }	
}
