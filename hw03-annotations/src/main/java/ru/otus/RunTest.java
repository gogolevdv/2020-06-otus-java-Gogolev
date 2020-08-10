package ru.otus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class RunTest {

    static void runTestCalc(TestCalc nameTestCalc) throws InvocationTargetException, IllegalAccessException {

        // Переменные для статистики
        int count = 0;    // Всего
        int success = 0;  // Успешно
        int fail = 0;     // Упало

        // Создаем массивы методов по каждой аннотации
        var methodBefore = new ArrayList<Method>();
        var methodTest = new ArrayList<Method>();
        var methodAfter = new ArrayList<Method>();

        // Собираем все методы класса
        Method[] method = nameTestCalc.getClass().getMethods();

        // Заполняем массивы методов
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

        // Выполняем методы с @Before
        for (Method mb : methodBefore) {
            mb.invoke(nameTestCalc);
        }

        // Выполняем методы с @BTest
        for (Method mt : methodTest) {
            mt.invoke(nameTestCalc);

            count++;

            if (nameTestCalc.result) success++;
            else fail++;

        }

        // Выполняем методы с @After
        for (Method ma : methodAfter) {
            ma.invoke(nameTestCalc);
        }

        // Выводим статистику
        System.out.println("Всего: " + count + " Успешно: " + success + " Упало: " + fail);

    }

}
