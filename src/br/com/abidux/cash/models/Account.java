package br.com.abidux.cash.models;

import java.util.ArrayList;

public class Account {
	
	public static ArrayList<Account> accounts = new ArrayList<>();
	
	private String playerName;
	private double cash;
	public Account(String playerName, double cash) {
		this.playerName = playerName;
		this.cash = cash;
	}
	
	public double getCash() {
		return cash;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setCash(double cash) {
		this.cash = cash;
	}
	
}