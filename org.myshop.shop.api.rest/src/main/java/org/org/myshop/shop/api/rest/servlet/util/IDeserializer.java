package org.org.myshop.shop.api.rest.servlet.util;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;

public interface IDeserializer<T> {

    public T deserialize(String jsonString) throws DeserializationException;
}
