package com.altynbekova.top.practice.repository;

import com.altynbekova.top.practice.entity.Staff;
import com.altynbekova.top.practice.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StaffRepository {
    private static final String INSERT_STAFF_MEMBER = "insert into staff " +
            "(firstname, lastname, tel, address, position_id) " +
            "values (?, ?, ?, ?, (select id from staff_positions where name=?))";

    public boolean addStaffMember(Staff staff) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     INSERT_STAFF_MEMBER)) {

            statement.setString(1, staff.getFirstname());
            statement.setString(2, staff.getLastname());
            statement.setString(3, staff.getTel());
            statement.setString(4, staff.getAddress());
            statement.setString(5, staff.getPosition().name());

            return statement.executeUpdate() == 1 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
