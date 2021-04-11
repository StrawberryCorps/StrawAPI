package bzh.strawberry.api.player;

/*
 * This file IStrawPlayer is part of a project StrawAPI.api.
 * It was created on 05/10/2020 23:16 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
import bzh.strawberry.api.net.IStrawScoreboard;
import bzh.strawberry.api.rank.IRank;

import java.util.List;

public interface IStrawPlayer {

    IStrawScoreboard getScoreboard() throws Exception;
    void createScoreBoard(String objectiveName);
    List<IRank> getRanks();
    IRank getRank();
}
