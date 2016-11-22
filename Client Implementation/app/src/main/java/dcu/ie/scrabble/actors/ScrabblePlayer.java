package dcu.ie.scrabble.actors;

import dcu.ie.scrabble.game_objects.Tile;

public class ScrabblePlayer extends Spectator
{

    //Contains the curent score of the player
    int score;

    //Contains the name of the player
    String name;

    //A collention of tiles the player has


    char [] playerRack;

    //This is the constructor
    public ScrabblePlayer(String name)
    {
        //Initialises all the variables of the ScrabblePlayer class
    }

    public void setScore(int s)
    {
        //Updates the score passed from the GameClient
    }

    public String getName()
    {
        //Returns the ScrabblePlayer's name
        return name;
    }

    public void setRack(char[] r)
    {
        //Sets the player's rack given to by the GameClient
    }
}