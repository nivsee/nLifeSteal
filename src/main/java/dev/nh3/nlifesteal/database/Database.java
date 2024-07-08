package dev.nh3.nlifesteal.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dev.nh3.nlifesteal.NLifeSteal;
import dev.nh3.nlifesteal.database.entities.PlayerEntity;
import java.sql.SQLException;

public class Database {
    private final String url = "jdbc:h2:" + NLifeSteal.getInstance().getDataFolder().getAbsolutePath() + "/database";
    private final JdbcPooledConnectionSource conn;
    private Dao<PlayerEntity, String> playerDao;

    public Database() throws SQLException {
        conn = new JdbcPooledConnectionSource(url);
        TableUtils.createTableIfNotExists(conn, PlayerEntity.class);
        playerDao = DaoManager.createDao(conn, PlayerEntity.class);
    }

    public JdbcPooledConnectionSource getConnection() {
        return conn;
    }

    public Dao<PlayerEntity, String> getPlayerDao() {
        return playerDao;
    }
}
