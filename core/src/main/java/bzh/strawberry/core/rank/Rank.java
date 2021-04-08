package bzh.strawberry.core.rank;

import bzh.strawberry.api.rank.IRank;

/*
This file IRank is part of a project StrawAPI.
It was created on 16/06/2020 at 01:50 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class Rank implements IRank {
    private int id;
    private int power;
    private String tab;
    private String chat;
    private String server;

    /**
     * Might represent the rank on the whole network
     * @param p
     * @param tab
     * @param chat
     */
    public Rank(int id, int p, String tab, String chat) {
        this.id = id;
        this.power = p;
        this.tab = tab;
        this.chat = chat;
    }

    /**
     * Might represent the rank on a given server
     * @param p
     * @param tab
     * @param chat
     * @param s
     */
    public Rank(int id, int p, String tab, String chat, String s) {
        this.id = id;
        this.power = p;
        this.tab = tab;
        this.chat = chat;
        this.server = s;
    }

    public int getId() {
        return id;
    }
    public int getPower() {
        return power;
    }
    public String getTab() {
        return tab;
    }
    public String getChat() {
        return chat;
    }
    public String getServer() {
        return server;
    }
}