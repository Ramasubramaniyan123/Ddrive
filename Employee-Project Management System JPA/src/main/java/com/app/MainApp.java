package com.app;



import com.model.*;
import com.service.DepartmentService;
import com.service.EmployeeService;
import com.service.ProjectService;
import com.service.TaskService;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static Scanner scanner = new Scanner(System.in);

    private static EmployeeService employeeService = new EmployeeService();
    private static DepartmentService departmentService = new DepartmentService();
    private static ProjectService projectService = new ProjectService();
    private static TaskService taskService = new TaskService();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== Employee Management System =====");
            System.out.println("1. Create Department");
            System.out.println("2. Create Employee");
            System.out.println("3. View Employees");
            System.out.println("4. Create Project");
            System.out.println("5. Create Task");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    createDepartment();
                    break;

                case 2:
                    createEmployee();
                    break;

                case 3:
                    viewEmployees();
                    break;

                case 4:
                    createProject();
                    break;

                case 5:
                    createTask();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private static void createDepartment() {

        System.out.print("Department Name: ");
        String name = scanner.nextLine();

        System.out.print("Location: ");
        String location = scanner.nextLine();

        Department dept = new Department(name, location);

        departmentService.createDepartment(dept);

        System.out.println("Department created");
    }

    private static void createEmployee() {

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("City: ");
        String city = scanner.nextLine();

        System.out.print("State: ");
        String state = scanner.nextLine();

        System.out.print("Country: ");
        String country = scanner.nextLine();

        System.out.print("Zip: ");
        String zip = scanner.nextLine();

        Address address = new Address(city, state, country, zip);

        Employee employee = new Employee(name, email, salary);
        employee.setAddress(address);

        employeeService.createEmployee(employee);

        System.out.println("Employee created");
    }

    private static void viewEmployees() {

        List<Employee> employees = employeeService.getAllEmployees();

        for (Employee e : employees) {
            System.out.println(e.getId() + " | " + e.getName() + " | " + e.getEmail() + " | " + e.getSalary());
        }
    }

    private static void createProject() {

        System.out.print("Project Name: ");
        String name = scanner.nextLine();

        System.out.print("Budget: ");
        double budget = scanner.nextDouble();
        scanner.nextLine();

        Project project = new Project(name, budget);

        projectService.createProject(project);

        System.out.println("Project created");
    }

    private static void createTask() {

        System.out.print("Task Title: ");
        String title = scanner.nextLine();

        System.out.print("Status: ");
        String status = scanner.nextLine();

        System.out.print("Project ID: ");
        Long projectId = scanner.nextLong();
        scanner.nextLine();

        Project project = projectService.getProject(projectId);

        if (project == null) {
            System.out.println("Project not found");
            return;
        }

        Task task = new Task(title, status);
        task.setProject(project);

        taskService.createTask(task);

        System.out.println("Task created");
    }
}