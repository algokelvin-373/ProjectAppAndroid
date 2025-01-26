package com.sbi.mvicalllibrary.icallservices.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkHandler {
    private DatagramSocket socket;
    private InetAddress remoteAddress;
    private int remotePort;

    public NetworkHandler(String address, int port) throws Exception {
        socket = new DatagramSocket();
        remoteAddress = InetAddress.getByName(address);
        remotePort = port;
    }

    public void sendAudioData(byte[] audioData) {
        try {
            DatagramPacket packet = new DatagramPacket(audioData, audioData.length, remoteAddress, remotePort);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (socket != null) {
            socket.close();
        }
    }
}
