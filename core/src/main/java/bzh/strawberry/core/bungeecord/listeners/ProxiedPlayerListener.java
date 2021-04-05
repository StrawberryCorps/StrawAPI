package bzh.strawberry.core.bungeecord.listeners;

import bzh.strawberry.core.bungeecord.StrawCoreBungee;
import bzh.strawberry.core.bungeecord.player.StrawProxiedPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/*
 * This file PlayerJoin is part of a project StrawAPI.core.
 * It was created on 21/09/2020 21:18 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */

public class ProxiedPlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        StrawProxiedPlayer strawPlayer = new StrawProxiedPlayer(player);
        strawPlayer.load(() -> {});
        StrawCoreBungee.CORE.getPlayers().add(strawPlayer);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        StrawCoreBungee.CORE.getPlayers().remove(StrawCoreBungee.CORE.getStrawPlayer(player.getUniqueId()));
    }

}
