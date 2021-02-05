package br.com.abidux.cash.models.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.abidux.cash.Main;

public class SQLiteConnection implements ConnectionModel {

	@Override
	public Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection("jdbc:sqlite:" + Main.database);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}