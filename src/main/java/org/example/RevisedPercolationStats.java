package org.example;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;
public class RevisedPercolationStats {
    private final double[] openSiteRatio;
    private final int trials;
    private  double meanVal;
    private  double stdDevVal;

    public RevisedPercolationStats(final int n, final int trials) {
        this.openSiteRatio = new double[trials];
        this.trials = trials;
        //this.n = n;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Grid size or the number of tirals can not be less than 0.");
        }
        RevisedPercolation p;
        for (int i = 0; i < trials ; i++) {
            p = new RevisedPercolation(n);
            int counter =0;
            
            while (p.percolates() == false && counter < (n*n)) {
                final int r = StdRandom.uniform(1, n+1);
                final int c = StdRandom.uniform(1, n+1);
                
                if (p.isOpen(r, c) == false) {
                    p.open(r, c);
                }
            }
            final double openSites = p.numberOfOpenSites();
            //StdOut.println("Total number of open sites for this round was: " + openSites);
            final double ratio = (openSites / (n*n));
            this.openSiteRatio[i] = ratio;
            this.meanVal = StdStats.mean(this.openSiteRatio);
            this.stdDevVal = StdStats.stddev(this.openSiteRatio);
        }

    }
    // sample mean of percolation threshold
    public double mean() {
        return this.meanVal;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stdDevVal;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (1.96 * (this.stdDevVal) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (1.96 * (this.stdDevVal) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(final String[] args) {
        Stopwatch timer = new Stopwatch();
        for (int i = 0; i < args.length; i++) {
            if (Integer.parseInt(args[i]) <= 0 || Integer.parseInt(args[i])  > 2000 ){
                throw new IllegalArgumentException("Please enter valid integers for gird size and number of trials.") ;
            }
        }
        final RevisedPercolationStats ps = new RevisedPercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("Mean value for these trials is:  "+ ps.mean());
        StdOut.println("Standard Deviation is: "+ ps.stdDevVal);
        StdOut.println("High end of confidnece: " + ps.confidenceHi());
        StdOut.println("Low end of confidence: "+ ps.confidenceLo());
        double time = timer.elapsedTime();
        StdOut.println("Using Quick Find for grid size: "+ args[0] + " and "+ args[1] +" Trials "+"takes: "+ time + " Seconds.");
   }
}