package dev.nh3.nlifesteal;

import dev.nh3.nlifesteal.commands.AdminCommands;
import dev.nh3.nlifesteal.commands.PlayerCommands;
import dev.nh3.nlifesteal.database.Database;
import dev.nh3.nlifesteal.listeners.PlayerDeath;
import dev.nh3.nlifesteal.listeners.PlayerInteract;
import dev.nh3.nlifesteal.listeners.PlayerJoin;
import dev.nh3.nlifesteal.managers.HealthManager;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteCommandsBukkit;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class NLifeSteal extends JavaPlugin {
    private static NLifeSteal instance;
    private static MiniMessage miniMessage;
    private static Database database;
    private static HealthManager healthManager;
    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        instance = this;
        miniMessage = MiniMessage.miniMessage();

        try {
            init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        healthManager = new HealthManager(database);
    }

    void init() throws SQLException {
        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }

        saveDefaultConfig();

        database = new Database();

        liteCommands = LiteBukkitFactory.builder("lifesteal", this)
                .commands(new AdminCommands())
                .commands(new PlayerCommands())
                .build();

        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    @Override
    public void onDisable() {
        try {
            database.getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static NLifeSteal getInstance() {
        return instance;
    }

    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public static HealthManager getHealthManager() {
        return healthManager;
    }
}
