package bzh.strawberry.api.factory;

import javax.sql.DataSource;

/*
 * This file DataFactory is part of a project StrawAPI.api.
 * It was created on 06/07/2020 19:00 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public abstract class DataFactory {
    public abstract DataSource getDataSource();
}