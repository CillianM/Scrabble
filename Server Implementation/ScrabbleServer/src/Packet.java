import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import java.awt.*;


@Root
public class Packet {

    //Connection
    @ElementArray (required=false)
    public String players[];
    @Attribute (required=false)
    public String currentPlayer;
    @ElementArray (required=false)
    public char [] rack;

    @ElementArray (required=false)
    public String cellSetterStrings[];

    //Update Player
    @ElementArray (required=false)
    public char tiles[];
    @ElementArray (required=false)
    public int scores[];
    @Attribute (required=false)
    public String playerTurn;

    @Attribute (required=false)
    public CellSetter [] tilesSet;

    public Packet() {}

    public Packet(String players[], String currentPlayer, char[] rack) {
        this.players=players;
        this.currentPlayer=currentPlayer;
        this.rack=rack;
    }

    public Packet(char tiles[], int scores[], String playerTurn, String currentPlayer) {
        this.tiles=tiles;
        this.scores=scores;
        this.playerTurn=playerTurn;
        this.currentPlayer=currentPlayer;
    }

    public Packet(CellSetter tileMove[],String name) {
        cellSetterStrings = new String [tileMove.length];
        for(int i = 0; i < tileMove.length;i++)
        {
            cellSetterStrings[i] = tileMove[i].toString();
        }
        this.currentPlayer=name;
    }

    public Packet(CellSetter tileMove[]) {
        cellSetterStrings = new String [tileMove.length];
        for(int i = 0; i < tileMove.length;i++)
        {
            cellSetterStrings[i] = tileMove[i].toString();
        }
    }

    public String[] getPlayers() {
        return players;
    }

    public CellSetter[] deSerializeCellSetter(String [] strings)
    {
        CellSetter[] tilesSet = new CellSetter [strings.length];
        for(int i = 0; i < strings.length;i++)
        {
            tilesSet[i] = new CellSetter(strings[i]);
        }

        return tilesSet;
    }

    public char[] getRack() {
        return rack;
    }

    public char[] getTiles() {
        return tiles;
    }

    public int[] getScores() {
        return scores;
    }

    public String getCurrentPlayer()
    {
        return currentPlayer;
    }

    public String getPlayerTurn()
    {
        return playerTurn;
    }

}
