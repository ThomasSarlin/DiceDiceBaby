package com.example.kanin.thirtythrow;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameLogic {
    private static String CR_KEY = "currentRound";
    private static String CS_KEY = "currentSet";
    private static String TS_KEY = "totalScore";
    private static String RS_KEY = "roundScore";
    private static String DI_KEY = "diceKey";
    private static String HI_KEY = "historyKey";
    private ArrayList<Dice> mDices;
    private static String TAG = "Logic";
    private int mCurrentRound;
    private int mMaxRounds = 10;
    private int mSet;
    private int mTotalScore;
    private int mRoundScore;
    private ArrayList<Score> mScoreHistory;

    public GameLogic() {
        addDices();
        mCurrentRound = 1;
        mSet = 1;
        mTotalScore = 0;
        mRoundScore = 0;
        mScoreHistory = new ArrayList<>();
    }
    private void addDices(){
        mDices = new ArrayList<>();
        mDices.add(new Dice());
        mDices.add(new Dice());
        mDices.add(new Dice());
        mDices.add(new Dice());
        mDices.add(new Dice());
        mDices.add(new Dice());
    }
    public GameLogic(Bundle bundle) {
        mDices = (ArrayList<Dice>) bundle.getSerializable(DI_KEY);
        mCurrentRound = bundle.getInt(CR_KEY);
        mSet = bundle.getInt(CS_KEY);
        mTotalScore = bundle.getInt(TS_KEY);
        mRoundScore = bundle.getInt(RS_KEY);
        mScoreHistory = (ArrayList<Score>) bundle.getSerializable(HI_KEY);
    }

    public void addToHistory(String type) {
        Log.d(TAG, "Added to history type: " + type);
        mScoreHistory.add(new Score(mRoundScore, type, mCurrentRound));
        mTotalScore += mRoundScore;
        mRoundScore = 0;
    }

    public ArrayList<Score> getScoreHistory() {
        return mScoreHistory;
    }

    public void addScore(int score) {
        mRoundScore += score;
    }

    public int getRound() {
        return mCurrentRound;
    }

    public ArrayList<Dice> getDices() {
        return mDices;
    }

    public boolean lastSet() {
        return mSet == 4;
    }

    public boolean lastRound() {
        return mCurrentRound == mMaxRounds;
    }

    public int roundsLeft() {
        return mMaxRounds - (mCurrentRound + 1);
    }

    public int checkScore(int targetScore) {
        int score = 0;
        for (Dice dice : mDices) {
            if (dice.isSelected())
                score += dice.getNumber();
        }
        return Integer.compare(score, targetScore);
    }

    public void collectLowScore() {
        int score = 0;
        for (Dice dice : mDices) {
            if (dice.getNumber() < 4)
                score += dice.getNumber();
        }
        addScore(score);
    }

    public void deactivateSeleted() {
        for (Dice dice : mDices) {
            if (dice.isSelected()) {
                dice.toggleSelected();
                dice.setNumber(0);
            }
        }
    }

    public void resetDices() {
        for (Dice dice : mDices) {
            dice.setNumber(0);
            dice.setSelected(false);
        }
    }

    public void rollDices() {
        for (Dice dice : mDices) {
            dice.rollDice();
        }
    }

    public void resetSelection() {
        for (Dice dice : mDices) {
            dice.setSelected(false);
        }
    }

    public int getTotalScore() {
        return mTotalScore;
    }

    public void nextSet() {
        mSet++;
    }

    public void nextRound() {
        if (mCurrentRound < mMaxRounds) {
            mCurrentRound++;
            mSet = 1;
        }
    }

    public Bundle getBundle() {
        Bundle result = new Bundle();
        result.putInt(CR_KEY, mCurrentRound);
        result.putInt(TS_KEY, mTotalScore);
        result.putInt(RS_KEY, mRoundScore);
        result.putInt(CS_KEY, mSet);
        result.putSerializable(DI_KEY, mDices);
        result.putSerializable(HI_KEY, mScoreHistory);
        return result;
    }
}
