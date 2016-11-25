import java.util.ArrayList;
import java.util.Random;

public class Bag
{
    ArrayList<Character> collection;

    public Bag()
    {
        for(int i = 0; i < collection.size(); i++)
        {
            /*  letter, amount, pointvalue (point value handled in GameServer)

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
            add('A', 9 ) ;
            add('B', 2 ) ;
            add('C', 2 ) ;
            add('D', 4 ) ;
            add('E', 12 ) ;
            add('F', 2 ) ;
            add('G', 3 ) ;
            add('H', 2 ) ;
            add('I', 9 ) ;
            add('J', 1 ) ;
            add('K', 1 ) ;
            add('L', 4 ) ;
            add('M', 2 ) ;
            add('N', 6 ) ;
            add('O', 8 ) ;
            add('P', 2 ) ;
            add('Q', 1 ) ;
            add('R', 6 ) ;
            add('S', 4 ) ;
            add('T', 6 ) ;
            add('U', 4 ) ;
            add('V', 2 ) ;
            add('W', 2 ) ;
            add('X', 1 ) ;
            add('Y', 2 ) ;
            add('Z', 1 ) ;
            add(' ', 2 ) ;
        }
    }

    public void add(char letter, int count)
    {
        while ( count-- > 0 ) {
            collection.add (letter) ;
        }
    }

    public char getTile()
    {
        //returns a letter from the collection
        int randInt = (int)(Math.random() * collection.size());
        return collection.remove(randInt);
    }

    public void putTile(char letter)
    {
        collection.add(letter);
    }

    public char [] getRandom(int count)
    {
        char [] randoms = new char[count];
        //return 7 random tiles from the collection
        for(int i = 0; i < count; i++)
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