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

    private char[] vertices; //vertex vertices
    private int[][] UWCmatrix; //UWC UWCmatrix, stored as ints

    public void readAdjacencyMatrix() { //reads in an adjacency UWCmatrix for prims and kruskal algorithms

        Path in = Paths.get("UWCMatrix.csv");

        try (BufferedReader reader = Files.newBufferedReader(in)) { //reader
            String line; //the line to be read
            
            int i = 1; //vars for checking current line, and indexing values into UWCmatrix
            int j = 0, k = 0;
            
            while ((line = reader.readLine()) != null) {

                String inLine[] = line.split(",");
                
                if (i == 1) { //if we are on the first line
                    UWCmatrix = new int[inLine.length][inLine.length]; //initialize both arrays
                    vertices = new char[inLine.length];

                    int b = 0;
                    for (String c : inLine) { //insert our vertices into vertices
                        vertices[b] = c.charAt(0);
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

                        UWCmatrix[j][k] = val; //index the value into our UWCmatrix
                        k++;
                    }
                    k = 0;
                    j++;
                }
                i++;
            }
            printMatrix(); //print the UWCmatrix
        } catch (IOException e) {
            System.err.format("An IOException occured when reading in from: " + in
                    + '\n' + "Error: " + e);
        }

    }

    public int minVertKey(int k[], boolean t[]){ //finds the minimum weighted, unvisited edge and returns it
        int min = Integer.MAX_VALUE;
        int minDex = -1;
        
        for(int i=0; i<k.length; i++){
            if(t[i] == false && k[i] < min){
                min = k[i];
                minDex = i;
            }
        }
        return minDex;
    }
    
    public char getVertex(int i){ //function for finding the corresponding vertex char from a given input
        char c;
        c = vertices[i];
        
        return c;
    }
    
    public void printMST(int p[], int mat[][]){ //fucntion for printing a MST
        int mstWeight=0;
        
        System.out.println("\nEdge  Weight");
        for(int i=1; i<mat.length; i++){
            System.out.println(getVertex(p[i]) + "" + getVertex(i) + "    "+mat[i][p[i]]);
            mstWeight += mat[i][p[i]];
        }
        System.out.println("\nTotal MST Weight = " + mstWeight);
    }
    
    public void prim(int mat[][]) { //prim's algorithm
            int v = mat.length;  //number of vertices in our matrix
             
            int mst[] = new int[v]; //stores the MST
            
            int vertKeys[] = new int[v]; //values used as vertex keys
            
            boolean treeSet[] = new boolean[v]; //vertices not yet in MST
            
            for (int i=0; i<v; i++){  //initalize vertKeys and visits as infinite and false
                vertKeys[i] = Integer.MAX_VALUE;
                treeSet[i] = false;
            }
            
            vertKeys[0] = 0; //our starting vertex
            mst[0] = -1; //root of MST
            
            for(int k=0; k<v-1; k++){
                int m = minVertKey(vertKeys, treeSet); //find our next vertex not yet visited
                
                treeSet[m] = true; //add the vertex to the visited tree
                
                for(int j=0; j<v; j++){
                    //if we have an edge, the vertex is not in our visited tree, and the edge is smaller than other found edges
                    if(mat[m][j] != Integer.MAX_VALUE && treeSet[j] == false 
                            && mat[m][j] < vertKeys[j]){
                        mst[j] = m;
                        vertKeys[j] = mat[m][j];
                    }
                }
            }
            //print our MST
            printMST(mst, mat);
            
    }

    public void kruskals() {

    }

    public void floydWarshall() {

    }
    
    public void printMST(){
        
    }
    
    public int[][] getMatrix(){
        return UWCmatrix;
    }

    public void printMatrix() {
        System.out.println("---The UWC matrix---");
        
        for (int k = 0; k < vertices.length; k++) {
            System.out.print(vertices[k] + "  ");
        }
        System.out.println();

        for (int i = 0; i < UWCmatrix.length; i++) {
            for (int j = 0; j < UWCmatrix[i].length; j++) {
                if (UWCmatrix[i][j] == Integer.MAX_VALUE) {
                    System.out.print("∞" + "  ");
                } else {
                    System.out.print(UWCmatrix[i][j] + "  ");
                }
            }
            System.out.println();
        }
    }
}
