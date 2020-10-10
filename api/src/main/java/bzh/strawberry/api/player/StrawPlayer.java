package bzh.strawberry.api.player;

import bzh.strawberry.api.rank.Rank;
import bzh.strawberry.api.servers.Server;
import org.bukkit.entity.Player;

import java.util.Map;

/*
This file StrawPlayer is part of a project StrawAPI.
It was created on 19/06/2020 at 23:30 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public abstract class StrawPlayer {

    private Map<Server, Rank> rankServers;
    private Rank staffAccess;

    protected StrawPlayer() {

    }

    public abstract StrawPlayer getStrawPlayer(Player p);

}
