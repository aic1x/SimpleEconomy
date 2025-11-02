package me.aic1x.simpleEconomy.commands;

import me.aic1x.simpleEconomy.data.MoneyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetMoneyCommand implements CommandExecutor {
    public final MoneyManager moneyManager;

    public SetMoneyCommand(MoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            if(p.hasPermission("permissions.admin")){
                if(strings.length==2){

                    String playername = strings[0];
                    String valS = strings[1];
                    double val = Double.parseDouble(valS);
                    Player target = Bukkit.getServer().getPlayerExact(playername);
                    if(target==null){
                        p.sendMessage("Player is not online");
                    }else{
                        moneyManager.setBalance(target.getUniqueId(),val);
                        p.sendMessage(ChatColor.GREEN+"You set "+ChatColor.RED+target.getDisplayName()+ChatColor.GREEN+"'s balance to "+ChatColor.GOLD+val+ChatColor.GREEN+" coins.");
                    }
                }else if(strings.length==1){
                    double val = Double.parseDouble(strings[0]);
                    moneyManager.setBalance(p.getUniqueId(),val);
                    p.sendMessage(ChatColor.RED+"Your balance was set to "+ChatColor.GOLD+val+ChatColor.GREEN+" coins.");
                }else{
                    p.sendMessage(ChatColor.RED+"Wrong usage. Use /setbalance <amount> for your balance or /balance <Player> <amount>.");
                }
            }else{
                p.sendMessage("You dont have the permission to do that.");
            }
        }else if(commandSender instanceof ConsoleCommandSender c){
            if(strings.length==2){
                String playername = strings[0];
                String valS = strings[1];
                double val = Double.parseDouble(valS);
                Player target = Bukkit.getServer().getPlayerExact(playername);
                if(target==null){
                    System.out.println("Player is not online");
                }else{
                    moneyManager.setBalance(target.getUniqueId(),val);
                    System.out.println(ChatColor.GREEN+"You set "+ChatColor.RED+target.getDisplayName()+ChatColor.GREEN+"'s balance to "+ChatColor.GOLD+val+ChatColor.GREEN+" coins.");
                }
            }else{
                System.out.println("You dont have a balance. Use /setbalance <Player> <amount>.");
            }
        }
        return true;
    }
}
