package org.org.myshop.shop.api.rest.servlet.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class Serializer<T> implements ISerializer<T> {
    
    private Type typeOfT;
    
    public Serializer(Type typeOfT) {
        this.typeOfT = typeOfT;
    }

    public T serialize(String stringToSerialize) {
        
        Gson gson = new Gson();
        
        return gson.fromJson(stringToSerialize, typeOfT);
    }

}
