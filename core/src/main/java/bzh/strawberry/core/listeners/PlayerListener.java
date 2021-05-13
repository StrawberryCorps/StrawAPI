package bzh.strawberry.core.listeners;

import bzh.strawberry.core.StrawCore;
import bzh.strawberry.core.player.StrawPlayer;
import bzh.strawberry.core.util.PlayerUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
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
        event.setJoinMessage(null);
        StrawPlayer strawPlayer = new StrawPlayer(player);
        strawPlayer.load(() -> {
            // On affecte les grades selon si le serveur est un jeu ou non
            if (!StrawCore.CORE.getStrawServer().isGame()) {

                player.setPlayerListName(strawPlayer.getRank().getTab() + player.getName());
                player.setDisplayName(strawPlayer.getRank().getTab() + player.getName());
                PlayerUtil.setTag(player, strawPlayer.getRank().getName(), strawPlayer.getRank().getPower(), strawPlayer.getRank().getTab(), null);

            }
        });
        StrawCore.CORE.getPlayers().add(strawPlayer);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        StrawCore.CORE.getStrawPlayer(player.getUniqueId()).save();
        StrawCore.CORE.getPlayers().remove(StrawCore.CORE.getStrawPlayer(player.getUniqueId()));
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        String message = event.getMessage();
        for (StrawPlayer strawPlayers : StrawCore.CORE.getPlayers()) {
            String messageTmp = message.replaceAll(("(?i)" + strawPlayers.getPlayer().getName()), ChatColor.of("#00B0FF") + "§l" + strawPlayers.getPlayer().getName() + "§r");
            strawPlayers.getPlayer().sendMessage(strawPlayers.getRank().getChat() + event.getPlayer().getDisplayName() + " §7» §r" + ChatColor.of("#B3E5FC") + messageTmp);
        }
    }
}
