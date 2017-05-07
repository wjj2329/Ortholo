/**
 * Created by williamjones on 5/6/17.
 */
public class Coordinate {
    int x;
    int y;

    @Override
    public String toString() {
        return x+" "+y;
    }

    public Coordinate(int x, int y)
    {
        this.x=x;
        this.y=y;
    }
}
