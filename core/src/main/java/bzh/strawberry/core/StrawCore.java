package bzh.strawberry.core;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.api.factory.DataFactory;
import bzh.strawberry.core.factory.MySQLFactory;
import bzh.strawberry.core.factory.SQLiteFactory;
import bzh.strawberry.core.gui.InterfaceManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;

import java.io.File;
import java.util.Arrays;

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

        File fileConfig = new File(getDataFolder().getAbsoluteFile().getParentFile().getParentFile(), "data.yml");
        if (!fileConfig.exists()) {
            info("Cannot find the database configuration !");
            this.getServer().shutdown();
            return;
        } else {
            YamlConfiguration dataYML = YamlConfiguration.loadConfiguration(fileConfig);

            String sqlUrl = dataYML.getString("url", "127.0.0.1");
            String sqlUsername = dataYML.getString("user", "root");
            String sqlPassword = dataYML.getString("pass", "passw0rd");
            int sqlMinPoolSize = dataYML.getInt("minpoolsize", 1);
            int sqlMaxPoolSize = dataYML.getInt("maxpoolsize", 10);

            if (dataYML.getString("datatype").equals("mysql")) {
                dataFactory = MySQLFactory.getInstance(sqlUrl, sqlUsername, sqlPassword, sqlMinPoolSize, sqlMaxPoolSize);
            } else if (dataYML.getString("datatype").equals("sqlite")) {
                dataFactory = SQLiteFactory.getInstance(sqlUrl, sqlUsername, sqlPassword, sqlMinPoolSize, sqlMaxPoolSize);
            }

            if (dataFactory == null) {
                this.getServer().shutdown();
                throw new NullPointerException("DataFactory can't be null ! Please verify data.yml !");
            }
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

    private void info(String msg){
        getServer().getLogger().info(msg);
    }

    public static StrawCore getInstance() {
        return CORE;
    }
}
