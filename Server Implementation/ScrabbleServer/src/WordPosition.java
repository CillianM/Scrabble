import java.awt.Point;

/*
* This class is to help the BoardReader keep track of where words start
* */
public class WordPosition
{
    private CellSetter startCell;
    private Point position; // this point is an alternative to a CellSetter
    private boolean isHorizontal;

    WordPosition(){}

    WordPosition(CellSetter c, boolean h)
    {
        startCell = c;
        position = new Point (c.getPosition());
        isHorizontal = h;
    }

    WordPosition(Point p, boolean h)
    {
        position = p;
        isHorizontal = h;
    }

    public CellSetter getCellSetter() {return startCell;}
    public Point getPosition()
    {
        //We return a new point to avoid pass by reference vs pass by value errors
        return new Point((int)position.getX(), (int)position.getY());
    }
    public boolean getIsHorizontal(){return isHorizontal;}

    public String toString()
    {
       return "(" + (int)getPosition().getX()
                  + ","
                  + (int)getPosition().getY()
                  + ") isHorizontal: "
                  + getIsHorizontal();
    }
}