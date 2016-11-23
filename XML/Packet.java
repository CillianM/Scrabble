package dcu.ie.scrabble.xml_tools;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Packet {

    private Packet() {}

    public Packet(String players[], String currentPlayer, char[] rack) {
        this.players=players;
        this.currentPlayer=currentPlayer;
        this.rack=rack;
    }

    public Packet(char tiles[], int scores[], String playerTurn) {
        this.tiles=tiles;
        this.scores=scores;
        this.playerTurn=playerTurn;
        this.curentPlayer=curentPlayer;
    }

    public Packet(CellSetter tileMove[],String name) {
        this.tileMove=tileMove;
        this.curentPlayer=name;
    }

    public Packet(CellSetter tileMove[]) {
        this.tileMove=tileMove;
    }
    //Connection
    public String players[];
    public String currentPlayer;
    public char [] rack;

    //Update Player
    public char tiles[];
    public int scores[];
    public String playerTurn;
    public String curentPlayer;

    //User makes a move
    public CellSetter tileMove[];
}
