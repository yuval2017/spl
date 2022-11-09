package com.company;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UdpClient {
    public static void send (String host, int port,String msg){
        byte[] buf = msg.getBytes(StandardCharsets.UTF_8);
        byte [] ansBuf = new byte[(1<<16)];
        try {
            //the ip of the host
            InetAddress address = InetAddress.getByName(host);
            //choose the port automaticly
            DatagramSocket socket = new DatagramSocket();

            DatagramPacket packet = new DatagramPacket(buf,buf.length,address,port);
            socket.send(packet);
            //create and buf
            packet = new DatagramPacket(ansBuf,ansBuf.length);
            // wit fot the reccive of the ans buf
            socket.receive(packet);
            String ans = new String(packet.getData(),0,packet.getLength(),StandardCharsets.UTF_8);
            System.out.println(ans);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
