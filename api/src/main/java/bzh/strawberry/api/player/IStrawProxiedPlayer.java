package bzh.strawberry.api.player;

import bzh.strawberry.api.rank.Rank;

import java.util.List;

/*
 * This file IStrawPlayer is part of a project StrawAPI.api.
 * It was created on 05/10/2020 23:16 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public interface IStrawProxiedPlayer {
    List<Rank> getRanks();
    Rank getRank();
}
