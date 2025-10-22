package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUILogin implements ActionListener {
    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel pswdLabel;
    private static JPasswordField pswdText;
    private static JButton button;
    private static JLabel success;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        userLabel = new JLabel("User: ");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        pswdLabel = new JLabel("Password: ");
        pswdLabel.setBounds(10, 50, 80, 25);
        panel.add(pswdLabel);

        pswdText = new JPasswordField(20);
        pswdText.setBounds(100, 50, 165, 25);
        panel.add(pswdText);

        button = new JButton("Login");
        button.setBounds(10, 80, 80, 25);
        button.addActionListener(new GUILogin());
        panel.add(button);

        success = new JLabel("");
        success.setBounds(10, 110, 800, 25);
        panel.add(success);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String pswd = pswdText.getText();
        System.out.println(user + ", " + pswd );

        attemptLogin(user, pswd);
    }

    public void attemptLogin(String user, String pswd) {
        if (user.equals("AaDiThEnOoB")) {
            if (pswd.equals("Aadi12ka456#")) {
                success.setText("Successfully Logged in! Welcome back " + user + "!");
            } else {
                success.setText("Wrong Password. Please try again.");
            }
        } else {
            success.setText("Invalid Username. Please try again.");
        }
    }
}
