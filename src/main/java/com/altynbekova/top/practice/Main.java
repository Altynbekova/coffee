package com.altynbekova.top.practice;

import com.altynbekova.top.practice.entity.AssortmentPosition;
import com.altynbekova.top.practice.entity.Type;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5433/coffee_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";
    private static final String INSERT_ASSORTMENT =
            "insert into assortment (ru_name, eng_name, price, type_id) " +
                    "VALUES (?, ?, ?, (select id from types where name = ?))";

    public static void main(String[] args) {
        addAssortmentPosition(new AssortmentPosition("ru name", "eng name", 200.50, Type.DESSERT));
    }

    private static void addAssortmentPosition(AssortmentPosition assortmentPosition) {

        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
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