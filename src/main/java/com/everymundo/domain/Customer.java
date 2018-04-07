package com.everymundo.domain;

import com.everymundo.dto.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * {@link Customer} class
 *
 * @author Angel Lecuona
 *
 */
@Document
public class Customer implements Serializable {

    private static final long serialVersionUID = 2260731212765336855L;

    @Id
    private String customerId;

    @Indexed
    private String name;

    private String lastName;
    private Date dob;
    private String email;
    private boolean active;

    private List<Order> orders;

    public Customer() {
        orders = new ArrayList<>();
    }

    public Customer(String name, String lastName, Date dob, String email, boolean active) {
        this.name = name;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.active = active;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
