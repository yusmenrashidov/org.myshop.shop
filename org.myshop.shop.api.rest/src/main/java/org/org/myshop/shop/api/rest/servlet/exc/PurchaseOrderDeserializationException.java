package org.org.myshop.shop.api.rest.servlet.exc;

public class PurchaseOrderDeserializationException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public PurchaseOrderDeserializationException(Throwable cause) {
        super(cause);
    }

    public PurchaseOrderDeserializationException(String msg) {
        super(msg);
    }

    public PurchaseOrderDeserializationException() {
        super();
    }
}
