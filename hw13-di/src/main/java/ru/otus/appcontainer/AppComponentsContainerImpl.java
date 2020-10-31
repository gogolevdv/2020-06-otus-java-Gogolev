package ru.otus.appcontainer;

import org.reflections.Reflections;
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

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<String, Object> appComponentsByReturnType = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        checkConfigClass(configClass);
        // You code here...

        Reflections reflection = new Reflections(configClass, new FieldAnnotationsScanner(), new MethodAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner());

        Set<Method> methods = reflection.getMethodsAnnotatedWith(AppComponent.class);

        Constructor<?> constructor = configClass.getConstructor();
        AppConfig appConfig = (AppConfig) constructor.newInstance();

        Object objectOfMethod;

        for (Method method:methods){
            appComponents.clear();
            for (Object param : method.getParameterTypes()) {
                appComponents.add(appComponentsByReturnType.get(String.valueOf(param)));
            }
            if (Arrays.stream(method.getParameterTypes()).count() == 0) {
                objectOfMethod = method.invoke(appConfig);

                appComponentsByName.put(method.getName(), objectOfMethod);
                appComponentsByReturnType.put(method.getReturnType().toString(), objectOfMethod);
            } else {
                objectOfMethod = method.invoke(appConfig, appComponents.toArray());
                appComponentsByName.put(method.getName(), objectOfMethod);
                appComponentsByReturnType.put(method.getReturnType().toString(), objectOfMethod);
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) throws Exception {

        for (Map.Entry<String,Object> entry:appComponentsByName.entrySet()){

            if (entry.getValue().getClass().getSimpleName().equals(componentClass.getSimpleName())){
                return (C) appComponentsByName.get(entry.getKey());
            }
            for (Class iface:entry.getValue().getClass().getInterfaces()){
                if(iface.getSimpleName().equals(componentClass.getSimpleName())){
                    return (C) appComponentsByName.get(entry.getKey());
                }
            }
        }
        throw new Exception("The class not found");
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
