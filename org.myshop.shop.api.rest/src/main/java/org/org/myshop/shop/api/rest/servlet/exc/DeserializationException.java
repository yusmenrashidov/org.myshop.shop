package org.org.myshop.shop.api.rest.servlet.exc;

public class DeserializationException extends Exception{

	private static final long serialVersionUID = 1L;

	public DeserializationException(Throwable cause) {
        super(cause);
    }

    public DeserializationException(String msg) {
        super(msg);
    }

    public DeserializationException() {
        super();
    }	
	
}
