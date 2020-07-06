package bzh.strawberry.api.gui;

import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*
 * This file IInterfaceManager is part of a project StrawAPI.api.
 * It was created on 06/07/2020 22:22 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public interface IInterfaceManager {

    void openInterface(AbstractInterface abstractInterface, Player player);
    void closeInterface(Player player);

    AbstractInterface getPlayerInterface(UUID uuid);

    ConcurrentHashMap<UUID, AbstractInterface> getOpenedInterface();
}
