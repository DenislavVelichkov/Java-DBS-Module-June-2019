package Students_Homeworks._01_DB_APPS_Introduction.Student_1.prob3;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String user = "root";
        String password = "1234";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);


        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db?useSSL=false", props);


        System.out.println("Enter villain id: ");
        int villainIdInput = sc.nextInt();


        if(hasVillainWithId(connection, villainIdInput)){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT m.name AS `minion_name`, m.age AS `minion_age`, v.name AS `villain_name` FROM minions AS `m`\n" +
                    "JOIN minions_villains AS `mv`\n" +
                    "ON mv.minion_id = m.id\n" +
                    "JOIN villains AS `v`\n" +
                    "ON v.id = mv.villain_id\n" +
                    "WHERE v.id = ?");
            preparedStatement.setInt(1, villainIdInput);

            ResultSet resultSet = preparedStatement.executeQuery();

            int counter = 1;

            while(resultSet.next()){
                System.out.println(counter + ". " + resultSet.getString("minion_name") + " " + resultSet.getInt("minion_age"));
                counter++;
            }
            if(counter == 1){
                System.out.println("<No minions>");
            }
        }
    }


    private static boolean hasVillainWithId(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM villains WHERE id = ?");

        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            System.out.println("Villain: " + resultSet.getString("name"));
            return true;
        }
        else{
            System.out.println("No villain with ID " + id + " exists in the database.");
            return false;
        }
    }
}
