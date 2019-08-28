package Students_Homeworks._01_DB_APPS_Introduction.Student_1.prob2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        String user =  "root";
        String password = "1234";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db?useSSL=false", props);

        PreparedStatement statement =
                connection.prepareStatement("SELECT v.name, COUNT(v.name) AS `minions_count` FROM villains AS `v`\n" +
                        "JOIN minions_villains AS `mv`\n" +
                        "ON mv.villain_id = v.id\n" +
                        "GROUP BY v.name\n" +
                        "HAVING `minions_count` > 15\n" +
                        "ORDER BY `minions_count` DESC;");


        ResultSet resultSet = statement.executeQuery();

        List<Villain> villains = new ArrayList<Villain>();

        while(resultSet.next()){
            Villain villain = new Villain(resultSet.getString("name"), resultSet.getLong("minions_count"));
            villains.add(villain);
        }

        villains.forEach(villain -> System.out.println(villain.name + " " + villain.minionsCount));
    }
}
