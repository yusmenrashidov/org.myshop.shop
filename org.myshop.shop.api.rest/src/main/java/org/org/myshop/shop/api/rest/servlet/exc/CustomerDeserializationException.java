package org.org.myshop.shop.api.rest.servlet.exc;

public class CustomerDeserializationException extends Exception{

	private static final long serialVersionUID = 1L;

    public CustomerDeserializationException(Throwable cause) {
        super(cause);
    }

    public CustomerDeserializationException(String msg) {
        super(msg);
    }

    public CustomerDeserializationException() {
        super();
    }	
}
