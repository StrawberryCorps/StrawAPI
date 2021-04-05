
package bzh.strawberry.api.util;

import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

/*
 * This file SkullItemBuilder is part of a project StrawAPI.api.
 * It was created on 06/07/2020 18:28 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class SkullItemBuilder extends ItemStackBuilder {

    public SkullItemBuilder(String name, String displayname) {
        super(Material.PLAYER_HEAD, 1, displayname);
        if (name == null) throw new AssertionError();

        SkullMeta tempSkullMeta = (SkullMeta) this.getItemMeta();
        assert tempSkullMeta != null;

        tempSkullMeta.setOwner(name);
        this.setItemMeta(tempSkullMeta);
    }

    public SkullItemBuilder(String name, String displayname, List<String> lore) {
        super(Material.PLAYER_HEAD, 1, displayname, lore);
        if (name == null) throw new AssertionError();

        SkullMeta tempSkullMeta = (SkullMeta) this.getItemMeta();
        assert tempSkullMeta != null;

        tempSkullMeta.setOwner(name);
        this.setItemMeta(tempSkullMeta);
    }
}