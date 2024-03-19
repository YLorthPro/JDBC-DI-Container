package be.ylorthioir.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory {
	public static final String URL= "jdbc:postgresql://localhost:5432/jdbc";
	public static final String USERNAME= "Yann";
	public static final String PASSWORD = "Charlotte2901";

	public static Connection createConnection() throws SQLException {
		return DriverManager.getConnection(URL,USERNAME,PASSWORD);

	}
}
