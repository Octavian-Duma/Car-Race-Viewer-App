package org.example.raceapp;

import javax.swing.*;
import java.awt.*;

class CarPanel extends JPanel {
    private int[] carPositions;
    private String[] carNames;
    private Color[] carColors;
    private int[] carOrder;
    private int index = 0;

    public CarPanel() {
        carPositions = new int[4];
        carNames = new String[]{"Red car", "Blue car", "Green car", "Yellow car"};
        carColors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        carOrder = new int[4];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 4; i++) {
            int yPos = 50 + i * 80;
            int xPos = carPositions[i];
            int carSize = 30;

            g.setColor(carColors[i]);
            g.fillOval(xPos, yPos, carSize, carSize);
            g.setColor(Color.BLACK);
            g.drawString(carNames[i], xPos, yPos - 5);
        }
    }

    public void updateCarPosition(String carName, int distance) {
        int carIndex = getCarIndex(carName);
        if (carIndex != -1) {
            carPositions[carIndex] = distance;
            repaint();
        }
    }

    private int getCarIndex(String carName) {
        for (int i = 0; i < 4; i++) {
            if (carNames[i].equals(carName)) {
                return i;
            }
        }
        return -1;
    }

    public void carFinished(String carName) {
        int pos = index;
        carOrder[pos] = getCarIndex(carName);
        index++;
        final String[] positions = {"1st", "2nd", "3rd", "4th"};
        updateCarPosition(carName, 400);
        System.out.print(carName + " finished the race on " + positions[pos] + " place in: ");
    }
}