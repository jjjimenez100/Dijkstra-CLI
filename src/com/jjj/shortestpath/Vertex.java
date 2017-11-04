package com.jjj.shortestpath;
/**
 * This class serves as the vertex model.
 * @author jjjimenez
 */
import java.util.ArrayList;
public class Vertex {    
    /**
     * Constructor for the Vertex Class
     * @param name the name of this vertex
     */
    public Vertex(String name){
        label = name;
        neighbor = new ArrayList<Edge>();
    }
     /**
     * Method that creates a string representation of this vertex
     * @return String string representation of this vertex
     */
    public String toString(){
        return "Vertex " + label;
    }
     /**
     * Method that returns the total number of neighbor.
     * @return int the total number of neighbors of this Vertex
     */
    public int getNeighborTotalCount(){
        return neighbor.size();
    }  
    /**
     * Method that returns the label/name specified on this vertex.
     * @return String The label/name of this Vertex
     */
    public String getLabel(){
        return this.label;
    }       
    /**
     * Method that returns a copy of this neighbor as an ArrayList.
     * @return ArrayList a copy of this neighbor
     */
    public ArrayList<Edge> getNeighbors(){
        return new ArrayList<Edge>(this.neighbor);
    }
    /**
     * Method that adds an edge if the current edge is not present. 
     * @param edge The edge to add
     */
    public void addNeighbor(Edge edge){
        if(neighbor.contains(edge)){
            return; //do nothing and terminate the method kapag andun na
        }      
        neighbor.add(edge); //else
    }   
    /**
     * Method that checks if the edge is already contained as a neighbor.
     * @param otherEdge the edge to search
     * @return returns true if it is contained in the neighbor
     */
    public boolean hasNeighbor(Edge otherEdge){
        return neighbor.contains(otherEdge);
    }  
    /**
     * Method that gets the neighbor instance.
     * @param index index of the Edge to retrieve
     * @return Edge edge at the specified index in the neighbor
     */
    public Edge getNeighbor(int index){
        return neighbor.get(index);
    }  
    /**
     * Method that receives an integer as index and removes the edge with the specified index.
     * @param index The index of the edge to remove from the neighbor
     * @return Edge The removed Edge
     */
    public Edge removeNeighbor(int index){
        return neighbor.remove(index);
    } 
    /**
     * Alternative method that removes an edge from a neighbor without returning any data.
     * @param edge The Edge to remove from the neighbor
     */
    public void removeNeighbor(Edge edge){
        this.neighbor.remove(edge);
    }   
    private ArrayList<Edge> neighbor;
    private String label;
}
