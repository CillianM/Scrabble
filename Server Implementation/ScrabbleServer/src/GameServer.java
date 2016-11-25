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
                            System.out.println("Players "  + (clientCount+1) + " of 4 currently connected");
                            clientCount++;
                            break;
                        }
                    }
                }
                else
                {
                    //GameServer game = new GameServer();
                    spectatorThreads.add(new Spectator(clientSocket));
                    spectatorCount++;
                    System.out.println("Spectators " + spectatorCount);

                }
            }

            catch(IOException e)
            {
                System.out.println(e + " at GameServer Main ");
                e.printStackTrace();
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

    public static String decodeData(String input)
    {
        //waiting on xml
        return input;
    }



    public void updateBoard(CellSetter[] lettersPlayed)
    {
        //Update the board with the specified data

        for(int i = 0; i < lettersPlayed.length; i++)
        {
            char c = lettersPlayed[i].character;
            gameBoard.setCells(lettersPlayed[i].position, new Tile(c, lettersPlayed[i].isSpace));
        }
    }

    public boolean validateMove(CellSetter[] lettersPlayed)
    {
        //get all words created in this move and check if they are in the dictionary
        ArrayList<WordPosition> wordPos = BoardReader.getWordPositions(lettersPlayed, gameBoard);
        String[] words = BoardReader.getWords(lettersPlayed,wordPos, gameBoard);
        for(int i = 0; i < words.length; i++)
        {
            if(!dictionary.search(words[i]))
            {
                return false;
            }
        }
        return true;
    }

    public void updatePlayer(int player, int score, char[] lettersToReturn)
    {
        //update specified player with specified data
    }

    public void executeMove(CellSetter[] lettersPlayed)
    {
        int playerID = 666;
        char[] lettersToReturn;
        int score = 0;

        //A normal play tiles move
        if(validateMove(lettersPlayed))
        {
            //place tiles
            updateBoard(lettersPlayed);
            //calculate score
            ArrayList<WordPosition> wordPos = BoardReader.getWordPositions(lettersPlayed, gameBoard);
            score = BoardReader.getScores(lettersPlayed, wordPos, gameBoard);

            //HOW TO ASSIGN THE SCORE????????????????????????

            //pull tiles from bag == to length of lettersPlayed
            lettersToReturn = new char[lettersPlayed.length];
            lettersToReturn = bag.getRandom(lettersPlayed.length);

        }
        else
        {
            //not a valid move, return the lettersPlayed
            lettersToReturn = new char[lettersPlayed.length];
            for(int i = 0; i < lettersPlayed.length; i++)
            {
                lettersToReturn[i] = lettersPlayed[i].character;
            }
        }
        //update the player after move execution
        updatePlayer(playerID,score,lettersToReturn);

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
        private static int playerNumber = 1;
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
                this.outputStream.println("You are player " + playerNumber++ + " waiting on other players");
            }
            catch (IOException e)
            {
                System.out.println(e + " in constructor of ClientThread");
                e.printStackTrace();
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
                        if(threads[i] != null && threads[i] != this && result != null)
                        {
                            outputStream.println(result);
                        }
                    }

                    for(int i = 0; i < spectatorThreads.size(); i++)
                    {
                        if(spectatorThreads.get(i) != null && threads[i] != this && result != null)
                        {
                            outputStream.println(result);
                        }
                    }
                }

            }

            catch(IOException e)
            {
                System.out.println(e + " in run method of ClientThread");
                e.printStackTrace();
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
                System.out.println(e + " in Spectator creation");
                e.printStackTrace();
            }
        }
    }
}

