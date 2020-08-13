package ru.otus;

import java.util.Arrays;
import java.util.Collection;

public class RunAppl {

    public static void main(String[] args) throws Exception {

        Class<?> clazz = Class.forName("ru.otus.TestCalc");

        // Заполняем массив в тестовыми данными
        Collection testData = Arrays.asList(new double[][]{
                {1.0, 2.0, 3.0},
                {2.2, 3.3, 5.4},
                {3.4, 4.5, 7.9}
        });

        // Выполняем тесты
        RunTest.runTestCalc(clazz, testData);
    }
}
