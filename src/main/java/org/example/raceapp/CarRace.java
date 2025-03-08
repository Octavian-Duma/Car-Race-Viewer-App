package org.example.raceapp;

class Car extends Thread {
    private String name;
    private int distance = 0;
    private long raceDuration = 0;
    private CarPanel carPanel;

    public Car(String name, CarPanel carPanel) {
        setName(name);
        this.name = name;
        this.carPanel = carPanel;
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        while (distance < 400) {
            int speed = (int) (Math.random() * 10) + 1;
            distance += speed;

            carPanel.updateCarPosition(name, distance);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        carPanel.carFinished(name);
        raceDuration = System.currentTimeMillis() - startTime;
        System.out.print((raceDuration) / 1000.0f + " sec");
        System.out.println();
    }

    public int getDistance() {
        return distance;
    }

    public long getRaceDuration() {
        return raceDuration;
    }
}

