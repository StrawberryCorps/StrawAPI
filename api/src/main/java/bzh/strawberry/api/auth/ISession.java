package bzh.strawberry.api.auth;

import java.sql.Timestamp;
import java.util.UUID;

public interface ISession {

    int getStraw_id();
    UUID getUuid();
    String getPassword();
    String getLastIP();
    boolean isPremium();
    Timestamp getFirstLogin();
    Timestamp getStart();
    int getVersion();
    String getUsername();
    boolean isLogged();

}
