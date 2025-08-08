package com.altynbekova.top.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Client {
    private int id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String tel;
    private String address;
    private int discount;
}
