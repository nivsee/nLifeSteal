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
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@Command(name = "lifesteal")
public class PlayerCommands {
    @Execute(name = "withdraw")
    void withdraw(@Context Player sender, @Arg Integer amount) throws SQLException {
        HealthManager healthManager = NLifeSteal.getHealthManager();
        if(amount <= 0) {
            sender.sendMessage("messages.error.invalid-amount");
        }

        int currentHealth = healthManager.getHealth(sender);
        if(currentHealth <= amount) {
            sender.sendMessage("messages.error.insufficient-health");
            return;
        }

        healthManager.updateHealth(sender, currentHealth - amount);
        Heart heart = new Heart(Config.getInteger("heart.cmd"), Config.getInteger("heart.added-on-consume"), Material.valueOf(Config.getString("heart.material")));

        sender.getInventory().addItem(heart.getItemStack().asQuantity(amount));
        sender.sendMessage(Utils.translate(Config.getString("messages.success.withdrawn")));
    }
}
