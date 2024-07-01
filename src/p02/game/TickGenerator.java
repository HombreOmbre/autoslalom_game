package p02.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TickGenerator
    extends Thread {

    private static TickGenerator instance;
    private boolean isRunning;
    private int interval;
    private List<TickEventListener> listeners;

    public static synchronized TickGenerator getInstance() {
        if (instance == null)
            instance = new TickGenerator();

        return instance;
    }

    private TickGenerator() {
        this.isRunning = false;
        this.listeners = new ArrayList<>();
        this.interval = 1000;
    }

    public void addEventListener(TickEventListener listener) {
        listeners.add(listener);
    }

    private void fireTickEvent(TickEvent event) {
        for (TickEventListener listener : listeners)
            listener.handleTickEvent(event);
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(interval);
                fireTickEvent(new TickEvent());

                if (interval > 300)
                    interval -= 20;
            } catch(InterruptedException e) {
                new IOException(e);
            }
        }
    }

    public void startTicking() {
        isRunning = true;
        this.start();
    }

    public void stopEvent() {
        this.isRunning = false;
        this.instance = null;
        resetInterval();
    }

    private void resetInterval() {
        this.interval = 1000;
    }
}
