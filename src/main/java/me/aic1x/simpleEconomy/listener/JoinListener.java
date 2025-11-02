package me.aic1x.simpleEconomy.listener;

import me.aic1x.simpleEconomy.data.MoneyManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    private final MoneyManager moneyManager;

    public JoinListener(MoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        String name = e.getPlayer().getDisplayName();
        moneyManager.registerUser(uuid,name);
    }

}
