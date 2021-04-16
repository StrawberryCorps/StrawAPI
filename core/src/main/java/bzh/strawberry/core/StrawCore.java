package bzh.strawberry.core;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.api.factory.DataFactory;
import bzh.strawberry.api.net.IStrawChat;
import bzh.strawberry.api.servers.IServer;
import bzh.strawberry.core.factory.MySQLFactory;
import bzh.strawberry.core.gui.InterfaceManager;
import bzh.strawberry.core.listeners.PlayerListener;
import bzh.strawberry.core.net.StrawChat;
import bzh.strawberry.core.player.StrawPlayer;
import org.bukkit.Bukkit;
import bzh.strawberry.core.rank.RanksManager;
import bzh.strawberry.core.servers.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

/*
This file StrawCore is part of a project StrawAPI.
It was created on 15/06/2020 at 22:35 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class StrawCore extends StrawAPI {

    public static StrawCore CORE;

    /*************/

    private InterfaceManager interfaceManager;
    private DataFactory dataFactory;

    private StrawChat strawChat;
    private Server server;
    private RanksManager ranksManager;

    private List<StrawPlayer> players;

    @Override
    public void onLoad(){
        CORE = this;
    }

    @EventHandler
    public void onEnable(){
        long startEnable = System.currentTimeMillis();

        getLogger().info("######################## [StrawCore - " + getDescription().getVersion() + "] #################################");
        info("Current version : " + this.getServer().getVersion());
        info("Authors : " + Arrays.toString(this.getDescription().getAuthors().toArray()));

        this.interfaceManager = new InterfaceManager();

        info("Loading database configuration...");

        this.saveDefaultConfig();
        File fileConfig = new File(getDataFolder(), "data.yml");
        if (!fileConfig.exists()) {
            this.saveResource("data.yml", false);
        }

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
            this.getServer().shutdown();
            throw new NullPointerException("DataFactory can't be null ! Please verify data.yml !");
        } else
            info("Database successfully connected with MySQL-Connector");

        FileConfiguration configuration = this.getConfig();
        this.server = new Server(this.getServer().getName(), this.getServer().getBukkitVersion(), this.getServer().getMaxPlayers(), "", configuration.getBoolean("game"));

        this.players = new ArrayList<>();
        this.strawChat = new StrawChat();

        this.ranksManager = new RanksManager();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getLogger().info("######################## [StrawCore - Enable in " + (System.currentTimeMillis() - startEnable) + " ms] #################################");

    }

    @Override
    public void onDisable() {
        try {
            ((MySQLFactory) getDataFactory()).shutdownDataSource(getDataFactory().getDataSource());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public InterfaceManager getInterfaceManager() {
        return interfaceManager;
    }

    @Override
    public DataFactory getDataFactory() {
        return this.dataFactory;
    }

    @Override
    public IStrawChat getChat() {
        return this.strawChat;
    }

    @Override
    public StrawPlayer getStrawPlayer(UUID uuid) {
        return this.players.stream().filter(strawPlayer -> strawPlayer.getUniqueID().equals(uuid)).findFirst().orElse(null);
    }

    @Override
    public Server getStrawServer() {
        return this.server;
    }

    public RanksManager getRanksManager() {
        return ranksManager;
    }

    @Override
    @SuppressWarnings("Deprecated")
    public Player getPlayer(String name) {
        return Bukkit.getOfflinePlayer(name).getPlayer();
    }

    public List<StrawPlayer> getPlayers() {
        return players;
    }

    private void info(String msg){
        getServer().getLogger().info(msg);
    }
}