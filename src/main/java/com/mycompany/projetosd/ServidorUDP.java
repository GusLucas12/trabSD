/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetosd;

import java.io.*;
import java.net.*;
import java.util.Random;

/**
 *
 * @author backu
 */
public class ServidorUDP {

    public static void main(String[] args) {
        DatagramSocket socket = null; // Porta do servidor
        try {
            socket = new DatagramSocket(12345);
            System.out.println("Aguardando conexão do player... ");
            byte[] buffer = new byte[1024];

            DatagramPacket playerRequest = new DatagramPacket(buffer, buffer.length);
            socket.receive(playerRequest);
            String playerAddress = playerRequest.getAddress().getHostAddress();
            int player1Port = playerRequest.getPort();
            System.out.println("Jogador 1 conectado: " + playerAddress + ":" + player1Port);

            jogo(playerAddress,player1Port,socket,playerRequest);
  
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean validandoMovimentos(String move) {
        String[] movimentosValidos = {"PEDRA", "PAPEL", "TESOURA"};
        for (String item : movimentosValidos) {
            if (item.equals(move)) {
                return true;
            }
        }
        return false;
    }

    private static String playerMovement(DatagramSocket socket,int port,String address, DatagramPacket packet) {
        String move = "oi";
        byte[] buffer = new byte[1024];
        try {

            while (true) {
                sendMessage(socket,address, port, "Faça um movimento válido ");
                packet.setData(buffer);
                socket.receive(packet);
                move = new String(packet.getData(), 0, packet.getLength());
                move = move.toUpperCase();
                System.out.println(move);
                if (validandoMovimentos(move)) {
                    return move;

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return move;
    }

    private static String botMovement(String[] movimentos) {
        String move;
        Random random = new Random();
        int randomize = random.nextInt(3);

        move = movimentos[randomize];
        return move;
    }
     private static void sendMessage(DatagramSocket socket,String address, int port, String message) throws IOException {
 
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(address), port);
        socket.send(packet);
    }
    public static void jogo(String address,int port,DatagramSocket socket,DatagramPacket packet) {
        try {
           
            String[] movimentosValidos = {"PEDRA", "PAPEL", "TESOURA"};
            String move1;
            String move2;

            move1 = playerMovement(socket,port,address,packet);
            move2 = botMovement(movimentosValidos);

            String moves = (move1 + " X " + move2);
            sendMessage(socket, address, port, moves);

            if ((move1.equals("PEDRA") && move2.equals("TESOURA"))
                    || (move1.equals("TESOURA") && move2.equals("PAPEL"))
                    || (move1.equals("PAPEL") && move2.equals("PEDRA"))) {

                     sendMessage(socket, address, port, "PLAYER1 WINS");

            } else if ((move2.equals("PEDRA") && move1.equals("TESOURA"))
                    || (move2.equals("TESOURA") && move1.equals("PAPEL"))
                    || (move2.equals("PAPEL") && move1.equals("PEDRA"))) {
                 sendMessage(socket, address, port, "PLAYER2 WINS");

            } else {
                  sendMessage(socket, address, port, "DRAWN");
            }
              sendMessage(socket, address, port, "FIM");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
