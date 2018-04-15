package com.company;

import com.company.Connection.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server(8180);
        server.startServer();
    }
}
