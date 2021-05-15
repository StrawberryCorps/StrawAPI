package bzh.strawberry.api.auth.event;

import bzh.strawberry.api.auth.ISession;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class InitSessionEvent extends Event {

    private ISession iSession;
    private ProxiedPlayer player;

    public InitSessionEvent(ProxiedPlayer player, ISession iSession) {
        this.player = player;
        this.iSession = iSession;
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }

    public ISession getiSession() {
        return iSession;
    }
}