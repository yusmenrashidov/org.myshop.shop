package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.Item;

public interface ItemDao {

    public void create(Item item);

    public List<Item> read();
    
    public Item get(String id);

    public Item update(Item item);

    public void delete(Item item);
}
