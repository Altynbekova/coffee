package com.altynbekova.top.practice.repository;

import com.altynbekova.top.practice.entity.Client;
import com.altynbekova.top.practice.util.DbUtil;

import java.sql.*;

public class ClientRepository {
    private static final String INSERT_CLIENT = "insert into clients " +
            "(firstname, lastname, birthdate, tel, address, discount) " +
            "values (?, ?, ?, ?, (select id from staff_positions where name=?))";

    public boolean addClient(Client client) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     INSERT_CLIENT)) {
            statement.setString(1, client.getFirstname());
            statement.setString(2, client.getLastname());
            statement.setDate(3, Date.valueOf(client.getBirthdate()));
            statement.setString(4, client.getTel());
            statement.setString(5, client.getAddress());
            statement.setDouble(6, client.getDiscount());

            return statement.executeUpdate() == 1 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
