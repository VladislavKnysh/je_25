package com.company;

import com.company.annotations.Autowired;
import com.company.annotations.Component;
import com.company.annotations.Controller;
import com.company.annotations.reflection.PackageScanner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationContext {
    private Map<Class, Object> beans = new HashMap<>();
    private final PackageScanner packageScanner = new PackageScanner();

    public ApplicationContext(){
        createBeans();
    }

    private void createBeans() {
        List<Class> componentsClasses = getComponentClasses();
        for (Class componentsClass : componentsClasses) {
            createBean(componentsClass);
        }
        for (Class componentsClass : componentsClasses) {
            postProcessBean(beans.get(componentsClass));
        }
    }


    private List<Class> getComponentClasses() {
        String packageName = "com.company";
        List<Class<?>> list1 = packageScanner.findClassesWithAnnotation(Controller.class, packageName);
        List<Class<?>> list2 = packageScanner.findClassesWithAnnotation(Component.class, packageName);
        return Stream
                .concat(list1.stream(), list2.stream())
                .collect(Collectors.toList());
    }

    private void createBean(Class componentsClass) {
        try {
            Object obj = componentsClass.getConstructor().newInstance();
            beans.put(componentsClass, obj);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("ERROR CREATE BEAN " + componentsClass.getSimpleName());
        }
    }

    private void postProcessBean(Object bean) {
        List<Field>fields=
      Arrays.stream(bean.getClass().getDeclaredFields())
              .filter(field -> field.isAnnotationPresent(Autowired.class))
              .collect(Collectors.toList());
        for (Field field : fields) {
            field.setAccessible(true);
            Class type = field.getType();
            Object value=
                    getBeanByType(type);
        try {
            field.set(bean, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        }

    }

    public Object getBeanByType(Class type) {
        return beans.keySet().stream().filter(type::isAssignableFrom)
                .findFirst()
                .map(cls->beans.get(cls))
                .orElse(null);
    }


}
