public class Tile
{
    char letter;
    int pointsValue;

    public Tile(char l, int p)
    {
        //Instantiate the tile with a set letter and points
        letter = l;
        pointsValue = p;
    }

    public int getPoints()
    {
        //return the points value for this tile
        return pointsValue;
    }

    public char getLetter()
    {
        //return the character for this tile
        return letter;
    }
}