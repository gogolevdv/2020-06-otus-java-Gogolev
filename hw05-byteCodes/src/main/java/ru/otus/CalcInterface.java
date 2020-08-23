package ru.otus;

public interface CalcInterface {
    @Log
    void calculation(int par1);
    void calculation(int par1,int par2);
    @Log
    void calculation(int par1,int par2,String par3);
}
