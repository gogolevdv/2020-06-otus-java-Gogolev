package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ioc {

    // Массивы для имен и аргументов методов с аннотацией
    static List<String> nameMethodsIsLogTrue = new ArrayList<>();
    static List<Class<?>[]> argsMethodsIsLogTrue = new ArrayList<>();


    private Ioc() {
    }

    static CalcInterface createMyClass() throws ClassNotFoundException {

        Class<?> clazz = Class.forName("ru.otus.CalcImpl");

        Method[] methods = clazz.getMethods();

        for (Method md : methods) {
            if (md.isAnnotationPresent(Log.class)) {
                // методы с аннотацией помещаем в массивы
                nameMethodsIsLogTrue.add(md.getName());
                argsMethodsIsLogTrue.add(md.getParameterTypes());
            }
        }

        InvocationHandler handler = new DemoInvocationHandler(new CalcImpl());

        return (CalcInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{CalcInterface.class}, handler);

    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final CalcInterface myClass;

        DemoInvocationHandler(CalcInterface myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (nameMethodsIsLogTrue.contains(method.getName())) {

                for (Class[] argsofmethod : argsMethodsIsLogTrue)
                    if (Arrays.equals(argsofmethod, method.getParameterTypes())) {
                       // выводим параметры методов с аннотацией
                        System.out.print("Executed method:" + method.getName() + "; Params: ");
                        for (Object qq : args) System.out.print(qq + " ");
                        System.out.println();
                    }
            }

            return method.invoke(myClass, args);
        }

    }
}
