package bzh.strawberry.core;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.api.factory.DataFactory;
import bzh.strawberry.api.l10n.ILang;
import bzh.strawberry.core.factory.MySQLFactory;
import bzh.strawberry.core.factory.SQLiteFactory;
import bzh.strawberry.core.gui.InterfaceManager;
import bzh.strawberry.core.l10n.L10nManager;
import bzh.strawberry.core.l10n.Lang;
import bzh.strawberry.core.listeners.PlayerListener;
import bzh.strawberry.core.player.StrawPlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/*
This file StrawCore is part of a project StrawAPI.
It was created on 15/06/2020 at 22:35 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class StrawCore extends StrawAPI {

    private static StrawCore CORE;

    /*************/

    private InterfaceManager interfaceManager;
    private DataFactory dataFactory;

    private L10nManager l10nManager;
    private Lang lang;

    private List<StrawPlayer> players;

    @Override
    public void onLoad(){
        CORE = this;
    }

    @EventHandler
    public void onEnable(){
        long startEnable = System.currentTimeMillis();

        info("### Started enabled of StrawberryCore");
        info("Current version : " + this.getServer().getVersion());
        info("Authors : " + Arrays.toString(this.getDescription().getAuthors().toArray()));

        this.interfaceManager = new InterfaceManager();

        info("Loading database configuration...");

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

        if (Objects.equals(dataYML.getString("datatype"), "mysql")) {
            try {
                dataFactory = MySQLFactory.getInstance("jdbc:mysql://" + sqlUrl + ":3306/" + database, sqlUsername, sqlPassword, sqlMinPoolSize, sqlMaxPoolSize);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (Objects.equals(dataYML.getString("datatype"), "sqlite")) {
            try {
                dataFactory = SQLiteFactory.getInstance(getDataFolder() + File.separator + sqlUrl, null, null, sqlMinPoolSize, sqlMaxPoolSize);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (dataFactory == null) {
            this.getServer().shutdown();
            throw new NullPointerException("DataFactory can't be null ! Please verify data.yml !");
        } else
            info("Database successfully connected with " + dataYML.getString("datatype") + "-Connector");

        this.players = new ArrayList<>();
        this.l10nManager = new L10nManager();
        this.lang = new Lang();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        try {
            final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            if(jarFile.isFile()) {
                final JarFile jar = new JarFile(jarFile);
                final Enumeration<JarEntry> entries = jar.entries();
                while(entries.hasMoreElements()) {
                    final String name = entries.nextElement().getName();
                    if (name.startsWith("locale/") && name.endsWith(".json"))
                        this.saveResource(name, true);
                }
                jar.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        try {
            File filesList[] = new File(getDataFolder() + "/locale/").listFiles();
            for (File file : filesList) {
                if (file.isDirectory()) {
                    for (File file1 : file.listFiles()) {
                        if (file1.getName().endsWith(".json")) {
                            Object obj = parser.parse(new FileReader(file1));
                            JSONObject jsonObject = (JSONObject) obj;
                            l10nManager.addLanguage(file.getName(), jsonObject);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        info("[CORE] Core enabled in " + (System.currentTimeMillis() - startEnable) + " ms...");

    }

    @Override
    public void onDisable() {
        // Disconnect all datasource !
        if (getDataFactory() instanceof MySQLFactory) {
            try {
                ((MySQLFactory) getDataFactory()).shutdownDataSource(getDataFactory().getDataSource());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (getDataFactory() instanceof SQLiteFactory) {
            try {
                ((SQLiteFactory) getDataFactory()).shutdownDataSource(getDataFactory().getDataSource());
            } catch (Exception e) {
                e.printStackTrace();
            }
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
    public ILang getL10n() {
        return this.lang;
    }

    public L10nManager getL10nManager() {
        return l10nManager;
    }

    public StrawPlayer getStrawPlayer(UUID uuid) {
        return this.players.stream().filter(strawPlayer -> strawPlayer.getUniqueID().equals(uuid)).findFirst().orElse(null);
    }

    public List<StrawPlayer> getPlayers() {
        return players;
    }

    private void info(String msg){
        getServer().getLogger().info(msg);
    }

    public static StrawCore getInstance() {
        return CORE;
    }
}
