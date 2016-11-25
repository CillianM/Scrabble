import java.util.HashMap;
public class Tile
{
    char letter;
    int pointsValue;
    static  HashMap<Character, Integer> letterPoints = new HashMap<Character, Integer>(){{
    put('A',1);
    put('B',3);
    put('C',3);
    put('D',2);
    put('E',1);
    put('F',4);
    put('G',2);
    put('H',4);
    put('I',1);
    put('J',8);
    put('K',5);
    put('L',1);
    put('M',3);
    put('N',1);
    put('O',1);
    put('P',3);
    put('Q',10);
    put('R',1);
    put('S',1);
    put('T',1);
    put('U',1);
    put('V',4);
    put('W',4);
    put('X',8);
    put('Y',4);
    put('Z',10);
    put(' ',0);
}};

    public Tile(char l, boolean isSpace)
    {
        //Instantiate the tile with a set letter and points
        letter = l;
        if(isSpace)
        {
            pointsValue = letterPoints.get(' ');
        }
        else
        {
            pointsValue = letterPoints.get(l);
        }
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