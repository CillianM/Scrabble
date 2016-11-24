package dcu.ie.scrabble.xml_tools;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import dcu.ie.scrabble.xml_tools.CellSetter;

@Root
public class Packet {

    //Connection
    @ElementArray (required=false)
    public String players[];
    @Attribute (required=false)
    public String currentPlayer;
    @ElementArray (required=false)
    public char [] rack;

    //Update Player
    @ElementArray (required=false)
    public char tiles[];
    @ElementArray (required=false)
    public int scores[];
    @Attribute (required=false)
    public String playerTurn;

    @Element (required=false)
    public CellSetter tileMove[];

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
        this.tileMove=tileMove;
        this.currentPlayer=name;
    }

    public Packet(CellSetter tileMove[]) {
        this.tileMove=tileMove;
    }

    public String[] getPlayers() {
        return players;
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

    public CellSetter[] getTileMove()
    {
        return tileMove;
    }
}
