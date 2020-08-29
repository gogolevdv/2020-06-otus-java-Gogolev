package ru.otus;

public class RunCalc {
    public static void main(String[] args) throws ClassNotFoundException {

        CalcInterface myCalc = Ioc.createMyClass();

        myCalc.calculation(42);
        myCalc.calculation(42, 24);
        myCalc.calculation(42, 24, "Hello!");

    }
}