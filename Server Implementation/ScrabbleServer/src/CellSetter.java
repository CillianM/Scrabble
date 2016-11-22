import java.awt.Point;

class CellSetter {
   	char character;
   	Point position;
   	boolean isSpace;

   	CellSetter () {}

   	CellSetter (char a, int x, int y, boolean isSpace) {
   		Point p = new Point(x,y);
   		this.character = a;
   		this.position = p;
		this.isSpace = isSpace;
   	}
}