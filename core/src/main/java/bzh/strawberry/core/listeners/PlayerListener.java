package bzh.strawberry.core.listeners;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.core.StrawCore;
import bzh.strawberry.core.player.StrawPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/*
 * This file PlayerJoin is part of a project StrawAPI.core.
 * It was created on 21/09/2020 21:18 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        StrawPlayer strawPlayer = new StrawPlayer(player);
        strawPlayer.load(() -> {
            player.sendMessage(StrawAPI.getAPI().getL10n().getTranslation(player.getUniqueId(), "core"));
        });
        StrawCore.getInstance().getPlayers().add(strawPlayer);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        StrawCore.getInstance().getPlayers().remove(StrawCore.getInstance().getStrawPlayer(player.getUniqueId()));
    }

}
