import java.awt.Point;

class CellSetter {
   	char character;
   	Point postion;
   	boolean isSpace;

   	CellSetter () {}

   	CellSetter (char a, int x, int y, boolean isSpace) {
   		Point p = new Point(x,y);
   		this.character = a;
   		this.postion = p;
   		this.isSpace = isSpace;
   	}
}