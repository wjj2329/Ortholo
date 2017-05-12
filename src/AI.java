import java.util.HashMap;

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
    private void updatepriorityonregion(PotentialMove movetoupdate, int depthlimit)
    {
        if(movetoupdate.getLocation().x==0||movetoupdate.getLocation().x==main.dimensions-1||movetoupdate.getLocation().y==0||movetoupdate.getLocation().y==main.dimensions-1)
        {
            if(movetoupdate.getLocation().x==0&&movetoupdate.getLocation().y==0)
            {
                    movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost + 10;
            }
            if(movetoupdate.getLocation().x==0&&movetoupdate.getLocation().y==main.dimensions-1)
            {
                movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost + 10;
            }
            if(movetoupdate.getLocation().x==main.dimensions-1&&movetoupdate.getLocation().y==0)
            {
                movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost + 10;
            }
            if(movetoupdate.getLocation().x==main.dimensions-1&&movetoupdate.getLocation().y==main.dimensions-1)
            {
                movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost + 10;
            }
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost + 5;
        }

        Coordinate c=movetoupdate.getLocation();
        if(c.x==1&&c.y==1)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }
        if(c.x==0&&c.y==1)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }
        if(c.x==1&&c.y==0)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }

        if(c.x==main.dimensions-1&&c.y==main.dimensions-2)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }
        if(c.x==main.dimensions-2&&c.y==main.dimensions-1)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }
        if(c.x==main.dimensions-2&&c.y==main.dimensions-2)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }

        //get the other bad edges
        if(c.x==1&&c.y==main.dimensions-1)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }
        if(c.x==1&&c.y==main.dimensions-2)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }
        if(c.x==0&&c.y==main.dimensions-2)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }


        if(c.y==1&&c.x==main.dimensions-1)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }
        if(c.y==0&&c.x==main.dimensions-2)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }
        if(c.y==1&&c.x==main.dimensions-2)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -5;
        }

        if(movetoupdate.getChildren().size()==0&&movetoupdate.getDepthLevel()!=depthlimit)
        {
            movetoupdate.numPiecesPotentiallyGainedOrLost = movetoupdate.numPiecesPotentiallyGainedOrLost -10;
        }



    }

   private PotentialMove root;

    public PotentialMove getRoot() {
        return root;
    }

    public void setRoot(PotentialMove root) {
        this.root = root;
    }



    public PotentialMove leftmosthelper(PotentialMove move)
    {
        if(move==null)
        {
            return null;
        }
        if (move.getChildren().size()==0)
        {
            return null;
        }
        else
        {
            for(int i=0; i<move.getChildren().size(); i++)
            {
                if(!move.getChildren().get(i).isvisited)
                {
                    return move.getChildren().get(i);
                }
            }
        }
        return null;
    }

    public PotentialMove leftmost(PotentialMove root)
    {
        PotentialMove mymove=leftmosthelper(root);
        while(true)
        {
            if(leftmosthelper(mymove)!=null) {
                mymove = leftmosthelper(mymove);
            }
            else
            {
                break;
            }

        }
        return mymove;
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
        printTreeInternal(depthLimit,root);
        PotentialMove mymove=root;
        while(true)
        {
            mymove=leftmost(mymove);
            if(mymove==null)
            {
                break;
            }
            updatepriorityonregion(mymove,depthLimit);
            mymove.isvisited=true;
            //the one above needs to be updated.
            if(mymove.parent.getWhoMadeMove()== main.Turn.Player)
            {

                if(mymove.parent.falseintialvalue)
                {
                    mymove.parent.setNumPiecesPotentiallyGainedOrLost(mymove.numPiecesPotentiallyGainedOrLost);
                    mymove.parent.falseintialvalue=false;

                }
                else
                {
                    if(mymove.parent.numPiecesPotentiallyGainedOrLost>mymove.numPiecesPotentiallyGainedOrLost)
                    {
                        mymove.parent.setNumPiecesPotentiallyGainedOrLost(mymove.numPiecesPotentiallyGainedOrLost);
                    }
                }
                for(PotentialMove m:mymove.parent.getChildren())
                {
                    updatepriorityonregion(m,depthLimit);
                    if(m.numPiecesPotentiallyGainedOrLost>mymove.parent.numPiecesPotentiallyGainedOrLost)
                    {
                        m.isvisited=true;
                    }
                }
            }
            else
            {
                if(mymove.parent.falseintialvalue)
                {
                    mymove.parent.setNumPiecesPotentiallyGainedOrLost(mymove.numPiecesPotentiallyGainedOrLost);
                    mymove.parent.falseintialvalue=false;
                }
                else
                {
                    if(mymove.parent.numPiecesPotentiallyGainedOrLost<mymove.numPiecesPotentiallyGainedOrLost)
                    {
                        mymove.parent.setNumPiecesPotentiallyGainedOrLost(mymove.numPiecesPotentiallyGainedOrLost);
                    }
                }
                for(PotentialMove m:mymove.parent.getChildren())
                {
                    updatepriorityonregion(m,depthLimit);
                    if(m.numPiecesPotentiallyGainedOrLost<mymove.parent.numPiecesPotentiallyGainedOrLost)
                    {
                        m.isvisited=true;
                    }
                }
            }
            mymove=root;
        }
        int maxscore=-100;
        PotentialMove bestmove=null;
        for(PotentialMove move:root.getChildren())
        {
            if(move.numPiecesPotentiallyGainedOrLost>maxscore)
            {
                maxscore=move.numPiecesPotentiallyGainedOrLost;
                bestmove=move;
            }
        }
        System.out.println("This gets me a score for this round of "+maxscore);
        //printTreeInternal(depthLimit, root);


        return bestmove;
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
