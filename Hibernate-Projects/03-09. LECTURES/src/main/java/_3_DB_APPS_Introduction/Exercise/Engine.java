package DB_APPS_Introduction_3.Exercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


public class Engine implements Runnable {
    private final String CONNECTION_STRING_URL = "jdbc:mysql://localhost:3306/minions_db";
    private Scanner scanner = new Scanner(System.in);
    private Connection connection;

    public Engine() {

    }

    /*
     * Initialize all Problems...
     * */

    public void run() {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "1234");

        try {
            this.connection =
                DriverManager.getConnection(CONNECTION_STRING_URL, properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*System.out.println("---------------PROBLEM 2:---------------");
        try {
            this.getVillainsNames();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("---------------PROBLEM 3:---------------");
        try {
            this.getMinionsNames();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("---------------PROBLEM 4:---------------");
        try {
            this.addMinion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("---------------PROBLEM 5:---------------");
        try {
            this.changeTownNamesCasing();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("---------------PROBLEM 6:---------------");
        try {
            this.removeVillain();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("---------------PROBLEM 7:---------------");
        try {
            this.printAllMinionsNames();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("---------------PROBLEM 8:---------------");
        try {
            this.increaseMinionsAge();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        System.out.println("---------------PROBLEM 9:---------------");
        try {
            this.increaseAgeStoredProcedure();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     *PROBLEM 2: Get Villain's Name
     * */

    private void getVillainsNames() throws SQLException {
        String query = "SELECT v.name, count(mv.minion_id) AS count FROM villains AS v\n" +
                           "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                           "GROUP BY v.name\n" +
                           "HAVING count > ?\n" +
                           "ORDER BY count DESC;";

        PreparedStatement preparedStatement =
            connection.prepareStatement(query);
        preparedStatement.setInt(1, 15);

        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            System.out.printf("%s %d%n",
                result.getString("name"),
                result.getInt("count"));
        }
    }

    /*
     *PROBLEM 3: Get Minion Names
     **/

    private void getMinionsNames() throws SQLException {
        String query = "SELECT v.name AS villain_name,\n" +
                           "m.name AS minion_name,\n" +
                           "m.age AS minion_age FROM minions AS m\n" +
                           "JOIN minions_villains AS mv ON m.id = mv.minion_id\n" +
                           "JOIN villains v on mv.villain_id = v.id\n" +
                           "WHERE v.id = ?\n" +
                           "GROUP BY m.name, v.name;";

        PreparedStatement preparedStatement =
            connection.prepareStatement(query);
        int param = Integer.parseInt(scanner.nextLine());

        preparedStatement.setInt(1, param);

        ResultSet result = preparedStatement.executeQuery();
        int count = 1;

        if (!result.next()) {
            System.out.printf("No villain with ID %d exists in the database.%n", param);
        }

        result.beforeFirst();

        while (result.next()) {
            if (result.isFirst()) {
                System.out.printf("Villain: %s%n",
                    result.getString("villain_name"));
            }

            System.out.printf("%d. %s %d%n",
                count++,
                result.getString("minion_name"),
                result.getInt("minion_age"));
        }
    }

    /*
     * PROBLEM 4: Add Minion
     * */

    private void addMinion() throws SQLException {
        scanner = new Scanner(System.in);
        String[] minionParams = scanner.nextLine().split("\\s+");
        String[] villianParams = scanner.nextLine().split("\\s+");

        String minionName = minionParams[1];
        int minionAge = Integer.parseInt(minionParams[2]);
        String townName = minionParams[3];
        String villainName = villianParams[1];

        if (!this.checkIfEntityExists(townName, "towns")) {
            this.insertTown(townName);

        }

        if (!this.checkIfEntityExists(villainName, "villains")) {
            this.insertVillain(villainName);
        }

        int townID = this.getEntityID(townName, "towns");
        this.insertMinion(minionName, minionAge, townID);

        int minionID = this.getEntityID(minionName, "minions");
        int villainID = this.getEntityID(villainName, "villains");
        this.insertIntoMinions_Villains_idRepo(minionID, villainID);

        System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);
    }

    private boolean checkIfEntityExists(String name, String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE name = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            return false;
        }

        return true;
    }

    private void insertTown(String townName) throws SQLException {
        String query = "INSERT INTO towns (name, country) VALUES('" + townName + "', NULL)";
        PreparedStatement statement = this.connection.prepareStatement(query);

        statement.execute();
        System.out.printf("Town %s was added to the database.%n", townName);
    }

    private void insertVillain(String villainName) throws SQLException {
        String query =
            String.format("INSERT INTO villains(name, evilness_factor)" +
                              " VALUES ('%s', 'evil')",
                villainName);
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.execute();

        System.out.printf("Villain %s was added to the database.%n", villainName);
    }

    private int getEntityID(String name, String tableName) throws SQLException {
        String query = String.format("SELECT Id FROM %s WHERE name = '%s'", tableName, name);
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt("id");
    }

    private void insertMinion(String minionName, int age, int townID) throws SQLException {
        String query = String.format("INSERT INTO minions(name, age, town_id) VALUES('%s', '%d', '%d')",
            minionName, age, townID);
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.execute();
    }

    private void insertIntoMinions_Villains_idRepo(int minionID, int villainID) throws SQLException {
        String query = String.format("INSERT INTO minions_villains(minion_id, villain_id) VALUES(%d, %d)",
            minionID, villainID);
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.execute();
    }

    /*
     * PROBLEM 5: Change Town Names Casing
     * */

    private void changeTownNamesCasing() throws SQLException {
        String changeTownsName = "SELECT name FROM towns WHERE country = ?";
        PreparedStatement statement = connection.prepareStatement(changeTownsName);

        String countryName = scanner.nextLine();
        statement.setString(1, countryName);
        ResultSet resultSet = statement.executeQuery();

        String finalResultAsStr = "";
        StringBuilder stringBuilder = new StringBuilder();

        if (!resultSet.next()) {
            System.out.println("No town names were affected.");
            return;
        }

        String changeNameQuery = String.format(
            "UPDATE towns SET name = REPLACE(name, name, UPPER (name)) WHERE country = '%s'", countryName);
        PreparedStatement preparedStatement = this.connection.prepareStatement(changeNameQuery);
        preparedStatement.execute();

        resultSet = statement.executeQuery();

        resultSet.beforeFirst();

        int affectedTownsCount = 0;
        while (resultSet.next()) {
            stringBuilder.append(resultSet.getString("name")).append(", ");
            affectedTownsCount++;
        }

        finalResultAsStr = stringBuilder.toString();
        finalResultAsStr = "[" + finalResultAsStr.substring(0, finalResultAsStr.length() - 2) + "]";

        System.out.printf("%d town names were affected.%n", affectedTownsCount);
        System.out.println(finalResultAsStr);
    }

    /*
     * PROBLEM 6: Remove Villain
     * */

    private void removeVillain() throws SQLException {
        int idToRemove = Integer.parseInt(scanner.nextLine());
        /*Preparing table for DELETE query*/
        String dropForeignKey = "ALTER TABLE minions_villains\n" +
                                    "DROP FOREIGN KEY fk_villains_minions;";
        String createNewForeignKey = "ALTER TABLE minions_villains\n" +
                                         "ADD CONSTRAINT fk_villains_minions\n" +
                                         "FOREIGN KEY (`villain_id`) REFERENCES villains (id) \n" +
                                         "ON DELETE CASCADE;";

        PreparedStatement preparedStatement = this.connection.prepareStatement(dropForeignKey);
        preparedStatement.execute();

        preparedStatement = this.connection.prepareStatement(createNewForeignKey);
        preparedStatement.execute();

        String extractVillainsNameAndMinionsCount = "SELECT v.name, COUNT(minion_id)\n" +
                                                        "FROM minions_villains\n" +
                                                        "JOIN villains v on villain_id = v.id\n" +
                                                        "WHERE villain_id = ?;";

        preparedStatement = this.connection.prepareStatement(extractVillainsNameAndMinionsCount);
        preparedStatement.setInt(1, idToRemove);
        ResultSet resultSet = preparedStatement.executeQuery();

        String villainName = "";
        int minionsCount = 0;

        while (resultSet.next()) {
            if (resultSet.getString(1) == null) {
                System.out.println("No such villain was found");
                return;
            }

            villainName = resultSet.getString(1);
            minionsCount = resultSet.getInt(2);
        }

        String deleteRecordQuery = "DELETE\n" +
                                       "FROM villains\n" +
                                       "WHERE id = ?;";
        preparedStatement = this.connection.prepareStatement(deleteRecordQuery);
        preparedStatement.setInt(1, idToRemove);
        preparedStatement.execute();

        System.out.printf("%s was deleted%n%d minions released%n", villainName, minionsCount);
    }

    /*
     * PROBLEM 7: Print All Minions Names
     * */

    private void printAllMinionsNames() throws SQLException {
        String getMinionNames = "SELECT name FROM minions";
        PreparedStatement preparedStatement = this.connection.prepareStatement(getMinionNames);
        ResultSet resultSet = preparedStatement.executeQuery();

        List listOfNames = new ArrayList();

        while (resultSet.next()) {
            listOfNames.add(resultSet.getString(1));
        }

        for (int i = 0; i < listOfNames.size() / 2; i++) {
            System.out.println(listOfNames.get(i));
            System.out.println(listOfNames.get(listOfNames.size() - i - 1));
        }
    }

    /*
     * PROBLEM 8: Increase Minions Age
     * */

    private void increaseMinionsAge() throws SQLException {
        String[] input = scanner.nextLine().split("\\s+");
        String query;
        Statement statement;

        for (String value : input) {
            int id = Integer.parseInt(value);
            query = "SELECT * FROM minions WHERE id = " + id;

            statement = this.connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String minionName = resultSet.getString("name");
                int minionAge = resultSet.getInt("age");

                resultSet.updateString("name", minionName.toLowerCase());
                resultSet.updateInt("age", minionAge + 1);
                resultSet.updateRow();
            }

        }
        
        String displayDataQuery = "SELECT name, age FROM minions";
        PreparedStatement preparedStatement = this.connection.prepareStatement(displayDataQuery);
        ResultSet displayFinalResult = preparedStatement.executeQuery();

        while (displayFinalResult.next()) {
            System.out.printf("%s %d%n", displayFinalResult.getString(1), displayFinalResult.getInt(2));
        }
    }

    /*
     * PROBLEM 9: Increase Age Stored Procedure
     * */

    private void increaseAgeStoredProcedure() throws SQLException {
        int minion_id = Integer.parseInt(scanner.nextLine());
        String dropProcedure = "DROP PROCEDURE IF EXISTS usp_get_older;";
        PreparedStatement dropStatement = this.connection.prepareStatement(dropProcedure);
        dropStatement.execute();
        
        String uspQuery = "CREATE PROCEDURE usp_get_older(minion_id INT)\n" +
                           "BEGIN\n" +
                           "UPDATE minions\n" +
                           "SET age = age + 1 WHERE Id = minion_id;\n" +
                           "END;";
        PreparedStatement uspStatement = this.connection.prepareStatement(uspQuery);
        uspStatement.execute();
        /***
         ***The SQL Creation Procedure In Workbench***
         *CREATE PROCEDURE usp_get_older(minion_id INT)
         *BEGIN
         *UPDATE minions
         *SET age = age + 1 WHERE Id = minion_id;
         *END;
         ***/

        CallableStatement statement = this.connection.prepareCall("{call usp_get_older(?)}");
        statement.setInt(1, minion_id);
        statement.execute();

        String displayMinion = String.format("SELECT name, age FROM minions WHERE Id = %d", minion_id);

        PreparedStatement preparedStatement = this.connection.prepareStatement(displayMinion);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d", resultSet.getString("name"), resultSet.getInt("age"));
        }
    }
}
