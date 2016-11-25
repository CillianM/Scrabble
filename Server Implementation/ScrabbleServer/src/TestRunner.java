import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;

import java.awt.Point;

public class TestRunner
{

    public static void main(String [] args)
    {

        // BoardReader Tests
        System.out.println("--------------------------------------------------------");
        System.out.println("Starting tests for BoardReader...");
        Board gameBoard = new Board();
        DictSearch dictionary = new DictSearch();

        //getWordPositions Test
        gameBoard.setCells(new Point(0,0), new Tile ('H', false));
        gameBoard.setCells(new Point(1,0), new Tile ('E', false));
        gameBoard.setCells(new Point(2,0), new Tile ('L', false));
        gameBoard.setCells(new Point(3,0), new Tile ('L', false));

        gameBoard.setCells(new Point(2,2), new Tile ('H', false));
        gameBoard.setCells(new Point(3,2), new Tile ('O', false));

        gameBoard.setCells(new Point(4,4), new Tile ('E', false));
        gameBoard.setCells(new Point(5,4), new Tile ('A', false));
        gameBoard.setCells(new Point(6,4), new Tile ('R', false));


        CellSetter[] lettersPlayed = {new CellSetter('O', 4, 0, false),
                                    new CellSetter('U', 4, 1, false),
                                    new CellSetter('T', 4, 2, false),
                                    new CellSetter('T', 4, 3, false),
                                    new CellSetter('R', 4, 5, false)};


        //getWordPositions
        ArrayList<WordPosition> wordPos = BoardReader.getWordPositions(lettersPlayed, gameBoard);

        System.out.println("The starting positions for words played this turn are: ");
        for(int i = 0; i < wordPos.size(); i++)
        {
            System.out.println(wordPos.get(i).toString());
        }

        //getWords test
        String[] words = BoardReader.getWords(lettersPlayed,wordPos ,gameBoard);
        boolean isWordsValid = true;
        System.out.println("\nThe words played this turn are: ");
        for(int j = 0; j < words.length; j++)
        {
            System.out.print(words[j]);
            if(!dictionary.search(words[j]))
            {
                System.out.println(" :which is not in the dictionary.");
                isWordsValid = false;
            }
            else
            {
                System.out.println(" :which is in the dictionary.");
            }
        }
        System.out.println("All words are valid = " + isWordsValid + "\n");

        //updateBoard
        System.out.println("Place letters (yes, even though outter is invalid)");
        for(int x = 0; x < lettersPlayed.length; x++)
        {
            char c = lettersPlayed[x].character;
            gameBoard.setCells(lettersPlayed[x].getPostion(), new Tile(c, false));
        }

        //getScores test
        System.out.println("The score for all words = " + BoardReader.getScores(lettersPlayed, wordPos, gameBoard));

        System.out.println("--------------------------------------------------------");

    }
}