package comparable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentMain {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Ram"));
        students.add(new Student(2, "Arun"));
        students.add(new Student(3, "Ajay"));
        students.add(new Student(5, "sandy"));
        students.add(new Student(4, "raja"));
        Collections.sort(students);
        System.out.println(students);
    }
}
