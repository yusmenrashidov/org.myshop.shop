package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;

public interface ISerializer<T> {

	public String serializeList(List<T> listToSerialize);
}
