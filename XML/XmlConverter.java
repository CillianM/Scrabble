import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlConverter 
{

	private XmlConverter(){}
	
	public static XmlConverter newInstance()
	{	
		return new XmlConverter();
	}
	
	public <T> String marshall(T instance, Class<T> cls) throws JAXBException
	{
		StringWriter writer = new StringWriter();
		JAXBContext cntxt = JAXBContext.newInstance(cls);
		cntxt.createMarshaller().marshal(instance, writer);
		return writer.toString();
	}
	
	public <T> T unmarshall(String xml, Class<T> cls) throws JAXBException
	{
		JAXBContext cntxt = JAXBContext.newInstance(cls);
		Object obj = cntxt.createUnmarshaller().unmarshal(new StringReader(xml));
		return cls.cast(obj);
	}
}