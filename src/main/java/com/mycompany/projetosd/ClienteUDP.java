/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetosd;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;
/**
 *
 * @author backu
 */
public class ClienteUDP {
    public static void main(String[] args)  throws IOException {
        Scanner scanner = new Scanner(System.in);
        String serverIP = "localhost"; // Endereço IP do servidor
        int serverPort = 12345; // Porta do servidor

        DatagramSocket socket = new DatagramSocket();

        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(serverIP), serverPort);

        System.out.println("Conectando ao servidor...");

        socket.send(packet);

        DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);

        socket.receive(responsePacket);

        String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
        System.out.println(response);

        while (true) {
            
            String choice = scanner.nextLine();

            byte[] choiceBuffer = choice.getBytes();
            packet.setData(choiceBuffer);

            socket.send(packet);

            socket.receive(responsePacket);

            response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println(response);
        }
    }
}
