package ru.otus;

public class Cat {
    String color;
    int age;
    int length;

    public Cat(String color, int age, int length) {
        this.color = color;
        this.age = age;
        this.length = length;
    }

    public String getColor() {
        return color;
    }

    public int getAge() {
        return age;
    }

    public int getLength() {
        return length;
    }
}
