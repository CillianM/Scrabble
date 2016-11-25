public class CompileXML {

	public static String createConnection(String players[], String currentPlayer, char rack[]) {
		XmlConverter xmlCon = XmlConverter.newInstance();
		Packet p = new Packet(players,currentPlayer,rack);
		String result="";
		try {
			result = xmlCon.marshall(p, Packet.class);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return result;
	}

	public static String updatePlayer(char tiles[], int scores[], String playerTurn) {
		XmlConverter xmlCon = XmlConverter.newInstance();
		Packet p = new Packet(tiles,scores,playerTurn,playerTurn);
		String result="";
		try {
			result = xmlCon.marshall(p, Packet.class);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return result;
	}

	public static String makeMove(CellSetter tileMove[]) {
		XmlConverter xmlCon = XmlConverter.newInstance();
		Packet p = new Packet(tileMove);
		String result="";
		try {
			result = xmlCon.marshall(p, Packet.class);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return result;
	}

	public static void main(String args[]) {
		String players[]={"Filip","Bernard"};
		char rack[] = {'a','b'};
		System.out.println(createConnection(players,"Filip",rack));
	}
}