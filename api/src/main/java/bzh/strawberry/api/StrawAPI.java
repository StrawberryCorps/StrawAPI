package bzh.strawberry.api;

import bzh.strawberry.api.gui.IInterfaceManager;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * This file StrawAPI is part of a project StrawAPI.api.
 * It was created on 16/06/2020 18:09 by Uicias.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public abstract class StrawAPI extends JavaPlugin {

    private static StrawAPI API;

    public StrawAPI() {
        API = this;
    }

    public static StrawAPI getAPI(){ return API;}

    public abstract IInterfaceManager getInterfaceManager();

}
