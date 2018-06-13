package com.example.kanin.thirtythrow;
/**
 * Class represents once round in the game of ThrowThirty and keeps track of which round,
 * score, dice combinations and what type of action was selected.
 */

import java.io.Serializable;
import java.util.ArrayList;

public class Score implements Serializable{
    private int mScore;
    private String mType;
    private int mRound;
    private ArrayList<ArrayList<Integer>> mDiceCombinations;

    Score(int score, String type, int round, ArrayList<ArrayList<Integer>> diceCombinations) {
        mScore = score;
        mType = type;
        mRound = round;
        mDiceCombinations = diceCombinations;
    }

    public int getScore() {
        return mScore;
    }

    public String getType() {
        return mType;
    }

    private String producDiceCombinationString(){
        StringBuilder result= new StringBuilder();
        for(ArrayList arrayList:mDiceCombinations){
            for(int i=0;i<arrayList.size();i++){
                result.append(arrayList.get(i));
                if(i!=arrayList.size()-1) result.append("+");
            }
            result.append((mDiceCombinations.size() - 1 == mDiceCombinations.indexOf(arrayList))
                    ? "" : ", ");
        }
        return result.toString();
    }
    public String toString(){
        return "Round: "+mRound +" Type: "+mType+"\nRound Score: "+mScore
                + "\nCombinations: "+producDiceCombinationString();
    }
}
