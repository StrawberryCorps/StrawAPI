
package bzh.strawberry.api.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    public SkullItemBuilder(List<String> lore, String url, String displayname) {
        super(Material.PLAYER_HEAD, 1, displayname, lore);
        if (url == null) throw new AssertionError("URL can't be null !");

        SkullMeta tempSkullMeta = (SkullMeta) this.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField;
        try {
            profileField = tempSkullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(tempSkullMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        this.setItemMeta(tempSkullMeta);
    }

}