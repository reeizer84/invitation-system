package com.example.chequers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerClient {
    private Socket socket_in;
    private Socket socket_out;
    private ServerSocket server;
    private BufferedReader in;
    private PrintWriter out;

    public PeerClient(int server_port, int out_port){
        try {
            server = new ServerSocket(server_port);
            socket_in = server.accept();
            socket_out = new Socket("192.168.0.7", out_port);

            in = new BufferedReader(new InputStreamReader(socket_in.getInputStream()));
            out = new PrintWriter(socket_out.getOutputStream(), true);

            out.println("Pozdro");

            String line;
            while (true) {
                line = in.readLine();
                if (line == null) return;
                else break;
            }
            System.out.println(line);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            try {
                in.close();
                out.close();
                socket_in.close();
                socket_out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
