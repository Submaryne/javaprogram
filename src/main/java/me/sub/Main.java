package me.sub;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static JFrame frame;

    public static void main(String[] args) {
        Panel panel;

        frame = new JFrame("ijijiji");
        frame.setBounds(100, 100, 850, 850);
        frame.add(panel = new Panel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                panel.repaint();
            }
        };
        final Timer timer = new Timer("Timer");
        timer.schedule(timerTask, 0, 5);
    }

}
