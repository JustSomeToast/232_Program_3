/*
 * Author: Tyler Gentry, Gavin Austin
 * Date: 4/10/2018                
 * Overview: Implementation of Prim's and Kruskal's algorithms for MST of a graph
 * and implementation of Floyd-Warshall's algorithm in weighted graphs. Reads in from 
 * an input file.
 * Reads in matrices from UWCMatrix.csv and floydMatrix.csv. Should be able to just run program and get output.
 * Open Project Properties to change this.
 */
package pkg232graphtraversal;

public class Main {

    public static void main(String[] args) {
        GraphAlgorithms grapher = new GraphAlgorithms();
        grapher.readUWCMatrix();
        grapher.prim(grapher.getPKMatrix());
        grapher.kruskals(grapher.getPKMatrix());
        grapher.readFloydMatrix();
        grapher.floydWarshall(grapher.getFloydMatrix());
        
    }
    
}
