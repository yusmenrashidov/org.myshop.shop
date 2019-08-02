package org.org.myshop.shop.api.rest.servlet.exc;

public class ProductGroupDeserializationException extends Exception{

	private static final long serialVersionUID = 1L;
	
	 public ProductGroupDeserializationException(Throwable cause) {
	   super(cause);
	 }

	 public ProductGroupDeserializationException(String msg) {
	   super(msg);
	 }

	 public ProductGroupDeserializationException() {
	   super();
	 }
}
