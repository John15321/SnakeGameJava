package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


/*
    S    N    E    K
             ____
            / . .\   BLEP
            \  ---<         BLEP
             \  /  BLEP
   __________/ /            BLEP
-=:___________/
 */

public class GamePanel extends JPanel implements ActionListener {
    // kod z dubbingiem
    static final int SZEROKUTKI = 500; // w
    static final int WYSOKUTKI = 500; // h
    static final int JEDNOSC = 25; // w sensie ze unit
    static final int GRA_SZEROKOSC = (SZEROKUTKI * WYSOKUTKI) / JEDNOSC;
    static final int ZYGZAK_MCQEEN = 75; // I AM SPEED https://bit.ly/3gwHa22
    final int x[] = new int[GRA_SZEROKOSC];
    final int y[] = new int[GRA_SZEROKOSC];
    int cialo = 6;
    int zezarteJapka;
    int japkoX;
    int japkoY;
    char AiR = 'R'; // kierunek
    static boolean zasuwa = false;
    Timer wunszowyCzasomierz;
    Random rngAleNieTakiDobryJakPanski; // <3


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
        if (zasuwa) {
            g.setColor(Color.cyan); // Cyjan, bo wszyscy chcemy teraz zażyć
            g.fillOval(japkoX, japkoY, JEDNOSC, JEDNOSC);


            for (int i = 0; i < cialo; i++) {
                if (i == 0) {
                    g.setColor(Color.black);
                    g.fillRect(x[i], y[i], JEDNOSC, JEDNOSC);
                } else {
                    //g.setColor(Color.darkGray);
                    g.setColor(new Color(rngAleNieTakiDobryJakPanski.nextInt(255), rngAleNieTakiDobryJakPanski.nextInt(255), rngAleNieTakiDobryJakPanski.nextInt(255)));
                    g.fillRect(x[i], y[i], JEDNOSC, JEDNOSC);
                }
            }
            g.setColor(Color.blue);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            FontMetrics meter = getFontMetrics(g.getFont());
            g.drawString("Winniczek " + zezarteJapka, (SZEROKUTKI - meter.stringWidth("Winniczek")) / 2, g.getFont().getSize());
        } else {
            GameOver(g);
        }
    }

    public void newApple() {
        Runnable runnable = () -> {
            japkoX = rngAleNieTakiDobryJakPanski.nextInt((int) (SZEROKUTKI / JEDNOSC)) * JEDNOSC;
            japkoY = rngAleNieTakiDobryJakPanski.nextInt((int) (WYSOKUTKI / JEDNOSC)) * JEDNOSC;
        };
        Thread thread = new Thread(runnable);
        thread.start();
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
        if ((x[0] == japkoX) && (y[0] == japkoY)) {
            cialo++;
            zezarteJapka++;
            newApple();
        }
    }

    public void checkCollisions() {
        //checks head collision with body
        for (int i = cialo; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                zasuwa = false;
            }
        }
        //head touches any border
        if (x[0] < 0) {
            zasuwa = false;
        }
        if (x[0] > SZEROKUTKI) {
            zasuwa = false;
        }
        if (y[0] < 0) {
            zasuwa = false;
        }
        if (y[0] > WYSOKUTKI) {
            zasuwa = false;
        }

        if (!zasuwa) {
            wunszowyCzasomierz.stop();
        }
    }

    public void GameOver(Graphics g) {
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        FontMetrics meter1 = getFontMetrics(g.getFont());
        g.drawString("Winiczek " + zezarteJapka, (SZEROKUTKI - meter1.stringWidth("Winiczek")) / 2, g.getFont().getSize());


        g.setColor(Color.blue);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 75));
        FontMetrics meter = getFontMetrics(g.getFont());
        g.drawString("Przegranet", (SZEROKUTKI - meter.stringWidth("Przegranet")) / 2, WYSOKUTKI / 2);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (zasuwa) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (AiR != 'R') {
                        AiR = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (AiR != 'L') {
                        AiR = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (AiR != 'D') {
                        AiR = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (AiR != 'U') {
                        AiR = 'D';
                    }
                    break;
            }
        }
    }
}
