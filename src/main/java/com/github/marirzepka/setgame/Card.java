package com.github.marirzepka.setgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Card extends JButton {
    int color;
    int texture;
    int shape;
    int count;
    Boolean selected = false;

    public Card(int color, int texture, int shape, int count, SetGame game) {
        this.color = color;
        this.texture = texture;
        this.shape = shape;
        this.count = count;


        setSize(200,100);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selected = !selected;
                repaint();
                game.updateGame();
            }
        });

    }

    public static Boolean isSet(Card a, Card b, Card c) {

        Boolean isSet = true;

        if (a.color == b.color && c.color != b.color) {isSet = false;}
        if (a.color == c.color && b.color != a.color) {isSet = false;}
        if (b.color == c.color && a.color != c.color) {isSet = false;}

        if (a.texture == b.texture && c.texture != b.texture) {isSet = false;}
        if (a.texture == c.texture && b.texture != a.texture) {isSet = false;}
        if (b.texture == c.texture && a.texture != c.texture) {isSet = false;}

        if (a.shape == b.shape && c.shape != b.shape) {isSet = false;}
        if (a.shape == c.shape && b.shape != a.shape) {isSet = false;}
        if (b.shape == c.shape && a.shape != c.shape) {isSet = false;}

        if (a.count == b.count && c.count != b.count) {isSet = false;}
        if (a.count == c.count && b.count != a.count) {isSet = false;}
        if (b.count == c.count && a.count != c.count) {isSet = false;}

        return isSet;
    }

    public static ArrayList<Card> getFullDeck() {
        ArrayList<Card> fullDeck = new ArrayList<>();

        return fullDeck;
    }

    @Override
    public void paintComponent(Graphics g1d) {

        //super.paintComponent(g1d);

        Graphics2D g = (Graphics2D) g1d;

        //g.scale(0.75,0.75);

        g.setStroke(new BasicStroke(4));

        g.drawRect(0, 0, 200, 100);

        if (selected) {
            g.setColor(Color.lightGray);
            g.fillRect(2, 2, 196, 96);
        }



        for (int x = 200/(count+1); x <= 200*count/(count+1); x+=200/(count+1)) {
            switch (shape) {
                case 0:
                    drawOval(g, x);
                    break;
                case 1:
                    drawDiamond(g, x);
                    break;
                case 2:
                    drawSquiggle(g, x);
                    break;
            }
        }


    }

    Color getColor() {
        return switch (color) {
            case 0 -> Color.red;
            case 1 -> Color.green.darker();
            case 2 -> Color.magenta.darker();
            default -> null;
        };
    }

    Paint getFillPaint() {

        switch (texture) {
            case 0:
                return Color.white;
            case 1:
                BufferedImage pattern = new BufferedImage(4,4,BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) pattern.getGraphics();
                g.setColor(getColor());
                g.drawLine(0,0,4,0);
                g.drawLine(0,1,4,1);

                TexturePaint p = new TexturePaint(pattern, new Rectangle(0,0,4,4));
                return p;
            case 2:
                return getColor();
        }
        return null;
    }


    void drawOval(Graphics2D g, int x) {
        g.setPaint(getFillPaint());
        g.fillOval(x-20,10,40,80);
        g.setPaint(getColor());
        g.drawOval(x-20, 10,40,80);
    }

    void drawDiamond(Graphics2D g, int x) {
        g.setPaint(getFillPaint());
        g.fillPolygon(new int[]{x - 20, x, x + 20, x},new int[]{50,90,50,10},4);
        g.setPaint(getColor());
        g.drawPolygon(new int[]{x - 20, x, x + 20, x},new int[]{50,90,50,10},4);
    }

    void drawSquiggle(Graphics2D g, int x) {
        g.setPaint(getFillPaint());
        g.fillOval(x-15,10,25,50);
        g.fillOval(x-5, 40,25,50);
        g.setPaint(getColor());
        g.drawOval(x-15,10,25,50);
        g.drawOval(x-5, 40,25,50);
    }
}
