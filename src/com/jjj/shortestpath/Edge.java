package com.jjj.shortestpath;
/**
 * This class serves as the Edge model.
 * @author jjjimenez
 */
public class Edge implements Comparable<Edge> { 
     /**
     * Constructor that takes three parameters, including the weight of the edge.
     * @param from The first vertex in the Edge
     * @param to The second vertex of the Edge
     * @param weight The weight of this Edge
     */
    public Edge(Vertex from, Vertex to, int weight){
        fromVertex = (from.getLabel().compareTo(to.getLabel()) <= 0) ? from : to;
        toVertex = (fromVertex == from) ? to : from;
        this.weight = weight;
    }
    /**
     * Constructor that takes two parameters, two vertices, then calls the other overloaded constructor, with a parameter of 1.
     * @param from The first vertex in the Edge
     * @param to The second vertex in the Edge
     */
    public Edge(Vertex from, Vertex to){
        this(from, to, 1);
    } 
     /**
     * Method that returns a string representation of this edge
     * @return String a String representation of this Edge
     */
    public String toString(){
        return "({" + fromVertex + ", " + toVertex + "}, " + weight + ")";
    }
    /**
     * Method that sets the weight of this edge
     * @param weight The new weight of this Edge
     */
    public void setWeight(int weight){
        this.weight = weight;
    } 
    /**
     * Method that checks for the neighboring vertex of a specified vertex.
     * @param current the current vertex being traversed.
     * @return The neighbor of current along the Edge
     */
    public Vertex getNeighbor(Vertex current){
        if(!(current.equals(fromVertex) || current.equals(toVertex))){
            return null;
        }       
        return (current.equals(fromVertex)) ? toVertex : fromVertex;
    }   
    /**
     * Method that returns fromVertex, the root vertex that is connected toVertex
     * @return Vertex this.one
     */
    public Vertex getFromVertex(){
        return fromVertex;
    }   
    /**
     * Method that returns toVertex, the vertex that is connected to the root vertex fromVertex
     * @return Vertex this.two
     */
    public Vertex getToVertex(){
        return toVertex;
    }    
    /**
     * Method that returns the weight of this edge
     * @return int The weight of this Edge
     */
    public int getWeight(){
        return weight;
    } 
    /**
     * Overrided method that implements the distinction of the current weight to the new weight.
     * @param other The Edge to compare against this
     * @return int this.weight - other.weight
     */
    public int compareTo(Edge other){
        return this.weight - other.weight;
    }     
    private Vertex fromVertex, toVertex;
    private int weight;
}
