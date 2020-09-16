package ru.otus;

import java.util.ArrayList;
import java.util.List;

public class AnyObject {
    private Integer par1;
    private String par2;
    private Long par3;
    private int par4;
    private Character par5;
    private char par6;

    private char[] arrchar;

    private List<String> arrlist;


    AnyObject(Integer par1, String par2, Long par3,int par4,Character par5, char par6){
        this.par1 = par1;
        this.par2 = par2;
        this.par3 = par3;
        this.par4 = par4;
        this.par5 = par5;
        this.par6 = par6;
    }

    AnyObject(char[] arrchar){
        this.arrchar = arrchar;
    }

    AnyObject(ArrayList<String> arrlist){
        this.arrlist = arrlist;
    }
}
