package bzh.strawberry.core;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.core.gui.InterfaceManager;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

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

        info("[CORE] Core enabled in " + (System.currentTimeMillis() - startEnable) + " ms...");

    }

    @Override
    public InterfaceManager getInterfaceManager() {
        return interfaceManager;
    }

    private void info(String msg){
        getServer().getLogger().info(msg);
    }

    public static StrawCore getInstance() {
        return CORE;
    }
}
