package me.aic1x.simpleEconomy;

import me.aic1x.simpleEconomy.commands.BalanceCommand;
import me.aic1x.simpleEconomy.commands.PayCommand;
import me.aic1x.simpleEconomy.commands.SetMoneyCommand;
import me.aic1x.simpleEconomy.data.MoneyManager;
import me.aic1x.simpleEconomy.listener.JoinListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class SimpleEconomy extends JavaPlugin {

    private MoneyManager moneyManager;

    FileConfiguration config = this.getConfig();


    @Override
    public void onEnable() {
        saveDefaultConfig();
        moneyManager = new MoneyManager(this);
        File balanceFile = new File(getDataFolder(), "balances.yml");
        try {
            moneyManager.loadBalances(balanceFile);
        }catch (IOException ex){
            ex.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new JoinListener(moneyManager), this);
        getCommand("balance").setExecutor(new BalanceCommand(moneyManager));
        getCommand("setbalance").setExecutor(new SetMoneyCommand(moneyManager));
        getCommand("pay").setExecutor(new PayCommand(moneyManager));


    }

    @Override
    public void onDisable() {
        moneyManager.saveBalances();
    }
}
