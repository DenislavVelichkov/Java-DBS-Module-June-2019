package Students_Homeworks._01_DB_APPS_Introduction.Student_1.prob7;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {

        String user = "root";
        String password = "1234";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db?useSSL=false", props);

        List<Minion> minions = new ArrayList<Minion>();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM minions");
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            minions.add(new Minion(resultSet.getString("name")));
        }

        for (int i = 0; i < minions.size() / 2; i++) {
            System.out.println(minions.get(i).name);
            System.out.println(minions.get(minions.size() - 1 - i).name);
        }

        if((double)minions.size() % 2 != 0){
            System.out.println(minions.get((int)Math.ceil(minions.size() / 2)).name);
        }
    }
}
