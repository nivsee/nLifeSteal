package dev.nh3.nlifesteal.listeners;

import dev.nh3.nlifesteal.NLifeSteal;
import dev.nh3.nlifesteal.config.Config;
import dev.nh3.nlifesteal.database.entities.PlayerEntity;
import dev.nh3.nlifesteal.managers.HealthManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws SQLException {
        HealthManager healthManager = NLifeSteal.getHealthManager();
        Player p = e.getPlayer();

        HealthManager.createIfNotExists(p);
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(healthManager.getHealth(p) * 2);
    }
}
