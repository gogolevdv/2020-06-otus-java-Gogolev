package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDataSet {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private long id;

    @Column(name = "street")
    private String street;

    @OneToOne(mappedBy = "address",cascade = CascadeType.ALL)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AddressDataSet() {
    }

    public AddressDataSet(String street,long id) {
        this.street = street;
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id =" + id +
                ", street ='" + street + '\'' +
                '}';
    }
}
