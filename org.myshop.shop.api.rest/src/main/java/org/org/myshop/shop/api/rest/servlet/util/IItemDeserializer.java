package org.org.myshop.shop.api.rest.servlet.util;

import java.io.IOException;

import org.myshop.shop.model.Item;

public interface IItemDeserializer {

    public Item deserialize(String itemJsonString) throws IOException;
}
