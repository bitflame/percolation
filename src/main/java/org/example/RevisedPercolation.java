package org.example;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.QuickUnionUF;
public class RevisedPercolation {
  private final int[][] grid;
  private int openCounter = 0;
  private final QuickUnionUF uf;
  private int topVirtSite = 0, botVirtSite = 0;

  public RevisedPercolation (final int n) {
    if (n <= 0) {
        throw new IndexOutOfBoundsException("Grid demensions can not be 0 or less.");
    }
      // this.n = n;
      int m = n + 1;
      grid = new int[m][m];
      this.uf = new QuickUnionUF(m * m+1);
      topVirtSite = 0;
      botVirtSite = m * m;
      for (int i = 1; i < m; i++) {
        for (int j = 1; j < m; j++) {
          this.grid[i][j] = 0;// 0 means blocked, 1 means open
        }
      }
  }
  public void open (final int row, final int col) {
    validate(row, col);
    if (isOpen(row, col)!=true){
      openCounter=openCounter+1;
    }
    this.grid[row][col] = 1;
    if (row == 1) {
        uf.union(topVirtSite, xyTo1D(row, col));
    }
    if (isValid(row+1) && isOpen(row+1, col)) uf.union(xyTo1D(row, col), xyTo1D(row+1, col));
    if (isValid(row-1) && isOpen(row-1, col)) uf.union(xyTo1D(row, col), xyTo1D(row-1, col));
    if (isValid(col+1) && isOpen(row, col+1)) uf.union(xyTo1D(row, col), xyTo1D(row, col+1));
    if (isValid(col-1) && isOpen(row, col-1)) uf.union(xyTo1D(row, col), xyTo1D(row, col-1));     
    if (row == grid.length-1){
        uf.union(xyTo1D(row, col), botVirtSite);
        if ((grid.length > 2) && isOpen(row-1, col)) uf.union(xyTo1D(row, col), xyTo1D(row-1, col));
        if ((col > 1 ) && (col < grid.length-1)){
            if (isOpen(row, col+1)) uf.union(xyTo1D(row, col), xyTo1D(row, col+1));
            if (isOpen(row, col-1)) uf.union(xyTo1D(row, col), xyTo1D(row, col-1));
        }
    }
  }
  private int xyTo1D(int row, int col) {
    return (row*grid.length)+col;//(1, 1) should equal 21 in the parents array 
  }
  private boolean isValid ( int val ) {
    if (val <= grid.length-1 && val >= 1) return true;
    else return false;
  }
  private void validate (int row, int col) {

    if (row <= 0 || row > grid.length) {
      throw new IllegalArgumentException(
          "Grid row length should be more than 0 and less" + "than the maximum row size.");
    }
    if (col <= 0 || col > grid.length) {
      throw new IllegalArgumentException(
          "Grid column length should be more than 0 and less" + "than the maximum column size.");
    }
  }
  public boolean isOpen(int row, int col) {
      validate(row, col);
      if (this.grid[row][col]==1){
          return true;
      } else
      return false;
  }
public boolean isFull(final int row, final int col){
    validate(row, col);
    if(uf.find(xyTo1D(row, col))==uf.find(topVirtSite)){
        return true;
    } else 
    return false;
}
public int numberOfOpenSites(){
    return openCounter;
}
public boolean percolates(){
    if (uf.find(topVirtSite)==uf.find(botVirtSite)) return true;
    else 
    return false;
}
public static void main (final String[] args) {
   RevisedPercolation RP = new RevisedPercolation(8);
        RP.open(1, 3);
        RP.open(1, 4);
        RP.open(1, 5);

        RP.open(2, 1);
        RP.open(2, 4);
        RP.open(2, 5);
        RP.open(2, 6);
        RP.open(2, 7);
        RP.open(2, 8);

        RP.open(3, 1);
        RP.open(3, 2);
        RP.open(3, 3);
        RP.open(3, 6);
        RP.open(3, 7);

        RP.open(4, 3);
        RP.open(4, 4);
        RP.open(4, 6);
        RP.open(4, 7);
        RP.open(4, 8);

        RP.open(5, 2);
        RP.open(5, 3);
        RP.open(5, 4);
        RP.open(5, 6);
        RP.open(5, 7);

        RP.open(6, 2);
        RP.open(6, 7);
        RP.open(6, 8);

        RP.open(7, 1);
        RP.open(7, 3);
        RP.open(7, 5);
        RP.open(7, 6);
        RP.open(7, 7);
        RP.open(7, 8);

        RP.open(8, 1);

        RP.open(8, 2);
        RP.open(8, 3);
        RP.open(8, 4);
        RP.open(8, 6);
        StdOut.println("Is 8, 6 full? " + RP.isFull(8, 6));
        StdOut.print(RP.numberOfOpenSites() + "Sites are open.");
        StdOut.println("Does it percolate? " + RP.percolates());
}
}