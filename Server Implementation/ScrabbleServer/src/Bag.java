import java.util.ArrayList;
import java.util.Random;

public class Bag
{
    ArrayList<Character> collection = new ArrayList<>();

    public Bag()
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
            add('a', 9 ) ;
            add('b', 2 ) ;
            add('c', 2 ) ;
            add('d', 4 ) ;
            add('e', 12 ) ;
            add('f', 2 ) ;
            add('g', 3 ) ;
            add('h', 2 ) ;
            add('i', 9 ) ;
            add('j', 1 ) ;
            add('k', 1 ) ;
            add('l', 4 ) ;
            add('m', 2 ) ;
            add('n', 6 ) ;
            add('o', 8 ) ;
            add('p', 2 ) ;
            add('q', 1 ) ;
            add('r', 6 ) ;
            add('s', 4 ) ;
            add('t', 6 ) ;
            add('u', 4 ) ;
            add('v', 2 ) ;
            add('w', 2 ) ;
            add('x', 1 ) ;
            add('y', 2 ) ;
            add('z', 1 ) ;
            add(' ', 2 ) ;
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