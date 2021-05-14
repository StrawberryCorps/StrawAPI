package bzh.strawberry.api.auth.event;

import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.AsyncEvent;

public class InitSessionEvent extends AsyncEvent<InitSessionEvent> {

    private ServerInfo serverInfo;
    private ProxiedPlayer player;

    public InitSessionEvent(ProxiedPlayer player, ServerInfo serverInfo, Callback<InitSessionEvent> done) {
        super(done);
        this.player = player;
        this.serverInfo = serverInfo;
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }
}