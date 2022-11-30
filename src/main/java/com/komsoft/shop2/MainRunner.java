package com.komsoft.shop2;


import com.komsoft.shop2.util.DBProperties;

public class MainRunner {

    public static void main(String[] args) {
        System.out.println("Do nothing...");
        DBProperties prop = new DBProperties("hikari");
        if (prop.isReady()) {
            System.out.println(prop.getDriverName());
            System.out.println(prop.getUrl());
            System.out.println(prop.getUserName());
            System.out.println(prop.getPassword());
        }

    }
}
