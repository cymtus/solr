package com.linkstec.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class Item implements Serializable {

    @Field
    private String id;
    @Field
    private String title;
    @Field
    private Long price;

    public Item() {
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
