package com.example.kanin.thirtythrow;

public class Score {
    int mScore;
    String mType;
    int mRound;

    public Score(int score, String type, int round) {
        mScore = score;
        mType = type;
        mRound = round;
    }

    public int getScore() {
        return mScore;
    }

    public String getType() {
        return mType;
    }

    public int getRound() {
        return mRound;
    }
}
