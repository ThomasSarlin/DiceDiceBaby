package com.example.kanin.thirtythrow;
/**
 * Class represents a dice, which can be rolled or selected.
 *
 * @author Thomas Sarlin - id15tsn
 */

import android.util.Log;
import java.io.Serializable;

public class Dice implements Serializable {
    private boolean mSelected;
    private int mNumber;
    private static String TAG = "dice";

    Dice() {
        mSelected = false;
        mNumber = 0;
    }

    public int getNumber() {
        return mNumber;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public void setNumber(int i) {
        mNumber = i; //if set to 0 or above 6, counts as "unknown"
    }

    public void toggleSelected() {
        Log.d("dice", "toggle" + mSelected);
        if (mNumber != 0) mSelected = !mSelected;
    }

    public void rollDice() {
        if (!mSelected) {
            mNumber = (int) Math.floor(Math.random() * 6 + 1);
            Log.d(TAG, "Number:" + mNumber);
        }
    }
}
