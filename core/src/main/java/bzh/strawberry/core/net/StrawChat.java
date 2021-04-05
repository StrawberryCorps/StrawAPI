package bzh.strawberry.core.net;

import bzh.strawberry.api.net.IStrawChat;
import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import org.bukkit.Color;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/*
 * This file StrawChat is part of a project StrawAPI.core.
 * It was created on 05/10/2020 22:31 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class StrawChat implements IStrawChat {

    @Override
    public void sendMessageJson(Player player, String jsonMessage) {
        IChatBaseComponent base = IChatBaseComponent.ChatSerializer.a(jsonMessage);
        PacketPlayOutChat chat = new PacketPlayOutChat(base, ChatMessageType.CHAT, player.getUniqueId());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);
    }

    @Override
    public void sendMessage(Player player, String message) {
        IChatBaseComponent base = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");
        PacketPlayOutChat chat = new PacketPlayOutChat(base, ChatMessageType.CHAT, player.getUniqueId());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);
    }

    @Override
    public void sendMessageWithColor(Player player, String message, Color color) {
        IChatBaseComponent base = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\",\"color\":\"#" + (Integer.toHexString(color.getRed()).toUpperCase() + Integer.toHexString(color.getGreen()).toUpperCase() + Integer.toHexString(color.getBlue()).toUpperCase()) + "\"}");
        PacketPlayOutChat chat = new PacketPlayOutChat(base, ChatMessageType.CHAT, player.getUniqueId());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);
    }
}
