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
                // 2. 메서드의 파라미터 여부 확인
                if (method.getParameterCount() != 0) {
                    // 파라미터가 있는 경우
                    Parameter[] parameters = method.getParameters();
                    for (Parameter parameter : parameters) {
                        if (parameter.isAnnotationPresent(Principle.class)) {
                            try {
                                method.invoke(uc, SessionUser.getInstance());
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                method.invoke(uc, Model.getInstance());
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } else {
                    // 파라미터가 없는 경우
                    try {
                        method.invoke(uc);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}