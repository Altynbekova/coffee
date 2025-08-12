package com.altynbekova.top.practice.repository;

import com.altynbekova.top.practice.entity.AssortmentPosition;
import com.altynbekova.top.practice.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AssortmentRepository implements BaseRepository<AssortmentPosition, Integer>{
    private static final String INSERT_ASSORTMENT =
            "insert into assortment (ru_name, eng_name, price, type_id) " +
                    "VALUES (?, ?, ?, (select id from types where name = ?))";
    private static final String UPDATE_PRICE_BY_NAME = "update assortment set price=? where ru_name=? or eng_name=?";

    public void addAssortmentPosition(AssortmentPosition assortmentPosition) {
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
            e.printStackTrace();
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
