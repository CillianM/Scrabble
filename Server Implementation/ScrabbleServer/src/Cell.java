public class Cell
{
    int typeID;
    int multiplier;
    Tile placedTile;
    boolean multiplierUsed;

    public Cell(int ID)
    {
        //Instantiate the placed tile to null with and apply
        //a multiplier if 0, then no multiplier

        /*
        * Cell IDs:
        * 0 == Normal Cell
        * 1 == Star Cell
        * 2 == Double Letter
        * 3 == Double Word
        * 4 == Triple Letter
        * 5 == Triple Word
        * 6 == Unplayable Cell
        * */
        typeID = ID;
        if(ID == 0 || ID == 6)
        {
            multiplier = 1;
        }
        else if(ID == 1 || ID == 2 || ID == 3)
        {
            multiplier = 2;
        }
        else if(ID == 4 || ID == 5)
        {
            multiplier = 3;
        }
    }

    public int getCellType()
    {
        //return the cell type
        return typeID;
    }

    public Tile getPlacedTile()
    {
        //return the tile placed here (if any)
        return placedTile;
    }

    public void setPlacedTile(Tile tile)
    {
        //sets a tile on the cell
        placedTile = tile;
    }

    public int getMultiplier()
    {
        return multiplier;
    }
}