package classes;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ODB odb = ODBFactory.open("database.test");

    }

    public static void run() {
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
                        // TODO: 08/01/2018  
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

    public static char dataSelection() {
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
}
