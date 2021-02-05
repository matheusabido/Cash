package br.com.abidux.cash.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.abidux.cash.api.CashAPI;
import br.com.abidux.cash.models.Account;

public class Cash implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("cash")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					Player player = (Player) sender;
					player.sendMessage("§7Seu cash: §e" + CashAPI.format(CashAPI.getAccount(player).getCash()));
					return true;
				}
			}
			if (args.length == 1) {
				String name = args[0];
				Account account = CashAPI.getAccount(name);
				sender.sendMessage(account != null ? "§7Cash de §e" + name.toLowerCase() + "§7: §e" + CashAPI.format(account.getCash()) : "§cConta não encontrada.");
				return true;
			}
			if (sender.hasPermission("cash.admin")) {
				if (args.length == 3) {
					Object[] data = getData(args);
					if (data.length == 0) {
						sender.sendMessage("§cEnvie um número aceitável.");
						return true;
					}
					switch (args[0]) {
					case "set":
						sender.sendMessage(CashAPI.setCash((String) data[0], (double) data[1]) ? "§aSaldo alterado." : "§cA conta não existe.");
						break;
					case "add":
						sender.sendMessage(CashAPI.addCash((String) data[0], (double) data[1]) ? "§aSaldo alterado." : "§cA conta não existe.");
						break;
					case "remove":
						sender.sendMessage(CashAPI.removeCash((String) data[0], (double) data[1]) ? "§aSaldo alterado." : "§cA conta não existe.");
						break;
					default:
						sender.sendMessage("\n§c[!] Comando incorreto. Use:\n§7/cash set <player> <amount>\n§7/cash add <player> <amount>\n§7/cash remove <player> <amount>");
						sender.sendMessage("");
						break;
					}
				}else {
					sender.sendMessage("\n§c[!] Comando incorreto. Use:\n§7/cash set <player> <amount>\n§7/cash add <player> <amount>\n§7/cash remove <player> <amount>");
					sender.sendMessage("");
				}
			}
		}
		return false;
	}
	
	private Object[] getData(String[] args) {
		try {
			String name = args[1];
			double amount = Double.parseDouble(args[2]);
			return new Object[] {name, amount};
		} catch (NumberFormatException ex) {
			return new Object[0];
		}
	}
	
}