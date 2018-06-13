package com.example.kanin.thirtythrow;

import android.util.Log;

public class Dice{
    private boolean mSelected;
    private int mNumber;
    private static String TAG="dice";

    public Dice(){
        mSelected=false;
        mNumber=0;
    }
    public int getNumber() {
        return mNumber;
    }
    public void rollDice(){
        if(!mSelected) {
            mNumber=(int)Math.floor(Math.random()*6+1);
            Log.d(TAG, "Number:" + mNumber);
        }
    }
    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }
    public void setNumber(int i){mNumber=i;}
    public void toggleSelected(){
        Log.d("dice","toggle"+mSelected);
        if(mNumber!=0)mSelected=!mSelected;
    }
}
