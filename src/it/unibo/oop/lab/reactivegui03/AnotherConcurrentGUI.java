package it.unibo.oop.lab.reactivegui03;


import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import it.unibo.oop.lab.reactivegui02.ConcurrentGUI;

public class AnotherConcurrentGUI extends ConcurrentGUI {

    private static final long serialVersionUID = 1L;

    public AnotherConcurrentGUI() {
        super();
        new Thread(new DisableButtonAgent()).start();
    }

    public class DisableButtonAgent implements Runnable {
        private static final int SECONDS = 100;
        private volatile int counter;

        public void run() {
            while (this.counter != DisableButtonAgent.SECONDS) {
                try {
                    this.counter++;
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                AnotherConcurrentGUI.super.getCounter().stopCounting();
                SwingUtilities.invokeAndWait(() -> {
                    AnotherConcurrentGUI.super.getStop().setEnabled(false);
                    AnotherConcurrentGUI.super.getUp().setEnabled(false);
                    AnotherConcurrentGUI.super.getDown().setEnabled(false);
                });
            } catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
