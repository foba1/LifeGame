package com.holub.refactoring;

import javax.swing.*;
import java.util.*;
import java.util.Timer;        // overrides java.awt.timer
import com.holub.tools.Publisher;

public class Clock {
    private Timer clock = new Timer();
    private TimerTask tick = null;

    private Clock() {

    }

    private static Clock instance;

    /**
     * The clock is a singleton. Get a reference to it by calling
     * <code>Clock.instance()</code>. It's illegal to call
     * <code>new Clock()</code>.
     */
    public synchronized static Clock instance() {
        if (instance == null) {
            instance = new Clock();
        }
        return instance;
    }

    public void startTicking(int millisecondsBetweenTicks) {
        if (tick != null) {
            tick.cancel();
            tick = null;
        }

        if (millisecondsBetweenTicks > 0) {
            tick = new TimerTask() {
                public void run() {
                    tick();
                }
            };
            clock.scheduleAtFixedRate(tick, 0, millisecondsBetweenTicks);
        }
    }

    public void stop() {
        startTicking(0);
    }

    private Publisher publisher = new Publisher();

    public void addClockListener(Listener observer) {
        publisher.subscribe(observer);
    }

    public interface Listener {

        void tick();
    }

    /**
     * Force the clock to "tick," even if it's not time for a tick. Useful for forcing a tick when the
     * clock is stopped. (Life uses this for single stepping.)
     */
    public void tick() {
        publisher.publish
            (new Publisher.Distributor() {
                 public void deliverTo(Object subscriber) {
                     if (!menuIsActive()) {
                         ((Listener) subscriber).tick();
                     }
                 }
             }
            );
    }

    private boolean menuIsActive() {
        MenuElement[] path =
            MenuSelectionManager.defaultManager().getSelectedPath();
        return (path != null && path.length > 0);
    }


}
