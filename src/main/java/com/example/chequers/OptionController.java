package com.example.chequers;

import Connectivity.ConnectionClass;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OptionController {
    public Label nick;
    public TextField opponent_nick;
    public Button invite;
    public Label invite_info;
    public Client client;
    public Button check_received;
    public boolean sent_invite = false;

    public void showField(){
        opponent_nick.setVisible(true);
        invite.setVisible(true);
    }

    public void inviteAction() throws Exception {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM authorization WHERE username = '" + opponent_nick.getText() + "';";
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()){
            if (client.sendInvite(opponent_nick.getText())){
                invite_info.setText ("Wysłano zaproszenie!");
                sent_invite = true;
            }
            else invite_info.setText ("Zaproszony użytkownik nie jest zalogowany!");
        }
        else{
            invite_info.setVisible(true);
            invite_info.setText ("Gracz o takim nicku nie istnieje!");
        }

    }

    public void checkInvites() throws IOException {
        if (client.checkForInvites()){
            int answer = client.answerInvite(client.invited_by);
            if (answer == 0) showInfo ("Trwa dołączanie do gry...");
        }
        else{
            invite_info.setVisible(true);
            invite_info.setText ("Nie dostałeś zaproszenia!");
        }
    }

    public void checkInvites2() throws IOException{
        if (sent_invite){
            String answer = client.checkForInvites2();
            if (answer.equals("accepted")) showInfo("Zaakceptowano zaproszenie! Trwa dołączanie do gry...");
            else if (answer.equals("not accepted")){
                sent_invite = false;
                showInfo("Odrzucono zaproszenie:(");
            }
            else if (answer.equals("not answered")) showInfo("Jeszczcze nie odpowiedzano na zaproszenie");
        }
        else{
            invite_info.setText ("Nie wysłałeś zaproszenia!");
        }
    }

    private void showInfo(String option) {
        JOptionPane.showMessageDialog(
                null,
                option);
    }
}
