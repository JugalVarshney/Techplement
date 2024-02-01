import java.io.*;
import java.util.*;

class Employee implements Serializable {
    private String id;
    private String name;
    private String designation;

    public Employee(String id, String name, String designation) {
        this.id = id;
        this.name = name;
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Designation: " + designation;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}

class EmployeeManagementSystem {
    private static final String FILE_NAME = "employee_data.txt";
    private List<Employee> employees;

    public EmployeeManagementSystem() {
        employees = new ArrayList<>();
        loadEmployees();
    }

    private void loadEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            employees = (List<Employee>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveEmployees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        saveEmployees();
    }

    public void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    public void updateEmployee(String id, String newName, String newDesignation) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                employee.setName(newName);
                employee.setDesignation(newDesignation);
                saveEmployees();
                System.out.println("Employee information updated successfully.");
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found.");
    }

    public void deleteEmployee(String id) {
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getId().equals(id)) {
                iterator.remove();
                saveEmployees();
                System.out.println("Employee deleted successfully.");
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found.");
    }

    public void searchEmployee(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                System.out.println("Employee found: " + employee);
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found.");
    }
}

public class EMS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeManagementSystem ems = new EmployeeManagementSystem();

        while (true) {
            System.out.println("\nEmployee Management System Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Search Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Employee Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Employee Designation: ");
                    String designation = sc.nextLine();

                    ems.addEmployee(new Employee(id, name, designation));
                    System.out.println("Employee added successfully.");
                    break;

                case 2:
                    ems.viewEmployees();
                    break;

                case 3:
                    System.out.print("Enter Employee ID to update: ");
                    String updateId = sc.nextLine();
                    System.out.print("Enter New Employee Name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter New Employee Designation: ");
                    String newDesignation = sc.nextLine();

                    ems.updateEmployee(updateId, newName, newDesignation);
                    break;

                case 4:
                    System.out.print("Enter Employee ID to delete: ");
                    String deleteId = sc.nextLine();
                    ems.deleteEmployee(deleteId);
                    break;

                case 5:
                    System.out.print("Enter Employee ID to search: ");
                    String searchId = sc.nextLine();
                    ems.searchEmployee(searchId);
                    break;

                case 6:
                    System.out.println("Exiting Employee Management System. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }
}
