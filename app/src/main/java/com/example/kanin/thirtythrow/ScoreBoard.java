package com.example.kanin.thirtythrow;
/**
 * Class representing the Controller for the ScoreBoard activity
 * @author: Thomas Sarlin - id15tsn
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ScoreBoard extends AppCompatActivity {
    private static String SC_KEY= "scoreKey";
    private static String TS_KEY= "totalScoreKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        setHeader(getIntent().getIntExtra(TS_KEY,0));
        setListView((ArrayList<Score>)getIntent().getSerializableExtra(SC_KEY));
    }
    private void setHeader(int totalScore){
        TextView header = findViewById(R.id.score);
        header.setText("TOTAL SCORE: "+totalScore);
    }
    private void setListView(ArrayList<Score> scores){
        ListView listView = findViewById(R.id.listView);
        ArrayList<String> StringScores = new ArrayList<>();
        for(Score s:scores){
            StringScores.add(s.toString());
        }
        listView.setAdapter(new ArrayAdapter<>(this ,
                android.R.layout.simple_list_item_1,StringScores));
    }

    public static Intent newIntent(Context packageContext, ArrayList<Score> scores,int totalScore){
        Intent i = new Intent(packageContext,ScoreBoard.class);
        i.putExtra(SC_KEY,scores);
        i.putExtra(TS_KEY,totalScore);
        return i;
    }
    public void onBackPressed() {
        AlertDialog.Builder aD=new AlertDialog.Builder(this);
        aD.setTitle("Warning");
        aD.setMessage("Are you sure you want to leave this amazing scoreboard?" +
                "\n\nThe game WILL restart and you'll lose your screen-shot moment.");
        aD.setPositiveButton("Yes", (dialog, which) -> ScoreBoard.super.onBackPressed());
        aD.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        aD.create();
        aD.show();
    }
}
