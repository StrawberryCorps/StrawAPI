package bzh.strawberry.core.bungeecord.listeners;

import bzh.strawberry.api.auth.event.InitSessionEvent;
import bzh.strawberry.core.bungeecord.StrawCoreBungee;
import bzh.strawberry.core.bungeecord.player.StrawProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import org.bukkit.event.EventHandler;

public class InitSessionListener implements Listener {

    @EventHandler
    public void onInitSession(InitSessionEvent event) {
        StrawProxiedPlayer strawProxiedPlayer = StrawCoreBungee.CORE.getStrawPlayer(event.getPlayer().getUniqueId());
        if (strawProxiedPlayer != null) {
            strawProxiedPlayer.setSession(event.getiSession());
        }
    }
}