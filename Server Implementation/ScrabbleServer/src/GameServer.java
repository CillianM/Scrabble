import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class GameServer
{
    private static ServerSocket serverSocket = null;
    private static int clientCount = 0;
    private static ClientThread [] clientThreads = new ClientThread[];
    private static Socket clientSocket = null;
    Board gameBoard;
    HashMap<Integer, String> playerData;
    Bag bag;
    DictSearch dictionary;

    public static void main(String [] args)
    {
        int portNumber = 7777;

        try
        {
            serverSocket = new ServerSocket(portNumber);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        while(true)
        {
            try
            {
                clientSocket = serverSocket.accept();

                for(int i = 0; i < clientThreads.length; i++)
                {
                    if(clientThreads[i] == null)
                    {
                        clientThreads[i] = new ClientThread(clientSocket, clientThreads))
                        Thread t = new Thread(clientThreads[i]);
                        t.start();
                        clientCount++;
                        System.out.println("Clients " + clientCount);
                        break;
                    }
                }
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
        }
    }

    public GameServer()
    {
        //instantiate and start the server using the
        //specified amount of client sockets

        bag = new Bag();
        DictSearch dictionary = new DictSearch();
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
        return dictionary.search(word);
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

class ClientThread
{
    private BufferedReader inputStream = null;
    private PrintStream outputStream = null;
    private Socket clientSocket = null;
    private ClientThread [] threads = new ClientThread[];
    private String name;
    private String quitMsg;

    public ClientThread(Socket clientSocket, ClientThread [] threads)
    {
        this.clientSocket = clientSocket;
        this.threads = threads;
        try {
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputStream = new PrintStream(clientSocket.getOutputStream());
            this.name = inputStream.readLine();
            this.quitMsg = "###" + clientSocket.getRemoteSocketAddress().toString() + "***";
            this.outputStream.println(quitMsg);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void run()
    {
        int maxClientCount = 4;
        ClientThread [] threads = new ClientThread[maxClientCount];

        try
        {
            for(int i = 0; i < maxClientCount; i++)
            {
                if(threads[i] != null)
                {
                    threads[i].outputStream.println(name + " just joined the game...");
                    //joining game logic here.
                }
            }

            while(true)
            {
                String line = inputStream.readLine();
                if(line.equals(quitMsg))
                {
                    break;
                }
                else
                {
                    for(int i = 0; i < maxClientCount; i++)
                    {
                        if(threads[i] != null && threads[i] != this)
                        {
                            //send move?
                        }
                    }
                }
            }

            for(int i = 0; i < maxClientCount; i++)
            {
                if(threads[i] != null && threads[i] != this)
                {
                    threads[i].outputStream.println(name + "has just left the chatroom...");

                }
            }

            for(int i = 0; i < maxClientCount; i++)
            {
                if(threads[i] == this)
                {
                    threads[i] = null;
                }
            }

        }

        catch(IOException e)
        {

        }


    }



}