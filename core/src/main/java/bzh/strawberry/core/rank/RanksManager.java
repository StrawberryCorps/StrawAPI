package bzh.strawberry.core.rank;

import bzh.strawberry.core.StrawCore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
This file RanksManager is part of a project StrawAPI.
It was created on 16/06/2020 at 01:50 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class RanksManager {

    private List<Rank> ranks;

    /**
     * In case you wanna specify ranks in this method
     */

    public RanksManager() {
        this.ranks = new ArrayList<>();
        try {
            Connection connection = StrawCore.CORE.getDataFactory().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ranks");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ranks.add(new Rank(resultSet.getInt("id"),
                        resultSet.getInt("power"),
                        resultSet.getString("name"),
                        resultSet.getString("tab"),
                        resultSet.getString("chat"),
                        resultSet.getString("server")));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public Rank getRank(int id) {
        return this.ranks.stream().filter(rank -> rank.getId() == id).findFirst().orElse(null);
    }
}