package de.mk.environment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PlayingFieldWindow extends JPanel {

    private static PlayingFieldWindow MY_INSTANCE;

    private static final int HEADER_MARGIN = 10;
    private static final int POINT_SIZE = 20;
    private static final int POINT_MARGIN = 2;
    private static final int FIELD_MARGIN = 10;

    private static Snake currentSnake;
    private static PlayingField playingField = new PlayingField();
    private static int generation = 0;
    private static double averageFitness = 0.0;

    private static Color[] COLORS = {
            new Color(255, 255, 255),
            new Color(122, 122, 122),
            new Color(250, 0, 0),
            new Color(110, 173, 250),
            new Color(14, 0, 250)
    };

    public PlayingFieldWindow(){
        PlayingFieldWindow.MY_INSTANCE = this;
    }

    public void placeSnake(Snake snake, int generation, double averageFitness){
        PlayingFieldWindow.currentSnake = snake;
        PlayingFieldWindow.generation = generation;
        PlayingFieldWindow.averageFitness = averageFitness;
        PlayingFieldWindow.playingField = new PlayingField();
        PlayingFieldWindow.playingField.setSnake(currentSnake);
    }

    public void showGame(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
        javax.swing.Timer t = new javax.swing.Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlayingField pf = PlayingFieldWindow.playingField;
                if (pf!=null && pf.snake!=null && pf.snake.alive) {
                    pf.moveSnake();
                }
                MY_INSTANCE.revalidate();
                MY_INSTANCE.repaint();
            }
        });
        t.start();
    }

    public boolean isRunning(){
        if (currentSnake==null) return false;
        else return currentSnake.alive;
    }

    private void createAndShowGui() {
        JFrame jFrame = new JFrame("Snake AI - Playing Field");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().add(MY_INSTANCE);
        jFrame.pack();
        jFrame.setLocationByPlatform(true);
        jFrame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x<playingField.getWidth(); x++){
            for (int y = 0; y<playingField.getHeight(); y++){
                Color color = COLORS[playingField.getAt(x,y)];
                if (playingField.snake==null && playingField.getAt(x,y)==2){
                    color = COLORS[0];
                }
                g.setColor(color);
                g.fillRect(FIELD_MARGIN + x * (POINT_SIZE + POINT_MARGIN),
                         HEADER_MARGIN + FIELD_MARGIN + y * (POINT_SIZE + POINT_MARGIN),
                        POINT_SIZE,
                        POINT_SIZE);

            }
        }
        g.setColor(Color.BLACK);
        g.drawString("Generation: "+generation, HEADER_MARGIN, 15);
        if (playingField.snake!=null) {
            g.drawString(String.format("Avg. Fitness: %.3f",averageFitness), HEADER_MARGIN +100, 15);
            g.drawString("Score: " + playingField.snake.body.size(), HEADER_MARGIN + 280, 15);
            g.drawString("Moves Alive: " + playingField.snake.timeAlive, HEADER_MARGIN + 350, 15);
            g.drawString("Moves Left: " + playingField.snake.life, HEADER_MARGIN + 462, 15);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(FIELD_MARGIN*2 + (POINT_SIZE+POINT_MARGIN)*playingField.getWidth(),
                FIELD_MARGIN*2 + (POINT_SIZE+POINT_MARGIN)*playingField.getHeight() + 10);
    }




}