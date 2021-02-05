package br.com.abidux.cash.api;

import java.text.DecimalFormat;

import org.bukkit.entity.Player;

import br.com.abidux.cash.models.Account;

public class CashAPI {
	
	public static Account getAccount(Player player) {
		return getAccount(player.getName());
	}
	
	public static Account getAccount(String player) {
		for (Account account : Account.accounts)
			if (account.getPlayerName().equalsIgnoreCase(player.toLowerCase())) return account;
		return null;
	}
	
	public static boolean setCash(String player, double amount) {
		Account account = getAccount(player);
		if (account == null) return false;
		account.setCash(amount);
		return true;
	}
	
	public static boolean addCash(String player, double amount) {
		Account account = getAccount(player);
		if (account == null) return false;
		account.setCash(account.getCash() + amount);
		return true;
	}
	
	public static boolean removeCash(String player, double amount) {
		Account account = getAccount(player);
		if (account == null) return false;
		double cash = account.getCash() - amount;
		account.setCash(cash < 0 ? 0 : cash);
		return true;
	}
	
	public static boolean hasCash(String player, double amount) {
		Account account = getAccount(player);
		return account != null && account.getCash() >= amount;
	}
	
	public static boolean setCash(Player player, double amount) {
		return setCash(player.getName(), amount);
	}
	
	public static boolean addCash(Player player, double amount) {
		return addCash(player.getName(), amount);
	}
	
	public static boolean removeCash(Player player, double amount) {
		return removeCash(player.getName(), amount);
	}
	
	public static boolean hasCash(Player player, double amount) {
		return hasCash(player.getName(), amount);
	}
	
	private static String[] prefixes = {"K", "M", "B", "T", "Q", "QQ", "Sx", "Sp", "Oc", "N", "D"};
	public static String format(double cash) {
		if (cash < 1000) return String.valueOf(cash);
		String[] c = new DecimalFormat("0,000").format(cash).split("\\.");
		return c[0] + prefixes[c.length - 1];
	}
	
}