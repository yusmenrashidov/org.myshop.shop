package org.org.myshop.shop.api.rest.servlet.exc;

public class PurchaseOrderLineDeserializationException extends Exception {

	private static final long serialVersionUID = 1L;

    public PurchaseOrderLineDeserializationException(Throwable cause) {
        super(cause);
    }

    public PurchaseOrderLineDeserializationException(String msg) {
        super(msg);
    }

    public PurchaseOrderLineDeserializationException() {
        super();
    }	
}
