package ru.geekbrains.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestExecutor {

    public static void main(String[] args) {
	    start(new Test1());
	    start(new Test2());
	    start(new Test3());
    }

    private static List<Method> getMethodsWithAnnotation(Class<?> type, Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> currentClass = type;
        while (currentClass != Object.class) {
            for (final Method method : currentClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    methods.add(method);
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return methods;
    }

    public static void start(Object suite) throws RuntimeException {
        Class<?> type = suite.getClass();

        List<Method> before = getMethodsWithAnnotation(type, BeforeSuite.class);
        List<Method> tests = getMethodsWithAnnotation(type, Test.class);
        List<Method> after = getMethodsWithAnnotation(type, AfterSuite.class);
        if (before.size() > 1) {
            throw new RuntimeException("Only one BeforeSuite method should be provided.");
        }
        if (after.size() > 1) {
            throw new RuntimeException("Only one AfterSuite method should be provided.");
        }

        try {
            if (before.size() == 1) {
                System.out.println("--- Before: " + type.toString() + " " + before.get(0).getName() + " running...");
                before.get(0).invoke(suite);
            }
            Collections.sort(tests, (method1, method2) -> {
                Test annotation1 = method1.getAnnotation(Test.class);
                Test annotation2 = method2.getAnnotation(Test.class);
                return annotation2.priority() - annotation1.priority();
            });
            for(final Method test : tests) {
                System.out.println("--- Test: " + type.toString() + " " + test.getName() + " running...");
                test.invoke(suite);
            }
            if (after.size() == 1) {
                System.out.println("--- After: " + type.toString() + " " + after.get(0).getName() + " running...");
                after.get(0).invoke(suite);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to invoke suite method.");
        }
    }
}
