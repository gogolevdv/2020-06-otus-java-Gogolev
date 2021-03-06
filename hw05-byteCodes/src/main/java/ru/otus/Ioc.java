package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Ioc {

    // Набор для имен и типов аргументов методов с аннотацией
    private final Set<String> methodIsLogTrue = new HashSet<>();

    Ioc() {
    }

    CalcInterface createMyClass() throws ClassNotFoundException {

        Class<?> clazz = Class.forName("ru.otus.CalcImpl");

        Method[] methods = clazz.getMethods();

        for (Method md : methods) {
            if (md.isAnnotationPresent(Log.class)) {
                // методы с аннотацией помещаем в set
                methodIsLogTrue.add(formationNamePlusArgsMethod(md));
            }
        }

        InvocationHandler handler = new DemoInvocationHandler(new CalcImpl());

        return (CalcInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{CalcInterface.class}, handler);

    }

    class DemoInvocationHandler implements InvocationHandler {
        private final CalcInterface myClass;

        DemoInvocationHandler(CalcInterface myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            for (String qq1 : methodIsLogTrue) {
                if (qq1.equals(formationNamePlusArgsMethod(method))) {
                    System.out.print("Executed method:" + method.getName() + "; Params: ");
                    for (Object qq : args) System.out.print(qq + " ");
                    System.out.println();
                }
            }

            return method.invoke(myClass, args);
        }

    }

    public String formationNamePlusArgsMethod(Method method) {
        return method.getName().concat(Arrays.toString(method.getParameterTypes()));
    }
}
