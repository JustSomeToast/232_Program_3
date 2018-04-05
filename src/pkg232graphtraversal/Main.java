/*
 * Author: Tyler Gentry, Gavin Austin
 * Date: 4/10/2018                
 * Overview: Implementation of Prim's and Kruskal's algorithms for MST of a graph
 * and implementation of Floyd-Warshall's algorithm in weighted graphs. Reads in from 
 * an input file.
 * Open Project Properties to change this.
 */
package pkg232graphtraversal;

public class Main {

    public static void main(String[] args) {
        GraphAlgorithms grapher = new GraphAlgorithms();
        grapher.readUWCMatrix();
        grapher.prim(grapher.getMatrix());
        grapher.kruskals(grapher.getMatrix());
    }
    
}
