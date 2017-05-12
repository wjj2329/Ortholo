import java.util.Random;

/**
 * Created by Alex on 5/12/17.
 */
public class RandomAI
{
    public RandomAI()
    {

    }

    public Coordinate makeRandomMove(int max)
    {
        Random r = new Random();
        return new Coordinate(r.nextInt(max + 1), r.nextInt(max + 1));
    }
}
