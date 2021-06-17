package com.company;

import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame() {
        // Window setup
        this.add(new GamePanel());
        // Name of window
        this.setTitle("Wunsz rzeczny jest niebezpieczny");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        // Packs the window based on preferred size of components
        this.pack();
        this.setVisible(true);
        // We center it on the screen
        this.setLocationRelativeTo(null);
    }
}
