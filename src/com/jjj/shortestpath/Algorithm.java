package com.jjj.shortestpath;
/**
 * This class serves as the main tester class, along with the structural algorithm itself.
 * @author jjjimenez
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Scanner;
public class Algorithm {
    /**
     * Main method to that calls helper methods for instantiation and abstraction.
     * @param args optional parameter taken at CLI
     */
    public static void main(String[] args){
        Graph graph = new Graph();
        ErrorHandling validation = new ErrorHandling();
        
        System.out.print("Enter number of vertices: ");
        while(!validation.isValidInput(1));
        int noOfVertices = validation.getNumInput();
        Vertex[] vertices = new Vertex[noOfVertices];
        
        System.out.println("Enter labels/name for each vertices: ");
        for(int counter = 0; counter < vertices.length; counter++){
            System.out.print("Vertex " + (counter+1) + ": ");
            while(!validation.isValidInput(2));
            vertices[counter] = new Vertex(validation.getStrInput()); //initialize objects of vertices
            graph.addVertex(vertices[counter], true);
        }
        
        System.out.print("Enter number of edges: ");
        while(!validation.isValidInput(1));
        int noOfEdges = validation.getNumInput();      
        Edge[] edges = new Edge[noOfEdges];
        
        System.out.println("-Enter edges-");
        //System.out.println("-From Vertex X, To Vertex Y, Length/Weight-");
        for(int counter = 0; counter < edges.length; counter++){
            System.out.println("Edge " + (counter+1) + ": ");
            
            System.out.print("From vertex: ");
            while(!validation.isValidInput(2));
            String fromVertex = validation.getStrInput();
            
            System.out.print("To vertex: ");
            while(!validation.isValidInput(2));
            String toVertex = validation.getStrInput();
                
            System.out.print("Length: ");
            while(!validation.isValidInput(1));
            int length = validation.getNumInput();
            System.out.println("");
            
            int fromIndex = 0;
            int toIndex = 0; //hanapin kung anong index naka lagay
            for(int counter2 = 0; counter2 < vertices.length; counter2++){
                if(vertices[counter2].getLabel().equalsIgnoreCase(fromVertex)){ //kung nag match kay input user
                   fromIndex = counter2; //kunin yung INDEX nung nag match
                }
                if(vertices[counter2].getLabel().equalsIgnoreCase(toVertex)){
                    toIndex = counter2; //same goes here
                }
            }
            if(fromIndex == toIndex) //di pedeng naka loop si vertex sa sarili niya
                throw new IllegalArgumentException("This program is designed only for undirected weighted graphs. No loops allowed.");
            
            edges[counter] = new Edge(vertices[fromIndex], vertices[toIndex], length);
        }
        
        for(Edge e: edges){
            graph.addEdge(e.getFromVertex(), e.getToVertex(), e.getWeight());
        }
        
        System.out.print("Find shortest path to vertex: ");
        while(!validation.isValidInput(2));
        String destination = validation.getStrInput();
        
        Algorithm dijkstra = new Algorithm(graph, vertices[0].getLabel());
        System.out.println(dijkstra.getDistance(destination));
        System.out.println(dijkstra.getPath(destination));
    }
    /**
     * Method that initializes vertices and edges to perform Algorithm's algorithm.
     * @param graphModel The graph model to traverse and reiterate
     * @param sourceVertex The starting Vertex label
     */
    public Algorithm(Graph graphModel, String sourceVertex){
        graph = graphModel;
        Set<String> vertexKeys = graph.vertexKeys();
        initialVertexLabel = sourceVertex;
        vertexConnections = new HashMap<>();
        distances = new HashMap<>();
        availableVertices = new PriorityQueue<>(vertexKeys.size(), new Comparator<Vertex>(){
            
            public int compare(Vertex one, Vertex two){
                int weightOne = Algorithm.this.distances.get(one.getLabel());
                int weightTwo = Algorithm.this.distances.get(two.getLabel());
                return weightOne - weightTwo;
            }
        });
        
        visitedVertices = new HashSet<Vertex>();
        
        //for each Vertex in the graph
        for(String key: vertexKeys){
            vertexConnections.put(key, null);
            distances.put(key, Integer.MAX_VALUE); //initialize to INFINITY
        }
               
        //set initial vertex to 0, dito ka magsstart
        distances.put(sourceVertex, 0);
        
        //initialize neighbors of initial vertex
        Vertex initialVertex = graph.getVertex(sourceVertex);
        ArrayList<Edge> initialVertexNeighbors = initialVertex.getNeighbors();
        for(Edge e : initialVertexNeighbors){
            Vertex other = e.getNeighbor(initialVertex);
            vertexConnections.put(other.getLabel(), sourceVertex);
            distances.put(other.getLabel(), e.getWeight());
            availableVertices.add(other);
        }        
        visitedVertices.add(initialVertex);       
        processVertices();     
    }   
    /**
     * This method applies Djikstra's algorithm to the graph using the Vertices
     * specified by initialVertexLabel as the starting point. 
     */
    private void processVertices(){       
        //habang di pa ubos lahat ng edges
        while(availableVertices.size() > 0){            
            //pick the lowest vertex, nasa pinaka head ng PQ yun
            Vertex next = availableVertices.poll();
            int distanceToNext = distances.get(next.getLabel());           
            //and for each available neighbor of the chosen vertex
            List<Edge> nextNeighbors = next.getNeighbors();     
            for(Edge e: nextNeighbors){
                Vertex other = e.getNeighbor(next);
                if(visitedVertices.contains(other)){
                    continue; //kung nasa kabilang neighbor yung nasa visited, tuloy lang
                }
                int currentWeight = distances.get(other.getLabel());
                int newWeight = distanceToNext + e.getWeight();     //bagong weight nung chinecheck na edge
                
                if(newWeight < currentWeight){ //kung mas mababa siya, itake as the new weight niya
                    vertexConnections.put(other.getLabel(), next.getLabel());
                    distances.put(other.getLabel(), newWeight);
                    availableVertices.remove(other);
                    availableVertices.add(other);
                }               
            }
            visitedVertices.add(next); //put the visited vertex
        }
    }    
    /**
     * Method that returns the shortest path calculated.
     * @param destinationVertex The Vertex whose shortest path from the initial Vertex is desired
     * @return List of Vertex objects starting at the initial vertex up to the destination vertex
     */
    public List<Vertex> getPath(String destinationVertex){
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        path.add(graph.getVertex(destinationVertex));
        
        while(!destinationVertex.equals(initialVertexLabel)){
            Vertex predecessor = graph.getVertex(vertexConnections.get(destinationVertex));
            destinationVertex = predecessor.getLabel();
            path.add(0, predecessor);
        }
        return path; 
    }   
    /**
     * Method that returns the distance of the vertex specified.
     * @param destinationVertex the distance of this vertex from the initial Vertex
     * @return int The distance from the initial Vertex to the Vertex specified by destination vertex
     */
    public int getDistance(String destinationVertex){
        return distances.get(destinationVertex);
    }
    private Graph graph;
    private String initialVertexLabel;
    private HashMap<String, String> vertexConnections;
    private HashMap<String, Integer> distances; 
    private PriorityQueue<Vertex> availableVertices;
    private HashSet<Vertex> visitedVertices;
}
