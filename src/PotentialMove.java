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

    /**
     * Constructor for PotentialMove.
     * When we create the ROOT MOVE, set depth level to 1.
     * @param location: see description
     * @param whoMadeMove: see description
     * @param colorOfPlayerWhoMadeMove: see description
     */
    public PotentialMove(Coordinate location, main.Turn whoMadeMove, main.Tile colorOfPlayerWhoMadeMove,
                         main.Tile[][] potentialBoard, main.State playingState, int depthLevel)
    {
        children = new ArrayList<>();
        this.location = location;
        this.whoMadeMove = whoMadeMove;
        this.colorOfPlayerWhoMadeMove = colorOfPlayerWhoMadeMove;
        this.potentialBoard = potentialBoard;
        this.playingState = playingState;
        this.depthLevel = depthLevel;
    }

    /**
     * Utility function for building our moves minimax tree.
     * @param coordinates: which moves it could make from here
     */
    public void updateChildren(ArrayList<Coordinate> coordinates)
    {
        for (Coordinate c : coordinates)
        {
            children.add(new PotentialMove(c, getOtherPlayer(), main.oppositecolor(colorOfPlayerWhoMadeMove),
                    updateChildBoard(), playingState, depthLevel + 1));
        }
    }

    /**
     * Analyzing the potential subsequent moves.
     * Creates each of our potential moves.
     * This updates the board AS IF THE MOVE HAS ALREADY BEEN PLAYED,
     *  EVEN THOUGH IT HASN'T!
     * @return the updated child board.
     */
    private main.Tile[][] updateChildBoard()
    {
        main.Tile[][] newBoard = potentialBoard.clone();
        // double check: should we use current player or OPPOSITE player?
        if (main.canplacepiece(newBoard, location.x, location.y, colorOfPlayerWhoMadeMove,
                main.dimensions, playingState, true))
        {

        }
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
}
