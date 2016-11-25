import java.io.StringWriter;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

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
            Strategy strategy = new AnnotationStrategy();
            Serializer serializer = new Persister(strategy);
            StringWriter writer = new StringWriter();
            serializer.write(instance, writer);
            return writer.toString();
        }
        catch (Exception e)
        {
            System.out.println(e);
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
            System.out.println(e);
            return null;
        }
    }
}
