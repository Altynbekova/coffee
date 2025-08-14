package com.altynbekova.top.practice.repository;

import com.altynbekova.top.practice.entity.AssortmentPosition;
import com.altynbekova.top.practice.entity.Type;
import com.altynbekova.top.practice.exception.RepositoryException;
import com.altynbekova.top.practice.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssortmentRepository implements BaseRepository<AssortmentPosition, Integer> {
    private static final String INSERT_ASSORTMENT =
            "insert into assortment (ru_name, eng_name, price, type_id) " +
                    "VALUES (?, ?, ?, (select id from types where name = ?))";
    private static final String UPDATE_PRICE_BY_NAME = "update assortment set price=? where ru_name=? or eng_name=?";
    private static final String SELECT_MIN_PRICE_BY_TYPE =
            "select min(price) from assortment where type_id=(select id from types where name=?)";
    private static final String SELECT_AVERAGE_PRICE_BY_TYPE =
            "select avg(price) from assortment where type_id=(select id from types where name=?)";
    private static final String SELECT_CHEAPEST_BY_TYPE =
            "select ru_name, eng_name, price from assortment " +
                    "join types on type_id = types.id " +
                    "where types.name = '?' " +
                    "and price = (select min(price) from assortment where type_id=(select id from types where name=?))";


    public AssortmentPosition findCheapest(Type assortmentPositionType){
        AssortmentPosition assortmentPosition = new AssortmentPosition();
        try (
                Connection connection = DbUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_CHEAPEST_BY_TYPE)
        ) {
            statement.setString(1, assortmentPositionType.name());
            statement.setString(2, assortmentPositionType.name());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                assortmentPosition.setRuName(resultSet.getString("ru_name"));
                assortmentPosition.setEngName(resultSet.getString("eng_name"));
                assortmentPosition.setPrice(resultSet.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assortmentPosition;
    }

    public double minPrice(Type assortmentPositionType) {
        try (
                Connection connection = DbUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_MIN_PRICE_BY_TYPE)
        ) {
            statement.setString(1, assortmentPositionType.name());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(assortmentPositionType + " not found");
    }

    public double averagePrice(Type assortmentPositionType) {
        try (
                Connection connection = DbUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_AVERAGE_PRICE_BY_TYPE)
        ) {
            statement.setString(1, assortmentPositionType.name());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(assortmentPositionType + " not found");
    }

    public void addAssortmentPosition(AssortmentPosition assortmentPosition) throws RepositoryException {
        try (
                Connection connection = DbUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_ASSORTMENT)
        ) {
            statement.setString(1, assortmentPosition.getRuName());
            statement.setString(2, assortmentPosition.getEngName());
            statement.setDouble(3, assortmentPosition.getPrice());
            statement.setString(4, assortmentPosition.getType().name());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(
                    "Не удалось добавить новую позицию ассортимента",
                    e);
        }
    }

    public void updatePrice(String name, double newPrice) {
        try (
                Connection connection = DbUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_PRICE_BY_NAME)
        ) {
            statement.setDouble(1, newPrice);
            statement.setString(2, name);
            statement.setString(3, name);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void save(AssortmentPosition entity) {

    }

    @Override
    public AssortmentPosition find(Integer integer) {
        return null;
    }

    @Override
    public void update(AssortmentPosition entity) {

    }

    @Override
    public void delete(Integer integer) {

    }
}
