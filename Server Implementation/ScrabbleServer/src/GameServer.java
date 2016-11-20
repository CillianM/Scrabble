import java.net.Socket;
import java.util.HashMap;

public class GameServer
{
    Socket serverSocket;
    Socket [] clientSockets;
    Board gameBoard;
    HashMap<Integer, String> playerData;
    Bag bag;

    public GameServer()
    {
        //instantiate and start the server using the
        //specified amount of client sockets

        bag = new Bag();
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

    public boolean sendData(Socket destination, String data)
    {
        //send the specied socket the data required
        return true;
    }

    public void updatePlayer(int player, String data)
    {
        //update specified player with specified data
    }

    public void updateBoard(String data)
    {
        //Update the board with the specified data
    }

    public boolean validateMove(String word)
    {
        //check if the word is legal
        return true;
    }

    public void receiveData(Socket source, String data)
    {
        //receive data from the specified port
    }

    public boolean checkConnections()
    {
        //check if there is a current game in progress
        return true;
    }
}