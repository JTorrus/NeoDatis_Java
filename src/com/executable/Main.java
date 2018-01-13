package com.executable;

import com.model.Department;
import com.model.Employee;
import org.neodatis.odb.*;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import java.sql.Date;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static ODB odb = ODBFactory.open("user.database");
    private static Objects<Department> departmentObjects;
    private static Objects<Employee> employeeObjects;

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
                System.out.println("3. Show employees with a specific department number");
                System.out.println("4. Show average salary of employees in a specific department");
                System.out.println("5. Show count of employees for each department");
                System.out.println("6. Exit");
                System.out.print("Choose an option -> ");
                option = sc.nextInt();

                switch (option) {
                    case 1:
                        switch (dataSelection()) {
                            case 'D':
                                if (!insertDepartment()) {
                                    System.out.println("Department not created, something went wrong");
                                }
                                break;
                            case 'E':
                                if (!insertEmployee()) {
                                    System.out.println("Employee not created, something went wrong");
                                }
                                break;
                            default:
                                System.out.println("Enter a valid data");;
                                break;
                        }
                        break;
                    case 2:
                        showDeptAndEmpl();
                        break;
                    case 3:
                        showEmplByDeptNum();
                        break;
                    case 4:
                        showEmplAvgSalaryByDeptName();
                        break;
                    case 5:
                        showEmplCountForEachDept();
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
        choice = sc.next();

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
        sc.useDelimiter("\n");

        boolean correct = false;
        String deptName;
        String loc;

            System.out.println("Enter the department's name");
            deptName = sc.next();

            if (!deptNameExists(deptName)) {
                System.out.println("Enter the department's localization");
                loc = sc.next();

                Department department = new Department(nextDeptNum(), deptName, loc);
                odb.store(department);
                correct = true;

                System.out.println("Department " + deptName + " succesfully created");
            } else {
                System.err.println("This dept name already exists");
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
        sc.useDelimiter("\n");

        boolean correct = false;
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
            dept = getDepartmentObjects(deptNumCheck).getFirst();

                System.out.println("Enter the employee's name");
                name = sc.next();

                System.out.println("Enter the employee's last name");
                lastName = sc.next();

                System.out.println("Enter the employee's job");
                job = sc.next();

                registerDate = new Date(Calendar.getInstance().getTime().getTime());

                System.out.println("Enter the employee's salary");
                salary = sc.nextFloat();

                System.out.println("Enter the employee's commission");
                commission = sc.nextFloat();

                Employee employee = new Employee(nextEmplNum(), lastName, name, job, registerDate, salary, commission, dept);
                odb.store(employee);
                correct = true;

                System.out.println("Employee " + name + " " + lastName + " succesfully created");
        } else {
            System.out.println("There's no department matching this number, you should create it previously");
        }

        return correct;
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

    private static void showDeptAndEmpl() {
        try {
            Values deptValues = odb.getValues(new ValuesCriteriaQuery(Class.forName("com.model.Department")).field("deptNum").field("deptName").field("loc"));
            Values emplValues = odb.getValues(new ValuesCriteriaQuery(Class.forName("com.model.Employee")).field("name").field("lastName").field("dept.deptName"));

            while (deptValues.hasNext()) {
                ObjectValues objectValues = deptValues.next();

                System.out.println("Department's num: " + objectValues.getByAlias("deptNum") + " --- name: " + objectValues.getByAlias("deptName") + " --- loc: " + objectValues.getByAlias("loc"));
            }

            while (emplValues.hasNext()) {
                ObjectValues objectValues = emplValues.next();

                System.out.println("Employee's first name: " + objectValues.getByAlias("name") + " --- last name: " + objectValues.getByAlias("lastName") + " --- department's name: " + objectValues.getByAlias("dept.deptName"));
            }
        } catch (ClassNotFoundException e) {
            System.err.println("No such class found");
        }
    }

    private static void showEmplByDeptNum() {
        Scanner sc = new Scanner(System.in);
        int deptNum;
        Objects<Employee> emplQueries;

        System.out.println("Enter the department's number");
        deptNum = sc.nextInt();

        try {
            IQuery query = new CriteriaQuery(Class.forName("com.model.Employee"), Where.equal("dept.deptNum", deptNum));
            emplQueries = odb.getObjects(query);

            while (emplQueries.hasNext()) {
                Employee employee = emplQueries.next();

                System.out.println(employee.getLastName());
            }
        } catch (ClassNotFoundException e) {
            System.err.println("No such class found");
        }
    }

    private static void showEmplAvgSalaryByDeptName() {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");

        String deptName;

        System.out.println("Enter the department's name");
        deptName = sc.next();

        try {
            IQuery query = new CriteriaQuery(Class.forName("com.model.Employee"), Where.equal("dept.deptName", deptName));
            Values emplValues = odb.getValues(new ValuesCriteriaQuery((CriteriaQuery) query).avg("salary"));

            while (emplValues.hasNext()) {
                ObjectValues objectValues = emplValues.next();

                System.out.println("Average: " + objectValues.getByAlias("salary"));
            }
        } catch (ClassNotFoundException e) {
            System.err.println("No such class found");
        }
    }

    private static void showEmplCountForEachDept() {
        try {
            Values emplValues = odb.getValues(new ValuesCriteriaQuery(Class.forName("com.model.Employee")).field("dept.deptName").count("emplNum").groupBy("dept.deptName"));

            while (emplValues.hasNext()) {
                ObjectValues objectValues = emplValues.next();

                System.out.println("There's " + objectValues.getByAlias("emplNum") + " employees in " + objectValues.getByAlias("dept.deptName"));
            }
        } catch (ClassNotFoundException e) {
            System.err.println("No such class found");
        }
    }

    private static int nextDeptNum() {
        departmentObjects = null;

        try {
            IQuery query = new CriteriaQuery(Class.forName("com.model.Department"));
            departmentObjects = odb.getObjects(query);
        } catch (ClassNotFoundException e) {
            System.err.println("No such class found");
        }

        return departmentObjects.size()+1;
    }

    private static int nextEmplNum() {
        employeeObjects = null;

        try {
            IQuery query = new CriteriaQuery(Class.forName("com.model.Employee"));
            employeeObjects = odb.getObjects(query);
        } catch (ClassNotFoundException e) {
            System.err.println("No such class found");
        }

        return employeeObjects.size()+1;
    }
}
