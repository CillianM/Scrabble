package dcu.ie.scrabble;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpaceReplacement extends AppCompatActivity {

    String[] arraySpinner;
    Spinner s;
    String character;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_replacement);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .6), (int) (height * .4));

        Dialog dialog = new Dialog(getApplicationContext());
        dialog.setCanceledOnTouchOutside(true);

        dialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        setResult(Activity.RESULT_CANCELED, new Intent().putExtra("character", character));
                        finish();
                    }
                }
        );

        this.arraySpinner = new String[] {
                "a",
                "b",
                "c",
                "d",
                "e",
                "f",
                "g",
                "h",
                "i",
                "j",
                "k",
                "l",
                "m",
                "n",
                "o",
                "p",
                "q",
                "r",
                "s",
                "t",
                "u",
                "v",
                "w",
                "x",
                "y",
                "z"
        };
        s = (Spinner) findViewById(R.id.potentialItem);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                character =  parentView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setResult(Activity.RESULT_CANCELED, new Intent().putExtra("character", character));
        finish();
    }

    public void returnLetter(View view)
    {
        setResult(Activity.RESULT_OK, new Intent().putExtra("character", character));
        finish();
    }

}
