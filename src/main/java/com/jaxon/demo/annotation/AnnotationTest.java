package com.jaxon.demo.annotation;

import java.lang.annotation.Annotation;

@MyAnnotation(className = "java.lang.String",value = 20)
public class AnnotationTest {

    public static void main(String[] args) {

        Class<AnnotationTest> annotationTestClass = AnnotationTest.class;

        MyAnnotation annotation1 = annotationTestClass.getAnnotation(MyAnnotation.class);
        System.out.println(annotation1.className());
        System.out.println(annotation1.value());

        Annotation[] annotations = annotationTestClass.getAnnotations();

        for(Annotation annotation :annotations){
            System.out.println(annotation.toString());
        }
    }
}
