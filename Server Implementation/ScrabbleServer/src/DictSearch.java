import java.util.*;
import java.io.*;

public class DictSearch
{
    Set<String> dict;

    public DictSearch()
    {
        dict = createDict();
    }

    public boolean search(String word)
    {
        return dict.contains(word.toUpperCase());
    }


    public Set<String> createDict()
    {
        Set<String> dict = new HashSet<>();
        try{
            Scanner file = new Scanner(new File("./dict.txt"));
            while (file.hasNext()) {
                dict.add(file.next().trim());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return dict;
    }
}