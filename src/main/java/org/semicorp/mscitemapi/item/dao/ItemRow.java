package org.semicorp.mscitemapi.item.dao;

import org.semicorp.mscitemapi.item.Item;

public class ItemRow implements  Comparable<Item> {

    private String id;
    private String name;
    private String descr;
    private String userId;


    @Override
    public int compareTo(Item o) {
        return 0;
    }

}
