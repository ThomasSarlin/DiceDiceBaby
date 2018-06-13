package com.example.kanin.thirtythrow;

import android.util.Log;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameLogic{

    private Map<ImageButton, Dice> mDices;
    private static String TAG = "Logic";
    private int mCurrentRound;
    private int mMaxRounds=10;
    private int mSet;
    private int mTotalScore;
    private int mRoundScore;
    private ArrayList<Score> mScoreHistory;

    public GameLogic(){
        mDices = new HashMap<>();
        mCurrentRound=1;
        mSet=1;
        mTotalScore=0;
        mRoundScore=0;
        mScoreHistory=new ArrayList<>();
    }
    public void addToHistory(String type){
        Log.d(TAG,"Added to history type: "+type);
        mScoreHistory.add(new Score(mRoundScore,type,mCurrentRound));
        mTotalScore+=mRoundScore;
        mRoundScore=0;
    }
    public ArrayList<Score> getScoreHistory() {
        return mScoreHistory;
    }
    public void addScore(int score){
        mRoundScore+=score;
    }

    public int getRound() {
        return mCurrentRound;
    }
    public void putDice(ImageButton button){
            mDices.put(button, new Dice());
    }

    public Map<ImageButton, Dice> getDices() {
        return mDices;
    }
    public boolean lastSet() {
        return mSet==4;
    }
    public boolean lastRound(){
        return mCurrentRound==mMaxRounds;
    }
    public int roundsLeft(){
        return mMaxRounds-(mCurrentRound+1);
    }
    public int checkScore(int targetScore){
        int score=0;
        for(Dice dice:mDices.values()){
            if(dice.isSelected())
                score+=dice.getNumber();
        }
        return Integer.compare(score, targetScore);
    }
    public void collectLowScore(){
        int score=0;
        for(Dice dice:mDices.values()){
            if(dice.getNumber()<4)
                score+=dice.getNumber();
        }
        addScore(score);
    }

    public void deactivateSeleted(){
        for (Map.Entry<ImageButton, Dice> entry : mDices.entrySet()) {
            if (entry.getValue().isSelected()) {
                entry.getValue().toggleSelected();
                entry.getValue().setNumber(0);
            }
        }
    }
    public void resetDices(){
        for(Map.Entry<ImageButton,Dice> entry:mDices.entrySet()){
            entry.getValue().setNumber(0);
            entry.getValue().setSelected(false);
        }
    }
    public void resetSelection() {
        for (Map.Entry<ImageButton, Dice> entry : mDices.entrySet()) {
            entry.getValue().setSelected(false);
        }
    }
    public int getTotalScore() {
        return mTotalScore;
    }
    public void nextSet(){
        mSet++;
    }
    public void nextRound(){
        if(mCurrentRound<mMaxRounds) {
            mCurrentRound++;
            mSet=1;
        }
    }
}
