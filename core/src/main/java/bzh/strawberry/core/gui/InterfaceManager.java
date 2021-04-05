package bzh.strawberry.core.gui;

/*
 * This file InterfaceManager is part of a project StrawAPI.core.
 * It was created on 06/07/2020 22:26 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */

import bzh.strawberry.api.gui.AbstractInterface;
import bzh.strawberry.api.gui.IInterfaceManager;
import bzh.strawberry.core.StrawCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InterfaceManager implements IInterfaceManager, Listener {

    private final ConcurrentHashMap<UUID, AbstractInterface> openedInterface;

    public InterfaceManager() {
        this.openedInterface = new ConcurrentHashMap<>();
        StrawCore.CORE.getServer().getPluginManager().registerEvents(this, StrawCore.CORE);
    }

    @Override
    public void openInterface(AbstractInterface abstractInterface, Player player) {
        if (this.openedInterface.containsKey(player.getUniqueId()))
            this.closeInterface(player);

        this.openedInterface.put(player.getUniqueId(), abstractInterface);
    }

    @Override
    public void closeInterface(Player player) {
        if (this.openedInterface.containsKey(player.getUniqueId())) {
            this.openedInterface.get(player.getUniqueId()).onInventoryClose(player);
            this.openedInterface.remove(player.getUniqueId());
        }
    }

    @Override
    public AbstractInterface getPlayerInterface(UUID uuid) {
        AbstractInterface abstractInterface = null;
        if (this.openedInterface.containsKey(uuid))
            abstractInterface = this.openedInterface.get(uuid);
        return abstractInterface;
    }

    @Override
    public ConcurrentHashMap<UUID, AbstractInterface> getOpenedInterface() {
        return openedInterface;
    }

    /*****************************************/


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.getPlayerInterface(event.getPlayer().getUniqueId()) != null) {
            this.closeInterface(event.getPlayer());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            AbstractInterface abstractInterface = this.getPlayerInterface(event.getWhoClicked().getUniqueId());
            if (abstractInterface != null) {
                event.setCancelled(true);
                if (event.getClickedInventory() instanceof PlayerInventory)
                    return;
                String action = abstractInterface.getAction(event.getSlot());
                if (action != null)
                    abstractInterface.onInventoryClick((Player) event.getWhoClicked(), event.getCurrentItem(), event.getClick(), action);
            }
        }
    }


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (this.getPlayerInterface(event.getPlayer().getUniqueId()) != null && event.getInventory().equals(this.getPlayerInterface(event.getPlayer().getUniqueId()).getInventory())) {
            this.closeInterface((Player) event.getPlayer());
        }
    }
}
