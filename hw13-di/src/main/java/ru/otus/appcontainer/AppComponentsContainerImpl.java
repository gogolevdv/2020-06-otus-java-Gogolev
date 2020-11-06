package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.config.AppConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...

        Reflections reflection = new Reflections(configClass, new FieldAnnotationsScanner(), new MethodAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner());

        List<Method> methods = reflection.getMethodsAnnotatedWith(AppComponent.class).stream()
                .sorted(Comparator.comparingInt(x -> x.getAnnotation(AppComponent.class).order())).collect(Collectors.toList());


        var appConfig = createAppConfig(configClass);

        Object objectOfMethod = null;

        for (Method method : methods) {

            var params = new ArrayList<>();

            for (Class<?> param : method.getParameterTypes()) {  params.add(getComponentByType(param));  }

            try {
                objectOfMethod = method.invoke(appConfig, params.stream().toArray(Object[]::new));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            appComponentsByName.put(method.getName(), objectOfMethod);
                appComponents.add(objectOfMethod);

        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private AppConfig createAppConfig(Class<?> configClass){
        try {
            Constructor<?> constructor = configClass.getConstructor();
            return (AppConfig) constructor.newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new ReflectionsException(String.format("Given class is not create %s", configClass.getName()));
        }
    }

    private Object getComponentByType(Class<?> component){
        return appComponents.stream().filter(x -> component.isAssignableFrom(x.getClass())).findFirst().orElseThrow();
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {

        return (C) getComponentByType(componentClass);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
