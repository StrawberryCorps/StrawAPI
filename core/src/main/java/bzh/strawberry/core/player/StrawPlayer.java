package bzh.strawberry.core.player;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.api.player.IStrawPlayer;
import bzh.strawberry.core.StrawCore;
import bzh.strawberry.core.callback.Callback;
import bzh.strawberry.core.net.StrawScoreboard;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/*
 * This file StrawPlayer is part of a project StrawAPI.core.
 * It was created on 21/09/2020 21:11 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */

public class StrawPlayer implements IStrawPlayer {

    private int strawId;
    private final Player player;

    private StrawScoreboard strawScoreboard;

    public StrawPlayer(Player player) {
        this.player = player;
    }

    public void load(Callback callback) {
        StrawCore.CORE.getServer().getScheduler().runTaskAsynchronously(StrawCore.CORE, () -> {
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

    public Player getPlayer() {
        return this.player;
    }

    public UUID getUniqueID() {
        return this.player.getUniqueId();
    }

    @Override
    public StrawScoreboard getScoreboard() throws Exception {
        if (this.strawScoreboard == null)
            throw new Exception("");
        return this.strawScoreboard;
    }

    public void createScoreBoard(String objectiveName) {
        if (this.strawScoreboard == null)
            this.strawScoreboard = new StrawScoreboard(this.player, objectiveName);
        this.strawScoreboard.create();
    }
}