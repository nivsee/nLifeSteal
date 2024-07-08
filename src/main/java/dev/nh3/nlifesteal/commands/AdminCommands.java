package dev.nh3.nlifesteal.commands;

import dev.nh3.nlifesteal.NLifeSteal;
import dev.nh3.nlifesteal.config.Config;
import dev.nh3.nlifesteal.managers.HealthManager;
import dev.nh3.nlifesteal.objects.Heart;
import dev.nh3.nlifesteal.utils.Utils;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

@Command(name = "lifesteal admin")
public class AdminCommands {
    @Execute(name = "get")
    @Permission("lifesteal.admin")
    void get(@Context Player sender, @OptionalArg Integer quantity) {
        Heart heart = new Heart(Config.getInteger("heart.cmd"), Config.getInteger("heart.added-on-consume"), Material.valueOf(Config.getString("heart.material")));

        ItemStack heartItem = heart.getItemStack();
        if (quantity != null) {
            heartItem.setAmount(quantity);
        }
        if(Utils.isInventoryFull(sender)){
            sender.sendMessage(Utils.translate(Config.getString("messages.error.full-inventory")));
            return;
        }

        sender.getInventory().addItem(heartItem);
        sender.sendMessage(Utils.translate(Config.getString("messages.success.get-hearts")));
    }

    @Execute(name = "set")
    @Permission("lifesteal.admin")
    void set(@Context Player sender, @Arg Player player, @Arg Integer quantity) throws SQLException {
        HealthManager healthManager = NLifeSteal.getHealthManager();
        healthManager.updateHealth(player, quantity);

        sender.sendMessage(Utils.translate(Config.getString("messages.success.set-hearts").replace("%player%", player.getName()).replace("%hearts%", String.valueOf(quantity))));
    }

    @Execute(name = "check")
    @Permission("lifesteal.admin")
    void check(@Context Player sender, @Arg Player player) throws SQLException {
        HealthManager healthManager = NLifeSteal.getHealthManager();
        int health = healthManager.getHealth(player);

        sender.sendMessage(Utils.translate(Config.getString("messages.info.check-hearts").replace("%player%", sender.getName()).replace("%hearts%", String.valueOf(health))));
    }

    @Execute(name = "reload")
    @Permission("lifesteal.admin")
    void reload(@Context Player sender) {
        Config.reload();
        sender.sendMessage(Utils.translate(Config.getString("messages.success.reload")));
    }
}
