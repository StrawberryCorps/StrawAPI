package bzh.strawberry.api;

import bzh.strawberry.api.factory.DataFactory;
import bzh.strawberry.api.player.IStrawProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;

/*
 * This file StrawAPI is part of a project StrawAPI.api.
 * It was created on 16/06/2020 18:09 by Uicias.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public abstract class StrawAPIBungee extends Plugin {

    private static StrawAPIBungee API;

    public StrawAPIBungee() {
        API = this;
    }

    public static StrawAPIBungee getAPI(){ return API;}

    public abstract DataFactory getDataFactory();
    public abstract IStrawProxiedPlayer getStrawPlayer(UUID uuid);

}