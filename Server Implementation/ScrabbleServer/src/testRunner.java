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

public class testRunner
{

    public static void main(String [] args)
    {
        // BoardReader Tests
        System.out.println("--------------------------------------------------------");
        System.out.println("Starting tests for BoardReader...");
        Board gameBoard = new Board();
        DictSearch dictionary = new DictSearch();

        HashMap<Character, Integer> letterPoints = new HashMap<Character, Integer>();
        letterPoints.put('A',1);
        letterPoints.put('B',3);
        letterPoints.put('C',3);
        letterPoints.put('D',2);
        letterPoints.put('E',1);
        letterPoints.put('F',4);
        letterPoints.put('G',2);
        letterPoints.put('H',4);
        letterPoints.put('I',1);
        letterPoints.put('J',8);
        letterPoints.put('K',5);
        letterPoints.put('L',1);
        letterPoints.put('M',3);
        letterPoints.put('N',1);
        letterPoints.put('O',1);
        letterPoints.put('P',3);
        letterPoints.put('Q',10);
        letterPoints.put('R',1);
        letterPoints.put('S',1);
        letterPoints.put('T',1);
        letterPoints.put('U',1);
        letterPoints.put('V',4);
        letterPoints.put('W',4);
        letterPoints.put('X',8);
        letterPoints.put('Y',4);
        letterPoints.put('Z',10);
        letterPoints.put(' ',0);


        /*
        // getWordPositions Test1
        gameBoard.setCells(new Point(2,1), new Tile ('Y', letterPoints.get('Y')));
        gameBoard.setCells(new Point(3,1), new Tile ('O', letterPoints.get('O')));


        CellSetter[] lettersPlayed = {new CellSetter('O', 4, 0, false),
                                    new CellSetter('U', 4, 1, false),
                                    new CellSetter('T', 4, 2, false)};
        */


        //getWordPositions Test2
        gameBoard.setCells(new Point(0,0), new Tile ('H', letterPoints.get('H')));
        gameBoard.setCells(new Point(1,0), new Tile ('E', letterPoints.get('E')));
        gameBoard.setCells(new Point(2,0), new Tile ('L', letterPoints.get('L')));
        gameBoard.setCells(new Point(3,0), new Tile ('L', letterPoints.get('L')));

        gameBoard.setCells(new Point(2,2), new Tile ('H', letterPoints.get('H')));
        gameBoard.setCells(new Point(3,2), new Tile ('O', letterPoints.get('O')));

        gameBoard.setCells(new Point(4,4), new Tile ('E', letterPoints.get('E')));
        gameBoard.setCells(new Point(5,4), new Tile ('A', letterPoints.get('A')));
        gameBoard.setCells(new Point(6,4), new Tile ('R', letterPoints.get('R')));


        CellSetter[] lettersPlayed = {new CellSetter('O', 4, 0, false),
                                    new CellSetter('U', 4, 1, false),
                                    new CellSetter('T', 4, 2, false),
                                    new CellSetter('T', 4, 3, false),
                                    new CellSetter('R', 4, 5, false)};


        //getWordPositions test
        ArrayList<WordPosition> wordPos = BoardReader.getWordPositions(lettersPlayed, gameBoard);

        //print start positions
        System.out.println("The starting positions for words played this turn are: ");
        for(int i = 0; i < wordPos.size(); i++)
        {
            System.out.println(wordPos.get(i).toString());
        }
        System.out.println("--------------------------------------------------------");

    }
}