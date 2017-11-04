package com.jjj.shortestpath;
/**
 * This class serves as the Graph model.
 * @author jjjimenez
 */
import java.util.*;
public class Graph {   
    public Graph(){
        vertices = new HashMap<String, Vertex>();
        edges = new HashMap<Integer, Edge>();
    } 
    /**
     * Constructor that accepts an arraylist and instantiates them as an object of Vertex. 
     * @param vertices The initial Vertices to instantiate this Graph
     */
    public Graph(ArrayList<Vertex> vertices){
        this.vertices = new HashMap<String, Vertex>();
        edges = new HashMap<Integer, Edge>();       
        for(Vertex v: vertices){
            this.vertices.put(v.getLabel(), v);
        }      
    }
    /**
     * Method that returns the labels/names of the Graph's vertex instantiations.
     * @return The unique labels/names of the Graph's Vertex objects
     */
    public Set<String> vertexKeys(){
        return this.vertices.keySet();
    }   
    /**
     * Method that returns the edges of this graph
     * @return the Edges of this graph
     */
    public Set<Edge> getEdges(){
        return new HashSet<Edge>(this.edges.values());
    }
    /**
     * Method that takes in two parameters that adds an edge between Vertices from and two of weight 1.
     * @param from The first vertex to add
     * @param to The second vertex to add
     * @return true if no same edges exists in the Graph
     */
    public boolean addEdge(Vertex from, Vertex to){
        return addEdge(from, to, 1);
    }   
    /**
     * Method that takes three parameters that adds an edge between vertices with a specified weight.
     * @param from The first Vertex of the Edge
     * @param to The second Vertex of the Edge
     * @param weight The weight of the Edge
     * @return true if no Edge already exists in the Graph
     */
    public boolean addEdge(Vertex from, Vertex to, int weight){
        if(from.equals(to)){
            return false;   
        }       
        //ensures the Edge is not in the Graph
        Edge e = new Edge(from, to, weight);
        if(edges.containsKey(e.hashCode())){
            return false;
        }      
        //and that the Edge isn't a neighbor of another vertex
        else if(from.hasNeighbor(e) || to.hasNeighbor(e)){
            return false;
        }          
        edges.put(e.hashCode(), e);
        from.addNeighbor(e);
        to.addNeighbor(e);
        return true;
    }   
    /**
     * Method that looks up if the graph contains this edge
     * @param edge The Edge to look up
     * @return true if this Graph contains this edge
     */
    public boolean containsEdge(Edge edge){
        if(edge.getFromVertex() == null || edge.getToVertex() == null){
            return false;
        }
        
        return this.edges.containsKey(edge.hashCode());
    }
     /**
     * Method that looks up for the specified Vertex
     * @param vertex The Vertex to look up
     * @return true if this Graph contains the specified vertex
     */
    public boolean containsVertex(Vertex vertex){
        return this.vertices.get(vertex.getLabel()) != null;
    }
    /**
     * Method that removes the specified edge on the graph
     * including each vertex's neighbor.
     * @param e The Edge to be removed from the Graph
     * @return Edge The Edge removed from the Graph
     */
    public Edge removeEdge(Edge e){
       e.getFromVertex().removeNeighbor(e);
       e.getToVertex().removeNeighbor(e);
       return this.edges.remove(e.hashCode());
    } 
    /**
     * Method that gets a specified vertex corresponding to the specified label.
     * @param label The specified Vertex label
     * @return Vertex The Vertex with the specified label
     */
    public Vertex getVertex(String label){
        return vertices.get(label);
    } 
    /**
     * This method adds a Vertex to the graph. If a Vertex with the same label
     * currently exists, then it is overwritten.
     * 
     * @param vertex the verted to be added
     * @param overwriteExisting boolean value to cheak if vertex is already existing
     * @return true if vertex was successfully added to the Graph
     */
    public boolean addVertex(Vertex vertex, boolean overwriteExisting){
        Vertex current = this.vertices.get(vertex.getLabel());
        if(current != null){
            if(!overwriteExisting){
                return false;
            }           
            while(current.getNeighborTotalCount() > 0){
                this.removeEdge(current.getNeighbor(0));
            }
        }     
        vertices.put(vertex.getLabel(), vertex);
        return true;
    }   
    /**
     * Method that removes the vertex corresponding to the specified label.
     * @param label the label of the Vertex to be removed
     * @return Vertex the removed instantiation of Vertex
     */
    public Vertex removeVertex(String label){
        Vertex v = vertices.remove(label);      
        while(v.getNeighborTotalCount() > 0){
            this.removeEdge(v.getNeighbor((0)));
        }       
        return v;
    }    
    private HashMap<String, Vertex> vertices;
    private HashMap<Integer, Edge> edges;   
}
