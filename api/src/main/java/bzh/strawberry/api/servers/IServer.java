package bzh.strawberry.api.servers;

/*
This file Server is part of a project StrawAPI.
It was created on 15/06/2020 at 23:47 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public interface IServer {
    String getName();
    String getVersion();
    int getMaxPlayer();
    String getPrefix();
    boolean isGame();
}
