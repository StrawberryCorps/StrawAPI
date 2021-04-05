package bzh.strawberry.core.bungeecord;

import bzh.strawberry.api.StrawAPIBungee;
import bzh.strawberry.api.factory.DataFactory;
import bzh.strawberry.api.player.IStrawProxiedPlayer;
import bzh.strawberry.core.bungeecord.listeners.ProxiedPlayerListener;
import bzh.strawberry.core.bungeecord.player.StrawProxiedPlayer;
import bzh.strawberry.core.factory.MySQLFactory;
import bzh.strawberry.core.player.StrawPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StrawCoreBungee extends StrawAPIBungee {

    public static StrawCoreBungee CORE;

    private DataFactory dataFactory;
    private List<StrawProxiedPlayer> players;

    @Override
    public void onEnable() {
        long startEnable = System.currentTimeMillis();

        info("### Started enabled of StrawberryCore");
        info("Current version : " + this.getProxy().getVersion());
        info("Authors : " + this.getDescription().getAuthor());

        info("Loading database configuration...");

        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "data.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File fileConfig = new File(getDataFolder(), "data.yml");
        YamlConfiguration dataYML = YamlConfiguration.loadConfiguration(fileConfig);

        String sqlUrl = dataYML.getString("url", "127.0.0.1");
        String sqlUsername = dataYML.getString("user", "root");
        String sqlPassword = dataYML.getString("pass", "passw0rd");
        String database = dataYML.getString("database", "strawberry");
        int sqlMinPoolSize = dataYML.getInt("minpoolsize", 1);
        int sqlMaxPoolSize = dataYML.getInt("maxpoolsize", 10);

        try {
            dataFactory = MySQLFactory.getInstance("jdbc:mysql://" + sqlUrl + ":3306/" + database, sqlUsername, sqlPassword, sqlMinPoolSize, sqlMaxPoolSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (dataFactory == null) {
            this.getProxy().stop();
            throw new NullPointerException("DataFactory can't be null ! Please verify data.yml !");
        } else
            info("Database successfully connected with MySQL-Connector");

        this.players = new ArrayList<>();

        this.getProxy().getPluginManager().registerListener(this, new ProxiedPlayerListener());

        info("[CORE] Core enabled in " + (System.currentTimeMillis() - startEnable) + " ms...");
    }

    @Override
    public void onDisable() {

    }

    private void info(String msg){
        getProxy().getLogger().info(msg);
    }

    @Override
    public DataFactory getDataFactory() {
        return this.dataFactory;
    }

    public List<StrawProxiedPlayer> getPlayers() {
        return players;
    }

    @Override
    public StrawProxiedPlayer getStrawPlayer(UUID uuid) {
        return this.players.stream().filter(strawPlayer -> strawPlayer.getUniqueID().equals(uuid)).findFirst().orElse(null);
    }
}
