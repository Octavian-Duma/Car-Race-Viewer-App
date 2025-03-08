package org.example.raceapp;

public class Timer extends Thread {
    private long duration;
    private boolean running;

    public Timer() {
        this.duration = 0;
        this.running = false;
    }

    public void startCount() {
        running = true;
        start();
    }

    public void stopCount() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(10);
                duration += 10;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public long getDuration() {
        return duration;
    }
}