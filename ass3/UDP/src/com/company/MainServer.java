package com.company;

public class MainServer {
    public static void main(String[] args){
        new UdpServer(7777).run();
    }
}
