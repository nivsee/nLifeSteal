package dev.nh3.nlifesteal.managers;

import com.j256.ormlite.dao.Dao;
import dev.nh3.nlifesteal.config.Config;
import dev.nh3.nlifesteal.database.Database;
import dev.nh3.nlifesteal.database.entities.PlayerEntity;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class HealthManager {
    public static Dao<PlayerEntity, String> playerDao;
    public HealthManager(Database database) {
        playerDao = database.getPlayerDao();
    }

    public static void createIfNotExists(Player player) throws SQLException {
        playerDao.createIfNotExists(new PlayerEntity(player.getUniqueId().toString(),Config.getInteger("player.hearts")));
    }

    public int getHealth(Player player) throws SQLException {
        PlayerEntity playerEntity = playerDao.queryForId(player.getUniqueId().toString());
        return playerEntity.getHealth();
    }

    public void updateHealth(Player player, int health) throws SQLException {
        PlayerEntity playerEntity = playerDao.queryForId(player.getUniqueId().toString());
        playerEntity.setHealth(health);

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health * 2);
        playerDao.update(playerEntity);
    }

    public void resetHealth(Player player) throws SQLException {
        PlayerEntity playerEntity = playerDao.queryForId(player.getUniqueId().toString());
        playerEntity.setHealth(Config.getInteger("player.hearts"));

        playerDao.update(playerEntity);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Config.getInteger("player.hearts") * 2);
    }
}
