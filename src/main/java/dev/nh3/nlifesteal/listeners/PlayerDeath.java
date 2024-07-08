package dev.nh3.nlifesteal.listeners;

import dev.nh3.nlifesteal.NLifeSteal;
import dev.nh3.nlifesteal.config.Config;
import dev.nh3.nlifesteal.managers.HealthManager;
import dev.nh3.nlifesteal.objects.Heart;
import dev.nh3.nlifesteal.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) throws SQLException {
        Player player = e.getPlayer();
        HealthManager healthManager = NLifeSteal.getHealthManager();
        Heart heart = new Heart(Config.getInteger("heart.cmd"), Config.getInteger("heart.added-on-consume"), Material.valueOf(Config.getString("heart.material")));
        int health = healthManager.getHealth(player);
        int newHealth = health - Config.getInteger("player.lost-on-death");

        if (newHealth > 0) {
            healthManager.updateHealth(player, newHealth);
        } else {
            healthManager.resetHealth(player);
            Config.getStringList("commands-on-death").forEach(command -> {
                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        command.replace("%player%", player.getName()).replace("%player_uuid%", player.getUniqueId().toString())
                );
            });
        }

        if (e.getPlayer().getKiller() == null) return;

        Player killer = e.getPlayer().getKiller();
        int killerHealth = healthManager.getHealth(killer);
        int newkillerHealth = killerHealth + Config.getInteger("player.lost-on-death");

        if (newkillerHealth > Config.getInteger("player.hearts-limit")) {
            if(Utils.isInventoryFull(killer)){
                killer.sendMessage(Utils.translate(Config.getString("messages.error.killer-full-inventory")));
                return;
            }
            killer.sendMessage(Utils.translate(Config.getString("messages.info.heart-limit")));
            killer.getInventory().addItem(heart.getItemStack());
            return;
        }

        healthManager.updateHealth(killer, newkillerHealth);
    }
}
