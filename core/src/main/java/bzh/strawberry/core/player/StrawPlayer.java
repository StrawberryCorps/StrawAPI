package bzh.strawberry.core.player;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.core.StrawCore;
import bzh.strawberry.core.callback.Callback;
import bzh.strawberry.core.l10n.data.Language;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

/*
 * This file StrawPlayer is part of a project StrawAPI.core.
 * It was created on 21/09/2020 21:11 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */

public class StrawPlayer {

    private int strawId;
    private final Player player;
    private Language lang;

    public StrawPlayer(Player player) {
        this.player = player;
    }

    public void load(Callback callback) {
        StrawCore.getInstance().getServer().getScheduler().runTaskAsynchronously(StrawCore.getInstance(), () -> {
            try {
                Connection connection = StrawAPI.getAPI().getDataFactory().getDataSource().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM straw_player WHERE `uuid` = '" + getUniqueID().toString() + "'");
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    strawId = resultSet.getInt("id");
                    lang = StrawCore.getInstance().getL10nManager().getLanguage(resultSet.getString("lang"));
                    resultSet.close();
                } else {
                    resultSet.close();
                    preparedStatement.close();
                    preparedStatement = connection.prepareStatement("INSERT INTO straw_player (`uuid`) VALUES ('" + getUniqueID().toString() + "')");
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    preparedStatement = connection.prepareStatement("SELECT * FROM straw_player WHERE `uuid`= ?");
                    preparedStatement.setString(1, player.getUniqueId().toString());
                    ResultSet resultSet1 = preparedStatement.executeQuery();
                    if (resultSet1.next()) {
                        strawId = resultSet1.getInt("id");
                        lang = StrawCore.getInstance().getL10nManager().getLanguage(resultSet1.getString("lang"));
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

    public Player getPlayer() {
        return this.player;
    }

    public UUID getUniqueID() {
        return this.player.getUniqueId();
    }

    public Language getLang() {
        return this.lang;
    }
}
