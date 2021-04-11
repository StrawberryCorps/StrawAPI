package bzh.strawberry.core.player;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.api.player.IStrawPlayer;
import bzh.strawberry.api.rank.IRank;
import bzh.strawberry.core.StrawCore;
import bzh.strawberry.core.callback.Callback;
import bzh.strawberry.core.net.StrawScoreboard;
import bzh.strawberry.core.rank.Rank;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;

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
    private List<IRank> ranks;

    public StrawPlayer(Player player) {
        this.player = player;
        this.ranks = new ArrayList<>();
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
                    preparedStatement = connection.prepareStatement("INSERT INTO players (`uuid`) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, getUniqueID().toString());
                    preparedStatement.executeUpdate();
                    resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet.next()) {
                        strawId = resultSet.getInt(1);
                    }
                    resultSet.close();
                    preparedStatement.close();
                }
                preparedStatement.close();

                // Chargement des ranks
                preparedStatement = connection.prepareStatement("SELECT * FROM player_ranks WHERE strawid = ?");
                preparedStatement.setInt(1, this.strawId);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    this.ranks.add(StrawCore.CORE.getRanksManager().getRank(resultSet.getInt("rankid")));
                    while (resultSet.next()) {
                        this.ranks.add(StrawCore.CORE.getRanksManager().getRank(resultSet.getInt("rankid")));
                    }
                } else {
                    // Ajout rank par défaut
                    this.ranks.add(StrawCore.CORE.getRanksManager().getRank(1));
                }

                Collections.sort(this.ranks);

                resultSet.close();
                preparedStatement.close();

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                player.kickPlayer("Une erreur est survenue sur vos données !");
            } finally {
                callback.done();
            }
        });
    }

    public void save() {

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

    @Override
    public List<IRank> getRanks() {
        return this.ranks;
    }

    @Override
    public Rank getRank() {
        return (Rank) this.ranks.get(0);
    }
}