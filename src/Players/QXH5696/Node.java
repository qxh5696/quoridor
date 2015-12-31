package Players.QXH5696;

/**
 * Created by qadirhaqq on 3/10/15.
 */
/*
 * Node.java
 *
 * Representation of a graph ndoe.
 *
 */

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a node in a graph.
 *
 * @author atd Aaron T Deever
 *
 */
import Interface.Coordinate;
public class Node {

    /*
     *  Name associated with this node.
     */
    private Coordinate location;

    /*
     * Neighbors of this node are stored as a list (adjacency list).
     */
    private List<Node> neighbors;

    /**
     * Constructor.  Initialized with an empty list of neighbors.
     *
     * @param location location representing the point on a board.
    */
    public Node(Coordinate location) {
        this.location = location;
        this.neighbors = new LinkedList<Node>();
    }

    public Node setLocation(int x, int y){
        Coordinate coor = new Coordinate(x, y);
        Node node = new Node(coor);
        return node;
    }

    /**
     * Get the Coordinate associated with this object.
     * @return coordinate.
     */
    public Coordinate getLocation() {
        return this.location;
    }

    /**
     * Add a neighbor to this node.  Checks if already present, and does not
     * duplicate in this case.
     *
     * @param n: node to add as neighbor.
     */
    public void addNeighbor(Node n) {
        if(!neighbors.contains(n)) {
            neighbors.add(n);
            n.neighbors.add(this);
        }
    }

    /**
     * Method to return the adjacency list for this node containing all
     * of its neighbors.
     *
     * @return the list of neighbors of the given node
     */
    public List<Node> getNeighbors() {
        return new LinkedList<Node>(neighbors);
    }

    /**
     * Reciprocally removes neighbors from one another
     * @param node node the person wants to be removed
     */
    public void removeNeighbor(Node node){
        //Node tempNode;
        /*
        for (int i = 0; i < this.neighbors.size(); i++){
            if(this.neighbors.get(i).getLocation().getRow() == node.getLocation().getRow()){
                if(this.neighbors.get(i).getLocation().getCol() == node.getLocation().getCol()){
                    tempNode = this.neighbors.get(i);
                    this.neighbors.remove(i);
                    tempNode.removeNeighbor(this);
                }
            }
        }
        */
        this.neighbors.remove(node);
        node.neighbors.remove(this);
    }

    /**
     * Method to generate a string associated with the node, including the
     * name of the node followed by the names of its neighbors.  Overrides
     * Object toString method.
     *
     * @return string associated with the node.
     */
    @Override
    public String toString() {
        String result;
        result = location.toString() + " : Neighbors(";

        for(Node nbr : neighbors) {
            result += nbr.getLocation().toString() + ",";
        }
        //get rid of Commas
        result = result.substring(0, result.length()-1);
        result += ")";
        return result;
    }

    /**
     *  Two Nodes are equal if they have the same coordinate.
     *  @param other The other object to check equality with
     *  @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Node) {
            Node n = (Node) other;
            if (this.getLocation().getRow() == n.getLocation().getRow()){
                if(this.getLocation().getCol() == n.getLocation().getCol()){
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * The hash code of a Node is just the hash code of the name,
     * since no two nodes can have the same name.
     */
    @Override
    public int hashCode() {
        return this.location.hashCode();
    }
}
