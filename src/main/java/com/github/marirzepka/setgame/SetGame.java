package com.github.marirzepka.setgame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class SetGame extends JFrame {

    long startTime;

    int matches = 0;
    double lastMatch = 0;
    double bestTime = Integer.MAX_VALUE;
    double runningAvg = 0;


    JLabel matchesLabel = new JLabel("Total Matches: 0");
    JLabel lastMatchLabel = new JLabel("Last Match: ");
    JLabel bestTimeLabel = new JLabel("Best Time: ");
    JLabel runningAvgLabel = new JLabel("Average: ");
    JLabel countLabel = new JLabel("Possibilities: ");

    ArrayList<Card> deck = new ArrayList<Card>();
    ArrayList<Card> active = new ArrayList<Card>();

    public SetGame() {

        startTime = System.currentTimeMillis();

        setSize(600,450);

        matchesLabel.setBounds(0,400,150,20);
        lastMatchLabel.setBounds(125,400,150,20);
        bestTimeLabel.setBounds(255,400,150,20);
        runningAvgLabel.setBounds(375,400,150,20);
        countLabel.setBounds(500,400,150,20);
        add(matchesLabel);
        add(lastMatchLabel);
        add(bestTimeLabel);
        add(runningAvgLabel);
        add(countLabel);

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fillDeck();

        for (int i = 0; i < 12; i++) {
            Card c = deck.removeFirst();
            active.add(c);
        }

        updateDisplay();
        updateMatchesCount();
    }

    void fillDeck() {
        deck.add(new Card(2,2,2,3,this));
        for (int col = 0; col < 3; col++) {
            for (int tex = 0; tex < 3; tex++) {
                for (int sha = 0; sha < 3; sha++) {
                    for (int cou = 1; cou <= 3; cou++) {
                        deck.add(new Card(col, tex, sha, cou, this));
                    }
                }
            }
        }
        Collections.shuffle(deck);
    }

    void updateDisplay() {
        for (int i = 0; i < 12; i++) {
            Card c = active.get(i);
            c.setBounds((i % 3)*200,(i / 3)*100,200,100);
            add(c);

        }
        repaint();
    }

    void updateMatchesCount() {
        int count = 0;
        for (int a = 0; a < 10; a++) {
            for (int b = a+1; b < 11; b++) {
                for (int c = b+1; c < 12; c++) {
                    if (Card.isSet(active.get(a), active.get(b), active.get(c))) {
                        count++;
                    }
                }
            }
        }
        countLabel.setText("Possibilities: "+count);
        if (count == 0) {
            refreshCards();
            updateMatchesCount();
            updateDisplay();
        }
    }

    void refreshCards() {
        Collections.shuffle(deck);
        for (int i = 0; i < 12; i++) {
            Card x = active.get(i);
            active.add(i,deck.removeFirst());
            active.remove(x);
            deck.add(x);
            remove(x);
        }
    }

    void updateGame() {
        ArrayList<Card> selection = new ArrayList<Card>();
        for (Card x : active) {
            if (x.selected) {
                selection.add(x);
            }

        }
        if (selection.size() != 3) {
            return;
        }
        if (Card.isSet(selection.get(0), selection.get(1), selection.get(2))) {

            selection.get(0).selected = false;
            selection.get(1).selected = false;
            selection.get(2).selected = false;

            for (Card x : selection) {
                active.add(active.indexOf(x), deck.removeFirst());
                active.remove(x);
                deck.add(x);
                remove(x);
            }

            matches++;
            matchesLabel.setText("Total Matches: " + matches);

            lastMatch = (double) (System.currentTimeMillis() - startTime) / 1000;
            lastMatchLabel.setText("Last Match: " + Math.round(lastMatch * 100) / 100d);

            if (bestTime > lastMatch) {
                bestTime = lastMatch;
                bestTimeLabel.setText("Best Time: " + Math.round(bestTime * 100) / 100d);
            }

            runningAvg = (runningAvg * (matches - 1) + lastMatch) / (matches);
            runningAvgLabel.setText("Average: " + Math.round(runningAvg * 100) / 100d);


            startTime = System.currentTimeMillis();


            repaint();

            updateDisplay();
            updateMatchesCount();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SetGame::new);
    }
}
