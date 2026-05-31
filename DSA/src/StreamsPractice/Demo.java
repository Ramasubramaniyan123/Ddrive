package StreamsPractice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(1, "Ram", "IT", 60000.0, 24),
                new Employee(2, "John", "HR", 40000, 30),
                new Employee(3, "David", "IT", 75000, 28),
                new Employee(4, "Anu", "Finance", 50000, 26),
                new Employee(5, "Steve", "HR", 45000, 32),
                new Employee(6, "Priya", "IT", 85000, 27),
                new Employee(7, "Kumar", "Finance", 55000, 29),
                new Employee(8, "Alex", "IT", 70000, 31)
        );

//        employees.stream()
//                .filter(employee -> employee.getDepartment().equalsIgnoreCase("IT"))
//                .map(Employee::getName)
//                .forEach(System.out::println);

//        employees.stream()
//                .filter(employee -> employee.getSalary() > 50000)
//                .map(Employee::getName)
//                .forEach(System.out::println);

//        List<String> collect = employees.stream()
//                .map(Employee::getName)
//                .toList();
//
//        System.out.println(collect);

//        employees.stream()
//                .sorted(Comparator.comparingDouble(Employee::getSalary))
//                .forEach(employee -> System.out.println(employee.getName() + " - " + (int) employee.getSalary()));

//        employees.stream()
//                .sorted(Comparator.comparingInt(Employee::getAge).reversed())
//                .forEach(e-> System.out.println(e.getName() + " - " + e.getAge()));

        //Count employees in IT department
//        var count = employees.stream()
//                .filter(employee -> employee.getDepartment().equalsIgnoreCase("IT"))
//                .count();
//        System.out.println(count);

        //Find max of salary
//        Employee employee = employees.stream()
//                .max(Comparator.comparingDouble(Employee::getSalary))
//                .orElse(null);
//        System.out.println(employee.getName() + " - "+ employee.getSalary());
//
//        Employee minSalary = employees.stream()
//                .min(Comparator.comparingDouble(Employee::getSalary))
//                .orElse(null);
//        System.out.println(minSalary.getName() + " - "+ minSalary.getSalary());

        //Find average Salary


//        double employee = employees.stream()
//                .mapToDouble(Employee::getSalary)
//                .average()
//                .orElse(0.0);
//        System.out.println(employee);

//        var list = employees.stream()
//                .map(Employee::getName)
//                .map(String::toUpperCase)
//                .toList();
//        System.out.println(list);

//        Map<String, List<String>> emp = new HashMap<>();
//        for (Employee employee : employees) {
//            emp.computeIfAbsent(employee.getDepartment(), k -> new ArrayList<>()).add(employee.getName());
//        }
//        for (String dept : emp.keySet()) {
//            System.out.println(dept + " -> " + String.join(", ", emp.get(dept)));
//        }

        var map = employees.stream()
                .collect(
                        Collectors.groupingBy(
                                Employee::getDepartment, Collectors.mapping(Employee::getName, Collectors.toList())
                        )
                );

        map.forEach((dept, names) ->
                System.out.println(dept + " -> " + String.join(" ,", names)));

    }
}
