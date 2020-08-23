package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Ioc {

    private Ioc() {
    }

    static CalcInterface createMyClass() {
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

            if (method.isAnnotationPresent(Log.class)) {
                System.out.print("Executed method:" + method.getName() + "; Params: " );
                for (Object qq : args) System.out.print(qq+" ");
                System.out.println();
            }

            return method.invoke(myClass, args);
        }

    }
}
