package bzh.strawberry.core.bungeecord.player;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.api.player.IStrawProxiedPlayer;
import bzh.strawberry.core.bungeecord.StrawCoreBungee;
import bzh.strawberry.core.callback.Callback;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StrawProxiedPlayer implements IStrawProxiedPlayer {
    private int strawId;
    private final ProxiedPlayer player;

    public StrawProxiedPlayer(ProxiedPlayer player) {
        this.player = player;
    }

    public void load(Callback callback) {
        StrawCoreBungee.CORE.getProxy().getScheduler().runAsync(StrawCoreBungee.CORE, () -> {
            try {
                Connection connection = StrawAPI.getAPI().getDataFactory().getDataSource().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE `uuid` = '" + getUniqueID().toString() + "'");
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    strawId = resultSet.getInt("id");
                    resultSet.close();
                } else {
                    resultSet.close();
                    preparedStatement.close();
                    preparedStatement = connection.prepareStatement("INSERT INTO players (`uuid`) VALUES ('" + getUniqueID().toString() + "')");
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE `uuid`= ?");
                    preparedStatement.setString(1, player.getUniqueId().toString());
                    ResultSet resultSet1 = preparedStatement.executeQuery();
                    if (resultSet1.next()) {
                        strawId = resultSet1.getInt("id");
                    }
                    resultSet1.close();
                }
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                callback.done();
            }
        });
    }

    public int getStrawId() {
        return this.strawId;
    }

    public ProxiedPlayer getPlayer() {
        return this.player;
    }

    public UUID getUniqueID() {
        return this.player.getUniqueId();
    }
}