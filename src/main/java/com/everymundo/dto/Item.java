package com.everymundo.dto;
/**
 * {@link Item} class
 * Data Transfer Object
 *
 * @author Angel Lecuona
 *
 */
public class Item {

    private String name;
    private Float price;

    public Item(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    public Item(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
