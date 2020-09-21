package bzh.strawberry.core.factory;

/*
 * This file SQLiteFactory is part of a project StrawAPI.core.
 * It was created on 07/07/2020 10:03 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */

import bzh.strawberry.api.factory.DataFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteFactory extends DataFactory {

    public static SQLiteFactory instance = null;
    public DataSource dataSource = null;
    private final String url;
    private final String name;
    private final String password;
    private final int minPoolSize;
    private final int maxPoolSize;

    public SQLiteFactory(String url, String name, String password, int minPoolSize, int maxPoolSize) throws SQLException {
        this.url = url;
        this.name = name;
        this.password = password;
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.setupDataSource();
    }

    public static SQLiteFactory getInstance(String url, String name, String password, int minPoolSize, int maxPoolSize) throws SQLException {
        if (SQLiteFactory.instance == null) synchronized (SQLiteFactory.class) {
            if (SQLiteFactory.instance == null) {
                SQLiteFactory.instance = new SQLiteFactory(url, name, password, minPoolSize, maxPoolSize);
            }
        }
        return SQLiteFactory.instance;
    }

    public void setupDataSource() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:" + this.url);
        dataSource.setInitialSize(this.minPoolSize);
        dataSource.setMaxTotal(this.maxPoolSize);
        this.dataSource = dataSource;

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("create table if not exists straw_player(id integer not null constraint straw_player_pk primary key autoincrement, uuid varchar(255) not null,\tlang varchar(255) default 'fr'); create unique index if not exists straw_player_id_uindex on straw_player (id); create unique index if not exists straw_player_uuid_uindex on straw_player (uuid);");
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (connection != null)
                connection.close();
        }
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void shutdownDataSource(DataSource dataSource) throws Exception {
        BasicDataSource basicDataSource = (BasicDataSource) dataSource;
        basicDataSource.close();
    }
}
