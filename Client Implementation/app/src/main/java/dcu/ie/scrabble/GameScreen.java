package dcu.ie.scrabble;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import dcu.ie.scrabble.actors.ScrabblePlayer;
import dcu.ie.scrabble.actors.Spectator;
import dcu.ie.scrabble.adapter.*;

public class GameScreen extends AppCompatActivity {

    int greenDot = R.drawable.green_dot;
    BoardAdapter boardAdapter;
    PieceAdapter pieceAdapter;
    Spectator player = null;

    GridView boardGrid;
    GridView piecesGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

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

        piecesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                int x = position % 15;
                int y = position / 15;
                Toast.makeText(getApplicationContext(), "(" + x + "," + y + ")", Toast.LENGTH_SHORT).show();
           }
        });
    }


}
