package com.codenbugs.sistemacongresos;

import com.codenbugs.sistemacongresos.util.DBConnection;

public class SistemaCongresos {

    public static void main(String[] args) {

        System.out.println("Hello World!");
        DBConnection connection = new DBConnection();
        connection.connect();
    }
}
