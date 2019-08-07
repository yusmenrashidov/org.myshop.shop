package org.org.myshop.shop.api.rest.servlet.exc;

public class VendorDeserializationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public VendorDeserializationException(Throwable cause) {
        super(cause);
    }

    public VendorDeserializationException(String msg) {
        super(msg);
    }

    public VendorDeserializationException() {
        super();
    }	

}
