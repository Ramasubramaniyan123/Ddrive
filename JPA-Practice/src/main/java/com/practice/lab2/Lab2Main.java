package com.practice.lab2;

import com.practice.lab1.Student;

import java.util.List;

public class Lab2Main {
    public static void main(String[] args) {
        StudentCrudService service = new StudentCrudService();
        try{
            Student s1 = service.createStudent("John", "Doe", "john.doe@example.com");
            Student s2 = service.createStudent("Jane", "Smith", "jane.smith@example.com");
            Student s3 = service.createStudent("Michael", "Johnson", "michael.johnson@example.com");
            Student s4 = service.createStudent("Emily","Davis", "emily.davis@example.com");
            Student s5 = service.createStudent("Robert", "Brown", "robert.brown@example.com");
            Student s6 = service.createStudent("Sophia", "Wilson", "sophia.wilson@example.com");

            System.out.println("\n===== ALL STUDENTS =====");
            List<Student> students = service.getAllStudent();
            students.forEach(System.out::println);

            System.out.println("\n===== FIND STUDENT BY ID =====");
            Student foundStudent = service.findStudentById(s3.getId());
            System.out.println(foundStudent);

            System.out.println("\n===== UPDATE STUDENT EMAIL =====");
            service.updateStudentEmail(s1.getId(), "john.new@example.com");
            System.out.println(service.findStudentById(s1.getId()));

            System.out.println("\n===== DELETE STUDENT =====");
            service.deleteStudent(s2.getId());

            System.out.println("\n===== STUDENTS AFTER DELETE =====");
            service.getAllStudent()
                    .forEach(System.out::println);
        } catch (Exception e) {
           service.shutdown();
        }

    }
}
