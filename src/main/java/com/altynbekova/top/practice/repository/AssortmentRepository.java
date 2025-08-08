package com.altynbekova.top.practice.repository;

import com.altynbekova.top.practice.entity.AssortmentPosition;
import com.altynbekova.top.practice.util.DbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.altynbekova.top.practice.util.DbUtil.URL;
import static com.altynbekova.top.practice.util.DbUtil.USERNAME;

public class AssortmentRepository {
    private static final String INSERT_ASSORTMENT =
            "insert into assortment (ru_name, eng_name, price, type_id) " +
                    "VALUES (?, ?, ?, (select id from types where name = ?))";

    public void addAssortmentPosition(AssortmentPosition assortmentPosition) {
        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, DbUtil.PASSWORD);
                PreparedStatement statement = connection.prepareStatement(INSERT_ASSORTMENT)
        ) {
            statement.setString(1, assortmentPosition.getRuName());
            statement.setString(2, assortmentPosition.getEngName());
            statement.setDouble(3, assortmentPosition.getPrice());
            statement.setString(4, assortmentPosition.getType().name());

            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
