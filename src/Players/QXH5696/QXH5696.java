package Players.QXH5696;
import Engine.Logger;
import Interface.Coordinate;
import Interface.PlayerModule;
import Interface.PlayerMove;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by qadirhaqq on 3/16/15.
 */
public class QXH5696 implements PlayerModule {

    //Variables I Added
    private Logger logger;
    private Map<Integer, Coordinate> playerHomes = new HashMap<Integer, Coordinate>();
    private Map<Integer, Integer> playerWalls = new HashMap<Integer, Integer>();
    private int numWalls;
    private static final int MAX_ROWS = 9;
    private static final int MAX_COLUMNS = 9;
    private static final Node[][] board = new Node[MAX_ROWS][MAX_COLUMNS];
    private int playerId;
    //

    /**
     * Initializes your player module. In this method, be sure to set up your data structures and pre-populate them with
     * the starting board configuration. All state should be stored in your player class.
     * @param logger reference to the logger class
     * @param playerId the id of this player (1 up to 4, for a four-player game)
     * @param numWalls the number of walls this player has
     * @param playerHomes locations of other players (null coordinate means invalid player; 1-based indexing)
     */
    @Override
    public void init(Logger logger, int playerId, int numWalls, Map<Integer, Coordinate> playerHomes) {
        this.numWalls = numWalls;
        Coordinate coor;
        this.playerId = playerId;
        this.logger = logger;
        if (this.playerId == 1){
            coor = new Coordinate(8,4);
            this.playerHomes.put(playerId, coor);
        }else if (this.playerId == 2){
            coor = new Coordinate(0,4);
            this.playerHomes.put(playerId, coor);
        }else if (this.playerId == 3){
            coor = new Coordinate(4,0);
            this.playerHomes.put(playerId, coor);
        }else{
            coor = new Coordinate(4,8);
            this.playerHomes.put(playerId, coor);
        }
        this.playerWalls.put(playerId, this.numWalls);
        for (int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                board[i][j] = new Node(new Coordinate(i, j));
            }
        }
        //top left corner
        board[0][0].addNeighbor(board[0][1]);
        board[0][0].addNeighbor(board[1][0]);
        //top right corner
        board[0][8].addNeighbor(board[0][7]);
        board[0][8].addNeighbor(board[1][8]);
        //bottom left corner
        board[8][0].addNeighbor(board[8][1]);
        board[8][0].addNeighbor(board[7][0]);
        //bottom right corner
        board[8][8].addNeighbor(board[8][7]);
        board[8][8].addNeighbor(board[7][8]);
        for (int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                    if (!(i+1 > MAX_ROWS-1)){
                        board[i][j].addNeighbor(board[i+1][j]);
                    }
                    if (!(j+1 > MAX_COLUMNS-1)){
                        board[i][j].addNeighbor(board[i][j+1]);
                    }
                    if (!(i-1 < 0)){
                        board[i][j].addNeighbor(board[i-1][j]);
                    }
                    if (!(j-1 < 0)){
                        board[i][j].addNeighbor(board[i][j-1]);
                    }
            }
        }
    }

    /**
     * Notifies you that a move was just made. Use this function to update your board state accordingly. You may assume
     * that all moves are given to you in the order that they are made.
     * @param playerMove
     */
    @Override
    public void lastMove(PlayerMove playerMove) {
        if (playerMove.isMove() == true) {
            Coordinate newCor = board[playerMove.getEndRow()][playerMove.getEndCol()].getLocation();
            playerHomes.put(playerMove.getPlayerId(), newCor);
        }
        else { //playerMove is false
            int newWallVal = playerWalls.get(playerMove.getPlayerId())-1;
            playerWalls.put(playerMove.getPlayerId(), newWallVal);
            Coordinate start = playerMove.getStart();
            Coordinate end = playerMove.getEnd();
            if (start.getRow() == end.getRow()){
                if (start.getRow() == 9 && end.getRow() == 9){
                    board[start.getRow()][start.getCol()].removeNeighbor(null);
                    board[end.getRow()][end.getCol()].removeNeighbor(null);
                }
                else if (start.getCol() == 9){
                    board[start.getRow()][start.getCol()].removeNeighbor(null);
                    board[end.getRow()][end.getCol()].removeNeighbor(null);
                }
                else if (end.getCol() == 9){//start = [4,7] , end = [4,9]
                    board[start.getRow()][start.getCol()].removeNeighbor(board[start.getRow()-1][start.getCol()]);//[4,7] removes [3,7]
                    board[end.getRow()][end.getCol()-1].removeNeighbor(board[end.getRow()-1][end.getCol()-1]);//[4,8] removes [3,8]
                }else{//start = [4,4] end = [4,6]
                    board[start.getRow()][start.getCol()].removeNeighbor(board[start.getRow()-1][start.getCol()]);//[4,4] removes [3,4]
                    board[end.getRow()][end.getCol()-1].removeNeighbor(board[end.getRow()-1][end.getCol()-1]);//[4,5] removes [3,5]
                }
            }
            else if (start.getCol() == end.getCol()){
                if (start.getCol() == 9 && end.getCol() == 9){
                    board[start.getRow()][start.getCol()].removeNeighbor(null);
                    board[end.getRow()][end.getCol()].removeNeighbor(null);
                }
                else if (start.getRow() == 9){
                    board[start.getRow()][start.getCol()].removeNeighbor(null);
                    board[end.getRow()][end.getCol()].removeNeighbor(null);
                }
                else if (end.getRow() == 9){
                    board[start.getRow()][start.getCol()].removeNeighbor(board[start.getRow()][start.getCol()-1]);//[7,8] removes [7,7]
                    board[end.getRow()-1][end.getCol()].removeNeighbor(board[end.getRow()-1][end.getCol()-1]);//[8,8] removes [8,7]
                }else{
                    board[start.getRow()][start.getCol()].removeNeighbor(board[start.getRow()][start.getCol()-1]);//[4,4] removes [4,3]
                    board[end.getRow()-1][end.getCol()].removeNeighbor(board[end.getRow()-1][end.getCol()-1]);//[5,4] removes [5,3]
                }
            }
        }
    }

    @Override
    public void playerInvalidated(int i) {

    }

    @Override
    public PlayerMove move() {
        return null;
    }

    /**
     * Return the 1-based player ID of this player.
     * @return the 1-based player ID of this player.
     */
    @Override
    public int getID() {
        return playerId;
    }

    /**
     * Returns the subset of the four adjacent cells to which a piece could move due to lack of walls. The system calls
     * this function only to verify that a Player's implementation is correct. However it is likely also handy for most
     * strategy implementations.
     * @param coordinate the "current location"
     * @return a set of adjacent coordinates (up-down-left-right only) that are not blocked by walls
     */
    @Override
    public Set<Coordinate> getNeighbors(Coordinate coordinate) {
        Set<Coordinate> neighbors = new HashSet<Coordinate>();
        int xVal =coordinate.getRow();
        int yVal = coordinate.getCol();
        for (int i = 0; i < board[xVal][yVal].getNeighbors().size(); i++){
            neighbors.add(board[xVal][yVal].getNeighbors().get(i).getLocation());
        }
        return neighbors;
    }

    /**
     * Returns any valid shortest path between two coordinates, if one exists. The system calls this function only to
     * verify that your implementation is correct. You may also use it to test your code.
     * @param start the start coordinate
     * @param end the end coordinate
     * @return an ordered list of Coordinate objects representing a path. If no path exists, return an empty list.
     */
    @Override
    public List<Coordinate> getShortestPath(Coordinate start, Coordinate end){
        List<Coordinate> shortestPath = new ArrayList<Coordinate>();
        if (start.equals(end)){
            shortestPath.add(0, start);
            return shortestPath;
        }
        List<Node> list = searchBFS(start, end);
        Coordinate temp;
        for (int i = 0; i < list.size(); i ++){
            shortestPath.add(list.get(i).getLocation());
        }
        return shortestPath;
    }


    /**
     * Get the remaining walls for your player.
     * @param i  1-based player ID number
     * @return
     */
    @Override
    public int getWallsRemaining(int i) {
        if(!(playerHomes.containsKey(i))){
            if (i == 1){
                playerWalls.put(i, this.numWalls);
            }
            else if (i == 2){
                playerWalls.put(i, this.numWalls);
            }else if (i == 3){
                playerWalls.put(i, this.numWalls);
            }else if (i == 4){
                playerWalls.put(i, this.numWalls);
            }
        }
        return playerWalls.get(i);
    }

    /**
     * Get the location of a given player.
     * @param i 1-based player ID number
     * @return the location of a given player
     */
    @Override
    public Coordinate getPlayerLocation(int i) {
        Coordinate coor;
        if (!playerHomes.containsKey(i)) {
            if (i == 2) {
                coor = new Coordinate(0, 4);
                this.playerHomes.put(i, coor);
            } else if (i == 3) {
                coor = new Coordinate(4, 0);
                this.playerHomes.put(i, coor);
            } else {
                coor = new Coordinate(4, 8);
                this.playerHomes.put(i, coor);
            }
        }
        return playerHomes.get(i);
    }

    /**
     * Get the location of every player. (1-based index)
     * @return a map representation of the location of every player.
     */
    @Override
    public Map<Integer, Coordinate> getPlayerLocations() {
        return playerHomes;
    }

    @Override
    public Set<PlayerMove> allPossibleMoves() {
        return null;
    }
    /**
     * Implementation of BFS to work with the coordinates of the board
     * @param start coordinate to start at
     * @param finish coordinate to end at
     * @return path of nodes, if no route is found, return list is empty
     */
    private List<Node> searchBFS(Coordinate start, Coordinate finish) {
        // assumes input check occurs previously
        Node startNode, finishNode;
        startNode = board[start.getRow()][start.getCol()];
        finishNode = board[finish.getRow()][finish.getCol()];

        // prime the dispenser (queue) with the starting node
        List<Node> dispenser = new LinkedList<Node>();
        dispenser.add(startNode);

        // construct the predecessors data structure
        Map<Node, Node> predecessors = new HashMap<Node,Node>();
        // put the starting node in, and just assign itself as predecessor
        predecessors.put(startNode, startNode);

        // loop until either the finish node is found, or the
        // dispenser is empty (no path)
        while (!dispenser.isEmpty()) {
            Node current = dispenser.remove(0);
            if (current == finishNode) {
                break;
            }
            // loop over all neighbors of current
            for (Node nbr : current.getNeighbors()) {
                // process unvisited neighbors
                if(!predecessors.containsKey(nbr)) {
                    predecessors.put(nbr, current);
                    dispenser.add(nbr);
                }
            }
        }

        return constructPath(predecessors, startNode, finishNode);
    }
    /**
     * Method to return a path from the starting to finishing node.
     *
     * @param predecessors Map used to reconstruct the path
     * @param startNode starting node
     * @param finishNode finishing node
     * @return a list containing the sequence of nodes comprising the path.
     * Empty if no path exists.
     */
    private List<Node> constructPath(Map<Node,Node> predecessors,
                                     Node startNode, Node finishNode) {
        // use predecessors to work backwards from finish to start,
        // all the while dumping everything into a linked list
        List<Node> path = new LinkedList<Node>();

        if(predecessors.containsKey(finishNode)) {
            Node currNode = finishNode;
            while (currNode != startNode) {
                path.add(0, currNode);
                currNode = predecessors.get(currNode);
            }
            path.add(0, startNode);
        }

        return path;
    }

}

