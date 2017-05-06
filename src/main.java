import java.io.IOException;
import java.util.Scanner;

/**
 * Created by williamjones on 5/4/17.
 */
public class main
{
    public enum Tile{
        Empty, Black, White
    }
    public enum Turn{
        Player, AI
    }
    private static Turn whogoes(int whogoesfirst)
    {
        switch(whogoesfirst)
        {
            case(1):
            {
                return Turn.AI;
            }
            case(2):
            {
                return Turn.Player;
            }
        }
        return null;
    }
    public enum State
    {
        Startup, RegularGame
    }
    public static void printboard(Tile board[][], int dimension)
    {
        for (int i=0; i<dimension; i++)
        {
            for(int j=0; j<dimension; j++)
            {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {

        int dimensions=Integer.parseInt(args[0]);
        Tile board[][]=new Tile[dimensions][dimensions];
        for(int i=0; i<dimensions; i++)
        {
            for(int j=0; j<dimensions; j++)
            {
                board[i][j]=Tile.Empty;
            }
        }
        Turn current_turn = whogoes(Integer.parseInt(args[1]));
        State current_state=State.Startup;
        int numberofpiecesplayed=0;
        int numberIneed=dimensions*dimensions;
       while(numberofpiecesplayed<numberIneed)
       {
           if(current_turn==Turn.Player)
           {
               System.out.println("Player 1 what will you place? ");
               Scanner myscan=new Scanner(System.in);
               int number= myscan.nextInt();
               int number2=myscan.nextInt();
               board[number][number2]=Tile.White;
               current_turn=Turn.AI;
           }
           else
           {
               System.out.println("AI what will you place? ");
               Scanner myscan=new Scanner(System.in);
               int number= myscan.nextInt();
               int number2=myscan.nextInt();
               board[number][number2]=Tile.Black;
               current_turn=Turn.Player;
           }
           printboard(board, dimensions);

       }

    }
    //center do we place or pre placed?  Also what about if dimensions are odd?

}
