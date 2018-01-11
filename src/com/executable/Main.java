package com.executable;

import com.model.Department;
import com.model.Employee;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ODBRuntimeException;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static ODB odb = ODBFactory.open("user.database");
    private static Objects<Department> departmentObjects;
    private static Objects<Employee> employeeObjects = null;

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        Scanner sc = new Scanner(System.in);

        boolean exit = false;
        int option;

        try {
            do {
                System.out.println("------------ MENU ------------");
                System.out.println("1. Insert data");
                System.out.println("2. Show all departments and employee's full names of the database");
                System.out.println("3. Show employees with a specific ID");
                System.out.println("4. Show average salary of employees in a specific department");
                System.out.println("5. Show count of employees for each department");
                System.out.println("6. Exit");

                option = sc.nextInt();

                switch (option) {
                    case 1:
                        if (dataSelection() == 'D') {
                            if (!insertDepartment()) {
                                System.out.println("Department not created, something went wrong");
                            }
                        } else if (dataSelection() == 'E') {
                            if ()
                        } else {
                            System.out.println(dataSelection());
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        exit = true;
                        break;
                }
            } while (!exit);
        } catch (InputMismatchException e) {
            System.err.println("Please, enter a correct value");
            sc.next();
        } catch (ODBRuntimeException o) {
            System.err.println("Something went wrong with the database");
        } finally {
            System.out.println("Closing connection");
            if (odb != null) odb.close();
        }
    }

    private static char dataSelection() {
        Scanner sc = new Scanner(System.in);

        String choice;

        System.out.println("Which type of data do you want to insert (Department or Employee)");
        choice = sc.nextLine();

        if (choice.equalsIgnoreCase("department")) {
            return 'D';
        } else if (choice.equalsIgnoreCase("employee")) {
            return 'E';
        } else {
            return 'X';
        }
    }

    private static boolean insertDepartment() {
        Scanner sc = new Scanner(System.in);

        boolean correct = false;
        int deptNum;
        String deptName;
        String loc;

        System.out.println("Enter the department's number");
        deptNum = sc.nextInt();

        if (!deptNumExists(deptNum)) {
            System.out.println("Enter the department's name");
            deptName = sc.next();

            if (!deptNameExists(deptName)) {
                System.out.println("Enter the department's localization");
                loc = sc.next();

                Department department = new Department(deptNum, deptName, loc);
                odb.store(department);
                correct = true;

                System.out.println("Department " + deptNum + " succesfully created");
            } else {
                System.err.println("This dept name already exists");
            }
        } else {
            System.err.println("This dept number already exists");
        }

        return correct;
    }

    private static boolean deptNumExists(int deptNum) {
        departmentObjects = null;

        try {
            IQuery query = new CriteriaQuery(Class.forName("com.model.Department"), Where.equal("deptNum", deptNum));
            departmentObjects = odb.getObjects(query);
        } catch (ClassNotFoundException e) {
            System.err.println("No such class found");
        }

        return departmentObjects.hasNext();
    }

    private static boolean deptNameExists(String deptName) {
        departmentObjects = null;

        try {
            IQuery query = new CriteriaQuery(Class.forName("com.model.Department"), Where.equal("deptName", deptName));
            departmentObjects = odb.getObjects(query);
        } catch (ClassNotFoundException e) {
            System.err.println("No such class found");
        }

        return departmentObjects.hasNext();
    }

    private static boolean insertEmployee() {
        Scanner sc = new Scanner(System.in);

        boolean correct = false;
        int empNum;
        String lastName;
        String name;
        String job;
        Date registerDate;
        float salary;
        float commission;
        Department dept;
        int deptNumCheck;

        System.out.println("Enter the department's number for this employee");
        deptNumCheck = sc.nextInt();

        if (deptNumExists(deptNumCheck)) {
            dept = (Department) getDepartmentObjects(deptNumCheck);
        } else {
            System.out.println("There's no department matching this number, you should create it previously");
        }
    }

    private static Objects<Department> getDepartmentObjects(int deptNumCheck) {
        Objects<Department> departmentReturned = null;

        try {
            IQuery query = new CriteriaQuery(Class.forName("com.model.Department"), Where.equal("deptNum", deptNumCheck));
            departmentReturned = odb.getObjects(query);
        } catch (ClassNotFoundException e) {
            System.err.println("No such class found");
        }

        return departmentReturned;
    }
}
