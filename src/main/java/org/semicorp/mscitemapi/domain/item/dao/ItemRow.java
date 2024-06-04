package org.semicorp.mscitemapi.domain.item.dao;

import org.semicorp.mscitemapi.domain.item.Item;

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
