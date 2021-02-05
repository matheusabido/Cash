package br.com.abidux.cash;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.abidux.cash.commands.Cash;
import br.com.abidux.cash.events.Join;
import br.com.abidux.cash.models.database.ConnectionModel;
import br.com.abidux.cash.models.database.MySQLConnection;
import br.com.abidux.cash.models.database.SQLiteConnection;
import br.com.abidux.cash.statements.Statements;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	public static File database;
	public static ConnectionModel connectionModel;
	
	@Override
	public void onEnable() {
		if (!new File(getDataFolder(), "config.yml").exists()) saveDefaultConfig();
		database = new File(getDataFolder(), "database.db");
		connectionModel = getConfig().getBoolean("database.mysql") ? new MySQLConnection(getConfig().getString("database.host"), getConfig().getString("database.port"), getConfig().getString("database.database"), getConfig().getString("database.user"), getConfig().getString("database.password")) : new SQLiteConnection();
		Statements.initialize();
		getCommand("cash").setExecutor(new Cash());
		Bukkit.getPluginManager().registerEvents(new Join(), this);
	}
	
	@Override
	public void onDisable() {
		Statements.saveAccounts();
	}
	
}