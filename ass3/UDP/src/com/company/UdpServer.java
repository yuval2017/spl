package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UdpServer implements Runnable {
    //socker server same as client.
    private DatagramSocket sock;
    private int port;

    public UdpServer (int _port){
        port = _port;
    }
    public void run(){
        try {
            // bind connection to the port
            sock = new DatagramSocket(port);
            byte [] buf = new byte[1<<16];
            byte[] ansBuf = "done".getBytes(StandardCharsets.UTF_8);

            while(true){
                //create a buf that hold the message from the client
                DatagramPacket packet = new DatagramPacket(buf,buf.length);
                //block method until client sent and whe its unblock start to fulfil the buffer
                sock.receive(packet);
                //from bytes to string
                String data = new String(packet.getData(),0,packet.getLength(),StandardCharsets.UTF_8);
                System.out.println(data);
                //get client address
                InetAddress clientAddress = packet.getAddress();
                //gt the client port
                int clientPort = packet.getPort();
                packet = new DatagramPacket(ansBuf,ansBuf.length,clientAddress,clientPort);
                //send the answer its a blocking method
                sock.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
