package bzh.strawberry.api.rank;

/*
This file IRank is part of a project StrawAPI.
It was created on 16/06/2020 at 01:50 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public interface IRank extends Comparable<IRank> {
    int getId();
    int getPower();
    String getName();
    String getTab();
    String getChat();
    String getServer();
}