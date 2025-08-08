package com.altynbekova.top.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Staff {
    private int id;
    private String firstname;
    private String lastname;
    private String tel;
    private String address;
    private Position position;
}
