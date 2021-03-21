package bzh.strawberry.api.net;

import org.bukkit.entity.Player;

/*
 * This file IStrawScoreboard is part of a project StrawAPI.api.
 * It was created on 05/10/2020 23:12 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public interface IStrawScoreboard {

    void create();
    void destroy();
    void setObjectiveName(String name);
    void setLine(int line, String value);
    void removeLine(int line);
    String getLine(int line);
    Player getBukkitPlayer();
    String getDisplayName();
    String[] getLines();
}
