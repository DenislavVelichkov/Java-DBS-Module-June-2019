package ORM_Fundamentals_5;

import ORM_Fundamentals_5.entities.User;
import ORM_Fundamentals_5.orm.EntityManager;
import orm.Connector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Connector.createConnect("root", "1234", "minions_db");

        Connection connection = Connector.getConnection();

        User user = new User(
            "username",
            "12345",
            12,
            Date.valueOf("2019-05-06"));

        EntityManager<User> userManager = new EntityManager<User>(connection);

        userManager.persist(user);

    }
}
