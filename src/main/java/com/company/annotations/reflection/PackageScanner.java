package com.company.annotations.reflection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PackageScanner {
    public List<Class<?>> scanPackage(String packageName) {
        try {
            String path = packageName.replace(".", "/");
            URL url = getClass().getClassLoader().getResource(path);
            File file = new File(url.toURI());
            return findClasses(file, packageName);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<Class<?>> findClasses(File directory, String prefix) throws ClassNotFoundException {
        List<Class<?>> res = new ArrayList<>();
        for (File f : directory.listFiles()) {
            if (f.isDirectory()) {
                res.addAll(findClasses(f, prefix + "." + f.getName()));
            } else if (f.isFile() && f.getName().endsWith(".class")) {
                String fileName = f.getName();
                String className = prefix+"."+fileName.substring(0,fileName.length() - 6);
                res.add(Class.forName(className));
            }
        }
        return res;
    }




    public List<Class<?>> findClassesWithAnnotation(Class annotation, String packageName){
        return scanPackage(packageName).stream()
                .filter(c->c.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }
}
