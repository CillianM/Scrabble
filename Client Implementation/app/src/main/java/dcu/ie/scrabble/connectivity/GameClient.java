package dcu.ie.scrabble.connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import dcu.ie.scrabble.actors.Spectator;

public class GameClient
{
    static String inboundData;
    static String outboundData;
    static Spectator user;
    static  Socket clientSocket;
    static Socket serverSocket;

    static boolean isPlayer;
    static PrintWriter output;
    static BufferedReader input;

    public static synchronized void setup(String serverAddress,String name)
    {
        //instantiate the game client with the specified server adderss
        /*try
        {
            serverSocket = new Socket(serverAddress, 7777);
            output = new PrintWriter(serverSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            String tmp = input.readLine();
            output.println(name);

            if(tmp.equals("player"))
            {
                isPlayer = true;
            }
            if(tmp.equals("spectator"))
            {
                isPlayer = false;
            }
        }
        catch(IOException e)
        {

        }*/
        isPlayer = true;
    }

    public static synchronized boolean isPlayer()
    {
        return isPlayer;
    }


    public static synchronized boolean sendData(String data)
    {
        //send the specied socket the data required
        return true;
    }

    public static synchronized void draw()
    {
        //Uses collected data and updates the players visuals
    }

    public static synchronized boolean sendMove(String data)
    {
        //Send the user move to the gameServer
        return true;
    }

    public static synchronized String receiveData()
    {
        //receive data from the specified port
        try {
            return input.readLine();
        }
        catch (IOException e)
        {

        }
        return null;
    }

    public static synchronized boolean makeConnection(Socket serverAddress)
    {
        //Returns a boolean based on if the connection is made
        return true;
    }

    public static synchronized String encodeData(String input)
    {
        //Encode the data to XML for transport
        return input;
    }

    public static synchronized String decodeData(String input)
    {
        //decode xml for use in the game
        return input;
    }

}
