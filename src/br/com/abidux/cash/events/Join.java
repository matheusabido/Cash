package br.com.abidux.cash.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.abidux.cash.api.CashAPI;
import br.com.abidux.cash.models.Account;

public class Join implements Listener {
	
	@EventHandler
	void join (PlayerJoinEvent e) {
		if (CashAPI.getAccount(e.getPlayer()) == null)
			Account.accounts.add(new Account(e.getPlayer().getName().toLowerCase(), 0));
	}
	
}