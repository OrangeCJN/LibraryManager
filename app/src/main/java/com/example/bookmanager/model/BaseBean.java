package com.example.bookmanager.model;

import java.io.Serializable;

/**
 * base class of all entity classes
 */
public class BaseBean implements Serializable {

    protected String key;

    public String getKey() {
        return key;
    }

    public BaseBean setKey(String key) {
        this.key = key;
        return this;
    }
}
