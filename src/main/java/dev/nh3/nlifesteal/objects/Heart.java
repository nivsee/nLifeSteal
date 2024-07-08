package dev.nh3.nlifesteal.objects;

import dev.nh3.nlifesteal.config.Config;
import dev.nh3.nlifesteal.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Heart {
    private int customModelData;
    private int hpAmmount;
    private Material material;

    public Heart(int customModelData, int hpAmmount, Material material) {
        this.customModelData = customModelData;
        this.hpAmmount = hpAmmount;
        this.material = material;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }

    public int getHpAmmount() {
        return hpAmmount;
    }

    public void setHpAmmount(int hpAmmount) {
        this.hpAmmount = hpAmmount;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ItemStack getItemStack(){
        ItemStack heart = new ItemStack(material);
        ItemMeta itemMeta = heart.getItemMeta();
        itemMeta.setCustomModelData(customModelData);
        itemMeta.displayName(Utils.translate(Config.getString("heart.name")));
        Utils.setLore(Utils.replaceInLore(Config.getStringList("heart.lore"), "%hearts%", String.valueOf(getHpAmmount())), itemMeta);
        heart.setItemMeta(itemMeta);
        return Utils.setPDC(heart, "hp", PersistentDataType.INTEGER, hpAmmount);
    }
}
