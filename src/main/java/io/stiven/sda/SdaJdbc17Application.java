package io.stiven.sda;

import java.util.Scanner;
import java.sql.*;

import static io.stiven.sda.Constants.*;

public class SdaJdbc17Application {

    //triple dot - przyjmuje 1 lub wiecej argumentów i zbija je w tablicę
    public static void main(String... args) {

        Scanner scan = new Scanner(System.in);
        int menuOption;
        int id;
        String name;
        String country;
        Connection conn = null;


        do {
            System.out.println("Prosze wybrać opcję:");
            System.out.println("1 - wyświetl całą tablicę");
            System.out.println("2 - wyświetl element");
            System.out.println("3 - dodaj element");
            System.out.println("4 - usuń element");
            System.out.println("5 - zaktualizuj element");
            System.out.println("6 - wyjdź");

            menuOption = scan.nextInt();

            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                new DDLRepository(conn).createTableCityIfNotExists();
                CityRepository cr = new CityRepository(conn);


                switch (menuOption) {
                    case 1:
                        System.out.println(cr.findAll());
                        break;
                    case 2:
                        System.out.println("Podaj id szukanego elementu:");
                        id = scan.nextInt();
                        System.out.println(cr.findById(id));
                        break;
                    case 3:
                        System.out.println("Podaj nazwę miasta:");
                        name = scan.next();
                        System.out.println("Podaj nazwę kraju:");
                        country = scan.next();
                        cr.save(new City(name, country));
                        break;
                    case 4:
                        System.out.println("Podaj id elementu do usunięcia:");
                        id = scan.nextInt();
                        cr.deleteById(id);
                        break;
                    case 5:
                        System.out.println("Podaj id elementu do aktualizacji:");
                        id = scan.nextInt();
                        System.out.println("Podaj nową nazwę miasta:");
                        name = scan.next();
                        System.out.println("Podaj nową nazwę kraju:");
                        country = scan.next();
                        cr.update(new City(id, name, country));
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                ConnectionUtils.rollback(conn);
            } finally {
                ConnectionUtils.close(conn);
            }


        } while (menuOption != 6);

    }
}


//
//
//            List<City> cities = new ArrayList<>();
//            cities.add(new City("Lublin", "Polska"));
//            cities.add(new City("Lubin", "Polska"));
//
//            System.out.println(cr.count());

//            cr.update(new City(4, "Poznań", "Polska"));

//            cr.save(cities);
//            System.out.println(cr.save(new City("Lubin","Polska")));

//            int id = cr.save(new City("Lublin", "Polska"));
//            cr.findById(id)
//                    .ifPresent(city -> System.out.println(city));
//            cr.deleteById(7);
//            cr.deleteById(14);
//            System.out.println(cr.save(new City("Warszawka", "Polska")));
//            cr.save(new City("Poznań", "Polska"));
//            cr.save(new City("Lubin", "Polska"));
//
//            System.out.println(cr.findAll());

//            cr.deleteById(1);
//            cr.deleteById(2);

//            System.out.println(cr.findAll());


//        } catch (SQLException e) {
//            e.printStackTrace();
//            ConnectionUtils.rollback(conn);
//        } finally {
//            ConnectionUtils.close(conn);
//        }

