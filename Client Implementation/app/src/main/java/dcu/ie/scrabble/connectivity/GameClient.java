package dcu.ie.scrabble.connectivity;

import java.net.Socket;

import dcu.ie.scrabble.actors.Spectator;

public class GameClient
{
    String inboundData;
    String outboundData;
    Spectator user;
    Socket ClientSocket;

    public GameClient(String serverAddress)
    {
        //instantiate the game client with the specified server adderss
    }

    public boolean sendData(String data)
    {
        //send the specied socket the data required
        return true;
    }

    public void draw()
    {
        //Uses collected data and updates the players visuals
    }

    public boolean sendMove(String data)
    {
        //Send the user move to the gameServer
        return true;
    }

    public void receiveData(String data)
    {
        //receive data from the specified port
    }

    public boolean makeConnection(Socket serverAddress)
    {
        //Returns a boolean based on if the connection is made
        return true;
    }

    public String encodeData(String input)
    {
        //Encode the data to XML for transport
        return input;
    }

    public String decodeData(String input)
    {
        //decode xml for use in the game
        return input;
    }


}
