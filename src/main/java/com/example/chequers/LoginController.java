package com.example.chequers;

import Connectivity.ConnectionClass;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.scene.control.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    public TextField username;
    public PasswordField password;
    public PasswordField password_conf;
    public Button login;
    public Label conn_info;
    public Button toggle;
    public Label toggle_label;
    public boolean during_login = true;
    public boolean logged_in = false;

    public String nick;

    public void switchLogin (){
        if (during_login){
            password_conf.setVisible (true);
            login.setText ("Zarejestruj się");
            toggle.setText ("Logowanie");
            during_login = false;
        }
        else{
            password_conf.setVisible (false);
            login.setText ("Zaloguj się");
            toggle.setText ("Rejestracja");
            during_login = true;
        }
        username.clear();
        password.clear();
        password_conf.clear();
    }

    private void showInfo() {
        JOptionPane.showMessageDialog(
                null,
                "Brak połączenia z bazą danych!",
                "Błąd!", 1);
    }

    public void login () throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Statement statement = null;
        boolean exception = false;
        try{
            Connection connection = connectionClass.getConnection();
            statement = connection.createStatement();
        }
        catch (CommunicationsException e){
            showInfo();
            exception = true;
        }

        if (!exception){
            if (during_login) {
                String sql = "SELECT * FROM authorization WHERE username = '" + username.getText() + "' AND password = '" + password.getText() + "';";
                ResultSet resultSet = statement.executeQuery(sql);

                if (resultSet.next()) {
                    conn_info.setText("Zalogowano!");
                    logged_in = true;
                    nick = username.getText();
                } else {
                    conn_info.setText("Błędne dane logowania!");
                }
            }
            else{
                String sql = "SELECT * FROM authorization WHERE username = '" + username.getText() + "';";
                ResultSet resultSet = statement.executeQuery (sql);

                if (resultSet.next()) {
                    conn_info.setText("Taki użytkownik już istnieje!");
                } else {
                    if (password.getText().equals (password_conf.getText())) {
                        sql = "INSERT INTO authorization (username, password) VALUES ('" + username.getText() + "','" + password.getText() + "');";
                        statement.executeUpdate(sql);
                        conn_info.setText("Utworzono konto!");
                        during_login = false;
                        switchLogin();
                    }
                    else {
                        System.out.println (password.getText());
                        System.out.println (password_conf.getText());
                        conn_info.setText("Podane hasła się różnią!");
                    }
                }
            }
        }
    }
}
