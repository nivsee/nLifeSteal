package dev.nh3.nlifesteal.listeners;

import dev.nh3.nlifesteal.NLifeSteal;
import dev.nh3.nlifesteal.config.Config;
import dev.nh3.nlifesteal.managers.HealthManager;
import dev.nh3.nlifesteal.utils.Utils;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.sql.SQLException;

public class PlayerInteract implements Listener {
    @EventHandler
    void onPlayerInteract(PlayerInteractEvent e) throws SQLException {
        Player player = e.getPlayer();
        Action action = e.getAction();
        HealthManager healthManager = NLifeSteal.getHealthManager();
        if( action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK ){
            if(player.getInventory().getItemInMainHand().getType() == Material.AIR) return;
            Integer hp = (Integer) Utils.getPDCItemData(player.getInventory().getItemInMainHand(), "hp", PersistentDataType.INTEGER);
            if (hp == null) return;

            int currentHp = healthManager.getHealth(player);
            if (currentHp + hp > Config.getInteger("player.hearts-limit")) {
                player.sendMessage(Utils.translate(Config.getString("messages.error.heart-limit")));
                return;
            }

            healthManager.updateHealth(player, currentHp + hp);
            player.sendMessage(Utils.translate(Config.getString("messages.success.consumed").replace("%hearts%", String.valueOf(hp))));
            player.getInventory().getItemInMainHand().subtract();

            if(Config.getBoolean("heart.effects.sound-enabled")){
                player.playSound(Sound.sound(
                        Key.key(Config.getString("heart.effects.sound")),
                        Sound.Source.valueOf(Config.getString("heart.effects.sound-source")),
                        (float) Config.getDouble("heart.effects.volume"),
                        (float) Config.getDouble("heart.effects.pitch"))
                );
            }
        }
    }
}
