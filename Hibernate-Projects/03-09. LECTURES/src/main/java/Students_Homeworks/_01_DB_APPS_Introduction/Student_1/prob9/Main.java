package Students_Homeworks._01_DB_APPS_Introduction.Student_1.prob9;

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

        PreparedStatement preparedStatement = connection.prepareStatement("call usp_get_older(?)");
        System.out.println("Enter minion ID: ");
        int minionId = sc.nextInt();

        preparedStatement.setInt(1, minionId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            System.out.println(resultSet.getString("name") + " " + resultSet.getString("age"));
        }

    }
}
