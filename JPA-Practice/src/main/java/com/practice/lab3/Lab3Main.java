package com.practice.lab3;

import com.practice.lab1.Student;
import com.practice.lab2.StudentCrudService;

import java.util.List;

public class Lab3Main {
    public static void main(String[] args) {
        StudentCrudService service = new StudentCrudService();

        try{
            System.out.println("Total Students " + service.countStudents());
            System.out.println("=== Students with lastName = 'Doe' ===");
            List<Student> singhList = service.findByLastName("Doe");
            singhList.forEach(System.out::println);
        }
        finally{
            service.shutdown();
        }
    }
}
