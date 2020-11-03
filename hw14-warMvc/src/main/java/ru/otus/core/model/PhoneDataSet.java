package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class PhoneDataSet {

    @Id
    @GeneratedValue
    @Column(name = "phone_id")
    private Long id;

    @Column(name = "number", nullable = false)
    private String number;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL) //вариат 1 создания схемы
    @JoinColumn (name="user_id")
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // вариант 2 создания схемы
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PhoneDataSet(){}

    public PhoneDataSet(Long id, String number) {
        this.id = id;
        this.number = number;
    }


    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }

}
