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
        typeID = ID;
        if(ID == 0)
        {
            multiplier = 1;
        }
        else if(ID == 1 || ID == 3)
        {
            multiplier = 2;
        }
        else if(ID == 2 || ID == 4)
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