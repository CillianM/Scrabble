import java.awt.Point;
import java.util.ArrayList;
public class BoardReader
{


    //when validating, assume the tiles have not been placed, so if the coordinates of the tile
    //matches one of the cell setters, take the info from the cell setter, not the current cell

    //when getting score, all tiles will be placed.  If the current cell coordinated match a cell setter, then
    //set the cell's multiplier used when calculating the score


    public static ArrayList<WordPosition> getWordPositions(CellSetter[] playedLetters, Board b)
    {
        //This method gets the starting cells positions and directions for the words in play this turn

        ArrayList<WordPosition> beginningWordPositions = new ArrayList<WordPosition>();
        ArrayList<WordPosition> otherWordsConnectedPositions = new ArrayList<WordPosition>();

        boolean isMainWordHorizontal;  //the main word is the word where we check if other words are attached to
        int xModifier, yModifier; // these will be used to move either horizontally or vertically

        //determine the direction of the main word (horizontal or vertical)
        if(playedLetters.length == 1)
        {
            isMainWordHorizontal = isUpAndDownEmpty(playedLetters[0].position, b);
        }
        else
        {
            isMainWordHorizontal = playedLetters[0].position.getY() == playedLetters[1].position.getY();
        }

        //set direction of travel to find the start of the main word
        if(isMainWordHorizontal)
        {
            //set direction of travel to left horizontally
            xModifier = -1;
            yModifier = 0;
        }
        else
        {
            //set direction of travel to up vertically
            xModifier = 0;
            yModifier = -1;
        }

        //The initial current position
        Point currentPos = playedLetters[0].position;
        //The initial next position
        Point nextPos = new Point((int) (currentPos.getX() + xModifier), (int)(currentPos.getY() + yModifier));

        while(!isCellEmpty(nextPos, playedLetters ,b))
        {
            if(isMainWordHorizontal)
            {
                if(!isUpAndDownEmpty(currentPos, b))
                {
                    otherWordsConnectedPositions.add(new WordPosition(currentPos, !isMainWordHorizontal));
                }
            }
            else
            {
                if(!isLeftAndRightEmpty(currentPos, b))
                {
                    otherWordsConnectedPositions.add(new WordPosition(currentPos, !isMainWordHorizontal));
                }
            }
            currentPos.setLocation(nextPos);
            nextPos.setLocation((int) (currentPos.getX() + xModifier), (int)(currentPos.getY() + yModifier));
        }

        //found top, add main word WordPosition to beginningWordPositions
        beginningWordPositions.add(new WordPosition(currentPos, isMainWordHorizontal));

        //set direction of travel to find the end of the main word
        if(isMainWordHorizontal)
        {
            //set direction of travel to left horizontally
            xModifier = 1;
            yModifier = 0;
        }
        else
        {
            //set direction of travel to up vertically
            xModifier = 0;
            yModifier = 1;
        }

        //The make the curPos = the next cell in the opposite direction of where we started
        currentPos = new Point((int) (playedLetters[0].position.getX() + xModifier), (int)(playedLetters[0].position.getY() + yModifier));
        //The next position
        nextPos = new Point((int) (currentPos.getX() + xModifier), (int)(currentPos.getY() + yModifier));

        //Do the same as above except this time move to the end of the main word
        if(!isCellEmpty(currentPos, playedLetters ,b))
        {
            while(!isCellEmpty(nextPos, playedLetters ,b))
            {
                if(isMainWordHorizontal)
                {
                    if(!isUpAndDownEmpty(currentPos, b))
                    {
                        otherWordsConnectedPositions.add(new WordPosition(currentPos, !isMainWordHorizontal));
                    }
                }
                else
                {
                    if(!isLeftAndRightEmpty(currentPos, b))
                    {
                        otherWordsConnectedPositions.add(new WordPosition(currentPos, !isMainWordHorizontal));
                    }
                }
                currentPos.setLocation(nextPos);
                nextPos.setLocation((int) (currentPos.getX() + xModifier), (int)(currentPos.getY() + yModifier));
            }
        }

        //at this stage we hav found the start of the "main" word, and we have found where "other" words are
        //connected to the main word.
        //now for each otherWord we do a simplified seek for just the start of that word, no need to worry
        //about CellSetters
        for(int i = 0; i < otherWordsConnectedPositions.size(); i++)
        {
            WordPosition otherWord = simpleBeginningSeek(playedLetters, b,otherWordsConnectedPositions.get(i));
            System.out.println("OtherWord Start = " + otherWord.toString());
            beginningWordPositions.add(otherWord);
        }

        return beginningWordPositions;

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
        System.out.println("Other connected at: " + startWP.toString());
        //The initial next position
        Point nextPos = new Point((int) (currentPos.getX() + xModifier), (int)(currentPos.getY() + yModifier));

        //while(nextPos.getX() >= 0 && nextPos.getY() >=0 && b.getCells(nextPos).getPlacedTile() != null )
        int i = 0;
        while(!isCellEmpty(nextPos, playedLetters ,b))
        {
            System.out.println("I'thiteration: " + i);
            currentPos.setLocation(nextPos);
            nextPos.setLocation((int) (currentPos.getX() + xModifier), (int)(currentPos.getY() + yModifier));
            i++;
        }

        //currentPos is the beginning of the word
        return new WordPosition(currentPos, startWP.getIsHorizontal());
    }

    private static boolean isCellEmpty(Point p, CellSetter[] playedLetters, Board b)
    {
        boolean isEmpty;

        if(p.getX() < 0 || p.getY() < 0 || p.getX() > 14 || p.getY() > 14)
        {
            isEmpty = true; //a position off of the board is considered empty
        }
        else
        {
            isEmpty = b.getCells(p).getPlacedTile() == null;
        }

        for(int i = 0; i < playedLetters.length; i++)
        {
            if(p.equals(playedLetters[i].position))
            {
                isEmpty = false;
            }
        }

        return isEmpty;
    }

    private static boolean isLeftAndRightEmpty(Point p, Board b)
    {
        //definition of empty is that the position on the left or right
        //does not have a tile onject.  Beyond the edge of the board is
        // considered to be empty
        boolean leftEmpty = true;
        boolean rightEmpty = true;

        if(p.getX() - 1 >= 0)
        {
            Point leftPoint = new Point((int) (p.getX()-1), (int) p.getY());
            leftEmpty = b.getCells(leftPoint).getPlacedTile() == null;
        }

        if(p.getX() + 1 <= 14)
        {
            Point rightPoint = new Point((int) (p.getX()+1), (int) p.getY());
            rightEmpty = b.getCells(rightPoint).getPlacedTile() == null;
        }

        return leftEmpty && rightEmpty;
    }

    private static boolean isUpAndDownEmpty(Point p, Board b)
    {
        boolean upEmpty = true;
        boolean downEmpty = true;

        if(p.getY() - 1 >= 0)
        {
            Point upPoint = new Point((int) p.getX(), (int) (p.getY() - 1));
            upEmpty = b.getCells(upPoint).getPlacedTile() == null;
        }

        if(p.getY() + 1 <= 14)
        {
            Point downPoint = new Point((int) (p.getX()), (int) (p.getY() + 1));
            downEmpty = b.getCells(downPoint).getPlacedTile() == null;
        }

        return upEmpty && downEmpty;
    }

}

