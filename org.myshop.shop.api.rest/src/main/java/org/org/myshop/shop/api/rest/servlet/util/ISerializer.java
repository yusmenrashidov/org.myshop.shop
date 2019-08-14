package org.org.myshop.shop.api.rest.servlet.util;

public interface ISerializer<T> {

    public T serialize(String stringToSerialize);
}
