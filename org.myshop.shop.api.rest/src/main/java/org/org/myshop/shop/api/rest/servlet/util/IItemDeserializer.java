package org.org.myshop.shop.api.rest.servlet.util;

import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.exc.ItemDeserializationException;

public interface IItemDeserializer {

    public Item deserialize(String itemJsonString) throws ItemDeserializationException;
}
