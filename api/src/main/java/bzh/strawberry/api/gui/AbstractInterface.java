package bzh.strawberry.api.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.TreeMap;

/*
 * This file AbstractInterface is part of a project StrawAPI.api.
 * It was created on 06/07/2020 19:04 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public abstract class AbstractInterface {

    protected TreeMap<Integer, String> actions = new TreeMap<>();
    protected Inventory inventory;
    protected Player player;

    public AbstractInterface(String display, int size, Player player) {
        if (size % 9 != 0 && size > 54)
            throw new AssertionError("size can't be > 54 and size % 9 should be 0 !");

        if (player == null)
            throw new NullPointerException("Player can't be null !");

        this.player = player;

        this.inventory = Bukkit.createInventory(null, size, display);
        this.show(player);
        player.openInventory(this.inventory);
    }

    public abstract void show(Player player);
    public abstract void onInventoryClose(Player player);

    public abstract void onInventoryClick(Player player, ItemStack itemStack, ClickType clickType, String action);

    public void addItem(ItemStack item, int slot, String action) {
        this.actions.put(slot, action);
        this.inventory.setItem(slot, item);
    }

    public String getAction(int slot) {
        if (!this.actions.containsKey(slot))
            return null;
        return this.actions.get(slot);
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
