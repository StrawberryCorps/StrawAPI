package bzh.strawberry.core;

import bzh.strawberry.api.StrawAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

/*
This file StrawCore is part of a project StrawAPI.
It was created on 15/06/2020 at 22:35 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class StrawCore extends JavaPlugin {

    private StrawAPI API;
    private static StrawCore CORE;

    @Override
    public void onLoad(){
        API = StrawAPI.getAPI();
        CORE = this;
    }

    @EventHandler
    public void onEnable(){
        long startEnable = System.currentTimeMillis();

        info("### Started enabled of StrawberryCORE");
        info("Current version : " + getServer().getVersion());
        info("Authors : Eclixal, Uicias");







        info("[CORE] Core enabled in " + (System.currentTimeMillis() - startEnable) + " ms...");

    }

    private void info(String msg){
        getServer().getLogger().info(msg);
    }


}
