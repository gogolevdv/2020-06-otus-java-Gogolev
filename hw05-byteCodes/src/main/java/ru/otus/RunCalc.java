package ru.otus;

public class RunCalc {
    public static void main(String[] args) throws ClassNotFoundException {

        Ioc myIoc = new Ioc();
        CalcInterface myCalc = myIoc.createMyClass();

        myCalc.calculation(42);
        myCalc.calculation(42, 24);
        myCalc.calculation(42, 24, "Hello!");

    }
}