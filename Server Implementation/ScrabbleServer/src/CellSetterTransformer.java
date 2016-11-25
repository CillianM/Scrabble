import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.transform.RegistryMatcher;
import org.simpleframework.xml.transform.Transformer;

import java.util.ArrayList;


/**
 * Created by Cillian on 25/11/2016.
 */
public class CellSetterTransformer implements Converter<CellSetter> {

    private Transformer transformer;


    public CellSetterTransformer()
    {
        this.transformer = new Transformer(new RegistryMatcher());
    }

    @Override
    public CellSetter read(InputNode inputNode) throws Exception
    {
        CellSetter cellSetter = new CellSetter();
        InputNode child = inputNode.getNext();
        int index = 0;

        while( child != null )
        {
            final Class c = Class.forName(child.getAttribute("class").getValue());
            cellSetter.setCharacter((Character)(transformer.read(child.getAttribute("character").getValue(), c)));
            cellSetter.setX((Integer)transformer.read(child.getAttribute("x").getValue(), c));
            cellSetter.setY((Integer)transformer.read(child.getAttribute("y").getValue(), c));
            cellSetter.setSpace((Boolean)transformer.read(child.getAttribute("isSpace").getValue(), c));
        }


        return cellSetter;
    }

    @Override
    public void write(OutputNode outputNode, CellSetter cellSetter) throws Exception
    {
            OutputNode characterNode = outputNode.getChild("character");
            characterNode.setValue(cellSetter.getCharacter() + "");
            OutputNode x = outputNode.getChild("x");
            x.setValue(cellSetter.getPostion().x + "");
            OutputNode y = outputNode.getChild("y");
            y.setValue(cellSetter.getPostion().y + "");
            OutputNode space = outputNode.getChild("isSpace");
            space.setValue(cellSetter.isSpace() + "");
    }
}
