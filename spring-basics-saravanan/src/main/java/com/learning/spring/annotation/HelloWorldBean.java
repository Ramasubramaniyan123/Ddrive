package com.learning.spring.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class HelloWorldBean {

    private String message;

    public HelloWorldBean() {

        //System.out.println("HelloWorldBean instance created!");
    }

    public void sayHello() {
        System.out.println("Hello World! The Spring Bean method was called successfully.");
    }

    public void setMessage(String message){
        this.message  = message;
    }

    public void getMessage(){
        System.out.println("Your Message : " + message);
    }


}
