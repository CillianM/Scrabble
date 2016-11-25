package dcu.ie.scrabble.xml_tools;

import android.graphics.Point;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.transform.RegistryMatcher;
import org.simpleframework.xml.transform.Transformer;

import java.util.ArrayList;


/**
 * Created by Cillian on 25/11/2016.
 */
public class CellSetterTransformer implements Converter<CellSetter []> {

    private Transformer transformer;


    public CellSetterTransformer()
    {
        this.transformer = new Transformer(new RegistryMatcher());
    }

    @Override
    public CellSetter [] read(InputNode inputNode) throws Exception
    {
        ArrayList<CellSetter> cellSetter = new ArrayList<>();
        InputNode child = inputNode.getNext();
        int index = 0;

        while( child != null )
        {
            final Class c = Class.forName(child.getAttribute("class").getValue());

            cellSetter.add(new CellSetter((Character)(transformer.read(child.getAttribute("character").getValue(), c)),
                    (Integer)transformer.read(child.getAttribute("x").getValue(), c),
                    (Integer)transformer.read(child.getAttribute("y").getValue(), c),
                    (Boolean)transformer.read(child.getAttribute("isSpace").getValue(), c)
            ));
            child = inputNode.getNext();
        }


        return cellSetter.toArray(new CellSetter[cellSetter.size()]);
    }

    @Override
    public void write(OutputNode outputNode, CellSetter [] cellSetter) throws Exception
    {
        for(int i = 0; i < cellSetter.length; i++)
        {
            OutputNode child = outputNode.getChild("cellsetter");

            child.setAttribute("character", cellSetter[i].getCharacter() + "");
            child.setAttribute("x", cellSetter[i].getPostion().x + "");
            child.setAttribute("y", cellSetter[i].getPostion().y + "");
            child.setAttribute("isSpace", cellSetter[i].isSpace() + "");
        }
    }
}
