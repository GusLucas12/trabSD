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
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int serverPort = 12345; // Porta do servidor

        Socket socket = new Socket("192.168.56.1", serverPort);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String result = "oi";
        while (true) {
            System.out.println(in.readLine());
            String movimento = scanner.nextLine();
            out.println(movimento);

            result = in.readLine();

        }
    }
}
