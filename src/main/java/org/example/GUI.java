package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class GUI implements ActionListener {

    int count = 0;

    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private JLabel label;

    public GUI() {
        frame = new JFrame();

        button = new JButton("Click Me!");
        button.addActionListener(this);

       label = new JLabel("Number of Clicks: 0");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Epic GUI");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        new GUI();

        sc.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Number of Clicks " + count);

        if (count == 7) {
            label.setText("STOP!");
        }
    }
}