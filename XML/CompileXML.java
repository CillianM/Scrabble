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

public class CompileXML {

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

			//Create XML file
			String filename = "connect.xml";
			String workingDirectory = System.getProperty("user.dir");

			String absoluteFilePath = "";

			absoluteFilePath = workingDirectory + File.separator + filename;

			System.out.println("Final filepath : " + absoluteFilePath);

			StreamResult result = new StreamResult(new File(absoluteFilePath));

			transformer.transform(source, result);

			System.out.println("File saved!");

		  	} 
	 	catch (ParserConfigurationException pce) {
			pce.printStackTrace();
	  	} catch (TransformerException tfe) {
			tfe.printStackTrace();
	  	}
	}

	public static void main(String args[]) {
		CellSetter send = new CellSetter();
		char newRack[] = {'a','a','b','a','b','a','b'};
		String arr[] = {"Filip", "Cillian", "Shaun"};
		connect(arr, "Filip", newRack);
	}
}