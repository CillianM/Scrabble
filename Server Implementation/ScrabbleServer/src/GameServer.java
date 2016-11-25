import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.ArrayList;

public class GameServer
{
    Board gameBoard;
    HashMap<Character, Integer> letterPoints;
    HashMap<Integer, String> playerData;
    Bag bag;
    DictSearch dictionary;

    public static void main(String [] args)
    {
        ServerSocket serverSocket = null;
        int clientCount = 0;
        int spectatorCount = 0;
        ClientThread [] clientThreads = new ClientThread[4];
        Socket clientSocket = null;
        ArrayList<Spectator> spectatorThreads = new ArrayList<>();

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
            //try to add as player, otherwise add to spectator array list
            try
            {
                clientSocket = serverSocket.accept();
                //if game isn't already in progress add players
                if(!checkConnections(clientThreads))
                {
                    for(int i = 0; i < clientThreads.length; i++)
                    {
                        if(clientThreads[i] == null)
                        {
                            clientThreads[i] = new ClientThread(clientSocket, clientThreads,spectatorThreads);
                            Thread t = new Thread(clientThreads[i]);
                            t.start();
                            clientCount++;
                            break;
                        }
                    }
                }
                else
                {

                    spectatorThreads.add(new Spectator(clientSocket));
                    spectatorCount++;
                    System.out.println("Spectators " + spectatorCount);

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
        assignTilePoints();
    }

    private void assignTilePoints()
    {
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
    }

    public static String decodeData(String input)
    {
        //waiting on xml
        return input;
    }

    public void updatePlayer(int player, String data)
    {
        //update specified player with specified data
    }

    public void updateBoard(CellSetter[] letterArray)
    {
        //Update the board with the specified data

        for(int i = 0; i < letterArray.length; i++)
        {
            char c = letterArray[i].character;
            gameBoard.setCells(letterArray[i].position, new Tile(c, letterPoints.get(c)));
        }
    }

    public boolean validateMove(CellSetter[] letterArray)
    {
        //get all words created in this move and check if they are in the dictionary
        String[] words = BoardReader.getWords(letterArray, gameBoard);
        for(int i = 0; i < words.length; i++)
        {
            if(!dictionary.search(words[i]))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean checkConnections(ClientThread[] clients)
    {
        //check if there is a current game in progress
        for(int i = 0; i < clients.length; i++)
        {
            if (clients[i] == null)
                return false;
        }
        return true;
    }

    static class ClientThread implements Runnable
    {
        private BufferedReader inputStream = null;
        private PrintStream outputStream = null;
        private Socket clientSocket = null;
        private ArrayList<Spectator> spectatorThreads;
        private ClientThread [] threads;
        private String name;
        private String role;//role signifier

        public ClientThread(Socket clientSocket, ClientThread [] threads, ArrayList<Spectator> spectatorThreads)
        {
            this.clientSocket = clientSocket;
            this.threads = threads;
            this.spectatorThreads = spectatorThreads;
            try
            {
                inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                outputStream = new PrintStream(clientSocket.getOutputStream());
                this.name = inputStream.readLine();
                this.role = "player";
                this.outputStream.println(role);
            }
            catch (IOException e)
            {
                System.out.println(e);
            }
        }

        public void run()
        {
            int maxClientCount = 4;

            try
            {

                while(true)
                {
                    String line = inputStream.readLine();
                    //decode line

                    //evaluate using Bernard's masters method. Possibly decode as well. So ^ might be unnecessary
                    String result = decodeData(line);;
                    for(int i = 0; i < maxClientCount; i++)
                    {
                        if(threads[i] != null && threads[i] != this)
                        {
                            outputStream.println(result);
                        }
                    }

                    for(int i = 0; i < spectatorThreads.size(); i++)
                    {
                        if(spectatorThreads.get(i) != null && threads[i] != this)
                        {
                            outputStream.println(result);
                        }
                    }
                }

            }

            catch(IOException e)
            {
                System.out.println(e);
            }


        }



    }

    static class Spectator
    {
        private PrintStream outputStream = null;
        private Socket clientSocket = null;

        public Spectator(Socket clientSocket)
        {
            this.clientSocket = clientSocket;
            try
            {
                outputStream = new PrintStream(clientSocket.getOutputStream());
                this.outputStream.println("spectator");
            }
            catch (IOException e)
            {
                System.out.println(e);
            }
        }
    }
}

