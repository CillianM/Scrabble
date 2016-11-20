import java.util.ArrayList;
import java.util.Random;

public class Bag
{
    ArrayList<Tile> collection;

    public Bag()
    {
        for(int i = 0; i < collection.size(); i++)
        {
            /*
                A	9	1
                B	2	3
                C	2	3
                D	4	2
                E	12	1
                F	2	4
                G	3	2
                H	2	4
                I	9	1
                J	1	8
                K	1	5
                L	4	1
                M	2	3
                N	6	1
                O	8	1
                P	2	3
                Q	1	10
                R	6	1
                S	4	1
                T	6	1
                U	4	1
                V	2	4
                W	2	4
                X	1	8
                Y	2	4
                Z	1	10
                Blank   2   0
             */
            add(new Tile('A',1), 9 ) ;
            add(new Tile('B',3), 2 ) ;
            add(new Tile('C',3), 2 ) ;
            add(new Tile('D',2), 4 ) ;
            add(new Tile('E',1), 12 ) ;
            add(new Tile('F',4), 2 ) ;
            add(new Tile('G',2), 3 ) ;
            add(new Tile('H',4), 2 ) ;
            add(new Tile('I',1), 9 ) ;
            add(new Tile('J',8), 1 ) ;
            add(new Tile('K',5), 1 ) ;
            add(new Tile('L',1), 4 ) ;
            add(new Tile('M',3), 2 ) ;
            add(new Tile('N',1), 6 ) ;
            add(new Tile('O',1), 8 ) ;
            add(new Tile('P',3), 2 ) ;
            add(new Tile('Q',10), 1 ) ;
            add(new Tile('R',1), 6 ) ;
            add(new Tile('S',1), 4 ) ;
            add(new Tile('T',1), 6 ) ;
            add(new Tile('U',1), 4 ) ;
            add(new Tile('V',4), 2 ) ;
            add(new Tile('W',4), 2 ) ;
            add(new Tile('X',8), 1 ) ;
            add(new Tile('Y',4), 2 ) ;
            add(new Tile('Z',10), 1 ) ;
            add(new Tile(' ',0), 2 ) ;
        }
    }

    public void add(Tile tile, int count)
    {
        while ( count-- > 0 ) {
            collection.add (tile) ;
        }
    }

    public Tile getTile()
    {
        //returns a letter from the collection
        int randInt = (int)(Math.random() * collection.size());
        return collection.remove(randInt);
    }

    public void putTile(Tile letter)
    {
        collection.add(letter);
    }

    public Tile [] getRandom()
    {
        Tile [] randoms = new Tile[7];
        //return 7 random tiles from the collection
        for(int i = 0; i < 7; i++)
        {
            int randInt = (int)(Math.random() * collection.size());
            if(collection.get(i) != null)
            {
                randoms[i] = collection.remove(randInt);
            }
            else
            {
                i--;
            }
        }
        return randoms;
    }
}