package com.example.kanin.thirtythrow;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Map;

public class ThirtyThrows extends AppCompatActivity {
    private static String TAG = "main";
    private Button mRollButton;
    private Button mNextRoundButton;
    private TextView mMessage;
    private TextView mHeader;
    private boolean mLockmenu = false;
    private Spinner mSpinner;
    private int mTargetValue;
    private GameLogic mGameLogic;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirty_throws);
        setup();
        checkGameStatus();
    }

    private void setup() {
        mGameLogic = new GameLogic();
        mMessage = findViewById(R.id.message);
        mHeader = findViewById(R.id.header);
        updateHeader();
        mSpinner = findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!mLockmenu && mGameLogic.lastSet()) {
                    if (i != 0) mTargetValue = i + 3;
                    else {
                        mGameLogic.collectLowScore();
                        mMessage.setText(R.string.message_score_added_fround_low);
                        mGameLogic.nextRound();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setDiceButtons();
        setNextButton();
        setRollButton();
        setMessage(R.string.message_step1);
    }

    private void setMessage(int rId) {
        mMessage.setText(rId);
    }

    private void updateHeader() {
        mHeader.setText("Round: " + mGameLogic.getRound() + "\nScore: " + mGameLogic.getTotalScore());
    }

    private void setNextButton() {
        mNextRoundButton = findViewById(R.id.next_round_button);
        mNextRoundButton.setOnClickListener(view -> {
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
        mGameLogic.putDice(findViewById(R.id.dice1));
        mGameLogic.putDice(findViewById(R.id.dice2));
        mGameLogic.putDice(findViewById(R.id.dice3));
        mGameLogic.putDice(findViewById(R.id.dice4));
        mGameLogic.putDice(findViewById(R.id.dice5));
        mGameLogic.putDice(findViewById(R.id.dice6));
        for (Map.Entry<ImageButton, Dice> entry : mGameLogic.getDices().entrySet()) {
            entry.getKey().setOnClickListener(v -> {
                entry.getValue().toggleSelected();
                renderImage(entry.getKey(), entry.getValue());
                if (mGameLogic.lastSet())
                    calculateScore();
            });
        }
    }

    private void setRollButton() {
        mRollButton = findViewById(R.id.roll_button);
        mRollButton.setOnClickListener(v -> {
            for (Map.Entry<ImageButton, Dice> entry : mGameLogic.getDices().entrySet()) {
                entry.getValue().rollDice();
                renderImage(entry.getKey(), entry.getValue());
            }
            mGameLogic.nextSet();
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
        } else mMessage.setText(R.string.message_step2_2);
        renderImages();
        updateHeader();
    }

    private void checkGameStatus() {
        if (mGameLogic.lastRound()) mNextRoundButton.setText(R.string.finished);
        if (mGameLogic.lastSet()) {
            mGameLogic.resetSelection();
            mRollButton.setVisibility(View.GONE);
            mNextRoundButton.setVisibility(View.VISIBLE);
            mMessage.setText(R.string.message_step2);
        } else {
            mNextRoundButton.setVisibility(View.GONE);
            mRollButton.setVisibility(View.VISIBLE);
            mMessage.setText(R.string.message_step1);
        }
        updateHeader();
        renderImages();
    }

    private void renderImages() {
        for (Map.Entry<ImageButton, Dice> entry : mGameLogic.getDices().entrySet()) {
            renderImage(entry.getKey(), entry.getValue());
        }
    }

    private void showScore() {
        Log.d(TAG, "SHOWSCORE");
    }

    private void renderImage(ImageButton button, Dice dice) {
        switch (dice.getNumber()) {
            case 1:
                button.setImageResource(dice.isSelected() ? R.drawable.white1selected : R.drawable.white1);
                break;
            case 2:
                button.setImageResource(dice.isSelected() ? R.drawable.white2selected : R.drawable.white2);
                break;
            case 3:
                button.setImageResource(dice.isSelected() ? R.drawable.white3selected : R.drawable.white3);
                break;
            case 4:
                button.setImageResource(dice.isSelected() ? R.drawable.white4selected : R.drawable.white4);
                break;
            case 5:
                button.setImageResource(dice.isSelected() ? R.drawable.white5selected : R.drawable.white5);
                break;
            case 6:
                button.setImageResource(dice.isSelected() ? R.drawable.white6selected : R.drawable.white6);
                break;
            default:
                button.setImageResource(R.drawable.unknown);
        }
    }
}
