package com.example.chequers;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {
    BufferedReader in;
    PrintWriter out;
    Socket socket;
    String server_address;
    String user_nick;
    String invited_by;

    public Client (String user_nick_in) throws IOException {
        String address = getServerAddress();
        if (address.equals("local")){
            InetAddress ip = InetAddress.getLocalHost();
            server_address = ip.getHostAddress();
        }
        else{
            server_address = address;
        }

        user_nick = user_nick_in;

        socket = new Socket (server_address, 9001);

        in = new BufferedReader (new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter (socket.getOutputStream(), true);

        out.println ("logging_in");
        String line = in.readLine();
        if (line != null && line.startsWith("who")) out.println (user_nick);
    }

    private String getServerAddress() {
        return JOptionPane.showInputDialog(
                null,
                "Wprowadź ip lub wpisz 'local'",
                "Szukanie rozgrywki",
                JOptionPane.QUESTION_MESSAGE);
    }

    private int getAnswer(String invited_by){
        return JOptionPane.showConfirmDialog(
                null,
                "Przyjmujesz zaproszenie od " + invited_by + "?",
                "Zaproszenie",
                JOptionPane.YES_NO_CANCEL_OPTION);
    }

    public boolean sendInvite (String nick) throws IOException {
        socket = new Socket (server_address, 9001);
        in = new BufferedReader (new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter (socket.getOutputStream(), true);

        out.println ("send_invite");
        String line = in.readLine();
        if (line != null && line.startsWith("from_who")) out.println (user_nick);

        line = in.readLine();
        if (line != null && line.startsWith("to_whom")) out.println (nick);

        line = in.readLine();
        if (line != null){
            if (line.startsWith("not_logged")) return false;
            else return line.startsWith("invite_sent");
        }
        return false;
    }

    public boolean checkForInvites () throws IOException {
        socket = new Socket (server_address, 9001);
        in = new BufferedReader (new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter (socket.getOutputStream(), true);

        out.println ("check_invite");
        String line = in.readLine();
        if (line != null && line.startsWith("who")) out.println (user_nick);

        line = in.readLine();
        if (line != null){
            if (line.startsWith("no_invite")) return false;
            else if (line.startsWith("invite")) {
                line = in.readLine();
                invited_by = line;
                return true;
            }
        }
        return false;
    }

    public int answerInvite(String invited_by) throws IOException {
        int answer = getAnswer(invited_by);
        //0 - yes, 1 - no, 2 - cancel
        if (answer == 0 || answer == 1) {
            socket = new Socket(server_address, 9001);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("answer");

            String line = in.readLine();
            if (line != null && line.startsWith("from")) out.println(invited_by);

            line = in.readLine();
            if (line != null && line.startsWith("to")) out.println(user_nick);

            line = in.readLine();
            if (line != null && line.startsWith("accepted")) out.println(answer);
        }
        return answer;
    }

    public String checkForInvites2() throws IOException {
        socket = new Socket (server_address, 9001);
        in = new BufferedReader (new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter (socket.getOutputStream(), true);

        out.println ("2check_invite");
        String line = in.readLine();
        if (line != null && line.startsWith("who")) out.println (user_nick);
        line = in.readLine();
        return line;

    }
}
