package ru.otus;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class RunTest {

    // Переменные для статистики
    static int count = 0;    // Всего
    static int success = 0;  // Успешно
    static int fail = 0;     // Упало


    static void runTestCalc(Class nameTestClass, Collection testData) throws Exception {

        // Создаем массивы методов по каждой аннотации
        var methodBefore = new ArrayList<Method>();
        var methodTest = new ArrayList<Method>();
        var methodAfter = new ArrayList<Method>();

        // Собираем все методы класса
        Method[] method = nameTestClass.getMethods();

        // Заполняем массивы методов по аннотациям
        for (Method md : method) {
            if (md.isAnnotationPresent(Before.class)) {
                methodBefore.add(md);
            }
            if (md.isAnnotationPresent(Test.class)) {
                methodTest.add(md);
            }
            if (md.isAnnotationPresent(After.class)) {
                methodAfter.add(md);
            }
        }

        Constructor<TestCalc> constructor = nameTestClass.getConstructor(double[].class);

        // ================================================
        // Выполняем циклы тестов в соответствии с количеством тестовых данных
        for (Object objectTestData : testData) {

            // Создаем объект класса с тестами
            TestCalc object = constructor.newInstance(objectTestData);

            // Выполняем методы с @Before
            for (Method mb : methodBefore) {
                mb.invoke(object);
            }

            // Выполняем методы с @BTest
            for (Method mt : methodTest) {
                mt.invoke(object);

                count++;

                if (object.isResult()) success++;
                else fail++;

            }

            // Выполняем методы с @After
            for (Method ma : methodAfter) {
                ma.invoke(object);
            }
        }
        //=============================

        // Выводим статистику
        System.out.println("Всего: " + count + " Успешно: " + success + " Упало: " + fail);
    }
}
