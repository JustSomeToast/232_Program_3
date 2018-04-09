/*
 * Author: Tyler Gentry, Gavin Austin
 * Date: 4/10/2018                
 * Overview: Implementation of Prim's and Kruskal's algorithms for MST of a graph
 * and implementation of Floyd-Warshall's algorithm in weighted graphs. Reads in from 
 * an input file.
 * Reads in from UWCMatrix.csv and floydMatrix.csv. Should be able to just run program and get output.
 * Open Project Properties to change this.
 */
package pkg232graphtraversal;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.*;

public class GraphAlgorithms {

    private char[] pkvertices; // prim/kruskal vertex vertices
    private int[][] primkrusMatrix; //UWC primkrusMatrix, stored as ints
    private char[] fwvertices;
    private int[][] floydMatrix;

    public void readUWCMatrix() { //reads in an adjacency primkrusMatrix for prims and kruskal algorithms

        Path in = Paths.get("UWCMatrix.csv");

        try (BufferedReader reader = Files.newBufferedReader(in)) { //reader
            String line; //the line to be read

            int i = 1; //vars for checking current line, and indexing values into primkrusMatrix
            int j = 0, k = 0;

            while ((line = reader.readLine()) != null) {

                String inLine[] = line.split(",");

                if (i == 1) { //if we are on the first line
                    primkrusMatrix = new int[inLine.length][inLine.length]; //initialize both arrays
                    pkvertices = new char[inLine.length];

                    int b = 0;
                    for (String c : inLine) { //insert our pkvertices into pkvertices
                        pkvertices[b] = c.charAt(0);
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

                        primkrusMatrix[j][k] = val; //index the value into our primkrusMatrix
                        k++;
                    }
                    k = 0;
                    j++;
                }
                i++;
            }
            printPKMatrix(); //print the primkrusMatrix
        } catch (IOException e) {
            System.err.format("An IOException occured when reading in from: " + in
                    + '\n' + "Error: " + e);
        }

    }

    public void readFloydMatrix() { //reads in the matrix used for floyd-warshall's algorithm
        Path in = Paths.get("floydMatrix.csv");

        try (BufferedReader reader = Files.newBufferedReader(in)) { //reader
            String line; //the line to be read

            int i = 1; //vars for checking current line, and indexing values into arrays
            int j = 0, k = 0;

            while ((line = reader.readLine()) != null) {

                String inLine[] = line.split(",");

                if (i == 1) { //if we are on the first line
                    floydMatrix = new int[inLine.length][inLine.length]; //initialize both arrays
                    fwvertices = new char[inLine.length];

                    int b = 0;
                    for (String c : inLine) { //insert our vertices
                        fwvertices[b] = c.charAt(0);
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

                        floydMatrix[j][k] = val; //index the value 
                        k++;
                    }
                    k = 0;
                    j++;
                }
                i++;
            }
            System.out.println("\n---The Floyd matrix---");
            printFloydMatrix(floydMatrix);
        } catch (IOException e) {
            System.err.format("An IOException occured when reading in from: " + in
                    + '\n' + "Error: " + e);
        }
    }

    public int minVertKey(int k[], boolean t[]) { //finds the minimum weighted, unvisited edge and returns it

        int min = Integer.MAX_VALUE;
        int minDex = -1;

        for (int i = 0; i < k.length; i++) {
            if (t[i] == false && k[i] < min) {
                min = k[i];
                minDex = i;
            }
        }
        return minDex;
    }

    public char getVertex(int i) { //function for finding the corresponding vertex char from a given input
        char c;
        c = pkvertices[i];

        return c;
    }

    public void printPrimMST(int p[], int mat[][]) { //fucntion for printing a MST
        int mstWeight = 0;

        System.out.println("\nMST for the matrix");
        System.out.println("-------------------");
        System.out.println("Edge  Weight");
        for (int i = 1; i < mat.length; i++) {
            System.out.println(getVertex(p[i]) + "" + getVertex(i) + "    " + mat[i][p[i]]);
            mstWeight += mat[i][p[i]];
        }
        System.out.println("-------------------");
        System.out.println("Total MST Weight = " + mstWeight);
    }

    public void prim(int mat[][]) { //prim's algorithm
        System.out.println("Running Prim's Algorithm...");
        int v = mat.length;  //number of pkvertices in our matrix

        int mst[] = new int[v]; //stores the MST

        int edgeKeys[] = new int[v]; //values used as edge keys

        boolean treeSet[] = new boolean[v]; //pkvertices not yet in MST

        for (int i = 0; i < v; i++) {  //initalize edgeKeys and visits as infinite and false
            edgeKeys[i] = Integer.MAX_VALUE;
            treeSet[i] = false;
        }

        edgeKeys[0] = 0; //our starting vertex
        mst[0] = -1; //root of MST

        for (int k = 0; k < v - 1; k++) {
            int m = minVertKey(edgeKeys, treeSet); //

            treeSet[m] = true; //add the vertex to the visited tree

            for (int j = 0; j < v; j++) {
                //if we have an edge, the vertex is not in our tree, and the edge is smaller than other connected edges
                if (mat[m][j] != Integer.MAX_VALUE && treeSet[j] == false && mat[m][j] < edgeKeys[j]) {
                    mst[j] = m;
                    edgeKeys[j] = mat[m][j];
                }
            }
        }
        //print our MST
        printPrimMST(mst, mat);

    }

    public void kruskals(int mat[][]) {
        int v = mat.length; //number of pkvertices in matrix
        int edges = 0; //initialize number of edges in MST to 0
        int start = 0; //represents starting vertex
        int end = 0; //represents ending vertex
        int min;
        int totalWeight = 0;
        char startAddedVertices[] = new char[v - 1]; //starting vertices of edges
        char endAddedVertices[] = new char[v - 1]; //ending vertices of edges
        char notFound[] = new char[v];  //vertices that have not been used
        char lastEdge1 = ' ';
        char lastEdge2 = ' ';
        int lastEdgeWeight = 0;

        System.out.println("\nRunning Kruskal's Algorithm...");
        System.out.println("\nMST for the matrix");
        System.out.println("-------------------");
        System.out.println("Edge  Weight");

        while (edges < v - 2) {
            min = Integer.MAX_VALUE;
            for (int i = 0; i < v; i++) {
                for (int j = 0; j < v; j++) {
                    if (mat[i][j] < min) {
                        //set new min
                        min = mat[i][j];
                        start = i;
                        end = j;
                    }
                }
            }

            int num = 0;
            int num2 = 0;
            //checks if adding edge will form circuit
            for (int i = 0; i < v - 1; i++) {
                if (startAddedVertices[i] == pkvertices[start] && (num == 0 || num == 1)) {
                    num++;
                    //don't add edge
                }
                if (endAddedVertices[i] == pkvertices[start] && (num == 0 || num == 1)) {
                    num++;
                    //don't add edge
                }
                if (startAddedVertices[i] == pkvertices[end] && (num2 == 0 || num2 == 1)) {
                    num2++;
                    //don't add edge
                }
                if (endAddedVertices[i] == pkvertices[end] && (num2 == 0 || num2 == 1)) {
                    num2++;
                    //don't add edge
                }
            }

            //adds edge to MST if it doesn't form circuit
            if (num < 2 && num2 < 2) {
                int counter = 0;
                while (startAddedVertices[counter] != 0) {
                    counter++;
                }
                startAddedVertices[counter] = pkvertices[start];
                counter = 0;
                while (endAddedVertices[counter] != 0) {
                    counter++;
                }
                endAddedVertices[counter] = pkvertices[end];
                edges++;
                totalWeight += min;
                System.out.print(pkvertices[start]);
                System.out.println(pkvertices[end] + "    " + min);
            }

            int count = 0;
            //checks number of edges added to MST
            while (startAddedVertices[count] != 0) {
                count++;
            }

            //finds last edge to add
            int count2 = 0;
            if (count == v - 2) {
                for (int i = 0; i < v; i++) {
                    if (found(startAddedVertices, pkvertices[i])) {
                        //do nothing
                    } else {
                        notFound[count2] = pkvertices[i];
                        count2++;
                    }
                    if (found(endAddedVertices, pkvertices[i])) {
                        //do nothing
                    } else {
                        notFound[count2] = pkvertices[i];
                        count2++;
                    }
                }
            }

            //finds keys of vertices and weight of last edge
            char repeated = findRepeating(notFound, notFound.length);
            int column = 0;
            for (int i = 0; i < v; i++) {
                if (repeated == pkvertices[i]) {
                    column = i;
                }
            }
            int minValue = Integer.MAX_VALUE;
            for (int i = 0; i < v; i++) {
                if (mat[column][i] < minValue) {
                    minValue = mat[column][i];
                    lastEdge1 = pkvertices[column];
                    lastEdge2 = pkvertices[i];
                    lastEdgeWeight = mat[column][i];
                }
            }
            mat[start][end] = mat[end][start] = Integer.MAX_VALUE;
        }
        System.out.println(lastEdge1 + "" + lastEdge2 + "    " + lastEdgeWeight);
        totalWeight += lastEdgeWeight;

        System.out.println("-------------------");
        System.out.println("Total MST Weight = " + totalWeight);
    }

    char findRepeating(char arr[], int size) {
        int i, j;
        char repeated = ' ';
        for (i = 0; i < size; i++) {
            for (j = i + 1; j < size; j++) {
                if (arr[i] == arr[j]) {
                    repeated = arr[i];
                }
            }
        }
        return repeated;
    }

    public boolean found(char arr[], char vertex) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == vertex) {
                return true;
            }
        }
        return false;
    }

    public void floydWarshall(int mat[][]) { //floyd-warhsall's all shortes paths algorithm
        System.out.println("Running Floyd-Warshall's algorithm...\n");
        int weights[][] = new int[mat.length][mat.length]; //create and array the same as our matrix
        int len = mat.length;
        int inf = Integer.MAX_VALUE; //

        for (int m = 0; m < len; m++) { //copy our matrix into our array
            for (int n = 0; n < len; n++) {
                weights[m][n] = mat[m][n];
            }
        }

        for (int k = 0; k < len; k++) { //triple nested loop for checking shortest paths between vertices
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    if (weights[i][k] != inf && weights[k][j] != inf && weights[i][j] > weights[i][k] + weights[k][j]) { //if we have a path
                        weights[i][j] = weights[i][k] + weights[k][j]; //update the graph
                        printFloydMatrix(weights); //print the changed graph
                    }

                }
            }
        }
    }

    public int[][] getPKMatrix() { //returns the matrix used for prim/kruskal
        return primkrusMatrix;
    }

    public int[][] getFloydMatrix() { //returns the matrix used for floyd-warshall
        return floydMatrix;
    }

    public void printPKMatrix() { //prints the matrix used for prims and kruskals algorithm
        System.out.println("---The UWC matrix---");

        for (int k = 0; k < pkvertices.length; k++) {
            System.out.print(pkvertices[k] + "  ");
        }
        System.out.println();

        for (int i = 0; i < primkrusMatrix.length; i++) {
            for (int j = 0; j < primkrusMatrix[i].length; j++) {
                if (primkrusMatrix[i][j] == Integer.MAX_VALUE) {
                    System.out.print("∞" + "  ");
                } else {
                    System.out.print(primkrusMatrix[i][j] + "  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printFloydMatrix(int mtp[][]) { //prints the matrix used for the floyd-warshall algorithm
        for (int k = 0; k < fwvertices.length; k++) {
            System.out.print("  " + fwvertices[k] + " ");
        }
        System.out.println();
        for (int i = 0; i < mtp.length; i++) {
            System.out.print(fwvertices[i] + " ");
            for (int j = 0; j < mtp[i].length; j++) {

                if (mtp[i][j] == Integer.MAX_VALUE) {
                    System.out.print("∞" + "  ");
                } else {
                    System.out.print(mtp[i][j] + "  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
