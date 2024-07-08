package dev.nh3.nlifesteal.config;

import dev.nh3.nlifesteal.NLifeSteal;

import java.util.List;

public class Config {

    public static void reload(){
        NLifeSteal.getInstance().reloadConfig();
    }

    public static String getString(String path){
        return NLifeSteal.getInstance().getConfig().getString(path);
    }

    public static Integer getInteger(String path){
        return NLifeSteal.getInstance().getConfig().getInt(path);
    }

    public static Boolean getBoolean(String path){
        return NLifeSteal.getInstance().getConfig().getBoolean(path);
    }

    public static double getDouble(String path){
         return NLifeSteal.getInstance().getConfig().getDouble(path);
    }

    public static List<String> getStringList(String path){
        return NLifeSteal.getInstance().getConfig().getStringList(path);
    }
}
