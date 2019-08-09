package org.org.myshop.shop.api.rest.servlet.exc;

public class PostedPurchaseOrderDeserializationException extends Exception{

	private static final long serialVersionUID = 1L;

    public PostedPurchaseOrderDeserializationException(Throwable cause) {
        super(cause);
    }

    public PostedPurchaseOrderDeserializationException(String msg) {
        super(msg);
    }

    public PostedPurchaseOrderDeserializationException() {
        super();
    }	
}
