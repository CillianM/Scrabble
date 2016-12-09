package dcu.ie.scrabble;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dcu.ie.scrabble.actors.ScrabblePlayer;
import dcu.ie.scrabble.actors.Spectator;
import dcu.ie.scrabble.adapter.*;
import dcu.ie.scrabble.connectivity.GameClient;
import dcu.ie.scrabble.game_objects.Board;
import dcu.ie.scrabble.xml_tools.Packet;
import dcu.ie.scrabble.xml_tools.*;

public class GameScreen extends AppCompatActivity {

    private static final int REQUEST_SPACE_REPLACEMENT = 0;
    BoardAdapter boardAdapter;
    PieceAdapter pieceAdapter;
    boolean isPlayer;
    Packet packet;
    InitializePlayers initializePlayers;
    SpectatorThread spectatorThread;
    PacketWait wait;

    LinearLayout gameScreen;
    ProgressBar progress;

    String myName;
    boolean myTurn = false;

    GridView boardGrid;
    GridView piecesGrid;

    Board board = new Board();

    String [] names = new String[4];

    //rack
    char [] chars;
    boolean [] tileUsed = new boolean[]{false,false,false,false,false,false,false,false};
    StringBuilder builder = new StringBuilder();



    //playing pieces
    char pieceToPlay;
    Boolean isPlayingPiece = false;
    Boolean firstPiece = true;

    OriginalBoardTiles [] originalIDs = new OriginalBoardTiles[7];
    OriginalRack [] originalRack = new OriginalRack[7];
    ArrayList<CellSetter> cellSetters = new ArrayList<>();

    Replacement spaceReplacement;
    boolean wasASpace = false;


    int playingCount = 0;
    int direction;

    PacketSender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        Intent intent = getIntent();
        myName = intent.getStringExtra("NAME");
        String playerFlag = intent.getStringExtra("PLAYER");
        if(playerFlag.equals("SET"))
        {
            isPlayer = true;
            Toast.makeText(getApplicationContext(), "You are a player", Toast.LENGTH_SHORT).show();
        }
        else
        {
            isPlayer = false;
            Toast.makeText(getApplicationContext(), "You are a spectator", Toast.LENGTH_SHORT).show();
        }


        gameScreen = (LinearLayout) findViewById(R.id.gameScreen);
        progress = (ProgressBar) findViewById(R.id.progress);

        boardAdapter = new BoardAdapter(this);
        pieceAdapter = new PieceAdapter(this);

        boardGrid = (GridView) findViewById(R.id.gameboard);
        boardGrid.setAdapter(boardAdapter);


        piecesGrid = (GridView) findViewById(R.id.pieces);
        piecesGrid.setAdapter(pieceAdapter);


        piecesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int x = position % 15;
                int y = position / 15;
                if(isPlayer) {
                    if (!isPlayingPiece) {

                        Toast.makeText(getApplicationContext(), "(" + x + "," + y + ")", Toast.LENGTH_SHORT).show();
                    } else {
                        if (myTurn) {
                            if (legal(x, y) || firstPiece) {
                                firstPiece = false;
                                originalIDs[playingCount] = new OriginalBoardTiles(pieceAdapter.squareIDs[position], position);
                                playingCount++;
                                Context context = v.getContext();
                                int imageId = context.getResources().getIdentifier("" + pieceToPlay, "drawable", context.getPackageName());
                                builder.append(pieceToPlay);
                                pieceAdapter.squareIDs[position] = imageId;
                                piecesGrid.setAdapter(pieceAdapter);
                                board.set(x, y);
                                cellSetters.add(new CellSetter(pieceToPlay, x, y, wasASpace));
                                wasASpace = false;
                                isPlayingPiece = false;
                            } else {
                                Toast.makeText(getApplicationContext(), "Must be linked to another tile", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Not your turn!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        if(isPlayer) {
            initializePlayers = new InitializePlayers(packet);
            initializePlayers.execute((Void) null);
        }
        else
        {

        }

    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            gameScreen.setVisibility(show ? View.GONE : View.VISIBLE);
            gameScreen.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    gameScreen.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            gameScreen.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SPACE_REPLACEMENT && resultCode == Activity.RESULT_OK)
        {
            String s = data.getStringExtra("character");

            pieceToPlay = s.charAt(0);
            wasASpace = true;
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }
        if(requestCode == REQUEST_SPACE_REPLACEMENT && resultCode== Activity.RESULT_CANCELED)
        {
            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean legal(int x,int y)
    {
        if(board.isSet(x,y))
        {
            return false;
        }
        else if(board.isSet((x-1),y))
        {
            return true;
        }
        else if(board.isSet((x+1),y))
        {
            return true;
        }
        else if(board.isSet(x,y-1))
        {
            return true;
        }
        else if(board.isSet(x,y-1))
        {
            return true;
        }

        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Send");
        menu.add(0, 2, 0, "Clear Move");
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == 1 &&isPlayer)
        {
            originalIDs = new OriginalBoardTiles[7];
            originalRack = new OriginalRack[7];
            playingCount = 0;
            myTurn = false;
            String finalWord = builder.toString();
            Toast.makeText(getApplicationContext(), finalWord, Toast.LENGTH_SHORT).show();
            builder = new StringBuilder();

            CellSetter [] array = cellSetters.toArray(new CellSetter[cellSetters.size()]);
            sender = new PacketSender(array);
            sender.execute((Void) null);

            wait = new PacketWait(packet);
            wait.execute((Void) null);
        }

        else if (id == 2&&isPlayer)
        {
            if (!isPlayingPiece)
            {
                unsetPieces();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Place piece first!", Toast.LENGTH_SHORT).show();
            }
        }


        return true;
    }

    private void unsetPieces()
    {
        for (int i = 0; i < originalIDs.length; i++) {
            if (originalIDs[i] != null) {
                pieceAdapter.squareIDs[originalIDs[i].postion] = originalIDs[i].id;


                int position = originalIDs[i].postion;

                int x = position % 15;
                int y = position / 15;
                board.unset(x,y);
                originalIDs[i] = null;
            }

            if (originalRack[i] != null)
            {
                chars[i] = originalRack[i].character;
                Context context = originalRack[i].view.getContext();
                int imageId;
                if(originalRack[i].isSpace)
                    imageId = context.getResources().getIdentifier("space", "drawable", context.getPackageName());
                else
                    imageId = context.getResources().getIdentifier(originalRack[i].character + "", "drawable", context.getPackageName());
                originalRack[i].view.setImageResource(imageId);
                originalRack[i] = null;

            }
        }
        tileUsed = new boolean[]{false,false,false,false,false,false,false,false};
        playingCount = 0;
        firstPiece = true;
        builder = new StringBuilder();
        cellSetters = new ArrayList<CellSetter>();
        piecesGrid.setAdapter(pieceAdapter);
    }

    public void placePiece(View view)
    {
        if(isPlayer) {
            if (myTurn) {
                if (!isPlayingPiece) {
                    String name = this.getResources().getResourceEntryName(view.getId());
                    int index = Integer.parseInt(name.substring(name.indexOf("_") + 1));
                    if (!tileUsed[index]) {
                        ImageView imageView = (ImageView) findViewById(view.getId());
                        pieceToPlay = chars[index];
                        chars[index] = '#';
                        if (pieceToPlay == ' ') {
                            spaceReplacement = new Replacement(imageView, view.getId(), index);
                            tileUsed[index] = true;
                            originalRack[playingCount] = new OriginalRack(imageView, view.getId(), pieceToPlay, true);
                            imageView.setImageResource(R.drawable.nothing);
                            isPlayingPiece = true;
                            getSpaceReplacement();
                        } else {
                            tileUsed[index] = true;
                            originalRack[playingCount] = new OriginalRack(imageView, view.getId(), pieceToPlay, false);
                            imageView.setImageResource(R.drawable.nothing);
                            isPlayingPiece = true;
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Place piece first!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Not your turn!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updateTiles(char [] tiles)
    {
        int index = 0;
        for(int i =0; i <chars.length; i++)
        {
            if(chars[i] == '#')
            {
                chars[i] = tiles[index];
                index++;
            }
        }

        setTiles(chars);
    }

    public void setTiles(char [] tiles)
    {
        try {
            ImageView image1 = (ImageView) findViewById(R.id.piece_0);
            ImageView image2 = (ImageView) findViewById(R.id.piece_1);
            ImageView image3 = (ImageView) findViewById(R.id.piece_2);
            ImageView image4 = (ImageView) findViewById(R.id.piece_3);
            ImageView image5 = (ImageView) findViewById(R.id.piece_4);
            ImageView image6 = (ImageView) findViewById(R.id.piece_5);
            ImageView image7 = (ImageView) findViewById(R.id.piece_6);
            Context context = image1.getContext();
            int imageId;

            for (int i = 0; i < tiles.length; i++) {
                ImageView image;
                if (i == 0) {
                    image = image1;
                } else if (i == 1) {
                    image = image2;
                } else if (i == 2) {
                    image = image3;
                } else if (i == 3) {
                    image = image4;
                } else if (i == 4) {
                    image = image5;
                } else if (i == 5) {
                    image = image6;
                } else {
                    image = image7;
                }
                if (tiles[i] == ' ')
                    imageId = context.getResources().getIdentifier("space", "drawable", context.getPackageName());
                else
                    imageId = context.getResources().getIdentifier(tiles[i] + "", "drawable", context.getPackageName());
                image.setImageResource(imageId);
            }

            tileUsed = new boolean[]{false,false,false,false,false,false,false,false};
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void updateScores(int [] scores)
    {
        TextView player1 = (TextView) findViewById(R.id.player1);
        TextView player2 = (TextView) findViewById(R.id.player2);
        TextView player3 = (TextView) findViewById(R.id.player3);
        TextView player4 = (TextView) findViewById(R.id.player4);

        player1.setText(names[0]+": " + scores[0]);
        player2.setText(names[1]+": " + scores[1]);
        player3.setText(names[2] + ": " + scores[2]);
        player4.setText(names[3] + ": " + scores[3]);


    }

    public void initialSetup(Packet packet)
    {
        names = packet.players;
        char [] rack = packet.rack;
        chars = packet.rack;
        TextView player1 = (TextView) findViewById(R.id.player1);
        TextView player2 = (TextView) findViewById(R.id.player2);
        TextView player3 = (TextView) findViewById(R.id.player3);
        TextView player4 = (TextView) findViewById(R.id.player4);

        if(names[0].equals(packet.currentPlayer))
        {
            player1.setTextColor(Color.GREEN);
        }
        else if(names[1].equals(packet.currentPlayer))
        {
            player2.setTextColor(Color.GREEN);
        }
        else if(names[2].equals(packet.currentPlayer))
        {
            player3.setTextColor(Color.GREEN);
        }
        else
        {
            player4.setTextColor(Color.GREEN);
        }

        player1.setText(names[0]+": 0");
        player2.setText(names[1]+": 0");
        player3.setText(names[2]+": 0");
        player4.setText(names[3]+": 0");


        setTiles(rack);

        if(myName.equals(packet.currentPlayer))
        {
            myTurn = true;
        }
        else
        {
            myTurn = false;
        }
    }


    public void evaluateData(Packet packet, boolean isUpdate)
    {
        if(isUpdate)
        {
            if(packet.playerTurn.equals(packet.currentPlayer) && packet.playerTurn.equals(myName))
            {
                myTurn = true;

            }
            else if(packet.playerTurn.equals(myName))
            {
                myTurn = true;

            }
            else {
                myTurn = false;
            }
            updateTiles(packet.tiles);
            updateScores(packet.scores);

            TextView player1 = (TextView) findViewById(R.id.player1);
            TextView player2 = (TextView) findViewById(R.id.player2);
            TextView player3 = (TextView) findViewById(R.id.player3);
            TextView player4 = (TextView) findViewById(R.id.player4);

            int scrabbleRed = getResources().getColor(R.color.scrabble_red);

            if(names[0].equals(packet.playerTurn))
            {
                player1.setTextColor(Color.GREEN);
                player2.setTextColor(Color.rgb(164,54,55));
                player3.setTextColor(Color.rgb(164,54,55));
                player4.setTextColor(Color.rgb(164,54,55));
            }
            else if(names[1].equals(packet.playerTurn))
            {
                player1.setTextColor(Color.rgb(164,54,55));
                player2.setTextColor(Color.GREEN);
                player3.setTextColor(Color.rgb(164,54,55));
                player4.setTextColor(Color.rgb(164,54,55));
            }
            else if(names[2].equals(packet.playerTurn))
            {
                player1.setTextColor(Color.rgb(164,54,55));
                player2.setTextColor(Color.rgb(164,54,55));
                player3.setTextColor(Color.GREEN);
                player4.setTextColor(Color.rgb(164,54,55));
            }
            else
            {
                player1.setTextColor(Color.rgb(164,54,55));
                player2.setTextColor(Color.rgb(164,54,55));
                player3.setTextColor(Color.rgb(164,54,55));
                player4.setTextColor(Color.GREEN);
            }
        }
        else
        {
            Context context = getApplicationContext();
            CellSetter [] setters = packet.deSerializeCellSetter(packet.cellSetterStrings);
            for(int i = 0; i < setters.length; i++)
            {
                board.set(setters[i].postion.x, setters[i].postion.y);
                int imageId;
                if(setters[i].character == ' ')
                    imageId = context.getResources().getIdentifier("space", "drawable", context.getPackageName());
                else
                    imageId = context.getResources().getIdentifier(setters[i].character + "", "drawable", context.getPackageName());
                int index = setters[i].postion.y * 15 + setters[i].postion.x;
                pieceAdapter.squareIDs[index] = imageId;
            }

            piecesGrid.setAdapter(pieceAdapter);
        }
    }

    public void getSpaceReplacement()
    {
        Intent intent = new Intent(this,SpaceReplacement.class);
        startActivityForResult(intent, REQUEST_SPACE_REPLACEMENT);
    }

    private class OriginalBoardTiles
    {
        int id;
        int postion;

        private OriginalBoardTiles(int id, int position)
        {
            this.id = id;
            this.postion = position;
        }
    }

    private class OriginalRack
    {
        int id;
        char character;
        ImageView view;
        boolean isSpace;

        private OriginalRack(ImageView view, int id, char character,boolean isSpace)
        {
            this.view = view;
            this.id = id;
            this.character = character;
            this.isSpace = isSpace;
        }
    }

    private class Replacement
    {
        ImageView image;
        int index;
        int id;

        private Replacement(ImageView image, int id,int index)
        {
            this.image = image;
            this.id = id;

        }

    }

    public class InitializePlayers extends AsyncTask<Void, Void, Boolean> {

        XmlConverter converter = XmlConverter.newInstance();
        String data;
        Packet packet;
        InitializePlayers(Packet packet){
            this.packet = packet;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try {
                data = GameClient.receiveData();
                if (!data.equals(null)) {
                    packet = converter.unmarshall(data, Packet.class);
                    initialSetup(packet);
                    return true;
                } else
                    return false;
            }
            catch (RuntimeException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            if (success)
            {
                showProgress(false);
            }

            else
            {
                onCancelled();
            }
        }

        @Override
        protected void onCancelled()
        {
            showProgress(false);
        }
    }

    public class SpectatorThread implements Runnable
    {
        SpectatorReceiver receiver;

        public SpectatorThread()
        {

        }

        @Override
        public void run()
        {
            while(true)
            {

            }
        }
    }

    public class SpectatorReceiver extends AsyncTask<Void, Void, Boolean>
    {
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                return true;
            }
            catch (RuntimeException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            if (success)
            {

            }

            else
            {
                onCancelled();
            }
        }

        @Override
        protected void onCancelled()
        {
            showProgress(false);
        }
    }

    //wait on data from server
    public class PacketWait extends AsyncTask<Void, Void, Boolean> {

        XmlConverter converter = XmlConverter.newInstance();
        String data;
        String moves;
        Packet dataPacket;
        Packet movePacket;
        PacketWait(Packet packet){
            this.dataPacket = packet;
            this.movePacket = packet;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try {
                data = GameClient.receiveData();
                moves = GameClient.receiveData();
                if (!data.equals(null)) {
                    dataPacket = converter.unmarshall(data, Packet.class);
                    movePacket = converter.unmarshall(moves, Packet.class);
                    return true;
                } else
                    return false;
            }
            catch (RuntimeException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            if (success)
            {
                if(dataPacket != null)
                    evaluateData(dataPacket, true);
                if(movePacket != null)
                    evaluateData(movePacket, false);
            }

            else
            {
                onCancelled();
            }
        }

        @Override
        protected void onCancelled()
        {
            showProgress(false);
        }
    }

    public class PacketSender extends AsyncTask<Void, Void, Boolean> {

        XmlConverter converter = XmlConverter.newInstance();
        CellSetter [] array;
        PacketSender(CellSetter [] array)
        {
            this.array = array;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {

            Packet p = new Packet(array,myName);
            try {
                String data = converter.marshall(p, Packet.class);
                data = data.replace("\n", "").replace("\r", "");
                GameClient.sendData(data);
                return true;
            }
            catch (Exception e)
            {
                System.out.println(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            if (success)
            {
            }

            else
            {
                onCancelled();
            }
        }

        @Override
        protected void onCancelled()
        {
            showProgress(false);
        }
    }

}
