package me.aic1x.simpleEconomy.commands;

import me.aic1x.simpleEconomy.data.MoneyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    public final MoneyManager moneyManager;

    public PayCommand(MoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player p){
            if(strings.length==2){
                String playername = strings[0];
                double val = Double.parseDouble(strings[1]);
                Player target = Bukkit.getServer().getPlayerExact(playername);
                if(target==null){
                    p.sendMessage(ChatColor.RED+"Player is not online.");
                }else{
                    boolean result = moneyManager.payUser(p.getUniqueId(),target.getUniqueId(),val);
                    if(result){
                        p.sendMessage(ChatColor.GREEN+"You successfully send "+ChatColor.GOLD+val+ChatColor.GREEN+" coins to "+ChatColor.RED+target.getDisplayName()+ChatColor.GREEN+".");
                        target.sendMessage(ChatColor.GREEN+"You recieved "+ChatColor.GOLD+val+ChatColor.GREEN+" coins from "+ChatColor.RED+p.getDisplayName()+ChatColor.GREEN+".");
                    }else{
                        p.sendMessage("You dont have this amount on your bank account.");
                    }
                }
            }else{
                p.sendMessage("Wrong usage. Use /pay <Player> <amount>");
            }
        }else{
            System.out.println("This command is for Players only.");
        }

        return true;
    }
}
