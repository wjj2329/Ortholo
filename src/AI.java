/**
 * Created by Alex Neville on 5/9/17.
 * AI: Class to encapsulate the AI computer player
 *  for our Reversi implementation.
 */
public class AI
{
    /**
     * Default constructor.
     */
    public AI()
    {

    }
   private PotentialMove root;

    public PotentialMove getRoot() {
        return root;
    }

    public void setRoot(PotentialMove root) {
        this.root = root;
    }

    /**
     * Determines optimal move for the AI to make based on the decision tree.
     * Use PotentialMove class.
     * 1. Build tree of potential decisions
     * 2. ...
     * @param depthLimit
     * @return
     */
    public PotentialMove calculateOptimalMove(int depthLimit)
    {

        return null;
    }
    public void createTree(int depthLimit)
    {
        createTreeInternal(depthLimit, root);
    }

    /**
     * Creates tree of all possible decisions.
     * @param depthLimit: max depth limit, specified in command line arguments
     * @param current: the current move we are building the children for
     */
    private void createTreeInternal(int depthLimit, PotentialMove current)
    {
        if (current.getDepthLevel() >= depthLimit)
        {
            return;
        }
        current.updateChildren();
        for (PotentialMove child : current.getChildren())
        {
            createTreeInternal(depthLimit, child);
        }
    }

    public void printTree(int depthLimit)
    {
        printTreeInternal(depthLimit, root);
    }

    private void printTreeInternal(int depthLimit, PotentialMove current)
    {
        if (current.getDepthLevel() >= depthLimit)
        {
            return;
        }
        current.print();
        for (PotentialMove child : current.getChildren())
        {
            printTreeInternal(depthLimit, child);
        }
    }
}
