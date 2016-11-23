import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Packet {

	public Packet() {}

	public Packet(String players[], String currentPlayer, char rack[]) {
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

	public Packet(CellSetter tileMove[]) {
		this.tileMove=tileMove;
	}
	//Connection
	String players[];
	String currentPlayer;
	char rack[];

	//Update Player
	char tiles[];
	int scores[];
	String playerTurn;
	String curentPlayer;

	//User makes a move
	CellSetter tileMove[];
}