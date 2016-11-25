package dcu.ie.scrabble.xml_tools;

import android.graphics.Point;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root
@Convert(value=CellSetterTransformer.class)
public class CellSetter
{
    @Attribute (required=false, name = "character")
    public char character;
    @Attribute (required=false, name = "point")
    public Point postion;
    @Attribute (required=false, name = "isSpace")
    public boolean isSpace;

    CellSetter () {}

    CellSetter (String serialized)
    {
        int xIndex = serialized.indexOf('-') + 1;
        int yIndex = serialized.indexOf('#') + 1;
        int bIndex = serialized.indexOf('*') + 1;
        this.character = serialized.charAt(0);
        String x = serialized.substring(xIndex,xIndex+1);
        String y = serialized.substring(yIndex,yIndex+1);
        this.postion = new Point(Integer.parseInt(x),Integer.parseInt(y));
        this.isSpace = Boolean.valueOf(serialized.substring(bIndex));
    }

    public CellSetter (char a, int x, int y,  boolean isSpace) {
        this.character = a;
        this.postion = new Point(x,y);;
        this.isSpace = isSpace;
    }

    public char getCharacter()
    {
        return character;
    }

    public Point getPostion()
    {
        return postion;
    }

    public boolean isSpace()
    {
        return isSpace;
    }

    public void setCharacter(char c)
    {
        character = c;
    }

    public void setX(int x)
    {
        postion.x = x;
    }

    public void setY(int y)
    {
        postion.y = y;
    }

    public void setSpace(boolean space)
    {
        isSpace = space;
    }

    public String toString()
    {
        return character + "-" + postion.x + "#" +postion.y + "*" + isSpace;
    }
}

