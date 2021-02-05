package br.com.abidux.cash.statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.abidux.cash.Main;
import br.com.abidux.cash.models.Account;

public class Statements {
	
	private static Connection connection;
	
	private static void openConnection() {
		Statements.connection = Main.connectionModel.getConnection();
	}
	
	public static void initialize() {
		try {
			openConnection();
			PreparedStatement st = connection.prepareStatement("create table if not exists jogadores (nome varchar(16), cash double)");
			st.executeUpdate();
			st.close();
			loadAccounts();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadAccounts() {
		try {
			PreparedStatement st = connection.prepareStatement("select * from jogadores");
			ResultSet rs = st.executeQuery();
			while (rs.next()) Account.accounts.add(new Account(rs.getString("nome"), rs.getDouble("cash")));
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean exists(String playerName) {
		boolean exists = false;
		try {
			PreparedStatement st = connection.prepareStatement("select nome from jogadores where nome=?");
			st.setString(1, playerName.toLowerCase());
			ResultSet rs = st.executeQuery();
			exists = rs.next();
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}
	
	public static void saveAccounts() {
		try {
			openConnection();
			PreparedStatement existsStatement = connection.prepareStatement("update jogadores set cash=? where nome=?");
			PreparedStatement st = connection.prepareStatement("insert into jogadores (nome, cash) values (?, ?)");
			for (Account account : Account.accounts) {
				if (exists(account.getPlayerName())) {
					existsStatement.setString(2, account.getPlayerName().toLowerCase());
					existsStatement.setDouble(1, account.getCash());
					existsStatement.executeUpdate();
				}else {
					st.setString(1, account.getPlayerName().toLowerCase());
					st.setDouble(2, account.getCash());
					st.executeUpdate();
				}
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}