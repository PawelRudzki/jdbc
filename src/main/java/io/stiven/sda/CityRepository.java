package io.stiven.sda;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CityRepository {

    private final Connection conn;

    private final String FIND_BY_ID_SQL = "SELECT * FROM city WHERE id=?";
    private final String FIND_ALL_SQL = "SELECT * FROM city";
    private final String SAVE_SQL = "INSERT INTO city (name, country) VALUES (?, ?)";
    private final String DELETE_SQL = "DELETE FROM city WHERE id=?";
    private final String UPDATE_BY_ID_SQL = "UPDATE city SET name=?, country=? WHERE id=?";

    public CityRepository(Connection conn) {
        this.conn = conn;
    }

    public int count() throws SQLException {
        CallableStatement callStat = conn.prepareCall( "{call count_city(?) } ");
        callStat.registerOutParameter("result", Types.INTEGER);
        //result to parametr out w naszej procedurze MySQL

        callStat.execute();

        return callStat.getInt(1);
    }

    public void update(City obj) throws SQLException {
        PreparedStatement stat = conn.prepareStatement(UPDATE_BY_ID_SQL);
        stat.setString(1, obj.getName());
        stat.setString(2, obj.getCountry());
        stat.setInt(3, obj.getId());

        stat.executeUpdate();
    }

    public Optional<City> findById(int id) throws SQLException {

        PreparedStatement cityStat = conn.prepareStatement(FIND_BY_ID_SQL);
        cityStat.setInt(1, id); //parameterIndex oznacza numer parametru w WHERE

        ResultSet rs = cityStat.executeQuery();

        if (rs.next()) {
            return Optional.ofNullable(CityRowMapper.mapToOb(rs));
        }
        return Optional.empty();
    }

    public List<City> findAll() throws SQLException {

        return CityRowMapper.mapToList(conn.prepareStatement(FIND_ALL_SQL).executeQuery());
    }

    public City save(City obj) throws SQLException {
        PreparedStatement cityStat = conn.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
        cityStat.setString(1, obj.getName());
        cityStat.setString(2, obj.getCountry());

        int affectedRows = cityStat.executeUpdate();
        if (affectedRows == 1) {
            ResultSet rs = cityStat.getGeneratedKeys();
            if (rs.next()) {
                obj.setId(rs.getInt(1));
                return obj;
            }
        }

        throw new SQLException("Cannot perform save.");
    }

    public void save(List<City> cities) throws SQLException {
        try {
            ConnectionUtils.setAutoCommit(conn, false);

            for (int i = 0; i < cities.size(); i++) {
                save(cities.get(i));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionUtils.rollback(conn);
        } finally {
            ConnectionUtils.setAutoCommit(conn, true);
        }
    }

    public boolean deleteById(int id) throws SQLException {
        PreparedStatement stat = conn.prepareStatement(DELETE_SQL);
        stat.setInt(1, id);

        int affectedRows = stat.executeUpdate();
        if (affectedRows == 1) {
            return true;
        } else {
            return false;
        }
    }
}
