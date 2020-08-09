package bzh.strawberry.core.factory;

/*
 * This file MySQLFactory is part of a project StrawAPI.core.
 * It was created on 07/07/2020 10:03 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */

import bzh.strawberry.api.factory.DataFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLFactory extends DataFactory {

    public static MySQLFactory instance = null;
    public DataSource dataSource = null;
    private final String url;
    private final String name;
    private final String password;
    private final int minPoolSize;
    private final int maxPoolSize;

    public MySQLFactory(String url, String name, String password, int minPoolSize, int maxPoolSize) throws SQLException {
        this.url = url;
        this.name = name;
        this.password = password;
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.setupDataSource();
    }

    public static MySQLFactory getInstance(String url, String name, String password, int minPoolSize, int maxPoolSize) throws SQLException {
        if (MySQLFactory.instance == null) synchronized (MySQLFactory.class) {
            if (MySQLFactory.instance == null) {
                MySQLFactory.instance = new MySQLFactory(url, name, password, minPoolSize, maxPoolSize);
            }
        }
        return MySQLFactory.instance;
    }

    public void setupDataSource() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.name);
        dataSource.setPassword(this.password);
        dataSource.setInitialSize(this.minPoolSize);
        dataSource.setMaxTotal(this.maxPoolSize);
        this.dataSource = dataSource;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
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
