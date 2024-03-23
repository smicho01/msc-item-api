package org.semicorp.mscitemapi.item.dao;

import org.semicorp.mscitemapi.item.Item;

public class ItemRow implements DomainType<Item>, Comparable<Item> {

    private String id;
    private String name;
    private String descr;
    private String ownerId;


    @Override
    public int compareTo(Item o) {
        return 0;
    }

    @Override
    public Item asModel() {
        return null;
    }
}
