package io.stiven.sda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityRowMapper {

    public static City mapToOb(ResultSet rs) throws SQLException {
            return new City(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("country"));
    }

    public static List<City> mapToList(ResultSet rs) throws SQLException {

        List<City> cities = new ArrayList<>();
        while (rs.next()) {
            cities.add(mapToOb(rs));
        }

        return cities;

    }
}
