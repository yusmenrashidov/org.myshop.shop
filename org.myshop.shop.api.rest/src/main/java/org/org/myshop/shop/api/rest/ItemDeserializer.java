package org.org.myshop.shop.api.rest;

import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.exc.ItemDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IItemDeserializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ItemDeserializer implements IItemDeserializer {

    public Item deserialize(String itemJsonString) throws ItemDeserializationException {
        Gson gson = new Gson();
        
        try {
            if (itemJsonString == null) {
                throw new ItemDeserializationException("Input string is null");
            }
            
            if (itemJsonString.isEmpty()) {
                throw new ItemDeserializationException("Input string is empty");
            }
            
            Item item = gson.fromJson(itemJsonString, Item.class);
            
            return item;
        } catch (JsonSyntaxException e) {
            throw new ItemDeserializationException(e);
        }
    }
}
