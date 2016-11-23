package dcu.ie.scrabble.xml_tools;

import android.graphics.Point;

public class CellSetter {
    public char character;
    public Point postion;
    public boolean isSpace;

    CellSetter () {}

    public CellSetter (char a, int x, int y, boolean isSpace) {
        Point p = new Point(x,y);
        this.character = a;
        this.postion = p;
        this.isSpace = isSpace;
    }
}
