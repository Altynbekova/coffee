package com.altynbekova.top.practice;

import com.altynbekova.top.practice.entity.AssortmentPosition;
import com.altynbekova.top.practice.entity.Position;
import com.altynbekova.top.practice.entity.Staff;
import com.altynbekova.top.practice.entity.Type;
import com.altynbekova.top.practice.exception.RepositoryException;
import com.altynbekova.top.practice.repository.AssortmentRepository;
import com.altynbekova.top.practice.repository.StaffRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static AssortmentRepository assortmentRepository = new AssortmentRepository();
    private static StaffRepository staffRepository = new StaffRepository();

    public static void main(String[] args) {
        assortmentInsert();
        System.out.println(assortmentRepository.averagePrice(Type.DESSERT));
//        testAssortmentPriceUpdate("eng name 2", 999.99);
    }

    private static void testAssortmentPriceUpdate(String byName, double newPrice) {
        assortmentRepository.updatePrice(byName, newPrice);
    }

    private static void testStaffInsert() {
        staffRepository.addStaffMember(Staff.builder()
                .firstname("John")
                .lastname("Doe")
                .tel("+23473698563")
                .address("Street Building 1")
                .position(Position.Бариста)
                .build());
    }

    private static void assortmentInsert() {
        try {
            assortmentRepository.addAssortmentPosition(
                    new AssortmentPosition("ru name 2", "eng name 2",
                            202.50, Type.DRINK));
        } catch (RepositoryException e) {
            LOGGER.debug("assortmentInsert() failed", e);
        }
    }
}