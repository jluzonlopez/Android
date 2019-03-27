package jluzon.mov.urjc.xorapp;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;

public class LevelFactory {
    private static ImageView img;
    private static int[] imgArray;
    private static Switch[] chkB;

    public LevelFactory(ImageView iv, int[] imgs, Switch[] cb){
        img = iv;
        imgArray = imgs;
        chkB = cb;
    }

    public static Level produce(int desc){
        Level l;
        switch (desc){
            case 0:
                l = new Level0(img,imgArray[desc],chkB);
                break;
            case 1:
                l = new Level1(img,imgArray[desc],chkB);
                break;
            case 2:
                l = new Level2(img,imgArray[desc],chkB);
                break;
            case 3:
                l = new Level3(img,imgArray[desc],chkB);
                break;
            default:
                l = new Level0(img,imgArray[desc],chkB);
        }
        return l;
    }
}
