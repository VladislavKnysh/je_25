package com.company.servlets;

import com.company.ApplicationContext;
import com.company.annotations.*;
import com.company.annotations.reflection.PackageScanner;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DispatcherServlet extends HttpServlet {
    private final ApplicationContext context = new ApplicationContext();
    private final List<Class<?>> controllersClasses;
    private boolean dynamic = false;

    public DispatcherServlet() {
        PackageScanner packageScanner = new PackageScanner();
        controllersClasses =
                packageScanner.findClassesWithAnnotation(
                        Controller.class,
                        "com.company");
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        for (Class<?> controller : controllersClasses) {
            try {
                for (Method m : controller.getDeclaredMethods()) {
                    Object instance = context.getBeanByType(controller);
                    String address = checkMethod(m, req);
                    if (address == null) continue;
                    String addr = req.getContextPath() + "/" + address;

                    if (addr.equalsIgnoreCase(req.getRequestURI()) && !dynamic) {

                        m.invoke(instance, req, res);
                    } else if (addr.equalsIgnoreCase(req.getRequestURI()) && dynamic) {
                        m.invoke(instance, (getDynamicID(req)),
                                req, res);
                    }
                }
                return;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }


        }
        res.setStatus(404);
        res.getWriter().write("NOT FOUND");

    }


    private String checkMethod(Method m, HttpServletRequest req) {

        if (req.getMethod().equalsIgnoreCase("get")
                && m.isAnnotationPresent(GetMapping.class)) {
            dynamic = false;
            return m.getAnnotation(GetMapping.class).value();
        }
        if (req.getMethod().equalsIgnoreCase("post")
                && m.isAnnotationPresent(PostMapping.class)) {
            dynamic = false;
            return m.getAnnotation(PostMapping.class).value();
        }
        if (req.getMethod().equalsIgnoreCase("delete")
                && m.isAnnotationPresent(DeleteMapping.class)) {
            dynamic = false;
            return m.getAnnotation(DeleteMapping.class).value();
        }

        if (req.getMethod().equalsIgnoreCase("put")
                && m.isAnnotationPresent(PutMapping.class)) {
            dynamic = false;
            return m.getAnnotation(PutMapping.class).value();
        }
        if (req.getMethod().equalsIgnoreCase("get")
                && m.isAnnotationPresent(GetDynamicMapping.class)) {
            dynamic = true;
            String a = getDynamicID(req);
            if (Objects.nonNull(a)) {
                return m.getAnnotation(GetDynamicMapping.class).value().replace("{id}",
                        a);
            }

        }

        return null;
    }


    private String getDynamicID(HttpServletRequest req) {
        Pattern pattern = Pattern.compile("(?<=\\/)\\d+$");
        Matcher matcher = pattern.matcher(req.getRequestURI());
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return null;
        }

    }
}
