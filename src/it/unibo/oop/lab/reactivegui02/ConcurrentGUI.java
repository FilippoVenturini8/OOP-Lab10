package it.unibo.oop.lab.reactivegui02;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public final class ConcurrentGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private final  JLabel lbl = new JLabel();

    public ConcurrentGUI() {
        super();
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        final JPanel canvas = new JPanel();
        this.add(canvas);
        final JButton up = new JButton("up");
        final JButton stop = new JButton("stop");
        final JButton down = new JButton("down");

        final Agent counter = new Agent();
        new Thread(counter).start();

        up.addActionListener(e -> {
            counter.setOperation(Operation.SOMMA);
        });

        down.addActionListener(e -> {
            counter.setOperation(Operation.SOTTRAZIONE);
        });

        stop.addActionListener(e -> {
            counter.stopCounting();
            up.setEnabled(false);
            down.setEnabled(false);
        });

        canvas.add(lbl);
        canvas.add(up);
        canvas.add(down);
        canvas.add(stop);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /*
     * enumeration
     */
    public enum Operation {
        SOMMA, SOTTRAZIONE;
    }

    public class Agent implements Runnable {
        private volatile int counter;
        private volatile boolean stop;
        private volatile Operation op;

        public Agent() {
            super();
            this.stop = false;
            this.counter = 0;
            this.op = Operation.SOMMA;
        }

        public void run() {
            while (!this.stop) {
                try {
                    SwingUtilities.invokeAndWait(() -> ConcurrentGUI.this.lbl.setText(Integer.toString(this.counter)));
                    if (this.op == Operation.SOMMA) {
                        this.counter++;
                    } else {
                        this.counter--;
                    }
                    Thread.sleep(100);
                } catch (InvocationTargetException  | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setOperation(final Operation op) {
            this.op = op;
        }

        public void stopCounting() {
            this.stop = true;
        }

    }

}
