package io.stiven.sda;

import java.sql.Connection;
import java.sql.SQLException;

public class DDLRepository {

    private final Connection conn;

    public DDLRepository(Connection conn) {
        this.conn = conn;
    }

    public boolean createTableCityIfNotExists() throws SQLException {
        return conn.createStatement()
                .execute(DDL_CITY_SQL);
    }

    private final String DDL_CITY_SQL = "CREATE TABLE IF NOT EXISTS city(" +
            "id int(11) NOT NULL AUTO_INCREMENT," +
            "name varchar(45) NOT NULL," +
            "country varchar(45) NOT NULL," +
            "PRIMARY KEY (`id`))";
}
