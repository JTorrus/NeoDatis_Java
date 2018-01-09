package com.model;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

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

        do {
            System.out.println("------------ MENU ------------");
            System.out.println("1. Insert data");
            System.out.println("2. Show all departments and employee's full name of the database");
            System.out.println("3. Show employees with an specific ID");
            System.out.println("4. Average salary of employees of a specific department");
            System.out.println("5. Count of employees for each department");
            System.out.println("6. Exit");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    if (dataSelection() == 'D') {

                    } else if (dataSelection() == 'E') {
                        // TODO: 08/01/2018  
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
            deptName = sc.nextLine();

            if (!deptNameExists(deptName)) {
                System.out.println("Enter the department's localization");
                loc = sc.nextLine();


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
        } finally {
            if (odb != null) {
                odb.close();
            }
        }

        if (departmentObjects != null) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean deptNameExists(String deptName) {
        departmentObjects = null;

        try {
            IQuery query = new CriteriaQuery(Class.forName("com.model.Department"), Where.equal("deptName", deptName));
            departmentObjects = odb.getObjects(query);
        } catch(ClassNotFoundException e) {
            System.err.println("No such class found");
        } finally {
            if (odb != null) {
                odb.close();
            }
        }

        if (departmentObjects != null) {
            return true;
        } else {
            return false;
        }
    }
}
