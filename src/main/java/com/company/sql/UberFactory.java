package com.company.sql;

public class UberFactory {

    private static final UberFactory INSTANCE = new UberFactory();

    public static UberFactory instance(){
        return INSTANCE;
    }

    private UberFactory() {
    }
}
