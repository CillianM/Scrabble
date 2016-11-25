import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class GameServer
{
    static Board gameBoard;
    static HashMap<Integer, String> playerData;
    static Bag bag;
    static DictSearch dictionary;
    static GameServer server;
    static ArrayList <Character> lettersToRack = new ArrayList<>();

    public static void main(String [] args)
    {
        server = new GameServer();
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
                            clientThreads[i] = new ClientThread(server,clientSocket, clientThreads,spectatorThreads);
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

    public static Packet[] decodeData(String input)
    {
        XmlConverter converter =XmlConverter.newInstance();
        Packet packet = converter.unmarshall(input,Packet.class);
        CellSetter [] cellSetters = packet.deSerializeCellSetter(packet.cellSetterStrings);
        boolean legal = executeMove(cellSetters,packet.currentPlayer);

        char [] newRack = new char[lettersToRack.size()];
        for (int i = 0; i < newRack.length; i++) {
            newRack[i]=lettersToRack.get(i);
        }
        String [] names = new String [4];
        int [] scores = new int [4];
        String nextPlayer = packet.currentPlayer;;
        Iterator it = playerData.entrySet().iterator();
        int index = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            names[0] = (String)pair.getKey();
            scores[0] = (Integer)pair.getValue();
            it.remove(); // avoids a ConcurrentModificationException
        }

        if(legal)
        {
            for(int i = 0; i < names.length; i++)
            {
                if(names[i].equals(packet.currentPlayer)&& i < 3)
                {
                    nextPlayer = names[i+1];
                }
                if(names[i].equals(packet.currentPlayer)&& i > 3)
                {
                    nextPlayer = names[0];
                }
            }
        }
        Packet [] packets = new Packet[2];
        packets[0] = new Packet(newRack,scores,nextPlayer,nextPlayer);
        cellSetters = new CellSetter[225];
        int y = 0;
        index = 0;
        for(int x = 0; x < 15; x++)
        {
            Cell tmp = gameBoard.getCells(new Point(x,y));
            cellSetters[index] = new CellSetter(tmp.getPlacedTile().getLetter(),x,y,false);

        }
        packets[1] = new Packet(cellSetters);

        //waiting on xml
        return packets;
    }



    public static void updateBoard(CellSetter[] lettersPlayed)
    {
        //Update the board with the specified data

        for(int i = 0; i < lettersPlayed.length; i++)
        {
            char c = lettersPlayed[i].character;
            gameBoard.setCells(lettersPlayed[i].getPostion(), new Tile(c, lettersPlayed[i].isSpace));
        }
    }

    public static boolean validateMove(CellSetter[] lettersPlayed)
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

    public static void updatePlayer(int player, int score, char[] lettersToReturn)
    {
        //update specified player with specified data
    }

    public static boolean executeMove(CellSetter[] lettersPlayed,String player)
    {
        int playerID = 666;
        char[] lettersToReturn;
        int score = 0;
        boolean legal;

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
            legal =true;
        }
        else
        {
            //not a valid move, return the lettersPlayed
            lettersToReturn = new char[lettersPlayed.length];
            for(int i = 0; i < lettersPlayed.length; i++)
            {
                lettersToReturn[i] = lettersPlayed[i].character;
            }
            legal = false;
        }
        //update the player after move execution
        updatePlayer(playerID,score,lettersToReturn);
        return legal;
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

    static class ClientThread implements Runnable {
        private GameServer server;
        private BufferedReader inputStream = null;
        private PrintStream outputStream = null;
        private Socket clientSocket = null;
        private ArrayList<Spectator> spectatorThreads;
        private ClientThread[] threads;
        private String name;
        private String role;//role signifier
        private static int playerNumber = 1;
        private HashMap<String, Integer> playerData;

        public ClientThread(GameServer server, Socket clientSocket, ClientThread[] threads, ArrayList<Spectator> spectatorThreads) {
            this.server = server;
            this.clientSocket = clientSocket;
            this.threads = threads;
            this.spectatorThreads = spectatorThreads;
            try {
                inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                outputStream = new PrintStream(clientSocket.getOutputStream());
                this.name = inputStream.readLine();
                this.role = "player";
                this.outputStream.println(role);
                this.outputStream.println("You are player " + playerNumber++ + " waiting on other players");
            } catch (IOException e) {
                System.out.println(e + " in constructor of ClientThread");
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                while (threads.length < 4) {
                    wait();
                }
            } catch (Exception e) {

            }

            try {
                while (true) {
                    String line = inputStream.readLine();
                    //decode line

                    //Need to get updates to players
                    Packet[] updates = decodeData(line);

                    XmlConverter converter = XmlConverter.newInstance();
                    String playerUpdate = converter.marshall(updates[0], Packet.class);
                    String boardUpdate = converter.marshall(updates[1], Packet.class);


                    for (int i = 0; i < threads.length; i++) {
                        if (threads[i] != null) {
                            outputStream.println(playerUpdate);
                            outputStream.println(boardUpdate);
                        }
                    }

                    for (int i = 0; i < spectatorThreads.size(); i++) {
                        if (spectatorThreads.get(i) != null) {
                            outputStream.println(playerUpdate);
                            outputStream.println(boardUpdate);
                        }
                    }
                }

            } catch (IOException e) {
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

