public class DecompileXML {

	public static Packet decode(String xml) {
		XmlConverter xmlCon = XmlConverter.newInstance();
		Packet p = new Packet();
		try {
			p = xmlCon.unmarshall(xml, Packet.class);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return p;
	}

	public static void main(String args[]) {
		String str="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><packet><players>Filip</players><players>Bernard</players><currentPlayer>Filip</currentPlayer><rack>97</rack><rack>98</rack></packet>";
		Packet p = decode(str);
		System.out.println(p.currentPlayer);
	}
}