package it.unibo.oop.lab.workers02;

import java.util.ArrayList;
import java.util.List;


public class MultiThreadedSumMatrix implements SumMatrix {
    private final int nthread;

    public MultiThreadedSumMatrix(final int nthread) {
        this.nthread = nthread;
    }

    public class Worker implements Runnable {
        private final List<Double> raw;

        public Worker() {
            super();
            this.raw = new ArrayList<>();
        }

        public void run() {
          
            
        }
        
    }
    @Override
    public double sum(final double[][] matrix) {
        // TODO Auto-generated method stub
        return 0;
    }

}
