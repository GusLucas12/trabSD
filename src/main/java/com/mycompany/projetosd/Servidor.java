/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetosd;

/**
 *
 * @author backu
 */
import java.io.*;
import java.net.*;
import java.util.Random;

public class Servidor {

    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(12345);
            InetAddress servidorAddress = InetAddress.getLocalHost();
            System.out.println("Endereço IP do servidor: " + servidorAddress.getHostAddress());
            System.out.println("Aguardando o Jogador...");

            Socket playerSocket = serverSocket.accept();

            System.out.println("Player conectado.");
          

            jogo(playerSocket);
            System.out.println("Fim da conexão");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean validandoMovimentos(String move) {
        String[] movimentosValidos = { "PEDRA", "PAPEL", "TESOURA" };
        for (String item : movimentosValidos) {
            if (item.equals(move)) {
                return true;
            }
        }
        return false;
    }

    private static String playerMovement(PrintWriter playerOut, BufferedReader playerIn) {
        String move = "oi";
        try {

            while (true) {
                playerOut.println("Digite um movimento válido");
                move = playerIn.readLine().toUpperCase();

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

    public static void jogo(Socket playerSocket) {
        try {
            PrintWriter playerOut = new PrintWriter(playerSocket.getOutputStream(), true);
            BufferedReader playerIn = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            String[] movimentosValidos = { "PEDRA", "PAPEL", "TESOURA" };
            String move1;
            String move2;

            move1 = playerMovement(playerOut, playerIn);
            move2 = botMovement(movimentosValidos);

            playerOut.println(move1 + " X " + move2);

            if ((move1.equals("PEDRA") && move2.equals("TESOURA"))
                    || (move1.equals("TESOURA") && move2.equals("PAPEL"))
                    || (move1.equals("PAPEL") && move2.equals("PEDRA"))) {

                playerOut.println("PLAYER1 WINS");

            } else if ((move2.equals("PEDRA") && move1.equals("TESOURA"))
                    || (move2.equals("TESOURA") && move1.equals("PAPEL"))
                    || (move2.equals("PAPEL") && move1.equals("PEDRA"))) {
                playerOut.println("PLAYER2 WINS");

            } else {
                playerOut.println("DRAWN");
            }
             playerOut.println("FIM");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
