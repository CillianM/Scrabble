/**
 * Created by Cillian on 23/11/2016.
 */
public class Test {

    public static void main(String [] args)
    {
        XmlConverter converter = XmlConverter.newInstance();
        String [] players = new String[]{"Cillian","Filip","Bernard","Shaun"};
        char [] rack = new char[]{' ','a','b','c','d','s','z'};
        Packet p = new Packet(players, "Cillian", rack);
        try
        {
            String initialData = converter.marshall(p,Packet.class);
            initialData = initialData.replace("\n", "").replace("\r", "");
            Packet p1 = converter.unmarshall(initialData,Packet.class);
            System.out.println();
            System.out.println(p1.players.length);
        }
        catch (Exception e)
        {

        }
    }
}
