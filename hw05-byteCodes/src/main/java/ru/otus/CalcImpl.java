package ru.otus;

public class CalcImpl implements CalcInterface{

    @Override
    public void calculation(int par1) {
        System.out.println("Result: " + par1 * 2);
        System.out.println("----------------");
    }

    @Override
    public void calculation(int par1, int par2) {
        System.out.println("Result: " + (par1 + par2));
        System.out.println("----------------");
    }

    @Override
    public void calculation(int par1, int par2, String par3) {
        System.out.println("Result: " + par1 * par2 + " " + par3);
        System.out.println("----------------");
    }

}
