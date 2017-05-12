import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by williamjones on 5/4/17.
 */
public class main
{
    private static AI computerPlayer;
    /**
     * The specified depth limit
     */
    private static int depthLimit;

    /**
     * Root move in the tree
     */
    public static PotentialMove root;

    /**
     * Dimensions of board -- passed in as command line argument
     */
    public static int dimensions;



    public static int numberofpeicesplayed=0;

    public enum Tile
    {
        Empty, Black, White
    }

    public enum Turn
    {
        Player, AI, Player2, AI2
    }


    public static Coordinate ai_taketurn(State state)
    {

        if(State.Startup==state)
        {
            Random myrandom=new Random();
            int x=myrandom.nextInt(mylocations.size());
            return mylocations.get(x);
        }

        computerPlayer.setRoot(root);
        computerPlayer.createTree(depthLimit);
        //computerPlayer.printTree(depthLimit);


        PotentialMove hi= computerPlayer.calculateOptimalMove(depthLimit); //returns best move found after all the calucations

        System.out.println("THE AI CHOOSES THIS LOCATION "+hi.getLocation().toString());
        return hi.getLocation();
    }

    private static void winner(Tile board[][], int dimension)
    {
        int black=0;
        int white=0;
        for(int i=0; i<dimension; i++)
        {
            for (int j=0; j<dimension; j++)
            {
                if(board[i][j]==Tile.White)
                {
                    white++;
                }
                if(board[i][j]==Tile.Black)
                {
                    black++;
                }

            }
        }
        if (black>white)
        {
            System.out.println("BLACK WINS!");
            System.out.println("Black has "+black+" number of stones");
            System.out.println("White has "+white+" number of stones");

        }
        else if (white>black) // added the else
        {
            System.out.println("WHITE WINS!");
            System.out.println("Black has "+black+" number of stones");
            System.out.println("White has "+white+" number of stones");
        }
        else
        {
            System.out.println("A TIE!");
            System.out.println("Black has "+black+" number of stones");
            System.out.println("White has "+white+" number of stones");
        }
    }

   public static ArrayList<Coordinate>mylocations=new ArrayList<>();

   public  static ArrayList<Coordinate> whereicanplay(Tile board[][], Tile currentplayer, int dimension, State mystate)
   {
      mylocations.clear();
      for (int i=0; i<dimension; i++)
      {
          for(int j=0; j<dimension; j++)
          {

             if( canplacepiece(board, i, j, currentplayer, dimension, mystate, false))
             {
                 mylocations.add(new Coordinate(i, j));
             }
          }
      }

      for (Coordinate c: mylocations)
      {
          System.out.println(c.toString());
      }

      return mylocations;
   }
    private static Turn whogoes(int whogoesfirst) {
        switch (whogoesfirst) {
            case (1): {
                return Turn.AI;
            }
            case (2): {
                return Turn.Player;
            }
        }
        return null;
    }

    public enum State {
        Startup, RegularGame
    }

    public static void printboard(Tile board[][], int dimension) {
        for (int i = 0; i < dimension; i++) {
            if(i ==0 )
            {
                for(int x=0; x<dimension; x++){
                    System.out.print("    "+x+" ");
                }
                System.out.println();
            }
            for (int j = 0; j < dimension; j++) {
                if(j==0)
                {
                    System.out.print(i+" ");
                }
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static Tile oppositecolor(Tile currentplayer)
    {
        if(currentplayer==Tile.Black)
        {
            return Tile.White;
        }
        else
        {
            return Tile.Black;
        }
    }

    public static boolean canplacepiece(Tile board[][], int x, int y, Tile currentplayer, int dimension, State mystate, boolean updateboard)//x is y and y is x because idk
    {
        Tile oppositeplayer=oppositecolor(currentplayer);
        if(x>=dimension||x<0||y<0||y>=dimension)
        {
            return false;
        }
        if(board[x][y]!=Tile.Empty)
        {
            return false;
        }
        if(mystate==State.Startup)
        {
            if (x <= dimension / 2 && y <= dimension / 2 && x >= ((dimension / 2) - 1) && y >= ((dimension / 2) - 1)) {
                return true;
            }
            else
            {
                return false;
            }
        }
        boolean isitgood=false;
        int x2, y2;//for x2--
        x2=x;
        y2=y;
        numberofpeicesplayed=0;
        ArrayList<Coordinate>minetocheck=new ArrayList<>();
        boolean islegal=true;
        if(x2<=0) {
            islegal=false;
        }
        else {
            //System.out.println("x + y"+board[x2][y2]);
            x2--;
            while (board[(x2)][y2] == oppositeplayer) {
                minetocheck.add(new Coordinate(x2, y2));
                x2--;
                if (x2 < 0) {
                    islegal = false;
                    break;
                }

            }
        }//do more of the outbounds check. 
        if(islegal&&board[x2][y2]==currentplayer)
        {
            if(minetocheck.size()>0) {
                isitgood = true;
            }
            if(updateboard) {
                for (Coordinate c : minetocheck) {
                    board[c.x][c.y] = currentplayer;
                    numberofpeicesplayed++;
                }
            }
        }


        x2=x;
        y2=y;
        minetocheck=new ArrayList<>();// for y2--
        islegal=true;
        if(y2<=0) {
        islegal=false;
        }
        else {
           // System.out.println("x + y"+board[x2][y2]);
            //System.out.println("x + y"+board[x2][y2-1]);
            y2--;
            while (board[x2][(y2)] == oppositeplayer) {
                minetocheck.add(new Coordinate(x2, y2));
                y2--;
                if (y2 < 0) {
                    islegal = false;
                    break;
                }

            }
        }


        if(islegal&&board[x2][y2]==currentplayer)
        {
            if(minetocheck.size()>0) {
                isitgood = true;
            }
            if(updateboard) {
                for (Coordinate c : minetocheck) {
                    board[c.x][c.y] = currentplayer;
                    numberofpeicesplayed++;

                }
            }
        }

        x2=x;
        y2=y;
        minetocheck=new ArrayList<>(); //x2-- and y2 --
        islegal=true;
        if(x2<=0||y2<=0)
        {
            islegal=false;
        }
        else {
            x2--; y2--;
            while (board[x2][y2] == oppositeplayer) {
                minetocheck.add(new Coordinate(x2, y2));
                x2--; y2--;
                if (y2 < 0 || x2 < 0) {
                    islegal = false;
                    break;
                }
            }
        }
        if(islegal&&board[x2][y2]==currentplayer)
        {
            if(minetocheck.size()>0) {
                isitgood = true;
            }
            if(updateboard) {
                for (Coordinate c : minetocheck) {
                    board[c.x][c.y] = currentplayer;
                    numberofpeicesplayed++;

                }
            }
        }

        x2=x;
        y2=y;
        minetocheck=new ArrayList<>(); //x2++ and y2 ++
        islegal=true;
        if(x2>=(dimension-1)||(y2>=dimension-1))
        {
            islegal=false;
        }
        else {
            x2++;y2++;
            while (board[(x2)][(y2)] == oppositeplayer) {
                minetocheck.add(new Coordinate(x2, y2));
                x2++;y2++;
                if (y2 > (dimension - 1 )|| (x2 > (dimension - 1))) {
                    islegal = false;
                    break;
                }
            }
        }
        if(islegal&&board[x2][y2]==currentplayer)
        {
            if(minetocheck.size()>0) {
                isitgood = true;
            }
            if(updateboard) {
                for (Coordinate c : minetocheck) {
                    board[c.x][c.y] = currentplayer;
                    numberofpeicesplayed++;

                }
            }
        }

        x2=x;
        y2=y;
        minetocheck=new ArrayList<>(); // for x2++
        islegal=true;
        if(x2>=dimension-1)
        {
            islegal=false;
        }
        else {
            x2++;
            while (board[(x2)][y2] == oppositeplayer) {
                minetocheck.add(new Coordinate(x2, y2));
                x2++;
                if (x2 > (dimension - 1)) {
                    islegal = false;
                    break;
                }
            }
        }
        if(islegal&&board[x2][y2]==currentplayer)
        {
            if(minetocheck.size()>0) {
                isitgood = true;
            }
            if(updateboard) {
                for (Coordinate c : minetocheck) {
                    board[c.x][c.y] = currentplayer;
                    numberofpeicesplayed++;

                }
            }
        }

        x2=x;
        y2=y;
        minetocheck=new ArrayList<>(); // for y2++
        islegal=true;
        if(y2>=(dimension-1))
        {
            islegal=false;
        }
        else {
            y2++;
            while (board[x2][(y2)] == oppositeplayer) {
                minetocheck.add(new Coordinate(x2, y2));
                y2++;
                if (y2 > (dimension - 1)) {
                    islegal = false;
                    break;
                }
            }
        }
        if(islegal&&board[x2][y2]==currentplayer)
        {
            if(minetocheck.size()>0) {
                isitgood = true;
            }
            if(updateboard) {
                for (Coordinate c : minetocheck) {
                    board[c.x][c.y] = currentplayer;
                    numberofpeicesplayed++;

                }
            }
        }


        x2=x;
        y2=y;
        minetocheck=new ArrayList<>(); //x2++ and y2 --
        islegal=true;
        if(x2>=dimension-1||y2<=0)
        {
            islegal=false;
        }
        else {
            x2++; y2--;
            while (board[(x2)][(y2)] == oppositeplayer) {
                minetocheck.add(new Coordinate(x2, y2));
                x2++; y2--;
                if (y2 < 0 || x2 > (dimension - 1)) {
                    islegal = false;
                    break;
                }
            }
        }
        if(islegal&&board[x2][y2]==currentplayer)
        {
            if(minetocheck.size()>0) {
                isitgood = true;
            }
            if(updateboard) {
                for (Coordinate c : minetocheck) {
                    board[c.x][c.y] = currentplayer;
                    numberofpeicesplayed++;

                }
            }
        }


        x2=x;
        y2=y;
        minetocheck=new ArrayList<>(); //x2-- and y2 ++
        islegal=true;
        if(x2<=0||y2>=(dimension-1))
        {
            islegal=false;
        }
        else {
            x2--; y2++;
            while (board[(x2)][(y2)] == oppositeplayer) { //check rightmost and right up
                minetocheck.add(new Coordinate(x2, y2));
                x2--; y2++;
                if (x2 < 0 || y2 >( dimension - 1)) {
                    islegal = false;
                    break;
                }
            }
        }
        if(islegal&&board[x2][y2]==currentplayer)
        {
            if(minetocheck.size()>0) {
                isitgood = true;
            }
            if(updateboard) {
                for (Coordinate c : minetocheck) {
                    board[c.x][c.y] = currentplayer;
                    numberofpeicesplayed++;

                }
            }
        }



        return isitgood;
    }
    public static void main(String[] args) throws IOException
    {
        computerPlayer = new AI();
        RandomAI randomPlayer = new RandomAI();
        depthLimit=Integer.parseInt(args[2]);
        dimensions = Integer.parseInt(args[0]);
        Tile board[][] = new Tile[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                board[i][j] = Tile.Empty;
            }
        }
        Turn current_turn = whogoes(Integer.parseInt(args[1]));
        State current_state = State.Startup;
        int numberofpiecesplayed = 0;
        boolean endgame1=false;
        boolean endgame2=false;
        int numberIneed = dimensions * dimensions;
        Tile temp;//can't remember what this was for.
        if(current_turn==Turn.AI)
        {
            temp=Tile.Black;

        }
        else
        {
            temp=Tile.White;
        }
        Tile personplayercolor;
        Tile computerplayercolor;
        if(args[3].equals("white"))
        {
            personplayercolor=Tile.White;
            computerplayercolor=Tile.Black;
        }
        else
        {
            personplayercolor=Tile.Black;
            computerplayercolor=Tile.White;
        }



        while (numberofpiecesplayed < numberIneed) {

            if(endgame1&&endgame2)
            {
                System.out.println("THIS IS A STALE MATE");
                break;
            }
            if (current_turn == Turn.Player) {

                System.out.println("Player 1 what will you place? "+ personplayercolor);//was white
                whereicanplay(board, personplayercolor, dimensions, current_state);
                if(mylocations.size()==0)
                {
                    System.out.println("YOU CAN'T play any pieces ");
                    current_turn=Turn.AI;
                    endgame1=true;
                    continue;
                }
                Scanner myscan = new Scanner(System.in);
                boolean playRandomly = true;
                int number;
                int number2;
                if (playRandomly)
                {
                    Coordinate c = randomPlayer.makeRandomMove(7);
                    number = c.x;
                    number2 = c.y;
                }
                else
                {
                    number = myscan.nextInt();
                    number2 = myscan.nextInt();
                }
                if (current_state == State.Startup) {
                    if (number <= dimensions / 2 && number2 <= dimensions / 2 && number >= ((dimensions / 2) - 1) && number >= ((dimensions / 2) - 1)) {
                       if(canplacepiece(board, number, number2, personplayercolor, dimensions, current_state, true)) {
                           board[number][number2] = personplayercolor;
                           current_turn = Turn.AI;
                           numberofpiecesplayed++;
                           endgame1=false;
                           endgame2=false;
                       }
                       else
                       {
                    System.out.println("INVALID SELECTION");
                       }
                    } else {
                        System.out.println("INVALID SELECTION");
                    }

                }

                if(current_state==State.RegularGame)
                {
                    if(canplacepiece(board, number, number2, personplayercolor, dimensions, current_state, true)&&number<dimensions&&number>=0&&number2<dimensions&&number2>=0) {
                        board[number][number2] = personplayercolor;
                        current_turn = Turn.AI;
                        numberofpiecesplayed++;
                    }
                    else
                    {
                        System.out.println("INVALID SELECTION");
                    }
                }

            } else
                {
                System.out.println("AI what will you place? "+computerplayercolor);//was black
                whereicanplay(board, computerplayercolor, dimensions, current_state);
                if(mylocations.size()==0)
                {
                    System.out.println("AI CAN'T play any pieces ");
                    current_turn=Turn.Player;
                    endgame2=true;
                    continue;
                }
                root = new PotentialMove(new Coordinate(-1, -1),current_turn,computerplayercolor,board,State.RegularGame, 1, null);
                Coordinate c=ai_taketurn(current_state);
                int number = c.x;
                int number2 = c.y;
                if (current_state == State.Startup) {
                    if (number <= dimensions / 2 && number2 <= dimensions / 2 && number >= ((dimensions / 2) - 1) && number >= ((dimensions / 2) - 1)) {
                        if(canplacepiece(board, number, number2, computerplayercolor, dimensions, current_state, true)) {
                            board[number][number2] = computerplayercolor;
                            current_turn = Turn.Player;
                            numberofpiecesplayed++;
                            endgame1=false;
                            endgame2=false;
                        }
                        else
                        {
                            System.out.println("INVALID SELECTION");

                        }

                    }
                    else {
                        System.out.println("INVALID SELECTION");

                    }

                }
                if(current_state==State.RegularGame)
                {
                    if(canplacepiece(board, number, number2, computerplayercolor, dimensions, current_state, true)&&number<dimensions&&number>=0&&number2<dimensions&&number2>=0) {
                        board[number][number2] = computerplayercolor;
                        current_turn = Turn.Player;
                        numberofpiecesplayed++;
                    }
                    else
                    {
                        System.out.println("INVALID SELECTION");

                    }
                }

            }
            if(numberofpiecesplayed>=4)
            {
                current_state=State.RegularGame;
            }


            printboard(board, dimensions);

        }
        winner(board, dimensions);
    }
}
