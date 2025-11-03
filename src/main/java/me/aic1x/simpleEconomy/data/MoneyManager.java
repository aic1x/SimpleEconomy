package me.aic1x.simpleEconomy.data;


import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MoneyManager {
    private final JavaPlugin plugin;
    public MoneyManager(JavaPlugin plugin){
        this.plugin = plugin;
    }

    Map<UUID, Balance> balances = new HashMap<>();
    class Balance{
        double balance;
        UUID uuid;
        String playername;
        public Balance(UUID uuid, String playername){
            this.uuid = uuid;
            this.playername = playername;
            double balance = plugin.getConfig().getDouble("start-value");
        }
    }
    public void registerUser(UUID uuid, String playername){
        if(!balances.containsKey(uuid)) {
            Balance b = new Balance(uuid, playername);
            balances.put(uuid, b);
        }
    }
    public double getBalance(UUID uuid){
        Balance b = balances.get(uuid);
        return b.balance;
    }
    public void setBalance(UUID uuid, double val){
        Balance b = balances.get(uuid);
        b.balance = val;
    }
    public boolean payUser(UUID sender, UUID target, double val){
        Balance s = balances.get(sender);
        Balance t = balances.get(target);

        if(s.balance>=val){
            double nbals = s.balance-val;
            double nbalt = t.balance+val;
            setBalance(sender, nbals);
            setBalance(target, nbalt);
            return true;
        }else{
            return false;
        }
    }
    public void saveBalances() {
        try {
            File folder = plugin.getDataFolder();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Yaml yaml = new Yaml();
            File file = new File(plugin.getDataFolder(), "balances.yml");

            Map<String, Object> ymlData = new HashMap<>();
            for (UUID uuid : balances.keySet()) {
                Balance b = balances.get(uuid);
                Map<String, Object> playerData = new HashMap<>();
                playerData.put("name", b.playername);
                playerData.put("balance", b.balance);
                ymlData.put(uuid.toString(), playerData);
            }


            try (FileWriter writer = new FileWriter(file)) {
                yaml.dump(ymlData, writer);
                System.out.println("Balances saved.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadBalances(File file) throws IOException {
        if (!file.exists()) return;

        Yaml yaml = new Yaml();
        try(FileInputStream in = new FileInputStream(file)){
            Object loaded = yaml.load(in);
            if(!(loaded instanceof Map)) return;

            Map<String, Object> ymlData = (Map<String, Object>) loaded;
            balances.clear();
            for(Map.Entry<String, Object> e : ymlData.entrySet()){
                String uuidS = e.getKey();
                Object val = e.getValue();
                if(!(val instanceof Map)) continue;

                Map<String, Object> playerData = (Map<String, Object>) val;
                String name = (String) playerData.get("name");
                Object balanceObj = playerData.get("balance");
                double bal = 0.0;
                if (balanceObj instanceof Number){
                    bal = ((Number) balanceObj).doubleValue();
                }else if(balanceObj instanceof String){
                    try{
                        bal = Double.parseDouble((String) balanceObj);
                    }catch (NumberFormatException ignored) {}
                }
                try{
                    UUID uuid = UUID.fromString(uuidS);
                    Balance b = new Balance(uuid, name);
                    b.balance = bal;
                    balances.put(uuid, b);
                }catch(IllegalArgumentException ignore){}
            }
        }
    }
}
