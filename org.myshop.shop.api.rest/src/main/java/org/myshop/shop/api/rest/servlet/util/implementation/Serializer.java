package org.myshop.shop.api.rest.servlet.util.implementation;

import java.util.List;
import java.lang.reflect.Type;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;

import com.google.gson.Gson;

public class Serializer<T> implements ISerializer<T>{

	private Type typeOfT; 
	
	public Serializer(Type typeOfT) {
		this.typeOfT = typeOfT;
	}
	
	
	public String serializeList(List<T> listToSerialize) {
		
		Gson gson = new Gson();
		
		String listToJson = gson.toJson(listToSerialize);
		
		return listToJson;
	}

}
