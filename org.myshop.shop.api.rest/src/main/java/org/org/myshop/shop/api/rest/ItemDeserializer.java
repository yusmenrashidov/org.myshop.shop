package org.org.myshop.shop.api.rest;

import java.io.IOException;

import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.util.IItemDeserializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDeserializer implements IItemDeserializer {

    public Item deserialize(String itemJsonString) throws IOException{
       
    	Item item = null;
    	ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	
			item = mapper.readValue(itemJsonString, Item.class);
		
        return item;
    }

}
