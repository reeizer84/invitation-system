package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {
    public static final int PORT = 9001;
    private static HashSet<String> logged_in = new HashSet<>();
    private static HashSet<Invite> pending_invites = new HashSet<>();

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (true) {
                new Handler(listener.accept()).start();
            }
        }
    }

    private static class Handler extends Thread{
        private final Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        public Handler (Socket socket){ this.socket = socket;}

        public void run(){
            try{
                in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
                out = new PrintWriter (socket.getOutputStream(), true);

                String line;
                //sprawdzanie czy przyszla wstepna wiadomosc od uzytkownika

                line = in.readLine();
                if (line == null) return;

                //procedura odpowiedzialna za logowanie
                if (line.startsWith ("logging_in")) {
                    out.println("who");
                    String nick = in.readLine();
                    if (nick == null);
                    else {
                        if (!logged_in.contains(nick)) {
                            logged_in.add(nick);
                            System.out.println("Nowy zalogowany uzytkownik: " + nick);
                        }
                    }
                }

                //zapisywanie zaproszen
                else if (line.startsWith("send_invite")){
                    Invite invite = new Invite();

                    out.println ("from_who");
                    line = in.readLine();

                    if (line == null) return;
                    else{
                        invite.setFrom (line);
                    }

                    out.println ("to_whom");
                    line = in.readLine();
                    if (line == null);
                    else{
                        if (logged_in.contains(line)){
                            invite.setTo (line);
                            if (!pending_invites.contains(invite)) {
                                pending_invites.add(invite);
                                System.out.println ("Zarejestrowano nowe zaproszenie: ");
                                System.out.println (invite.from + " " + invite.to);
                            }
                            out.println ("invite_sent");
                        }
                        else{
                            out.println ("not_logged");
                        }
                    }
                }
                //sprawdzanie przychodzacych zaproszen
                else if (line.startsWith("check_invite")){
                    out.println ("who");
                    line = in.readLine();

                    if (line == null);
                    else{
                        boolean found = false;
                        for (Invite invite: pending_invites) {
                            if (invite.to.equals(line)){
                                out.println("invite");
                                out.println(invite.from);
                                found = true;
                                break;
                            }
                        }

                        if (!found){
                            out.println ("no_invite");
                        }
                        System.out.println ("Uzytkownik sprawdzil czy wyslano do niego zaproszenie");
                    }

                }

                //reakcja na zaproszenie
                else if (line.startsWith("answer")) {
                    String from, to;
                    out.println ("from");
                    line = in.readLine();
                    if (line == null) return;
                    else from = line;

                    out.println ("to");
                    line = in.readLine();
                    if (line == null) return;
                    else to = line;

                    Invite invite = null;
                    boolean found = false;
                    for (Invite invite1: pending_invites){
                        if (invite1.from.equals(from) && invite1.to.equals(to)){
                            invite = invite1;
                            found = true;
                            break;
                        }
                    }

                    if (found){
                        out.println ("accepted");
                        line = in.readLine();

                        if (line == null) return;
                        else{
                            int answer = Integer.parseInt(line);
                            if (answer == 0) invite.accepted = true;
                            else invite.accepted = false;
                        }

                        invite.answered = true;
                        System.out.println ("Uzytkownik odpowiedzial na zaproszenie: " + invite.accepted + " (odpowiedz)");
                        System.out.println ("Zaakceptowano?: " + invite.accepted);
                    }
                }

                else if (line.startsWith("2check_invite")){
                    out.println ("who");
                    line = in.readLine();

                    if (line == null);
                    else {
                        for (Invite invite : pending_invites) {
                            if (invite.from.equals(line)) {
                                if (invite.answered){
                                    if (invite.accepted) out.println ("accepted");
                                    else out.println ("not accepted");
                                    pending_invites.remove (invite);
                                }
                                else out.println ("not answered");
                                break;
                            }
                        }
                        System.out.println("Uzytkownik sprawdzil czy odpowiedziano na zaproszenie");
                    }
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally{
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

