import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;

public class CompileXML {

	public static String createFile(String file) {
		//Create XML file
		String filename = file;
		String workingDirectory = System.getProperty("user.dir");

		String absoluteFilePath = "";
		absoluteFilePath = workingDirectory + File.separator + filename;
		System.out.println("File saved at: " + absoluteFilePath);

		return absoluteFilePath; 
	}

	public static void connect(String names [], String curentPlayer,char newRack[]) {
		try {

			DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docFact.newDocumentBuilder();

			Document doc = docBuild.newDocument();
			Element rootElement = doc.createElement("connection");
			doc.appendChild(rootElement);

			Element players = doc.createElement("players");
			rootElement.appendChild(players);

			//Create the User entries to be shared with
			for (int i=0;i < names.length; i++) {
				String playerName = "player"+(i+1);
				Element name = doc.createElement(playerName);
				name.appendChild(doc.createTextNode(names[i]));
				players.appendChild(name);
			}

			//Send who is the current player
			Element current = doc.createElement("current");
			rootElement.appendChild(current);
			Attr play = doc.createAttribute("player");
			play.setValue(curentPlayer);
			current.setAttributeNode(play);

			Element rack = doc.createElement("rack");
			rootElement.appendChild(rack);

			//Send the user the initial Rack
			for (int j=0;j < newRack.length; j++) {
				String tilePlace = "tile"+(j+1);
				String individualChar = ""+(newRack[j]);
				Element tile = doc.createElement(tilePlace);
				tile.appendChild(doc.createTextNode(individualChar));
				rack.appendChild(tile);
			}
			

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			
			StreamResult result = new StreamResult(new File(createFile("connect.xml")));
			transformer.transform(source, result);

		  	} 
	 	catch (ParserConfigurationException pce) {
			pce.printStackTrace();
	  	} catch (TransformerException tfe) {
			tfe.printStackTrace();
	  	}
	}

	public static void makeMove(CellSetter [] move) {
		try {
			DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docFact.newDocumentBuilder();

			Document doc = docBuild.newDocument();
			Element rootElement = doc.createElement("move");
			doc.appendChild(rootElement);

			//Create the User entries to be shared with
			for (int i=0;i < move.length; i++) {
				String tileNo = "tile"+(i+1);
				//Tile
				Element tile = doc.createElement(tileNo);
				rootElement.appendChild(tile);
				//TileVal
				Element tileVal = doc.createElement("tileVal");
				String tileChar = ""+move[i].character;
				tileVal.appendChild(doc.createTextNode(tileChar));
				tile.appendChild(tileVal);
				//Points
				Element pos = doc.createElement("pos");
				String posInt = ""+move[i].postion.getX()+","+move[i].postion.getX();
				pos.appendChild(doc.createTextNode(posInt));
				tile.appendChild(pos);
				//IsSpace
				Element isSpace = doc.createElement("isSpace");
				String space = "";
				if(move[i].isSpace)	{
					space = "true";
				}
				else {
					space = "false";
				}
				isSpace.appendChild(doc.createTextNode(space));
				tile.appendChild(isSpace);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(new File(createFile("move.xml")));
			transformer.transform(source, result);

		  	} 
	 	catch (ParserConfigurationException pce) {
			pce.printStackTrace();
	  	} catch (TransformerException tfe) {
			tfe.printStackTrace();
	  	}
	}

	public static void updatePlayer(char [] rack, int [] allScores, int currentPlayer) {
		try {
			DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docFact.newDocumentBuilder();

			Document doc = docBuild.newDocument();
			Element rootElement = doc.createElement("update");
			doc.appendChild(rootElement);

			Element tiles = doc.createElement("tiles");
			rootElement.appendChild(tiles);

			//Create the User entries to be shared with
			for (int i=0;i < rack.length; i++) {
				String tileNo = "tile"+(i+1);
				//Tile
				Element tile = doc.createElement(tileNo);
				String tileChar = ""+rack[i];
				tile.appendChild(doc.createTextNode(tileChar));
				tiles.appendChild(tile);
			}
			//Scores
			Element scores = doc.createElement("scores");
			String pointsInt = Arrays.toString(allScores);
			scores.appendChild(doc.createTextNode(pointsInt));
			rootElement.appendChild(scores);

			//Curent move
			Element currentMove = doc.createElement("currentMove");
			String current = ""+currentPlayer;
			currentMove.appendChild(doc.createTextNode(current));
			rootElement.appendChild(currentMove);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(new File(createFile("updatePlayer.xml")));
			transformer.transform(source, result);

		  	} 
	 	catch (ParserConfigurationException pce) {
			pce.printStackTrace();
	  	} catch (TransformerException tfe) {
			tfe.printStackTrace();
	  	}
	}

	public static void main(String args[]) {
		CellSetter send1 = new CellSetter('a',1,2, false);
		CellSetter send2 = new CellSetter('b',3,4, false);
		CellSetter send3 = new CellSetter(' ',5,6, true);
		CellSetter send[]={send1,send2,send3};
		int scores[] = {56,68,11,44};
		char newRack[] = {'a','a','b','a','b','a','b'};
		String arr[] = {"Filip", "Cillian", "Shaun"};
		//connect(arr, "Filip", newRack);
		//makeMove(send);
		updatePlayer(newRack, scores, 2);
	}
}