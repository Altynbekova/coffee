package com.altynbekova.top.practice.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
/*@Setter
@Getter
@ToString
@EqualsAndHashCode*/
@Data
public class AssortmentPosition {
    private String ruName;
    private String engName;
    private double price;
    private Type type;
}
