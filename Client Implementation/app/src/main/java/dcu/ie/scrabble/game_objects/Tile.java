package dcu.ie.scrabble.game_objects;

public class Tile
{
    char letter;
    int pointsValue;

    public Tile(char letter, int points)
    {
        //Instantiate the tile with a set letter and points
    }

    public int getPoints()
    {
        //return the points value for this tile
        return 1;
    }

    public char getLetter()
    {
        //return the character for this tile
        return 'c';
    }
}
