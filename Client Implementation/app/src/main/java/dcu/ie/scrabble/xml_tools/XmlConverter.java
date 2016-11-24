package dcu.ie.scrabble.xml_tools;

import java.io.StringWriter;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class XmlConverter
{

    private XmlConverter(){}

    public static XmlConverter newInstance()
    {
        return new XmlConverter();
    }

    public <T> String marshall(T instance, Class<T> cls)
    {
        try {
            Serializer serializer = new Persister();
            StringWriter writer = new StringWriter();
            serializer.write(instance, writer);
            return writer.toString();
        }
        catch (Exception e)
        {
            return null;
        }


    }

    public <T> T unmarshall(String xml, Class<T> cls)
    {
        try
        {
            Serializer serializer = new Persister();
            Object obj = serializer.read(cls, xml);
            return cls.cast(obj);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
