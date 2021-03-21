package bzh.strawberry.api.net;

import org.bukkit.Color;
import org.bukkit.entity.Player;

/*
 * This file IStrawChat is part of a project StrawAPI.api.
 * It was created on 05/10/2020 22:30 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public interface IStrawChat {

    void sendMessageJson(Player player, String jsonMessage);
    void sendMessage(Player player, String message);
    void sendMessageWithColor(Player player, String message, Color color);

}
