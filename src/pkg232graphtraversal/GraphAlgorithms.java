/*
 * Author: Tyler Gentry
 * Date:                  
 * Overview:
 * Open Project Properties to change this.
 */
package pkg232graphtraversal;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.*;

public class GraphAlgorithms {

    private char[] labels; //vertex labels
    private int[][] matrix; //adjacency matrix, stored as ints

    public void readMatrix() {

        Path in = Paths.get("adjacencyMatrix.csv");

        try (BufferedReader reader = Files.newBufferedReader(in)) { //reader
            String line; //the line to be read
            
            int i = 1; //vars for checking current line, and indexing values into matrix
            int j = 0, k = 0;
            
            while ((line = reader.readLine()) != null) {

                String inLine[] = line.split(",");
                
                if (i == 1) { //if we are on the first line
                    matrix = new int[inLine.length][inLine.length]; //initialize both arrays
                    labels = new char[inLine.length];

                    int b = 0;
                    for (String c : inLine) { //insert our vertices into labels
                        labels[b] = c.charAt(0);
                        b++;
                    }
                }
                if (i > 1) { //if we are past the first line
                    for (String c : inLine) {
                        int val;
                        if (c.equals("∞")) { //if we have an infinity (no edge)
                            val = Integer.MAX_VALUE; //represent no edge as max value
                        } else {
                            val = Integer.parseInt(c); //we have an edge
                        }

                        matrix[j][k] = val; //index the value into our matrix
                        k++;
                    }
                    k = 0;
                    j++;
                }
                i++;
            }
            printMatrix(); //print the matrix
        } catch (IOException e) {
            System.err.format("An IOException occured when reading in from: " + in
                    + '\n' + "Error: " + e);
        }

    }

    public void primAlg() {

    }

    public void kruskalAlg() {

    }

    public void floydWarshall() {

    }

    public void printMatrix() {
        System.out.println("---The matrix---");
        
        for (int k = 0; k < labels.length; k++) {
            System.out.print(labels[k] + " ");
        }
        System.out.println();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == Integer.MAX_VALUE) {
                    System.out.print("∞" + " ");
                } else {
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
