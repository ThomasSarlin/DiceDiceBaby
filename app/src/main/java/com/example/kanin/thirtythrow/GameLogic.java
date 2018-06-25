package com.example.kanin.thirtythrow;
/**
 * Class represents the Model part of the MVC in the ThirtyThrow Application,
 * responsible for calculations, dices and keeping track of rounds/sets.
 * @author Thomas Sarlin - id15tsn
 */

import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;

public class GameLogic {
    //Keys for Bundle creation and extraction
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
    private ArrayList<ArrayList<Integer>> mDiceCombination;
    private ArrayList<Integer> mCurrentCombination;

    GameLogic() {
        addDices();
        mCurrentRound = 1;
        mSet = 1;
        mTotalScore = 0;
        mRoundScore = 0;
        mScoreHistory = new ArrayList<>();
        mDiceCombination = new ArrayList<>();

    }

    private void addDices(){
        mDices = new ArrayList<>();
        mDices.addAll(Arrays.asList(
                new Dice(),
                new Dice(),
                new Dice(),
                new Dice(),
                new Dice(),
                new Dice()));
    }

    /**
     * Constructor used for creating a new instance when the application changes orientation
     * or state without loosing the current game data.
     * @param bundle
     */
    GameLogic(Bundle bundle) {
        mCurrentRound = bundle.getInt(CR_KEY);
        mSet = bundle.getInt(CS_KEY);
        mTotalScore = bundle.getInt(TS_KEY);
        mRoundScore = bundle.getInt(RS_KEY);
        mScoreHistory = (ArrayList<Score>) bundle.getSerializable(HI_KEY);
        mDices = (ArrayList<Dice>) bundle.getSerializable(DI_KEY);
    }

    public void addToHistory(String type) {
        Log.d(TAG, "Added to history type: " + type);
        mScoreHistory.add(new Score(mRoundScore, type, mCurrentRound,mDiceCombination));
        mDiceCombination= new ArrayList<>();
        mRoundScore = 0;
    }

    public ArrayList<Score> getScoreHistory() {
        return mScoreHistory;
    }

    public void addScore(int score) {
        mRoundScore += score;
        mTotalScore += score;
        mDiceCombination.add(mCurrentCombination);

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
        mCurrentCombination= new ArrayList<>();
        int score = 0;
        for (Dice dice : mDices) {
            if (dice.isSelected()) {
                score += dice.getNumber();
                mCurrentCombination.add(dice.getNumber());
            }
        }
        return Integer.compare(score, targetScore);
    }

    public void collectLowScore() {
        mCurrentCombination= new ArrayList<>();
        int score = 0;
        for (Dice dice : mDices) {
            if (dice.getNumber() < 4) {
                score += dice.getNumber();
                mCurrentCombination.add(dice.getNumber());
            }
        }
        addScore(score);
    }

    //Used when score is collected to make selected dices inactive.
    public void deactivateSelected() {
        for (Dice dice : mDices) {
            if (dice.isSelected()) {
                dice.toggleSelected();
                dice.setNumber(0);
            }
        }
    }

    //Reset function in between rounds.
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

    //Getter for bundle used when the application changes orientation or swaps between states
    public Bundle getBundle() {
        Bundle result = new Bundle();
        result.putInt(CR_KEY, mCurrentRound);
        result.putInt(TS_KEY, mTotalScore);
        result.putInt(RS_KEY, mRoundScore);
        result.putInt(CS_KEY, mSet);
        result.putSerializable(HI_KEY, mScoreHistory);
        result.putSerializable(DI_KEY, mDices);
        return result;
    }
}
