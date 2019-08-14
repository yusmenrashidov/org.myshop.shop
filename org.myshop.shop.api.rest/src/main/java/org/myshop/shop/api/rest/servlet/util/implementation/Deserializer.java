package org.myshop.shop.api.rest.servlet.util.implementation;

import java.lang.reflect.Type;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Deserializer<T> implements IDeserializer<T> {
    
    private Type typeOfT;
    
    public Deserializer(Type typeOfT) {
        this.typeOfT = typeOfT;
    }

    public T deserialize(String jsonString) throws DeserializationException{
        
        Gson gson = new Gson();
        
        try {
			if(jsonString == null) {
				throw new DeserializationException("Input string is null");
				
			}
			if(jsonString.isEmpty()) {
				throw new DeserializationException("Input string is empty");
			}
			
			return gson.fromJson(jsonString, typeOfT);
		}catch(JsonSyntaxException e) {
			throw new DeserializationException(e);
		}
        
        
        
    }

}
