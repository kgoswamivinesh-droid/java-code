import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Student implements Serializable {
    String studentID;
    String name;
    String grade;
    Student(String studentID, String name, String grade){
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }
}

class Employee implements Serializable {
    String name;
    String id;
    String designation;
    double salary;
    Employee(String name, String id, String designation, double salary){
        this.name = name;
        this.id = id;
        this.designation = designation;
        this.salary = salary;
    }
}

class AppendableObjectOutputStream extends ObjectOutputStream {
    AppendableObjectOutputStream(OutputStream out) throws IOException { super(out); }
    protected void writeStreamHeader() throws IOException {}
}

public class CombinedProgram {
    static Scanner sc = new Scanner(System.in);

    public static void sumIntegers() {
        ArrayList<Integer> numbers = new ArrayList<>();
        System.out.println("Enter integers separated by space:");
        String[] inputs = sc.nextLine().split(" ");
        for(String s : inputs){
            Integer num = Integer.parseInt(s);
            numbers.add(num);
        }
        int sum = 0;
        for(Integer n : numbers){
            sum += n;
        }
        System.out.println("Sum of integers: " + sum);
    }

    public static void studentSerialization() throws Exception {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Grade: ");
        String grade = sc.nextLine();
        Student s = new Student(id, name, grade);

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("student.dat"));
        out.writeObject(s);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("student.dat"));
        Student deserialized = (Student) in.readObject();
        in.close();

        System.out.println("Student ID: " + deserialized.studentID);
        System.out.println("Name: " + deserialized.name);
        System.out.println("Grade: " + deserialized.grade);
    }

    public static void employeeManagement() throws Exception {
        while(true){
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            if(choice == 1){
                System.out.print("Enter name: ");
                String name = sc.nextLine();
                System.out.print("Enter ID: ");
                String id = sc.nextLine();
                System.out.print("Enter designation: ");
                String desig = sc.nextLine();
                System.out.print("Enter salary: ");
                double salary = Double.parseDouble(sc.nextLine());
                Employee e = new Employee(name, id, desig, salary);
                ObjectOutputStream out;
                File file = new File("employees.dat");
                if(file.exists()){
                    FileOutputStream fos = new FileOutputStream(file, true);
                    out = new AppendableObjectOutputStream(fos);
                } else {
                    out = new ObjectOutputStream(new FileOutputStream(file));
                }
                out.writeObject(e);
                out.close();
            } else if(choice == 2){
                File file = new File("employees.dat");
                if(!file.exists()){
                    System.out.println("No employees found.");
                    continue;
                }
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                try{
                    while(true){
                        Employee e = (Employee) in.readObject();
                        System.out.println("Name: " + e.name + ", ID: " + e.id + ", Designation: " + e.designation + ", Salary: " + e.salary);
                    }
                } catch(EOFException ex){
                    in.close();
                }
            } else if(choice == 3){
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        while(true){
            System.out.println("===== Main Menu =====");
            System.out.println("1. Sum of Integers");
            System.out.println("2. Serialize/Deserialize Student");
            System.out.println("3. Employee Management");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            if(choice == 1){
                sumIntegers();
            } else if(choice == 2){
                studentSerialization();
            } else if(choice == 3){
                employeeManagement();
            } else if(choice == 4){
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
