package org.example.raceapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarRaceViewerApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Car Race Viewer");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(1000, 500);
            mainFrame.setLayout(new BorderLayout());
            mainFrame.setLocationRelativeTo(null);

            JPanel titlePanel = new JPanel();
            JLabel titleLabel = new JLabel("Race Car Viewer");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titlePanel.add(titleLabel);
            mainFrame.add(titlePanel, BorderLayout.NORTH);

            JPanel controlPanel = new JPanel();
            JButton startButton = new JButton("Start Race");
            controlPanel.add(startButton);
            mainFrame.add(controlPanel, BorderLayout.SOUTH);

            JPanel containerPanel = new JPanel(new BorderLayout());

            JPanel leftPanel = new JPanel(new BorderLayout());
            JPanel centerPanel = new JPanel(new BorderLayout());
            JPanel rightPanel = new JPanel(new BorderLayout());

            containerPanel.add(leftPanel, BorderLayout.WEST);
            containerPanel.add(centerPanel, BorderLayout.CENTER);
            containerPanel.add(rightPanel, BorderLayout.EAST);

            SemaphorePanel semaphorePanel = new SemaphorePanel();
            CarPanel carPanel = new CarPanel();
            JTextArea resultsText = new JTextArea();
            resultsText.setEditable(false);
            JScrollPane resultsScrollPane = new JScrollPane(resultsText);

            leftPanel.setPreferredSize(new Dimension((int) (mainFrame.getWidth() * 0.2), mainFrame.getHeight()));
            centerPanel.setPreferredSize(new Dimension((int) (mainFrame.getWidth() * 0.6), mainFrame.getHeight()));
            rightPanel.setPreferredSize(new Dimension((int) (mainFrame.getWidth() * 0.2), mainFrame.getHeight()));

            leftPanel.add(new JLabel("Semaphore", SwingConstants.CENTER), BorderLayout.NORTH);
            leftPanel.add(semaphorePanel, BorderLayout.CENTER);

            centerPanel.add(new JLabel("Race Track", SwingConstants.CENTER), BorderLayout.NORTH);
            centerPanel.add(carPanel, BorderLayout.CENTER);

            rightPanel.add(new JLabel("Race Results", SwingConstants.CENTER), BorderLayout.NORTH);
            rightPanel.add(resultsScrollPane, BorderLayout.CENTER);

            mainFrame.add(containerPanel, BorderLayout.CENTER);

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Thread(() -> startRace(semaphorePanel, carPanel, resultsText)).start();
                }
            });

            mainFrame.setVisible(true);
        });
    }

    private static void startRace(SemaphorePanel semaphorePanel, CarPanel carPanel, JTextArea resultsText) {
        Timer timer = new Timer();
        timer.startCount();

        SemaphoreThread semaphoreThread = new SemaphoreThread(semaphorePanel);
        semaphoreThread.start();

        PlaySound ps = new PlaySound();

        Car car1 = new Car("Red car", carPanel);
        Car car2 = new Car("Blue car", carPanel);
        Car car3 = new Car("Green car", carPanel);
        Car car4 = new Car("Yellow car", carPanel);

        try {
            semaphoreThread.join();
            ps.playSound();

            car1.start();
            car2.start();
            car3.start();
            car4.start();

            car1.join();
            car2.join();
            car3.join();
            car4.join();

            ps.stopSound();
            timer.stopCount();

            showResults(resultsText, timer.getDuration(), car1, car2, car3, car4);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static void showResults(JTextArea resultsText, long raceDuration, Car... cars) {
        StringBuilder results = new StringBuilder("Race Results:\n");
        results.append("Total race duration: ").append(raceDuration).append(" ms\n\n");

        for (Car car : cars) {
            results.append(car.getName()).append(": ").append(car.getRaceDuration()).append(" ms\n");
        }

        resultsText.setText(results.toString());
    }
}