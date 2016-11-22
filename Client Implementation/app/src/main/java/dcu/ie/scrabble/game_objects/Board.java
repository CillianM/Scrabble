package dcu.ie.scrabble.game_objects;

public class Board
{
    private boolean [][] playingBoard;

    public Board()
    {
        playingBoard = new boolean[15][15];
    }

    public void set(int x, int y)
    {
        playingBoard[x][y] = true;
    }

    public void unset(int x, int y)
    {
        playingBoard[x][y] = false;
    }

    public boolean isSet(int x,int y)
    {
        return playingBoard[x][y];
    }
}
