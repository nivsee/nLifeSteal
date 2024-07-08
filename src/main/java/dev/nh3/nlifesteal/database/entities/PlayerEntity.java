package dev.nh3.nlifesteal.database.entities;

import com.j256.ormlite.field.DatabaseField;

public class PlayerEntity {
    @DatabaseField(id = true)
    private String uuid;

    @DatabaseField(columnName = "health")
    private int health;
    public PlayerEntity(){}

    public PlayerEntity(String uuid, int health){
        this.uuid = uuid;
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
