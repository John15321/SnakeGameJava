package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    // kod z dubbingiem
    static final int SZEROKUTKI = 500; // w
    static final int WYSOKUTKI = 500; // h
    static final int JEDNOSC = 25; // w sensie ze unit
    static final int GRA_SZEROKOSC = (SZEROKUTKI * WYSOKUTKI) / JEDNOSC;
    static final int ZYGZAK_MCQEEN = 75; // I AM SPEED https://bit.ly/3gwHa22
    final int x[] = new int[GRA_SZEROKOSC];
    final int y[] = new int[GRA_SZEROKOSC];
    int cialo = 4;
    int zezarteJapka;
    int japkoX;
    int japkoY;
    char AiR = 'R'; // kierunek
    boolean zasuwa = false;
    Timer wunszowyCzasomierz;
    Random rngAleNieTakiDobryJakPanski;


    GamePanel() {
        rngAleNieTakiDobryJakPanski = new Random();
        this.setPreferredSize(new Dimension(SZEROKUTKI, WYSOKUTKI));
        this.setBackground(Color.magenta);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        zasuwa = true;
        wunszowyCzasomierz = new Timer(ZYGZAK_MCQEEN, this);
        wunszowyCzasomierz.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
//        for (int i = 0; i < WYSOKUTKI / JEDNOSC; i++) {
//            g.drawLine(i * JEDNOSC, 0, i * JEDNOSC, WYSOKUTKI);
//            g.drawLine(0, i * JEDNOSC, SZEROKUTKI, i * JEDNOSC);
//        }
        g.setColor(Color.cyan); // Cyjan, bo wszyscy chcemy teraz zażyć
        g.fillOval(japkoX, japkoY, JEDNOSC, JEDNOSC);

        for (int i = 0; i < cialo; i++) {
            if (i == 0) {
                g.setColor(Color.black);
                g.fillRect(x[i], y[i], JEDNOSC, JEDNOSC);
            }else{
                g.s
            }
        }
    }

    public void newApple() {
        japkoX = rngAleNieTakiDobryJakPanski.nextInt((int) (SZEROKUTKI / JEDNOSC)) * JEDNOSC;
        japkoY = rngAleNieTakiDobryJakPanski.nextInt((int) (WYSOKUTKI / JEDNOSC)) * JEDNOSC;
    }

    public void move() {
        for (int i = cialo; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (AiR) {
            case 'U':
                y[0] = y[0] - JEDNOSC;
                break;
            case 'D':
                y[0] = y[0] + JEDNOSC;
                break;
            case 'L':
                x[0] = x[0] - JEDNOSC;
                break;
            case 'R':
                x[0] = x[0] + JEDNOSC;
                break;
        }
    }

    public void checkApple() {

    }

    public void checkCollisions() {

    }

    public void GameOver(Graphics g) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
//
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        }
    }
}
