import java.util.*;
import java.util.stream.*;
import java.util.function.*;

class Employee {
    String name;
    int age;
    double salary;
    Employee(String name, int age, double salary) {
        this.name = name; this.age = age; this.salary = salary;
    }
    public String toString() {
        return name + " - Age: " + age + ", Salary: " + salary;
    }
}

class Student {
    String name;
    double marks;
    Student(String name, double marks) {
        this.name = name; this.marks = marks;
    }
    public String toString() {
        return name + " - Marks: " + marks;
    }
}

class Product {
    String name;
    double price;
    String category;
    Product(String name, double price, String category) {
        this.name = name; this.price = price; this.category = category;
    }
    public String toString() {
        return name + " (" + category + ") - " + price;
    }
}

public class LambdaStreamApp {
    public static void main(String[] args) {
        System.out.println("=== Part A: Sorting Employees with Lambda ===");
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", 25, 45000));
        employees.add(new Employee("Alice", 30, 55000));
        employees.add(new Employee("Bob", 22, 40000));
        employees.add(new Employee("Charlie", 28, 60000));

        System.out.println("\nSort by Name:");
        employees.sort((e1, e2) -> e1.name.compareTo(e2.name));
        employees.forEach(System.out::println);

        System.out.println("\nSort by Age:");
        employees.sort((e1, e2) -> Integer.compare(e1.age, e2.age));
        employees.forEach(System.out::println);

        System.out.println("\nSort by Salary (Descending):");
        employees.sort((e1, e2) -> Double.compare(e2.salary, e1.salary));
        employees.forEach(System.out::println);

        System.out.println("\n=== Part B: Filter and Sort Students Using Streams ===");
        List<Student> students = Arrays.asList(
                new Student("Ravi", 85),
                new Student("Neha", 72),
                new Student("Amit", 90),
                new Student("Priya", 65),
                new Student("Karan", 78)
        );
        List<String> topStudents = students.stream()
                .filter(s -> s.marks > 75)
                .sorted((s1, s2) -> Double.compare(s2.marks, s1.marks))
                .map(s -> s.name)
                .collect(Collectors.toList());
        topStudents.forEach(System.out::println);

        System.out.println("\n=== Part C: Stream Operations on Product Dataset ===");
        List<Product> products = Arrays.asList(
                new Product("Laptop", 70000, "Electronics"),
                new Product("Phone", 30000, "Electronics"),
                new Product("Shoes", 2000, "Fashion"),
                new Product("Shirt", 1200, "Fashion"),
                new Product("TV", 50000, "Electronics"),
                new Product("Watch", 4000, "Fashion")
        );

        System.out.println("\nGroup by Category:");
        Map<String, List<Product>> grouped = products.stream()
                .collect(Collectors.groupingBy(p -> p.category));
        grouped.forEach((cat, list) -> {
            System.out.println(cat + ": " + list);
        });

        System.out.println("\nMost Expensive Product in Each Category:");
        Map<String, Optional<Product>> maxByCat = products.stream()
                .collect(Collectors.groupingBy(p -> p.category,
                        Collectors.maxBy(Comparator.comparingDouble(p -> p.price))));
        maxByCat.forEach((cat, prod) -> System.out.println(cat + ": " + prod.get()));

        System.out.println("\nAverage Price of All Products:");
        double avg = products.stream()
                .collect(Collectors.averagingDouble(p -> p.price));
        System.out.println("Average Price: " + avg);
    }
}
