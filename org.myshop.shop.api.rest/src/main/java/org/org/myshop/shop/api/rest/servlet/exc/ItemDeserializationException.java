package org.org.myshop.shop.api.rest.servlet.exc;

public class ItemDeserializationException extends Exception {

    private static final long serialVersionUID = 1L;

    public ItemDeserializationException(Throwable cause) {
        super(cause);
    }

    public ItemDeserializationException(String msg) {
        super(msg);
    }

    public ItemDeserializationException() {
        super();
    }
    
}
