/**
 * Created by Cillian on 23/11/2016.
 */
public class Test {

    public static void main(String [] args)
    {
        XmlConverter converter = XmlConverter.newInstance();
        CellSetter [] moves = new CellSetter[]
                {
                    new CellSetter('a',1,2,false),
                    new CellSetter('b',3,4,true),
                    new CellSetter('a',5,6,false)
                };
        Packet p = new Packet(moves, "Cillian");
        try
        {
            String initialData = converter.marshall(p,Packet.class);
            initialData = initialData.replace("\n", "").replace("\r", "");
            Packet p1 = converter.unmarshall(initialData,Packet.class);
            CellSetter [] c = p1.deSerializeCellSetter(p1.cellSetterStrings);
            System.out.println();
        }
        catch (Exception e)
        {

        }
    }
}
