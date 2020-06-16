package bzh.strawberry.api.rank;

import bzh.strawberry.api.servers.Server;

/*
This file Rank is part of a project StrawAPI.
It was created on 16/06/2020 at 01:50 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class Rank {

    private int power;
    private String tab;
    private String chat;
    private Server server;

    /**
     * Might represent the rank on the whole network
     * @param p
     * @param tab
     * @param chat
     */

    public Rank(int p, String tab, String chat){
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

    public Rank(int p, String tab, String chat, Server s){
        this.power = p;
        this.tab = tab;
        this.chat = chat;
        this.server = s;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

}
