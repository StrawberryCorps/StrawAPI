package bzh.strawberry.core.servers;

import bzh.strawberry.api.servers.IServer;

/*
This file Server is part of a project StrawAPI.
It was created on 15/06/2020 at 23:54 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class Server implements IServer {

    private String name;
    private String version;
    private int maxPlayer;
    private String prefix;
    private boolean game;

    public Server(String name, String version, int maxPlayer, String prefix, boolean game) {
        this.name = name;
        this.version = version;
        this.maxPlayer = maxPlayer;
        this.prefix = prefix;
        this.game = game;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public int getMaxPlayer() {
        return maxPlayer;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public boolean isGame() {
        return game;
    }
}
