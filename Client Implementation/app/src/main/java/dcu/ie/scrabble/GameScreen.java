package dcu.ie.scrabble;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

import dcu.ie.scrabble.actors.ScrabblePlayer;
import dcu.ie.scrabble.actors.Spectator;
import dcu.ie.scrabble.adapter.*;
import dcu.ie.scrabble.connectivity.GameClient;
import dcu.ie.scrabble.game_objects.Board;

public class GameScreen extends AppCompatActivity {

    int greenDot = R.drawable.green_dot;
    BoardAdapter boardAdapter;
    PieceAdapter pieceAdapter;
    Spectator player = null;

    GridView boardGrid;
    GridView piecesGrid;

    Board board = new Board();

    //rack
    char [] chars = new char[]{'a','b','c','d','e','f','g'};
    boolean [] tileUsed = new boolean[]{false,false,false,false,false,false,false,false};
    StringBuilder builder = new StringBuilder();

    //playing pieces
    char pieceToPlay;
    Boolean isPlayingPiece = false;
    Boolean firstPiece = true;
    Boolean illegalMove = false;

    OriginalBoardTiles [] originalIDs = new OriginalBoardTiles[7];
    OriginalRack [] originalRack = new OriginalRack[7];
    ArrayList<CellSetter> cellSetters = new ArrayList<>();


    int playingCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        Intent intent = getIntent();
        String playerFlag = intent.getStringExtra("PLAYER");
        if(playerFlag.equals("SET"))
        {
            player = new ScrabblePlayer(intent.getStringExtra("NAME"));
            Toast.makeText(getApplicationContext(), "You are a player", Toast.LENGTH_SHORT).show();
        }
        else
        {
            player = new Spectator();
            Toast.makeText(getApplicationContext(), "You are a spectator", Toast.LENGTH_SHORT).show();
        }



        boardAdapter = new BoardAdapter(this);
        pieceAdapter = new PieceAdapter(this);

        boardGrid = (GridView) findViewById(R.id.gameboard);
        boardGrid.setAdapter(boardAdapter);


        piecesGrid = (GridView) findViewById(R.id.pieces);
        piecesGrid.setAdapter(pieceAdapter);


        piecesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                int x = position % 15;
                int y = position / 15;
                if(!isPlayingPiece) {

                    Toast.makeText(getApplicationContext(), "(" + x + "," + y + ")", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (legal(x,y) || firstPiece)
                    {
                        firstPiece = false;
                        originalIDs[playingCount] = new OriginalBoardTiles(pieceAdapter.squareIDs[position], position);
                        playingCount++;
                        Context context = v.getContext();
                        int imageId = context.getResources().getIdentifier("" + pieceToPlay, "drawable", context.getPackageName());
                        builder.append(pieceToPlay);
                        pieceAdapter.squareIDs[position] = imageId;
                        piecesGrid.setAdapter(pieceAdapter);
                        board.set(x, y);
                        cellSetters.add(new CellSetter(pieceToPlay, new Point(x, y), (pieceToPlay == ' ')));
                        isPlayingPiece = false;
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Must be linked to another tile", Toast.LENGTH_SHORT).show();
                    }
                }
           }
        });
    }

    public boolean legal(int x,int y)
    {
        if(board.isSet((x-1),y))
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

        if (id == 1)
        {
            String finalWord = builder.toString();
            Toast.makeText(getApplicationContext(), finalWord, Toast.LENGTH_SHORT).show();
        }

        else if (id == 2)
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

            if (originalRack[i] != null) {

                Context context = originalRack[i].view.getContext();
                int imageId = context.getResources().getIdentifier(originalRack[i].character + "", "drawable", context.getPackageName());
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
        if(!isPlayingPiece) {
            String name = this.getResources().getResourceEntryName(view.getId());
            int index = Integer.parseInt(name.substring(name.indexOf("_") + 1));
            if (!tileUsed[index]) {
                ImageView imageView = (ImageView) findViewById(view.getId());
                pieceToPlay = chars[index];
                tileUsed[index] = true;
                originalRack[playingCount] = new OriginalRack(imageView, view.getId(), pieceToPlay);
                imageView.setImageResource(R.drawable.nothing);
                isPlayingPiece = true;
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Place piece first!", Toast.LENGTH_SHORT).show();
        }
    }

    public void evaluateData(String data)
    {
        String decodedData = GameClient.decodeData(data);
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

        private OriginalRack(ImageView view, int id, char character)
        {
            this.view = view;
            this.id = id;
            this.character = character;
        }
    }

    private class CellSetter
    {
        char character;
        Point position;
        boolean isSpace;

        private CellSetter(char character,Point position,boolean isSpace)
        {
            this.character = character;
            this.position = position;
            this.isSpace = isSpace;
        }
    }

    //wait on data from server
    public class PacketWait extends AsyncTask<Void, Void, Boolean> {

        String data;
        PacketWait(){}

        @Override
        protected Boolean doInBackground(Void... params)
        {
            data = GameClient.receiveData();
            if(!data.equals(null))
                return true;
            else
                return false;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            if (success)
            {
                evaluateData(data);
            }

            else
            {
                onCancelled();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

}
