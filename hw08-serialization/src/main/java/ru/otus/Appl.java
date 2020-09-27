package ru.otus;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Appl {

    public static void main(String[] args) {


        Gson gson = new Gson();
        MyGson mygson = new MyGson();

        char[] mychar = {'1','2','3','4'};

        var mylist = new ArrayList<String>();
        mylist.add("One");
        mylist.add("Two");
        mylist.add("Three");

        AnyObject obj = new AnyObject(123,"Test",101L,33,'s','f');

        AnyObject obj_char = new AnyObject(mychar);
        AnyObject obj_list = new AnyObject(mylist);

        String sss = gson.toJson(obj);
        String sss1 = gson.toJson(obj_char);
        String sss2 = gson.toJson(obj_list);
        String ddd = mygson.toJson(obj);
        String ddd1 = mygson.toJson(obj_char);
        String ddd2 = mygson.toJson(obj_list);

        System.out.println(sss);
        System.out.println(sss1);
        System.out.println(sss2);

        System.out.println(ddd);
        System.out.println(ddd1);
        System.out.println(ddd2);


    }
}
