package org.org.myshop.shop.api.rest;

import java.io.IOException;

import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.util.IItemDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ItemDeserializer implements IItemDeserializer {

    public Item deserialize(String itemJsonString) throws IOException{
       
    	Item item = null;
    	ObjectMapper mapper = new ObjectMapper();
    	
			item = mapper.readValue(itemJsonString, Item.class);
		
        return item;
    }

}
