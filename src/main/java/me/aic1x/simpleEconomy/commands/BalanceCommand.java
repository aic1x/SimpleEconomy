package me.aic1x.simpleEconomy.commands;

import com.google.gson.internal.bind.JsonTreeReader;
import me.aic1x.simpleEconomy.data.MoneyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    private final MoneyManager moneyManager;

    public BalanceCommand(MoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player p) {
            if (strings.length == 0) {
                double bal = moneyManager.getBalance(p.getUniqueId());
                p.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.GOLD + bal + ChatColor.GREEN + " coins.");
            } else if (strings.length > 0 && p.hasPermission("permissions.admin")) {
                String playername = strings[0];
                Player target = Bukkit.getServer().getPlayerExact(playername);
                if (target != null) {
                    double bal = moneyManager.getBalance(target.getUniqueId());
                    p.sendMessage(ChatColor.GREEN + target.getDisplayName() + "'s balance is " + ChatColor.GOLD + bal + ChatColor.GREEN + " coins.");
                } else {
                    p.sendMessage(ChatColor.RED + "Player is not online.");
                }

            } else {
                p.sendMessage(ChatColor.RED + "You dont have the permission to do that.");
            }
        } else if (commandSender instanceof ConsoleCommandSender c) {
            if (strings.length == 0) {
                System.out.println("You dont have a balance");
            } else {
                String playername = strings[0];
                Player target = Bukkit.getServer().getPlayerExact(playername);
                if (target != null) {
                    double bal = moneyManager.getBalance(target.getUniqueId());
                    System.out.println(ChatColor.GREEN + target.getDisplayName() + "'s balance is " + ChatColor.GOLD + bal + ChatColor.GREEN + " coins.");
                }
            }
        }
        return true;
    }
}
