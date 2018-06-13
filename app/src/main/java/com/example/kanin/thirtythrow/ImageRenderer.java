package com.example.kanin.thirtythrow;
/**
 * HelpClass which keeps track of how the imagebuttons should be rendered.
 */

import android.widget.ImageButton;
import android.widget.ImageView;

public class ImageRenderer {
    public static void renderImageButton(ImageButton button, Dice dice) {
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
    public static void RenderImageView(ImageView image, int diceNumber){
        switch (diceNumber) {
            case 1:
                image.setImageResource(R.drawable.white1);
                break;
            case 2:
                image.setImageResource(R.drawable.white2);
                break;
            case 3:
                image.setImageResource(R.drawable.white3);
                break;
            case 4:
                image.setImageResource(R.drawable.white4);
                break;
            case 5:
                image.setImageResource(R.drawable.white5);
                break;
            case 6:
                image.setImageResource(R.drawable.white6);
                break;
            default:
                image.setImageResource(R.drawable.unknown);
        }
    }
}
