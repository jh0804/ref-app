package ex03;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Scanner;

// A 개발자가 만든 것
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();

        Method[] methods = UserController.class.getDeclaredMethods();

        UserController uc = new UserController();
        for (Method method : methods) {
            Annotation anno = method.getDeclaredAnnotation(RequestMapping.class);
            RequestMapping rm = (RequestMapping) anno;

            // 1. 메서드 찾기
            if (rm.value().equals(path)) {
                Object[] invokeArgs = new Object[method.getParameterCount()];
                Parameter[] parameters = method.getParameters();

                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    if (parameter.isAnnotationPresent(Principle.class)) {
                        invokeArgs[i] = SessionUser.getInstance();
                    } else {
                        invokeArgs[i] = Model.getInstance();
                    }
                }

                try {
                    method.invoke(uc, invokeArgs);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}