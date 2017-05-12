import java.util.ArrayList;

/**
 * Created by Alex Neville on 5/9/17.
 * PotentialMove: Class designed to encapsulate a POTENTIAL move on the game board.
 */
public class PotentialMove
{
    /**
     * Set of potential moves that theoretically could occur AFTER this one.
     * Always opponent's move.
     */
    private ArrayList<PotentialMove> children = null;



    public boolean isvisited=false;
    public boolean falseintialvalue=true;

    public PotentialMove parent;

    public ArrayList<PotentialMove> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<PotentialMove> children) {
        this.children = children;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public main.Turn getWhoMadeMove() {
        return whoMadeMove;
    }

    public void setWhoMadeMove(main.Turn whoMadeMove) {
        this.whoMadeMove = whoMadeMove;
    }

    public main.Tile getColorOfPlayerWhoMadeMove() {
        return colorOfPlayerWhoMadeMove;
    }

    public void setColorOfPlayerWhoMadeMove(main.Tile colorOfPlayerWhoMadeMove) {
        this.colorOfPlayerWhoMadeMove = colorOfPlayerWhoMadeMove;
    }

    public main.Tile[][] getPotentialBoard() {
        return potentialBoard;
    }

    public void setPotentialBoard(main.Tile[][] potentialBoard) {
        this.potentialBoard = potentialBoard;
    }

    public main.State getPlayingState() {
        return playingState;
    }

    public void setPlayingState(main.State playingState) {
        this.playingState = playingState;
    }

    public int getDepthLevel() {
        return depthLevel;
    }

    public void setDepthLevel(int depthLevel) {
        this.depthLevel = depthLevel;
    }

    /**
     * Where this move is going to occur.
     * For root, this will be null.
     * So pass in null to root's constructor.
     */
    private Coordinate location = null;

    /**
     * Player that made this move.
     */
    private main.Turn whoMadeMove = null;

    /**
     * Corresponding color of player.
     */
    private main.Tile colorOfPlayerWhoMadeMove = null;

    /**
     * The board we could have
     */
    private main.Tile[][] potentialBoard = null;

    /**
     * Whenever we need this class, we will only ever be in playing state.
     */
    private main.State playingState = null;

    /**
     * How deep we are in
     */
    private int depthLevel = 0;

    public int numPiecesPotentiallyGainedOrLost = 0;

    /**
     * Constructor for PotentialMove.
     * When we create the ROOT MOVE, set depth level to 1.
     * @param location: see description
     * @param whoMadeMove: see description
     * @param colorOfPlayerWhoMadeMove: see description
     */
    public PotentialMove(Coordinate location, main.Turn whoMadeMove, main.Tile colorOfPlayerWhoMadeMove,
                         main.Tile[][] potentialBoard, main.State playingState, int depthLevel, PotentialMove parent)
    {
        children = new ArrayList<>();
        this.location = location;
        this.whoMadeMove = whoMadeMove;
        this.colorOfPlayerWhoMadeMove = colorOfPlayerWhoMadeMove;
        this.potentialBoard = potentialBoard;
        this.playingState = playingState;
        this.depthLevel = depthLevel;
        this.parent=parent;
    }

    /**
     * Utility function for building our moves minimax tree.
     */
    public void updateChildren()
    {
        ArrayList<Coordinate> coordinates=main.whereicanplay(potentialBoard, colorOfPlayerWhoMadeMove, main.dimensions, main.State.RegularGame);
        for (Coordinate c : coordinates)
        {
            PotentialMove l=new PotentialMove(c, getOtherPlayer(), main.oppositecolor(colorOfPlayerWhoMadeMove),
                    updateChildBoard(c), playingState, depthLevel + 1, this);


            l.setNumPiecesPotentiallyGainedOrLost(main.numberofpeicesplayed);
            //System.out.println(l.toString());
            children.add(l);
        }
    }

    /**
     * Analyzing the potential subsequent moves.
     * Creates each of our potential moves.
     * This updates the board AS IF THE MOVE HAS ALREADY BEEN PLAYED,
     *  EVEN THOUGH IT HASN'T!
     * @return the updated child board.
     */

    private main.Tile[][] updateboard()
    {
        main.Tile[][]myboard=new main.Tile[main.dimensions][main.dimensions];
        for(int i=0; i<main.dimensions; i++)
        {
            for(int j=0; j<main.dimensions; j++)
            {
                myboard[i][j]=this.potentialBoard[i][j];
            }
        }
        return myboard;
    }

    public void setNumPiecesPotentiallyGainedOrLost(int numPiecesPotentiallyGainedOrLost) {
        this.numPiecesPotentiallyGainedOrLost = numPiecesPotentiallyGainedOrLost;
    }

    private main.Tile[][] updateChildBoard(Coordinate c)
    {
        //System.out.println("Before");
       // main.printboard(potentialBoard, main.dimensions);
        main.Tile[][] newBoard = updateboard();
        //System.out.println("After");
        //System.out.println("With coordinates "+this.location.toString());
        //main.printboard(newBoard, main.dimensions);
        // double check: should we use current player or OPPOSITE player?
     main.canplacepiece(newBoard, c.x, c.y, colorOfPlayerWhoMadeMove,
                main.dimensions, playingState, true);
        newBoard[c.x][c.y]=this.getColorOfPlayerWhoMadeMove();


        return newBoard;
    }

    /**
     * Utility method
     * @return the other player
     */
    private main.Turn getOtherPlayer()
    {
        switch(whoMadeMove)
        {
            case AI:
                return main.Turn.Player;
            default:
                return main.Turn.AI;
        }
    }

    public void print()
    {
        System.out.println(toString());
    }




    /**
     * This does a BFS to print out
     */
    @Override
    public String toString()
    {
        // color, player who is making the turn (so min if player), numPiecesGained,
        StringBuilder sol = new StringBuilder();
        sol.append("THIS IS THE PRINT BOARD FOR BELOW");
        main.printboard(potentialBoard, main.dimensions);
        sol.append("PRINTING OUT POTENTIAL MOVE NODE:\n");
        sol.append("Color: " + colorOfPlayerWhoMadeMove + "\n");
        sol.append("Player Making Move: " + whoMadeMove + "\n");
        sol.append("Number of Potential Pieces This Move Could Gain or Lose: " + numPiecesPotentiallyGainedOrLost + "\n");
        sol.append("Coordinates are: "+location.toString()+"\n");
        sol.append("The depth limit I am at is "+depthLevel+'\n');
        return sol.toString();

    }
}
