package org.org.myshop.shop.api.rest.servlet.exc;

public class ItemCategoryDeserializationException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ItemCategoryDeserializationException(Throwable cause) {
		super(cause);
	}
	
	public ItemCategoryDeserializationException(String msg) {
		super(msg);
	}
	
	public ItemCategoryDeserializationException(){
		super();
	}	
	
}
