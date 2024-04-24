package Connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    public Connection connection;
    public Connection getConnection() throws SQLException {
        String db_name = "chequers";
        String user_name = "root";
        String password = "";

        connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db_name, user_name, password);
        return connection;
    }
}
