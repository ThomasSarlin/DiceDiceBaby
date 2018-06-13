package com.example.kanin.thirtythrow;
/**
 * Main class for ThirtyThrow application, acts as the controller for the application,
 * responsible for all communications betweeen the model(GameLogic)
 * and the View(activity_thirty_throws).
 * @author Thomas Sarlin - id15tsn
 */

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ThirtyThrows extends AppCompatActivity {
    private static String TAG = "main";
    private static String GL_KEY = "Gamelogic";
    private ArrayList<ImageButton> mImageButtons;
    private Button mRollButton;
    private Button mNextRoundButton;
    private TextView mMessage;
    private TextView mHeader;
    private boolean mLockmenu = false;
    private Spinner mSpinner;
    private int mTargetValue=0;
    private GameLogic mGameLogic;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBundle(GL_KEY, mGameLogic.getBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirty_throws);
        if (savedInstanceState != null)
            mGameLogic = new GameLogic(savedInstanceState.getBundle(GL_KEY));
        else {
            mGameLogic = new GameLogic();
        }
        setup();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(mGameLogic.lastRound()){
            mGameLogic=new GameLogic();
            checkGameStatus();
        }
    }
    private void setup() {
        mMessage = findViewById(R.id.message);
        mHeader = findViewById(R.id.header);
        updateHeader();
        mSpinner = findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!mLockmenu && mGameLogic.lastSet()) {
                    if (i != 0){
                        mTargetValue = i + 3;
                        mMessage.setText(R.string.message_step2);
                    }
                    else {
                        mMessage.setText(R.string.message_score_added_fround_low);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setNextButton();
        setDiceButtons();
        setRollButton();
    }

    private void setMessage(int rId) {
        mMessage.setText(rId);
    }

    private void updateHeader() {
        mHeader.setText("Round: " + mGameLogic.getRound() + "\nScore: " + mGameLogic.getTotalScore());
    }

    private void setNextButton() {
        mNextRoundButton = findViewById(R.id.next_round_button);
        mNextRoundButton.setVisibility(View.GONE);
        mNextRoundButton.setOnClickListener(view -> {
            if (mTargetValue == 0) mGameLogic.collectLowScore();
            mGameLogic.addToHistory(mSpinner.getSelectedItem().toString());

            if (!mGameLogic.lastRound()) {
                mLockmenu = false;
                mGameLogic.nextRound();
                mGameLogic.resetDices();
                renderImages();
                checkGameStatus();
            } else {
                showScore();
            }
        });
    }

    private void setDiceButtons() {
        mImageButtons = new ArrayList<>();
        addDiceButton(R.id.dice1, mGameLogic.getDices().get(0));
        addDiceButton(R.id.dice2, mGameLogic.getDices().get(1));
        addDiceButton(R.id.dice3, mGameLogic.getDices().get(2));
        addDiceButton(R.id.dice4, mGameLogic.getDices().get(3));
        addDiceButton(R.id.dice5, mGameLogic.getDices().get(4));
        addDiceButton(R.id.dice6, mGameLogic.getDices().get(5));
    }

    private void addDiceButton(int rId, Dice dice) {
        ImageButton imageButton = findViewById(rId);
        imageButton.setOnClickListener(view -> {
            dice.toggleSelected();
            ImageRenderer.renderImageButton(imageButton, dice);
            if (mGameLogic.lastSet())
                calculateScore();
        });
        mImageButtons.add(imageButton);
    }

    private void setRollButton() {
        mRollButton = findViewById(R.id.roll_button);
        mRollButton.setOnClickListener(v -> {
            mGameLogic.rollDices();
            mGameLogic.nextSet();
            renderImages();
            checkGameStatus();
        });
    }

    private void calculateScore() {
        if (mGameLogic.checkScore(mTargetValue) == 0) {
            mGameLogic.deactivateSeleted();
            mGameLogic.addScore(mTargetValue);
            mMessage.setText(R.string.message_score_added_fround);
            mLockmenu = true;
        } else if (mGameLogic.checkScore(mTargetValue) == 1) {
            mGameLogic.resetSelection();
            mMessage.setText(R.string.message_too_large);
        } else {mMessage.setText(R.string.message_step2_2);}
        renderImages();
        updateHeader();
    }

    private void checkGameStatus() {
        if (mGameLogic.lastRound()) {
            mNextRoundButton.setText(R.string.finished);
        }
        if (mGameLogic.lastSet()) {
            mGameLogic.resetSelection();
            mRollButton.setVisibility(View.GONE);
            mNextRoundButton.setVisibility(View.VISIBLE);
                mMessage.setText(mTargetValue==0?
                        R.string.message_score_added_fround_low:
                        R.string.message_step2_2);
        } else {
            mNextRoundButton.setVisibility(View.GONE);
            mRollButton.setVisibility(View.VISIBLE);
            mMessage.setText(R.string.message_step1);
        }
        updateHeader();
        renderImages();
    }

    private void renderImages() {
        for (int i = 0; i < 6; i++) {
            ImageRenderer.renderImageButton(mImageButtons.get(i), mGameLogic.getDices().get(i));
        }
    }

    private void showScore() {
        Log.d(TAG, "SHOWSCORE");
        startActivity(ScoreBoard.newIntent(ThirtyThrows.this
                ,mGameLogic.getScoreHistory(),mGameLogic.getTotalScore()));
    }

    public void onBackPressed() {
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("Warning");
        alertdialog.setMessage("Are you sure you Want to exit the game?\nAll progress will be lost.");
        alertdialog.setPositiveButton("Yes", (dialog, which) -> ThirtyThrows.super.onBackPressed());
        alertdialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertdialog.create();
        alertdialog.show();
    }
}
