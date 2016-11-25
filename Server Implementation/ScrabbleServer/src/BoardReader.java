import java.awt.Point;
import java.util.ArrayList;
public class BoardReader
{

    //when getting score, all tiles will be placed.  If the current cell coordinated match a cell setter, then
    //set the cell's multiplier used when calculating the score
    public static int getScores(CellSetter[] playedLetters, ArrayList<WordPosition> wordPositions ,Board b)
    {
        int sum = 0;
        for(int i = 0; i < wordPositions.size(); i++)
        {
            sum += getSingleScore(playedLetters,wordPositions.get(i),b);
        }
        return sum;
    }

    private static int getSingleScore(CellSetter[] playedLetters, WordPosition wp ,Board b)
    {
        int sum = 0;
        int wordMultiplier = 1;
        int letterMultiplier = 1;
        int letterPoints = 0;
        int xModifier, yModifier;
        if(wp.getIsHorizontal())
        {
            //set direction of travel to right horizontally
            xModifier = 1;
            yModifier = 0;
        }
        else
        {
            //set direction of travel to down vertically
            xModifier = 0;
            yModifier = 1;
        }
        //Set the initial currentPos
        Point currentPos = wp.getPosition();

        //iterate through the word
        while(cellStatus(currentPos,playedLetters,b) == 1)
        {
            letterPoints = b.getCells(currentPos).getPlacedTile().getPoints();
            if(!b.getCells(currentPos).getMultiplierUsed())
            {
                //set multipliers
                if(b.getCells(currentPos).getCellType() == 2 ||
                        b.getCells(currentPos).getCellType() == 4)
                {
                    letterMultiplier = b.getCells(currentPos).getMultiplier();
                }
                else
                {
                    wordMultiplier = wordMultiplier * b.getCells(currentPos).getMultiplier();
                }
                //set the cell multiplier as used
                b.getCells(currentPos).setMultiplierUsed();
            }
            sum += (letterPoints * letterMultiplier);
            letterMultiplier = 1;
            currentPos.setLocation((int)(currentPos.getX() + xModifier), (int)(currentPos.getY() + yModifier));
        }

        //mult sum by word mult
        sum = sum * wordMultiplier;
        return sum;
    }


    //when getting words, assume the tiles have not been placed, so if the coordinates of the tile
    //matches one of the cell setters, take the info from the cell setter, not the current cell
    public static String[] getWords(CellSetter[] playedLetters, ArrayList<WordPosition> wordPositions, Board b)
    {
        String[] words = new String[wordPositions.size()];

        for(int i = 0; i < wordPositions.size(); i++)
        {
            words[i] = getSingleWord(wordPositions.get(i), playedLetters, b);
        }

        return words;
    }

    private static String getSingleWord(WordPosition wp,CellSetter[] playedLetters, Board b)
    {
        String word = "";
        int xModifier, yModifier;
        if(wp.getIsHorizontal())
        {
            //set direction of travel to right horizontally
            xModifier = 1;
            yModifier = 0;
        }
        else
        {
            //set direction of travel to down vertically
            xModifier = 0;
            yModifier = 1;
        }

        //Set the initial currentPos
        Point currentPos = wp.getPosition();

        //iterate through the word
        while(cellStatus(currentPos,playedLetters,b) == 1)
        {
            if(b.getCells(currentPos).getPlacedTile() == null) //tile hasn't been placed yet, get value from a CellSetter
            {
                for(int i = 0; i < playedLetters.length; i++)
                {
                    if(currentPos.equals(playedLetters[i].getPosition()))
                    {
                        word += playedLetters[i].character;
                        break;
                    }
                }
            }
            else //the tile is placed, so get value directly
            {
                word += b.getCells(currentPos).getPlacedTile().getLetter();
            }

            currentPos.setLocation((int)(currentPos.getX() + xModifier), (int)(currentPos.getY() + yModifier));
        }

        return word;
    }


    public static ArrayList<WordPosition> getWordPositions(CellSetter[] playedLetters, Board b)
    {
        //This method gets the starting cells positions and directions for the words in play this turn

        ArrayList<WordPosition> beginningOfWords = new ArrayList<WordPosition>();
        ArrayList<WordPosition> otherWords = new ArrayList<WordPosition>();

        boolean isMainWordHorizontal;  //the main word is the word where we check if other words are attached to
        int xModifier, yModifier; // these will be used to move either horizontally or vertically

        //determine the direction of the main word (horizontal or vertical)
        if(playedLetters.length == 1)
        {
            if(upAndDownCellStatus(playedLetters[0].getPosition(), playedLetters, b) == 1)
            {
                isMainWordHorizontal = true;
            }
            else
            {
                isMainWordHorizontal = false;
            }
        }
        else
        {
            isMainWordHorizontal = playedLetters[0].getPosition().getY() == playedLetters[1].position.getY();
        }

        WordPosition searchStartPosition = new WordPosition(playedLetters[0], isMainWordHorizontal);
        beginningOfWords.add(0,simpleBeginningSeek(playedLetters,b,searchStartPosition));

        //We have found the top of the main word.  Now to move down the word and find the top of any new connecting words.

        //set direction of travel to the end of the main word
        if(isMainWordHorizontal)
        {
            //set direction of travel to right horizontally
            xModifier = 1;
            yModifier = 0;
        }
        else
        {
            //set direction of travel to down vertically
            xModifier = 0;
            yModifier = 1;
        }

        //Set the initial currentPos
        Point currentPos = beginningOfWords.get(0).getPosition();

        //now I want to go through the entire word.  If the current tile is a cellSetter, then check for connected word.
        while(cellStatus(currentPos,playedLetters,b) == 1)
        {
            if(tileToBePlacedHere(currentPos,playedLetters))
            {
                if(isMainWordHorizontal)
                {
                    if(upAndDownCellStatus(currentPos,playedLetters,b) == 1)
                    {
                        otherWords.add(new WordPosition(new Point(currentPos),!isMainWordHorizontal));
                    }
                }
                else
                {
                    if(leftAndRightCellStatus(currentPos,playedLetters,b) == 1)
                    {
                        otherWords.add(new WordPosition(new Point(currentPos),!isMainWordHorizontal));
                    }
                }
            }

            currentPos.setLocation((int)(currentPos.getX() + xModifier), (int)(currentPos.getY() + yModifier));
        }

        for(int i = 0; i < otherWords.size(); i++)
        {
            beginningOfWords.add(simpleBeginningSeek(playedLetters, b,otherWords.get(i)));
        }

        return beginningOfWords;
    }

    private static WordPosition simpleBeginningSeek(CellSetter[] playedLetters, Board b ,WordPosition startWP)
    {
        //this simple seek looks for the beginning of a word from some starting wordPosition.
        //We can assume that all tiles have been placed, other than the starting wordPosition

        int xModifier, yModifier; //set these based on whether or not startwp is horizontal or vertical
        if(startWP.getIsHorizontal())
        {
            xModifier = -1;
            yModifier = 0;
        }
        else
        {
            xModifier = 0;
            yModifier = -1;
        }

        //The initial current position
        Point currentPos = startWP.getPosition();
        //The initial next position
        Point nextPos = new Point((int) (currentPos.getX() + xModifier), (int)(currentPos.getY() + yModifier));

        while(cellStatus(nextPos, playedLetters ,b) == 1)
        {
            currentPos.setLocation(nextPos);
            nextPos.setLocation((int) (currentPos.getX() + xModifier), (int)(currentPos.getY() + yModifier));
        }

        //currentPos is the beginning of the word
        return new WordPosition(currentPos, startWP.getIsHorizontal());
    }

    private static boolean tileToBePlacedHere(Point p, CellSetter[] playedLetters)
    {
        //Checks if a cellSetter is to set a tile in this position
        for(int i = 0; i < playedLetters.length; i++)
        {
            if(p.equals(playedLetters[i].getPosition()))
                return true;
        }
        return false;
    }

    private static int cellStatus(Point p, CellSetter[] playedLetters, Board b)
    {
        int status;

        if(p.getX() < 0 || p.getY() < 0 || p.getX() > 14 || p.getY() > 14)
        {
            status = -1; // status -1 = position off of the board
        }
        else
        {
            if(b.getCells(p).getPlacedTile() != null)
            {
                status = 1; // status 1 = has tile
            }
            else
            {
                status = 0; // status 0 = has no tile
            }
        }

        //loop through each of the tiles to be played and see if they will be placed in the current cell
        //if so, then consider the cell as having a tile
        if(tileToBePlacedHere(p,playedLetters))
        {
            status = 1;
        }
        return status;
    }

    private static int leftAndRightCellStatus(Point p, CellSetter[] playedLetters, Board b)
    {

        int leftStatus;
        int rightStatus;
        Point leftPoint = new Point((int) (p.getX()-1), (int) p.getY());
        Point rightPoint = new Point((int) (p.getX()+1), (int) p.getY());

        leftStatus = cellStatus(leftPoint, playedLetters, b);
        rightStatus = cellStatus(rightPoint, playedLetters, b);

        //If either the left or right status is 1 (i.e has cell) then the function will return 1
        // else return 0 (no tile).  We don't care if either left or right is off the board, we just want
        // to know if they have tiles.
        if(leftStatus == 1 || rightStatus == 1)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    private static int upAndDownCellStatus(Point p, CellSetter[] playedLetters, Board b)
    {
        int upStatus;
        int downStatus;
        Point upPoint = new Point((int) (p.getX()), (int) p.getY() -1);
        Point downPoint = new Point((int) (p.getX()), (int) p.getY() +1);

        upStatus = cellStatus(upPoint, playedLetters, b);
        downStatus = cellStatus(downPoint, playedLetters, b);

        //If either the up or down status is 1 (i.e has tile) then the function will return 1
        // else return 0 (no tile).  We don't care if either up or down is off the board, we just want
        // to know if they have tiles.
        if(upStatus == 1 || downStatus == 1)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

}

