import java.awt.*;
import java.util.ArrayList;

public class Board
{
    private Cell [][] playingBoard;

    public Board()
    {
        //Instantiate playing board
        playingBoard = new Cell[15][15];
        //add star
        playingBoard[7][7] = new Cell(1);

        //instantiate triple words
        Point [] locations = new Point[]{
                new Point(0,0),
                new Point(7,0),
                new Point(14,0),
                new Point(0,7),
                new Point(0,7),
                new Point(14,14),
                new Point(7,14),
                new Point(14,14)
        };
        add(locations,5);
        //instantiate triple letters
        locations = new Point[]{
                new Point(5,1),
                new Point(9,1),
                new Point(1,5),
                new Point(5,5),
                new Point(9,5),
                new Point(13,5),
                new Point(1,9),
                new Point(5,9),
                new Point(9,9),
                new Point(13,9),
                new Point(5,13),
                new Point(9,13)
        };
        add(locations,4);
        //instantiate double words
        locations = new Point[]{
                new Point(1,1),
                new Point(13,1),
                new Point(2,2),
                new Point(12,2),
                new Point(3,3),
                new Point(11,3),
                new Point(4,4),
                new Point(10,4),
                new Point(4,10),
                new Point(10,10),
                new Point(3,11),
                new Point(11,11),
                new Point(2,12),
                new Point(12,12),
                new Point(1,13),
                new Point(13,13)
        };
        add(locations,3);
        //instantiate double letters
        locations = new Point[]{
                new Point(3,0),
                new Point(11,0),
                new Point(6,2),
                new Point(8,2),
                new Point(0,3),
                new Point(7,3),
                new Point(14,3),
                new Point(12,6),
                new Point(8,6),
                new Point(6,6),
                new Point(2,6),
                new Point(11,7),
                new Point(3,7),
                new Point(2,8),
                new Point(6,8),
                new Point(8,8),
                new Point(12,8),
                new Point(0,11),
                new Point(7,11),
                new Point(14,11),
                new Point(6,12),
                new Point(8,12),
                new Point(3,14),
                new Point(11,14)
        };
        add(locations,2);
        fillBlanks();
    }

    private void add(Point [] indexes,int id)
    {

        for(int i = 0; i<indexes.length;i++)
        {
            playingBoard[indexes[i].x][indexes[i].y] = new Cell(id);
        }
    }

    private void fillBlanks()
    {
        ArrayList<Point> locations = new ArrayList<>();
        for(int y = 0; y<15;y++)
        {
            for(int x = 0; x<15;x++)
            {
                if(playingBoard[x][y] == null)
                    locations.add(new Point(x,y));
            }
        }
        Point [] points = locations.toArray(new Point[locations.size()]);
        add(points,0);
    }

    public Cell getCells(Point position)
    {
        //return cell at specified postion
        return playingBoard[(int)position.getX()][(int)position.getY()];
    }

    public void setCells(Point position, Tile newTile)
    {
        //Set the cell at
        //the specified postion to the specified tile
        playingBoard[position.x][position.y].placedTile = newTile;
    }
}